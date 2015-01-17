import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Properties;

import org.junit.Test;

public class ExploreDerbyTest {  

    @Test  
    public void testCreateDB()   
        throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {  
        String driver = "org.apache.derby.jdbc.EmbeddedDriver";  
        String dbName = "/home/woody/Workstation/Workspace/eclipse/so/derbyDB";  
        String protocol = "jdbc:derby:";  

        Properties props = new Properties();
        props.put("user", "user1");  
        props.put("password", "password");  

        Class.forName(driver).newInstance();  
          
        System.out.println(DriverManager.getLoginTimeout());  
          
        Enumeration<Driver> ds = DriverManager.getDrivers();  
        while (ds.hasMoreElements()) {  
            System.out.println(ds.nextElement());  
        }  
        Connection conn = DriverManager.getConnection(protocol+dbName+";create=true");  
        //Properties p = conn.getClientInfo();  
        //System.out.println(p);  
        conn.setAutoCommit(false);  
          
        Statement s = conn.createStatement();  
        //s.execute("create table location2(num int, addr varchar(40))");  
        //System.out.println(s.getFetchDirection() + " | " +   
        //        s.getFetchSize() + " | " +  s.getMaxFieldSize()  + " | " +   
        //        s.getMaxRows()  + " | " +  s.getQueryTimeout());  
        conn.commit();  
        conn.close();  
    }  
    @Test  
    public void testCreateTables()  
        throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {  
        String driver = "org.apache.derby.jdbc.EmbeddedDriver";  
        String protocol = "jdbc:derby:";  
        String dbName="/home/woody/Workstation/Workspace/eclipse/so/derbyDB";  
          
        String[] sqls = {  
                "create table mail_server(id int, name varchar(50), host varchar(50), port int, protocol varchar(10), is_deleted char(1), description varchar(100) )",  
                "create table job_history(id int, mail_server_id int, user_id varchar(50), password varchar(50), folder_name varchar(50), befor_days int, status varchar(10), " +  
                "created_time time, started_time time, stopped_time time)"  
        };  
        Class.forName(driver).newInstance();  
        Connection conn = DriverManager.getConnection(protocol+dbName);  
        conn.setAutoCommit(false);  
        Statement stmt = conn.createStatement();  
        for (int i = 0; i < sqls.length; i++) {  
            stmt.addBatch(sqls[i]);  
        }  
        int[] r = stmt.executeBatch();  
        System.out.println(r);  
        conn.commit();  
        conn.close();  
    }  
}  