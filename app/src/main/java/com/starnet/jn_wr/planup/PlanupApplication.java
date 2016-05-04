package com.starnet.jn_wr.planup;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import java.util.ArrayList;
import java.util.List;

public class PlanupApplication extends Application {

    private static PlanupApplication instanse;
    private static List<Activity> activityList = new ArrayList<Activity>();

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Fresco.initialize(this, createFrescoConfig());
        instanse = this;
        mContext = getApplicationContext();

    }

    public static PlanupApplication getInstance() {

        if (instanse == null) {
            instanse = new PlanupApplication();
        }
        return instanse;
    }

    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        activityList.clear();

        System.exit(0);

    }

    public void exitToLogin() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        activityList.clear();
    }

    public Context mContext;

    private ImagePipelineConfig createFrescoConfig() {
        DiskCacheConfig mainDiskCacheConfig = DiskCacheConfig.newBuilder(this)
                .setBaseDirectoryPath(getExternalCacheDir())
                .setBaseDirectoryName("fresco cache")
                .setMaxCacheSize(100 * 1024 * 1024)
                .setMaxCacheSizeOnLowDiskSpace(10 * 1024 * 1024)
                .setMaxCacheSizeOnVeryLowDiskSpace(5 * 1024 * 1024)
                .setVersion(1)
                .build();
        return ImagePipelineConfig.newBuilder(this)
//                .setBitmapMemoryCacheParamsSupplier(bitmapCacheParamsSupplier)
//                .setCacheKeyFactory(cacheKeyFactory)
//                .setEncodedMemoryCacheParamsSupplier(encodedCacheParamsSupplier)
//                .setExecutorSupplier(executorSupplier)
//                .setImageCacheStatsTracker(imageCacheStatsTracker)
                .setMainDiskCacheConfig(mainDiskCacheConfig)
//                .setMemoryTrimmableRegistry(memoryTrimmableRegistry)
//                .setNetworkFetchProducer(networkFetchProducer)
//                .setPoolFactory(poolFactory)
//                .setProgressiveJpegConfig(progressiveJpegConfig)
//                .setRequestListeners(requestListeners)
//                .setSmallImageDiskCacheConfig(smallImageDiskCacheConfig)
                .build();
    }
}
