package piv.beans.common.utils;

/**
 * Created by admin on 2017/6/25.
 */
public class JavaDocUtils {
    public static StringBuffer packageDoc(String fileName, String packageName, String date) {
        StringBuffer sb = new StringBuffer();
        sb.append("/** \r\n");
        sb.append("* 文件名称:" + fileName + ".java \r\n");
        sb.append("* 包名:" + packageName + " \r\n");
        sb.append("* 创建时间:" + date + " \r\n");
        sb.append("* \r\n");
        sb.append("*/ \r\n");
        return sb;
    }

    public static StringBuffer fileDoc(String fileName, String tableComment, String date) {
        StringBuffer sb = new StringBuffer();
        sb.append(" /** \r\n");
        sb.append("* 类名: " + fileName + " \r\n");
        sb.append("* 作用说明: " + tableComment + " \r\n");
        sb.append("* 创建时间: " + date + " \r\n");
        sb.append("* \r\n");
        sb.append("* @author admin \r\n");
        sb.append("* @since JDK 1.8 \r\n");
        sb.append("*/ \r\n");
        return sb;
    }

    public static StringBuffer methodGetDoc(String columnComment) {
        StringBuffer sb = new StringBuffer();
        sb.append("    /** \r\n");
        sb.append("    *  \r\n");
        sb.append("    * 方法作用说明:get方法 \r\n");
        sb.append("    * 使用说明: " + columnComment + " \r\n");
        sb.append("    * 使用注意事项: \r\n");
        sb.append("    * \r\n");
        sb.append("    * @author admin \r\n");
        sb.append("    * @return " + columnComment + "\r\n");
        sb.append("    * @since JDK 1.8 \r\n");
        sb.append("    */ \r\n");
        return sb;
    }

    public static StringBuffer methodSetDoc(String columnComment, String columnName) {
        StringBuffer sb = new StringBuffer();
        sb.append("    /** \r\n");
        sb.append("    *  \r\n");
        sb.append("    * 方法作用说明:set方法 \r\n");
        sb.append("    * 使用说明: "+columnComment+" \r\n");
        sb.append("    * 使用注意事项: \r\n");
        sb.append("    * \r\n");
        sb.append("    * @author admin \r\n");
        sb.append("    * @param " + columnName + " " + columnComment + " \r\n");
        sb.append("    * @since JDK 1.8 \r\n");
        sb.append("    */ \r\n");
        return sb;
    }

    public static StringBuffer columnDoc(String columnComment) {
        StringBuffer sb = new StringBuffer();
        sb.append("    /** \r\n");
        sb.append("    * "+columnComment+" \r\n");
        sb.append("    */ \r\n");
        return sb;
    }
}
