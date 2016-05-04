package com.starnet.jn_wr.planup.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

public class SystemUtil {

	@SuppressLint("NewApi")
	public static Bitmap fastblur(Context context, Bitmap sentBitmap, int radius) {

		if (Build.VERSION.SDK_INT > 16) {
			Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

			final RenderScript rs = RenderScript.create(context);
			final Allocation input = Allocation.createFromBitmap(rs, sentBitmap, Allocation.MipmapControl.MIPMAP_NONE,
					Allocation.USAGE_SCRIPT);
			final Allocation output = Allocation.createTyped(rs, input.getType());
			final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
			script.setRadius(radius /* e.g. 3.f */);
			script.setInput(input);
			script.forEach(output);
			output.copyTo(bitmap);
			return bitmap;
		}

		// Stack Blur v1.0 from
		// http://www.quasimondo.com/StackBlurForCanvas/StackBlurDemo.html
		//
		// Java Author: Mario Klingemann <mario at quasimondo.com>
		// http://incubator.quasimondo.com
		// created Feburary 29, 2004
		// Android port : Yahel Bouaziz <yahel at kayenko.com>
		// http://www.kayenko.com
		// ported april 5th, 2012

		// This is a compromise between Gaussian Blur and Box blur
		// It creates much better looking blurs than Box Blur, but is
		// 7x faster than my Gaussian Blur implementation.
		//
		// I called it Stack Blur because this describes best how this
		// filter works internally: it creates a kind of moving stack
		// of colors whilst scanning through the image. Thereby it
		// just has to add one new block of color to the right side
		// of the stack and remove the leftmost color. The remaining
		// colors on the topmost layer of the stack are either added on
		// or reduced by one, depending on if they are on the right or
		// on the left side of the stack.
		//
		// If you are using this algorithm in your code please add
		// the following line:
		//
		// Stack Blur Algorithm by Mario Klingemann <mario@quasimondo.com>

		Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

		if (radius < 1) {
			return (null);
		}

		int w = bitmap.getWidth();
		int h = bitmap.getHeight();

		int[] pix = new int[w * h];
		Log.e("pix", w + " " + h + " " + pix.length);
		bitmap.getPixels(pix, 0, w, 0, 0, w, h);

		int wm = w - 1;
		int hm = h - 1;
		int wh = w * h;
		int div = radius + radius + 1;

		int r[] = new int[wh];
		int g[] = new int[wh];
		int b[] = new int[wh];
		int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
		int vmin[] = new int[Math.max(w, h)];

		int divsum = (div + 1) >> 1;
		divsum *= divsum;
		int dv[] = new int[256 * divsum];
		for (i = 0; i < 256 * divsum; i++) {
			dv[i] = (i / divsum);
		}

		yw = yi = 0;

		int[][] stack = new int[div][3];
		int stackpointer;
		int stackstart;
		int[] sir;
		int rbs;
		int r1 = radius + 1;
		int routsum, goutsum, boutsum;
		int rinsum, ginsum, binsum;

		for (y = 0; y < h; y++) {
			rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
			for (i = -radius; i <= radius; i++) {
				p = pix[yi + Math.min(wm, Math.max(i, 0))];
				sir = stack[i + radius];
				sir[0] = (p & 0xff0000) >> 16;
				sir[1] = (p & 0x00ff00) >> 8;
				sir[2] = (p & 0x0000ff);
				rbs = r1 - Math.abs(i);
				rsum += sir[0] * rbs;
				gsum += sir[1] * rbs;
				bsum += sir[2] * rbs;
				if (i > 0) {
					rinsum += sir[0];
					ginsum += sir[1];
					binsum += sir[2];
				} else {
					routsum += sir[0];
					goutsum += sir[1];
					boutsum += sir[2];
				}
			}
			stackpointer = radius;

			for (x = 0; x < w; x++) {

				r[yi] = dv[rsum];
				g[yi] = dv[gsum];
				b[yi] = dv[bsum];

				rsum -= routsum;
				gsum -= goutsum;
				bsum -= boutsum;

				stackstart = stackpointer - radius + div;
				sir = stack[stackstart % div];

				routsum -= sir[0];
				goutsum -= sir[1];
				boutsum -= sir[2];

				if (y == 0) {
					vmin[x] = Math.min(x + radius + 1, wm);
				}
				p = pix[yw + vmin[x]];

				sir[0] = (p & 0xff0000) >> 16;
				sir[1] = (p & 0x00ff00) >> 8;
				sir[2] = (p & 0x0000ff);

				rinsum += sir[0];
				ginsum += sir[1];
				binsum += sir[2];

				rsum += rinsum;
				gsum += ginsum;
				bsum += binsum;

				stackpointer = (stackpointer + 1) % div;
				sir = stack[(stackpointer) % div];

				routsum += sir[0];
				goutsum += sir[1];
				boutsum += sir[2];

				rinsum -= sir[0];
				ginsum -= sir[1];
				binsum -= sir[2];

				yi++;
			}
			yw += w;
		}
		for (x = 0; x < w; x++) {
			rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
			yp = -radius * w;
			for (i = -radius; i <= radius; i++) {
				yi = Math.max(0, yp) + x;

				sir = stack[i + radius];

				sir[0] = r[yi];
				sir[1] = g[yi];
				sir[2] = b[yi];

				rbs = r1 - Math.abs(i);

				rsum += r[yi] * rbs;
				gsum += g[yi] * rbs;
				bsum += b[yi] * rbs;

				if (i > 0) {
					rinsum += sir[0];
					ginsum += sir[1];
					binsum += sir[2];
				} else {
					routsum += sir[0];
					goutsum += sir[1];
					boutsum += sir[2];
				}

				if (i < hm) {
					yp += w;
				}
			}
			yi = x;
			stackpointer = radius;
			for (y = 0; y < h; y++) {
				// Preserve alpha channel: ( 0xff000000 & pix[yi] )
				pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

				rsum -= routsum;
				gsum -= goutsum;
				bsum -= boutsum;

				stackstart = stackpointer - radius + div;
				sir = stack[stackstart % div];

				routsum -= sir[0];
				goutsum -= sir[1];
				boutsum -= sir[2];

				if (x == 0) {
					vmin[y] = Math.min(y + r1, hm) * w;
				}
				p = x + vmin[y];

				sir[0] = r[p];
				sir[1] = g[p];
				sir[2] = b[p];

				rinsum += sir[0];
				ginsum += sir[1];
				binsum += sir[2];

				rsum += rinsum;
				gsum += ginsum;
				bsum += binsum;

				stackpointer = (stackpointer + 1) % div;
				sir = stack[stackpointer];

				routsum += sir[0];
				goutsum += sir[1];
				boutsum += sir[2];

				rinsum -= sir[0];
				ginsum -= sir[1];
				binsum -= sir[2];

				yi += w;
			}
		}

		Log.e("pix", w + " " + h + " " + pix.length);
		bitmap.setPixels(pix, 0, w, 0, 0, w, h);
		return (bitmap);
	}

	/**
	 * 复制到剪切板
	 * @param context
	 * @param content
	 */
	public static void copyToClipBord(Context context,String content){
		ClipboardManager clipboardManager = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
		clipboardManager.setPrimaryClip(ClipData.newPlainText(null, content));

		if (clipboardManager.hasPrimaryClip()){
			clipboardManager.getPrimaryClip().getItemAt(0).getText();
		}
	}

	/**
	 * 判断APP是否在前台
	 * @param context
	 * @return
	 */
	public static boolean isAppOnForeground(Context context) {
		// Returns a list of application processes that are running on the
		// device

		ActivityManager activityManager = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
		String packageName = context.getApplicationContext().getPackageName();

		List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
				.getRunningAppProcesses();
		if (appProcesses == null)
			return false;

		for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
			// The name of the process that this object is associated with.
			if (appProcess.processName.equals(packageName)
					&& appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 判断网络连接是否正常
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 获取系统版本号
	 * @return
	 */
	public static int getSDKVersionNumber() {  
	    int sdkVersion=10;  
	    try {  
	        sdkVersion = Integer.valueOf(Build.VERSION.SDK);
	    } catch (NumberFormatException e) {
	        sdkVersion = 0;  
	    }  
	    return sdkVersion;  
	}
	
	/**
	 * 获取APP信息,并保存为全局
	 * 
	 * @param mactivity
	 * @return
	 */
	private static String APPINFO = "";

//	public static String getAppinfo(Context activity, Boolean isFormat) {
//		if (StringUtil.isEmpty(APPINFO)) {
//
//			APPINFO = "<AppInfo>" + "<PlatformType>" + ConstantPool.getInstance().PLATFORM_TYPE
//					+ "</PlatformType>" + "<PlatformVer>" + android.os.Build.VERSION.SDK
//					+ "</PlatformVer>" + "<AppVer>" + SystemUtil.getSoftwareVersion(activity)
//					+ "</AppVer>" + "<OS>" + ConstantPool.getInstance().OS + "</OS>" + "<DeviceID>"
//					+ DeviceIDUtil.getCombineUniqueDeviceId(activity) + "</DeviceID>"
//					+ "<DeviceType>" + android.os.Build.MODEL + "</DeviceType>" + "<Resolution>"
//					+ SystemUtil.getScreenWidth(activity) + "*"
//					+ SystemUtil.getScreenHeight(activity) + "</Resolution>" + "</AppInfo>";
//
//		}
//		return isFormat ? StringUtil.formatXml2Html(APPINFO) : APPINFO;
//	}

	/**
	 * 获取屏幕宽度（分辨率）(get the width of screen/resolution)
	 * 
	 * @param context
	 *            上下文
	 * @return 屏幕宽度（分辨率）(width of screen/resolution)
	 */
	public static int getScreenWidth(Context context) {
		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay();
		return display.getWidth();
	}

	/**
	 * 获取屏幕高度（分辨率）(get the height of screen/resolution)
	 * 
	 * @param context
	 *            上下文
	 * @return 屏幕高度（分辨率）(height of screen/resolution)
	 */
	public static int getScreenHeight(Context context) {
		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay();
		return display.getHeight();
	}

	/**
	 * 获取软件版本号
	 * 
	 * @return 软件版本号
	 */
	public static String getSoftwareVersion(Context mcontext) {
		String pkName = mcontext.getPackageName(); // 获取当前Context所在包名
		String versionName = "";
		try {
			versionName = mcontext.getPackageManager().getPackageInfo(pkName, 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionName;

	}

	/**
	 * 获取系统当前可用内存大小
	 * 
	 * @param mcontext
	 *            （引用上下文）
	 * @return 当前可用内存大小
	 */
	public static String getAvailMemory(Activity mcontext) {
		ActivityManager am = (ActivityManager) mcontext.getSystemService(Context.ACTIVITY_SERVICE);
		MemoryInfo mi = new MemoryInfo();
		am.getMemoryInfo(mi); // 当前系统的可用内存
		return Formatter.formatFileSize(mcontext.getBaseContext(), mi.availMem); // 将获取的内存大小规格化
	}

	/**
	 * 获取系统SD卡总空间大小和可用空间大小
	 * 
	 * @param mcontext
	 *            （引用上下文）
	 * @return SD卡总空间大小和可用空间大小
	 */
	public static String[] getSDCardMemory(Activity mcontext) {
		String[] sdCardInfo = new String[2];
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			File sdcardDir = Environment.getExternalStorageDirectory();
			StatFs sf = new StatFs(sdcardDir.getPath());
			long bSize = sf.getBlockSize();
			long bCount = sf.getBlockCount();
			long availBlocks = sf.getAvailableBlocks();

			sdCardInfo[0] = Formatter.formatFileSize(mcontext.getBaseContext(), bSize * bCount); // 总大小
			sdCardInfo[1] = Formatter
					.formatFileSize(mcontext.getBaseContext(), bSize * availBlocks); // 可用大小
		}

		return sdCardInfo;
	}

	/**
	 * 根据手机的分辨率从 dip的单位 转成为 px(像素)，返回float
	 * 
	 * @param context
	 *            （引用上下文）
	 * @param dipValue
	 * @return pxValue
	 */
	public static float dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return  (dipValue * scale + 0.5f);
	}
	/**
	 * 根据手机的分辨率从 dip的单位 转成为 px(像素)，返回整数
	 *
	 * @param context
	 *            （引用上下文）
	 * @param dipValue
	 * @return pxValue
	 */
	public static int dip2px2(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dip
	 * 
	 * @param context
	 *            （引用上下文）
	 * @param pxValue
	 * @return dipValue
	 */
	public static float px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return  (pxValue / scale + 0.5f);
	}

	public static int px2dip2(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
     * 获取屏幕尺寸与密度.
     *
     * @param context the context
     * @return mDisplayMetrics
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        Resources mResources;
        if (context == null){
            mResources = Resources.getSystem();
            
        }else{
            mResources = context.getResources();
        }
        DisplayMetrics mDisplayMetrics = mResources.getDisplayMetrics();
        return mDisplayMetrics;
    }
    
//	/**
//     * 描述：dip转换为px.
//     *
//     * @param context the context
//     * @param dipValue the dip value
//     * @return px值
//     */
//    public static float dip2px(Context context, float dipValue) {
//        DisplayMetrics mDisplayMetrics = getDisplayMetrics(context);
//        return applyDimension(TypedValue.COMPLEX_UNIT_DIP,dipValue,mDisplayMetrics);
//    }
//
//    /**
//     * 描述：px转换为dip.
//     *
//     * @param context the context
//     * @param pxValue the px value
//     * @return dip值
//     */
//    public static float px2dip(Context context, float pxValue) {
//        DisplayMetrics mDisplayMetrics = getDisplayMetrics(context);
//        return pxValue / mDisplayMetrics.density;
//    }
    
    /**
     * 描述：sp转换为px.
     *
     * @param context the context
     * @param spValue the sp value
     * @return sp值
     */
    public static float sp2px(Context context, float spValue) {
        DisplayMetrics mDisplayMetrics = getDisplayMetrics(context);
        return applyDimension(TypedValue.COMPLEX_UNIT_SP,spValue,mDisplayMetrics);
    }
    
    /**
     * 描述：px转换为sp.
     *
     * @param context the context
     * @param spValue the sp value
     * @return sp值
     */
    public static float px2sp(Context context, float pxValue) {
        DisplayMetrics mDisplayMetrics = getDisplayMetrics(context);
        return pxValue / mDisplayMetrics.scaledDensity;
    }

	/**
	 * 描述：根据屏幕大小缩放.
	 *
	 * @param context the context
	 * @return the int
	 */
	public static int scale(Context context, float value) {
		DisplayMetrics mDisplayMetrics = getDisplayMetrics(context);
		return scale(mDisplayMetrics.widthPixels,
				mDisplayMetrics.heightPixels, value);
	}
	
	/**  UI设计的基准宽度. */
	public static final int uiWidth = 720;
	
	/**  UI设计的基准高度. */
	public static final int uiHeight = 1080;
	
	/**
	 * 描述：根据屏幕大小缩放.
	 *
	 * @param displayWidth the display width
	 * @param displayHeight the display height
	 * @param pxValue the px value
	 * @return the int
	 */
	public static int scale(int displayWidth, int displayHeight, float pxValue) {
		float scale = 1;
		try {
			float scaleWidth = (float) displayWidth / uiWidth;
			float scaleHeight = (float) displayHeight / uiHeight;
			scale = Math.min(scaleWidth, scaleHeight);
		} catch (Exception e) {
		}
		return Math.round(pxValue * scale + 0.5f);
	}
	
	/**
	 * TypedValue官方源码中的算法，任意单位转换为PX单位
	 * @param unit  TypedValue.COMPLEX_UNIT_DIP
	 * @param value 对应单位的值
	 * @param metrics 密度
	 * @return px值
	 */
    public static float applyDimension(int unit, float value,
                                       DisplayMetrics metrics){
        switch (unit) {
        case TypedValue.COMPLEX_UNIT_PX:
            return value;
        case TypedValue.COMPLEX_UNIT_DIP:
            return value * metrics.density;
        case TypedValue.COMPLEX_UNIT_SP:
            return value * metrics.scaledDensity;
        case TypedValue.COMPLEX_UNIT_PT:
            return value * metrics.xdpi * (1.0f/72);
        case TypedValue.COMPLEX_UNIT_IN:
            return value * metrics.xdpi;
        case TypedValue.COMPLEX_UNIT_MM:
            return value * metrics.xdpi * (1.0f/25.4f);
        }
        return 0;
    }

	/**
	 * 判断当前设备是手机还是平板
	 * 
	 * @param context
	 * @return true：平板 false：手机
	 */
	public static boolean isTabletDevice(Context context) {
		if (Build.VERSION.SDK_INT >= 11) { // honeycomb
			// test screen size, use reflection because isLayoutSizeAtLeast is
			// only available since 11
			Configuration con = context.getResources().getConfiguration();
			try {
				Method mIsLayoutSizeAtLeast = con.getClass().getMethod("isLayoutSizeAtLeast",
						int.class);
				Boolean r = (Boolean) mIsLayoutSizeAtLeast.invoke(con, 0x00000004); // Configuration.SCREENLAYOUT_SIZE_XLARGE
				return r;
			} catch (Exception x) {
				x.printStackTrace();
				return false;
			}
		}
		return false;
	}

	// 监听两次返回键
	public static Boolean twoBackPress(Activity activity, long mExitTime) {
		Boolean isExit = true;
		if ((System.currentTimeMillis() - mExitTime) > 2000) {
			Toast.makeText(activity, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			isExit = false;
		} else {

			SystemUtil.exit(activity);
		}
		return isExit;
	}

	// 结束当前activity
	public static void finish(Activity activity) {
		activity.finish();
		System.gc();
	}

	// 退出app
	public static void exit(Activity activity) {
		activity.finish();
		android.os.Process.killProcess(android.os.Process.myPid());// 退出时杀死该进程
		System.exit(0);
		System.gc();
	}

	/**
	 * 判断 SD卡是否存在
	 * 
	 * @return
	 */
	public static boolean isSDCardExist() {
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
		return sdCardExist;
	}

    //隐藏虚拟键盘
    public static void hideKeyboard(View v,Context context) {

        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
    }

    //显示虚拟键盘
    public static void showKeyboard(View v,Context context){

        InputMethodManager imm = (InputMethodManager) v.getContext( ).getSystemService( context.INPUT_METHOD_SERVICE );
        imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
    }

	/**
	 * 获取渠道名
	 * @param ctx 此处习惯性的设置为activity，实际上context就可以
	 * @return 如果没有获取成功，那么返回值为空
	 */
	public static String getChannelName(Activity ctx) {
		if (ctx == null) {
			return null;
		}
		String channelName = null;
		try {
			PackageManager packageManager = ctx.getPackageManager();
			if (packageManager != null) {
				//注意此处为ApplicationInfo 而不是 ActivityInfo,因为友盟设置的meta-data是在application标签中，而不是某activity标签中，所以用ApplicationInfo
				ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
				if (applicationInfo != null) {
					if (applicationInfo.metaData != null) {
						channelName = applicationInfo.metaData.getString("");
					}
				}

			}
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return channelName;
	}

	/**
	 * 获取application中指定的meta-data
	 * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
	 */
	public static String getAppMetaData(Context ctx, String key) {
		if (ctx == null || StringUtil.isEmpty(key)) {
			return null;
		}
		String resultData = null;
		try {
			PackageManager packageManager = ctx.getPackageManager();
			if (packageManager != null) {
				ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
				if (applicationInfo != null) {
					if (applicationInfo.metaData != null) {
						resultData = applicationInfo.metaData.getString(key);
					}
				}

			}
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}

		return resultData;
	}

}
