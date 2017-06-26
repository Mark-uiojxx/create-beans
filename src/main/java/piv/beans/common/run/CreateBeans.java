package piv.beans.common.run;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.io.FileUtils;
import piv.beans.common.utils.JDBCUtils;
import piv.beans.common.utils.JavaDocUtils;
import piv.beans.common.utils.MyCamelUnderUtils;
import piv.beans.common.utils.MyDateUtils;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/6/25.
 */
public class CreateBeans implements Serializable {

    public static void main(String[] args) throws SQLException, IOException {
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        //获取所有表名
        List<Object[]> tables = queryRunner.query("show tables where tables_in_testdb like 'test_%'", new ArrayListHandler());
        for(Object[] objects: tables) {
            StringBuffer clazzFile = new StringBuffer();
            String tableName = objects[0].toString();
            Map<String, Object> tableCommentMap = queryRunner.query("select table_name,table_comment from information_schema.TABLES where TABLE_NAME='" + tableName + "'",
                    new MapHandler());
            //得到表的注释
            String tableComment = tableCommentMap.get("table_comment").toString();
            //查询表中的所有列
            List<Map<String, Object>> columnDetailMap = queryRunner.query(
                    "select column_name,data_type,COLUMN_COMMENT from information_schema.columns where table_schema = 'testdb' and table_name='" + tableName + "'",
                    new MapListHandler());
            //类名
            String fileName = MyCamelUnderUtils.toCamelCase(tableName).substring(tableName.indexOf("_"));
            //包
            String packageName = "com.test.beans." + tableName.split("_")[1] + ".dbmodel;";
            //当前时间
            String date = MyDateUtils.dateToString(new Date(), MyDateUtils.YYYY_MM_DD_HH_MM_SS);
            //包的注释
            StringBuffer packageDoc = JavaDocUtils.packageDoc(fileName, packageName, date);
            //类的注释
            StringBuffer fileDoc = JavaDocUtils.fileDoc(fileName, tableComment, date);
            //类的开头
            String classLineStart = "public class " + fileName + "  implements Serializable { \r\n";

            clazzFile.append(packageDoc);
            clazzFile.append("package " + packageName + "\r\n\r\n");
            clazzFile.append("import java.math.BigDecimal;\r\n");
            clazzFile.append("import java.util.Date;\r\n");
            clazzFile.append("import java.io.Serializable;\r\n");
            clazzFile.append(fileDoc);
            clazzFile.append(classLineStart + "\r\n");
            clazzFile.append(JavaDocUtils.columnDoc("序列化对象"));
            clazzFile.append("    private static final long serialVersionUID = 1L;\r\n");
            clazzFile.append(JavaDocUtils.columnDoc("表名"));
            clazzFile.append("    public static final String TABLE_NAME = \"" + tableName + "\";  \r\n");
            StringBuffer getSetMethod = new StringBuffer();
            for (Map<String, Object> map : columnDetailMap) {
                //属性名
                String columnName = MyCamelUnderUtils.toCamelCase(map.get("column_name").toString());
                //属性的注释
                String columnComment = map.get("COLUMN_COMMENT").toString();
                //属性的类型
                String javaType = "";
                if (map.get("data_type").toString().contains("varchar") || map.get("data_type").toString().contains("longtext")
                        || map.get("data_type").toString().contains("enum")) {
                    javaType = "String";
                } else if (map.get("data_type").toString().contains("int")) {
                    javaType = "Integer";
                } else if (map.get("data_type").toString().contains("date")) {
                    javaType = "Date";
                } else if (map.get("data_type").toString().contains("char")) {
                    javaType = "Character";
                } else if (map.get("data_type").toString().contains("decimal")) {
                    javaType = "BigDecimal";
                }
                clazzFile.append(JavaDocUtils.columnDoc(columnComment));
                clazzFile.append("    private " + javaType + " " + columnName + ";  \r\n");
                clazzFile.append(JavaDocUtils.columnDoc(columnComment));
                clazzFile.append("    public static final String " + map.get("column_name").toString().toUpperCase() + "_ATTR = \"" + columnName + "\";  \r\n");
                clazzFile.append(JavaDocUtils.columnDoc(columnComment));
                clazzFile.append(
                        "    public static final String " + map.get("column_name").toString().toUpperCase() + " = \"" + map.get("column_name").toString() + "\";  \r\n");

                getSetMethod.append(JavaDocUtils.methodGetDoc(columnComment));
                String firstUpperCase = columnName.substring(0, 1).toUpperCase() + columnName.substring(1);
                getSetMethod.append("    public " + javaType + " get" + firstUpperCase + "() {\r\n");
                getSetMethod.append("        return this." + columnName + ";\r\n");
                getSetMethod.append("    }\r\n\r\n");
                getSetMethod.append(JavaDocUtils.methodSetDoc(columnComment, columnName));
                getSetMethod.append("    public void set" + firstUpperCase + "(" + javaType + " " + columnName + "){\r\n");
                getSetMethod.append("        this." + columnName + " = " + columnName + ";\r\n");
                getSetMethod.append("    }\r\n\r\n");
            }
            clazzFile.append(getSetMethod);
            //类的结尾
            String classLineEnd = "}";
            clazzFile.append(classLineEnd + "\r\n");
            File dirs = new File(JDBCUtils.getCreateFileDir() + "com\\test\\beans\\" + tableName.split("_")[1] + "\\dbmodel");
            if (!dirs.exists()) {
                dirs.mkdirs();
            }
            File javaFile = new File(JDBCUtils.getCreateFileDir() + "com\\test\\beans\\" + tableName.split("_")[1] + "\\dbmodel\\" + fileName + ".java");
            FileUtils.writeStringToFile(javaFile, clazzFile.toString(), "UTF-8");
        }
    }

}
