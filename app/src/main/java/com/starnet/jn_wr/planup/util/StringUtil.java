package com.starnet.jn_wr.planup.util;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理类
 *
 * @author 程祝�?
 * @time 整理时间�?012-8-13 上午11:39:38
 */
public class StringUtil {

    /**
     * xml解析
     * @param content
     * @return
     */
    public static Map<String, String> decodeXml(String content) {

        try {
            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {

                String nodeName = parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:

                        if ("xml".equals(nodeName) == false) {
                            //实例化student对象
                            xml.put(nodeName, parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }

            return xml;
        } catch (Exception e) {
            Log.e("orion", e.toString());
        }
        return null;

    }

    /**
     * 截取字符串前60个字符，如果不足60个，原样返回
     */

    public static String subString60(String content) {

        if (content.length() > 60) {
            return content.substring(0, 59);
        }

        return content;
    }

    /**
     * 获取XML字符串中指定标签的�?
     *
     * @param xml   XML字符�?
     * @param label 标签�?
     * @return 指定标签的�?
     */
    public static String getXmlValueByLabel(String xml, String label) {
        String content = null;
        if (xml == null) {
            return content;
        }
        int start = xml.indexOf("<" + label + ">");
        int end = xml.indexOf("</" + label + ">");
        if (start != -1 && end != -1) {
            content = xml.substring(start + label.length() + 2, end);
        }
        return content;
    }

    public static final boolean isNoSense(String str) {
        if (str == null || str.trim().equals("") || str.trim().equals("null") || str.trim().equals("0")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断字符串是否为�?
     *
     * @param str 任意字符�?
     * @return true表示为空或�?NULL，false表示不为�?
     */
    public static final boolean isEmpty(String str) {
        if (str == null || str.trim().equals("") || str.length() == 0 || str.trim().equals("null")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 字符串转html格式
     *
     * @param--- （字符串�? 用例：htmEncode(".<>!@#$%^&");
     * @author 程祝�?
     */
    public static String htmEncode(String s) {
        StringBuffer stringbuffer = new StringBuffer();
        int j = s.length();
        for (int i = 0; i < j; i++) {
            char c = s.charAt(i);
            switch (c) {
                case 60:
                    stringbuffer.append("&lt;");
                    break;
                case 62:
                    stringbuffer.append("&gt;");
                    break;
                case 38:
                    stringbuffer.append("&amp;");
                    break;
                case 34:
                    stringbuffer.append("&quot;");
                    break;
                case 169:
                    stringbuffer.append("&copy;");
                    break;
                case 174:
                    stringbuffer.append("&reg;");
                    break;
                case 165:
                    stringbuffer.append("&yen;");
                    break;
                case 8364:
                    stringbuffer.append("&euro;");
                    break;
                case 8482:
                    stringbuffer.append("&#153;");
                    break;
                case 13:
                    if (i < j - 1 && s.charAt(i + 1) == 10) {
                        stringbuffer.append("<br>");
                        i++;
                    }
                    break;
                case 32:
                    if (i < j - 1 && s.charAt(i + 1) == ' ') {
                        stringbuffer.append("&nbsp;");
                        i++;
                        break;
                    }
                default:
                    stringbuffer.append(c);
                    break;
            }
        }
        return new String(stringbuffer.toString());
    }

    /**
     * 正则表达式判断IP地址是否合法
     *
     * @param ipStr --- （IP地址�? 用例：ipAddCheck("10.0.0.0"); 注： 返回true为验证合法，反之�?
     * @author 程祝�?
     */
    public static boolean ipAddCheck(String ipStr) {
        Pattern pattern = Pattern
                .compile("^((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])\\.){3}(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])(:\\d{4})$");
        return pattern.matcher(ipStr).matches();
    }

    /**
     * �?lt;转化�?,�?gt;转化�?
     *
     * @param xml 带格式化的字符串
     * @return 格式化后的字符串
     */
    public static String formatHtml2Xml(String xml) {
        if (isEmpty(xml)) {
            return xml;
        }
        xml = xml.replaceAll("&lt;", "<").replace("&gt;", ">");
        return xml;
    }

    /**
     * �?转化�?lt;,�?转化�?gt;
     *
     * @param xml 带格式化的字符串
     * @return 格式化后的字符串
     */
    public static String formatXml2Html(String xml) {
        if (isEmpty(xml)) {
            return xml;
        }
        xml = xml.replaceAll("<", "&lt;").replace(">", "&gt;");
        return xml;
    }

    /**
     * 邮箱验证
     *
     * @param strEmail 邮箱名（字符串）
     * @return false无效的邮箱名 true有效的邮箱名
     */
    public static boolean isEmail(String strEmail) {
        if (isEmpty(strEmail)) {
            return false;
        }
        Pattern pattern = Pattern
                .compile("^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
        Matcher mat = pattern.matcher(strEmail);
        return mat.find();
    }

    /**
     * 验证密码
     */
    public static boolean isPswOk(String psw) {
        if (psw.length() >= 6 && psw.length() <= 20) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (StringUtil.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }

    /**
     * 判断字符串中是否含有数字
     *
     * @param str 待检测字符串
     * @return false不含数字 true含有数字
     */
    public static boolean isContainDigit(String str) {
        boolean isDigit = false;
        for (int i = 0; i < str.length(); i++) { // 循环遍历字符�?
            if (Character.isDigit(str.charAt(i))) {
                isDigit = true;
                break;
            }
        }
        return isDigit;
    }

    /**
     * 读取assets目录下txt文件
     *
     * @param fileName 文件�? context 上下�?
     * @return txt文本内容
     */
    public static String getFromAssets(String fileName, Context context) {
        String txtStr = "";
        try {
            InputStreamReader inputReader = new InputStreamReader(context
                    .getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            while ((line = bufReader.readLine()) != null){
                txtStr += line;
            }
            bufReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return txtStr;
    }

    /**
     * 删除重复的ArrayList
     *
     * @param arlList
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static ArrayList<String> removeDuplicateWithOrder(ArrayList arlList) {
        Set set = new HashSet();
        List newList = new ArrayList();
        for (Iterator iter = arlList.iterator(); iter.hasNext(); ) {
            Object element = iter.next();
            if (set.add(element))
                newList.add(element);
        }
        arlList.clear();
        arlList.addAll(newList);
        return arlList;
    }

    /**
     * 截取字符�?
     */
    public static String subString(String str, int start, int end) {
        if (null == str || end < 0 || start < 0) {
            return "";
        }
        return str.length() > end ? str.substring(start, end) : str;
    }

    /**
     * 截取字符�?
     */
    public static String subString(String str, int start) {
        if (null == str || start < 0) {
            return "";
        }
        return str.length() > start ? str.substring(start) : str;
    }

    /**
     * 截取str中某段位置，起点为str1，终点为str2
     */
    public static String getsubString(String str, String str1, String str2) {
        int start = str.indexOf(str1);
        int end = str.indexOf(str2);
        return str.substring(start + str1.length(), end);
    }

    /**
     * 去除数组中重复的记录
     */
    public static String[] getArrayDistinct(String[] a) {
        // array_unique
        List<String> list = new LinkedList<String>();
        for (int i = 0; i < a.length; i++) {
            if (!list.contains(a[i])) {
                list.add(a[i]);
            }
        }
        return (String[]) list.toArray(new String[list.size()]);
    }

    /**
     * 判断字符串中是否存在特殊字符
     */
    public static boolean isSpeSign(String str) {
        if (isEmpty(str)) {
            return false;
        }

        Pattern pattern = Pattern
                .compile("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#�?…�?&*（）—�?+|{}【�?‘；：�?“�?。，、？]");
        Matcher mat = pattern.matcher(str);
        return mat.find();
    }

    /**
     * 聚好产品中应用，判断字符串中是否存在特殊字符�?
     */
    public static boolean isContainSpeSign(String str) {
        if (isEmpty(str)) {
            return false;
        }

        Pattern pattern = Pattern.compile("[&*<>]");
        Matcher mat = pattern.matcher(str);
        return mat.find();
    }

    /**
     * 判断是否是汉字、空格、标点
     *
     * @param str
     * @return
     */
    public static boolean isContainCH(String str) {
        if (isEmpty(str)) {
            return false;
        }
        if (str.equalsIgnoreCase(" ")) {
            return true;
        }

        Pattern p;
        Matcher m;
        p = Pattern.compile("[\u4e00-\u9fa5]");
        m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        p = Pattern.compile("\\p{Punct}");
        m = p.matcher(str);
        if (m.find()) {
            return true;
        }

        return false;
    }

    /**
     * 描述：获取字符串的长度.
     *
     * @param str 指定的字符串
     * @return 字符串的长度（中文字符计2个）
     */
    public static int strLength(String str) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        if (!isEmpty(str)) {
            //获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
            for (int i = 0; i < str.length(); i++) {
                //获取一个字符
                String temp = str.substring(i, i + 1);
                //判断是否为中文字符
                if (temp.matches(chinese)) {
                    //中文字符长度为2
                    valueLength += 2;
                } else {
                    //其他字符长度为1
                    valueLength += 1;
                }
            }
        }
        return valueLength;
    }

    /**
     * 去除字符串中的空格、回车、换行符、制表符
     * @param str
     * @return
     */
    /*public static String replaceBlank(String str) {
        String dest = "";
		if (str!=null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}*/

    /**
     * 去掉html中的格式
     */
    public static String replaceHtml(String str) {
        String dest = "  ";
        if (str != null) {
            Pattern p = Pattern.compile("<[^>]*>");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }

        return dest;
    }

    /**
     * 去除bom报头
     */
    public static String formatString(String s) {
        if (s != null) {
            s = s.replaceAll("\ufeff", "");
        }
        return s;
    }

    /**
     * 关键字高亮显示
     *
     * @param text
     * @param target
     * @return
     */
    public static SpannableStringBuilder highlight(String text, String target) {

        if (text.indexOf(target) > 60) {
            text = text.substring(50, text.length());
            text = "..." + text + "...";
        }

        SpannableStringBuilder spannable = new SpannableStringBuilder(text);
        CharacterStyle span = null;

        Pattern p = Pattern.compile(target);
        Matcher m = p.matcher(text);

        while (m.find()) {
            span = new ForegroundColorSpan(Color.parseColor("#f65131"));// 需要重复！
            spannable.setSpan(span, m.start(), m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return spannable;
    }

}