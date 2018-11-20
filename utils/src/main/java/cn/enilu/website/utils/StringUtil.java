package cn.enilu.website.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created  on  2018/7/11 0011
 * StringUtil
 *
 * @author enilu
 */
public class StringUtil {

    private static final AtomicLong ORDER_SEQ = new AtomicLong(1);
    private static final Pattern SELECT_PATTERN_COMPILE = Pattern.compile("(?si)^.*?select(.*?)from");

    /**
     * 是否为空字符
     */
    public static boolean isEmpty(Object str) {

        if (str == null || str.toString().trim().length() == 0) {
            return true;
        }
        if ("null".equalsIgnoreCase(str.toString()) || "undefined".equalsIgnoreCase(str.toString())) {
            return true;
        }
        return false;
    }

    /**
     * 是否为非空字符
     */
    public static boolean isNotEmpty(String str) {
        return (!isEmpty(str));
    }

    /**
     * 判断是否为null或空字符
     */
    public static boolean isNullOrEmpty(Object o) {
        if (o == null) {
            return true;
        }
        if (String.valueOf(o).replace((char) 12288, ' ').trim().length() == 0) {
            return true;
        }
        if ("null".equals(o)) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否不为null或非空字符
     */
    public static boolean isNotNullOrEmpty(Object o) {
        return !isNullOrEmpty(o);
    }

    /**
     * 判断字符串的内容是否是数字
     */
    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; ++i) {
            if (!(Character.isDigit(str.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 根据Unicode编码完美的判断中文汉字和符号
     * @param c
     * @return
     */
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    /**
     * 判断中文汉字和符号
     * @param strName
     * @return
     */
    public static boolean haveChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断中文汉字和符号
     * @param strName
     * @return
     */
    public static boolean allChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (!isChinese(c)) {
                return false;
            }
        }
        return true;
    }

    public static String trimAll(String o) {
        if (isNotNullOrEmpty(o)) {
            o = o.replaceAll(" ", "");
            o = o.replaceAll(",", "");
        }
        return trim(o);
    }

    public static String trim(Object o) {
        return trim(o, "");
    }

    public static String trim(Object o, String defaultString) {
        if (o == null) {
            return defaultString;
        }
        return trim(o.toString());
    }

    public static String trim(CharSequence cs) {
        if (null == cs) {
            return null;
        }
        int length = cs.length();
        if (length == 0) {
            return cs.toString();
        }
        int l = 0;
        int last = length - 1;
        int r = last;
        for (; l < length; l++) {
            if (!Character.isWhitespace(cs.charAt(l))) {
                break;
            }
        }
        for (; r > l; r--) {
            if (!Character.isWhitespace(cs.charAt(r))) {
                break;
            }
        }
        if (l > r) {
            return "";
        } else if (l == 0 && r == last) {
            return cs.toString();
        }
        return cs.subSequence(l, r + 1).toString();
    }

    /**
     * 简单判断字符串是否符合json格式
     */
    public static boolean isJson(String str) {
        if (isNotEmpty(str)) {
            if (str.trim().startsWith("{") || str.trim().startsWith("[{")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 中文转码,因为将CharacterEncodingFilter纳入了Nutz来管理,所以造成包装请求参数失效
     */
    public static String toGBK(String str) {
        String result = "";
        try {
            result = new String(str.getBytes("iso8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 全角转半角
     */
    public static String toDBC(String value) {
        if (value == null) {
            return "";
        }
        // 全角转半角
        char[] c = value.toCharArray();
        for (int index = 0; index < c.length; index++) {
            if (c[index] == '　') {
                c[index] = ' ';
            } else if ((c[index] > 65280) && (c[index] < 65375)) {
                c[index] = (char) (c[index] - 65248);
            }
        }
        return String.valueOf(c).replaceAll("[\\s\\t\\u00A0]+$", "").replaceAll("^[\\s\\t\\u00A0]+", "");
    }

    /**
     * 根据文件扩展名判断文件类型,是否为图片文件
     */
    public static boolean isPic(String fileName) {
        return fileName.matches("^[\\s\\S]+\\.+(gif|jpg|jpeg|png|bmp)$");
    }
    static Pattern ipPattern = Pattern.compile("((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)");
    public static boolean isIp(String ip) {

        return ipPattern.matcher(ip).find();
    }

    public static String getMethod(Field field) {
        return (field.getType() == boolean.class ? "is" : "get") + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
    }

    public static String getMethod(String field) {
        return "get" + field.substring(0, 1).toUpperCase() + field.substring(1);
    }

    /**
     * 根据文件扩展名判断文件类型,是否为图片文件
     */
    public static boolean validateImgFile(String suffixName) {
        String[] imgFileType = {"jpg", "jpeg", "gif", "bmp", "png"};
        return inArray(imgFileType, suffixName);
    }

    /**
     * 判断元素是否存在于数组中
     */
    public static boolean inArray(String[] arry, String elem) {
        if (arry != null && arry.length > 0 && elem != null && elem.trim().length() > 0) {
            for (String arr : arry) {
                if (elem.trim().equalsIgnoreCase(arr)) {
                    return true;
                }
            }
        }
        return false;
    }



    public static boolean validIP(String rule, String ip) {
        if ("1.1.1.1".equals(ip)) {
            return false;
        }
        if ("0.0.0.0".equals(ip)) {
            return true;
        }
        if (rule != null && !rule.contains(ip)) {
            return false;
        }

        return true;
    }

    public static String cleanString(String str) {
        return str.replaceAll("\\t|\\s", "");
    }

    /**
     * 组织机构代码清理
     */
    public static String orgCodeFormat(String orgCode) {
        if (isEmpty(orgCode)) {
            return "";
        }
        orgCode = orgCode.replaceAll("[^\\da-zA-Z]", "");
        return orgCode.length() == 9 ? orgCode : "";
    }


    public static String getResUrl(String url) {
        return url.replaceAll("/v[0-9]+", "");
    }

    static Pattern urlPattern = Pattern.compile("/.*?/.*?/.*?/(v[0-9]+)");
    public static String getUrlVersion(String url) {

        Matcher m = urlPattern.matcher(url);
        if (m.find()) {
            return m.group(1);
        }

        return null;
    }

    public static String cutLeft(String str, int width) {
        if (str == null) {
            return "";
        }

        int len = str.length();
        if (len <= width) {
            return str;
        }

        return str.substring(0, width);
    }

    public static String firstIpInSection(String ip) {
        if (isEmpty(ip)) {
            return null;
        }
        return ip.substring(0, ip.lastIndexOf(".")) + ".1";
    }

    /**
     * 过滤特殊字符
     *
     * @param nickname
     * @return
     */
    public static String filterSpecialChar(String nickname) {
        if (nickname == null) {
            return null;
        }
        nickname = nickname.replaceAll("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", "");
        return nickname;
    }

    /**
     * 判断指定字符串是否是中文名，匹配规则：包含汉字或者汉字中间可以有·
     * @param userName
     * @return
     */
    public static boolean isChineseName(String userName) {
        return isNotEmpty(userName) && userName.matches("[\\u4e00-\\u9fa5]+·?[\\u4e00-\\u9fa5]+");
    }
    public static boolean equals(String s1, String s2) {
        return s1 == null ? s2 == null : s1.equals(s2);
    }

    public static  boolean equalsIgnoreCase(String s1,String s2){
        return s1 == null ? s2 == null : s1.equalsIgnoreCase(s2);
    }

    public static String sNull(Object obj) {
        return sNull(obj,"");
    }
    public static String sNull(Object obj,String def) {
        return obj != null ? obj.toString() : def;
    }

    public static String join(String s, Object[] args) {
        StringBuilder sb = new StringBuilder();
        if (null == args || 0 == args.length) {
            return sb.toString();
        }

        sb.append(args[0]);
        for (int i = 1; i < args.length; i++) {
            sb.append(s).append(args[i]);
        }

        return sb.toString();
    }

    public static boolean isNotBlank(String str) {
        return isNotEmpty(str);
    }

    /**
     * 首字母小写
     * @param str
     * @return
     */
    public static String captureName(String str) {
        str = str.substring(0, 1).toLowerCase() + str.substring(1);
        return  str;

    }
}
