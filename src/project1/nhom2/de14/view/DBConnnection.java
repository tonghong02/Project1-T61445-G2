/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project1.nhom2.de14.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dell
 */
public class DBConnnection {

    public static String url = null;
    public static String driverName = null;
    public static String username = null;
    public static String password = null;
    public static Connection con;

    // khoi tao
    public static void init() {
        url = "jdbc:sqlserver://localhost:1433;databaseName=Quan_ly_Khach_San";
        driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        username = "sa";
        password = "tonghong96";
    }

    // mo ket noi
    public static boolean open() {
        try {
            if (con == null || con.isClosed()) {
                init();
                try {
                    Class.forName(driverName);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(DBConnnection.class.getName()).log(Level.SEVERE, null, ex);
                }
                con = DriverManager.getConnection(url, username, password);
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //  tao query
    public static PreparedStatement createQuery(String sql) {
        PreparedStatement ps = null;
        if (DBConnnection.open()) {
            try {
                ps = DBConnnection.con.prepareStatement(sql);
            } catch (SQLException ex) {
                Logger.getLogger(DBConnnection.class.getName()).log(Level.SEVERE, null, ex);
                DBConnnection.close(ps);
            }

        }
        return ps;
    }

    // dong ket noi
    public static void close() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBConnnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void close(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBConnnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        close();
    }
    
    public static void close(Statement state) {
        if (state != null) {
            try {
                state.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBConnnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        close();
    }

    public static void close(PreparedStatement ps, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBConnnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        close(ps);
    }
    
     public static void close(Statement state, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBConnnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        close(state);
    }

}
