
import java.sql.*;
/**
 * Created by Lx on 2017/1/7.
 */
public class DataBase {


    /*static Connection getConn() {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/bilibili";
        String username = "root";
        String password = "yrlx1314";
        Connection conn = null;
        try {
            Class.forName(driver); //classLoader,加载对应驱动
            conn = (Connection) DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;

    }
//
    public static void insert() throws SQLException {


}*///因为插入有误，出现重复插入现象，所以更换插入方式
    Connection connection;
    Statement statement;
    ResultSet rSet;
    String sql;

    //获取数据库连接
    public void getconnection() throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bilibili", "root", "yrlx1314");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //向表内插入数据
    public void insert(int tid, String title, String tname, String author, int coin, int favorite, int url) throws Exception {
        try {
            statement = connection.createStatement();
            sql = "insert into yurui (tid,title,tname, author, coin, favorite, url)" + "values('" + tid + "','" + title + "','" + tname + "', '" + author + "','" + coin + "','" + favorite + "','" + url + "')";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String[] select(String str) throws Exception {//选出前三名
        String data[] = new String[3];
        try {
            int account = 0;
            statement = connection.createStatement();
            sql = "select * from yurui where tname= '" + str + "'order by favorite desc LIMIT 3";
            rSet = statement.executeQuery(sql);
            while (rSet.next()) {
                String rs = rSet.getString("url");
                System.out.println("rs:" + rs);
                data[account] = rs;
                account++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
}
