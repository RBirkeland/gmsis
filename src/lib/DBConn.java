/**
 * Created by ft301 on 24/02/15.
 */
package lib;

import java.sql.*;

public class DBConn {

    private static DBConn instance = null;

    protected DBConn() {}

    public static DBConn getInstance() {
        if (instance == null) {
            instance = new DBConn();
        }
        return instance;
    }

    private final String PREFIX = "jdbc:sqlite:";
    private String url = PREFIX + "Resources/gmsis.db";
    private Connection conn;

    public void setDBPath(String path) {
        instance.url = instance.PREFIX + path;
    }

    public boolean connIsSet() {
        return conn != null;
    }

    public Connection getConn() {
        if (conn != null)
            return conn;

        try {
            conn = DriverManager.getConnection(url);
        } catch(SQLException e) {
            System.err.println("Database not found");
            e.printStackTrace();
        }

        return conn;
    }

    public void close() {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }

    public void updateDB(String sql) {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println("SQL failure");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Uninitialised connection");
            e.printStackTrace();
        }
    }

    public void updateDB(String... sqls) {
        try {
            Statement stmt = conn.createStatement();

            for (int i = 0; i < sqls.length; i++)
                stmt.executeUpdate(sqls[i]);
        } catch (SQLException e) {
            System.err.println("SQL failure");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Uninitialised connection");
            e.printStackTrace();
        }
    }

    public ResultSet queryDB(String sql) {
        ResultSet rs = null;

        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.err.println("SQL failure");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Uninitialised connection");
            e.printStackTrace();
        }
        return rs;
    }

}
