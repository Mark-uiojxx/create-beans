package piv.beans.common.utils;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

/**
 * Created by admin on 2017/6/25.
 */
public class JDBCUtils {
    private static PropertiesConfiguration config = new PropertiesConfiguration();

    static {
        config.setReloadingStrategy(new FileChangedReloadingStrategy());
        config.setFileName("./conf/jdbc.properties");
    }
    
    public static MysqlDataSource getDataSource() {
        MysqlDataSource mds = new MysqlDataSource();
        mds.setUrl(config.getString("jdbc.url"));
        mds.setUser(config.getString("jdbc.username"));
        mds.setPassword(config.getString("jdbc.password"));
        return mds;
    }

    public  static String getCreateFileDir() {
        return config.getString("create_file_dir");
    }
}
