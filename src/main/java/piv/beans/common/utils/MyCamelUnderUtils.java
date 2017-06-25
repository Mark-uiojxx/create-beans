package piv.beans.common.utils;

/**
 * Created by admin on 2017/6/25.
 */
public class MyCamelUnderUtils {
    /**
     *
     */
    private static final char SEPARATOR = '_';
    /**
     *
     * 方法作用说明:toUnderline
     * 使用说明:驼峰字符串转下划线
     * 使用注意事项:
     *
     * @param s 驼峰字符串
     * @return 下划线字符串
     * @since JDK 1.8
     */
    public static String toUnderline(String s) {
        if (s == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            boolean nextUpperCase = true;
            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }
            if ((i >= 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    if (i > 0){
                        sb.append(SEPARATOR);
                    }
                }
                upperCase = true;
            } else {
                upperCase = false;
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }

    /**
     *
     * 方法作用说明:toCamelCase
     * 使用说明:下划线字符串转驼峰
     * 使用注意事项:
     *
     * @param s 下划线字符串
     * @return 驼峰字符串
     * @since JDK 1.8
     */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = s.toLowerCase();
        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     *
     * 方法作用说明:toCapitalizeCamelCase
     * 使用说明:
     * 使用注意事项:
     *
     * @param s s
     * @return a
     * @since JDK 1.8
     */
    public static String toCapitalizeCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = toCamelCase(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
