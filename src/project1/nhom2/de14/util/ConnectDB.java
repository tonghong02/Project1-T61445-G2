/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project1.nhom2.de14.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LeThanhLoi
 */
public class ConnectDB {

	private final String className = "com.mysql.jdbc.Driver";
	private Connection connection;

	// kêt nối  database
	public void connect(String nameDatabase, String user, String pass) {
		try {
			Class.forName(className);
			String url = "jdbc:mysql://localhost:3306/" + nameDatabase;
			try {
				connection = DriverManager.getConnection(url, user, pass);
				System.out.println("Connect Success !!!");
			} catch (SQLException ex) {
				System.out.println("Error !!!");
			}
		} catch (ClassNotFoundException ex) {
			System.out.println("Class not found ");
		}
	}

	// lấy dữ liệu theo cột trong bảng
	public Vector<String> select(String table, String key[]) {
		ResultSet rs = null;
		Vector<String> vector = new Vector<String>(100);
		String sqlcommand = "select * " + " from " + table;
		PreparedStatement pst = null;
		if (key == null) {
			sqlcommand = "select * " + " from " + table;
		} else {

			for (int i = 0; i < key.length; i++) {
				sqlcommand = sqlcommand + key;
				if (i < key.length - 1) {
					sqlcommand += " AND ";
				}
			}
			sqlcommand = sqlcommand + " ;";
		}
		try {
			pst = connection.prepareStatement(sqlcommand);
			rs = pst.executeQuery();
			String str = new String();
			switch (table) {
				case "phong":
					while (rs.next()) {

						vector.add(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4));
					}
				case "nhanvien":
					while (rs.next()) {

						vector.add(rs.getString(1) + "\t" + rs.getString(2) + "\t" + CalendarAdapter.SQLDateToString(rs.getDate(3)) + "\t" + rs.getString(4)
								+ "\t" + rs.getString(5) + "\t" + rs.getString(6) + "\t" + rs.getString(7) + "\t" + rs.getString(8));
					}
				case "khachhang":
					while (rs.next()) {

						vector.add(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t"
								+ rs.getString(4) + "\t" + rs.getString(5) + "\t" + rs.getString(6) + "\t" + rs.getString(7));
					}
				case "hoadon":
					while (rs.next()) {

						vector.add(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t"
								+ rs.getString(5));
					}
				case "dichvu":
					while (rs.next()) {

						vector.add(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4));
					}
				case "chitietdichvu":
					while (rs.next()) {

						vector.add(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4));
					}
				case "datphong":
					while (rs.next()) {

						vector.add(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t"
								+ rs.getString(5) + "\t" + rs.getString(6) + "\t" + rs.getString(7));
					}
				case "chitietdatphong":
					while (rs.next()) {

						vector.add(rs.getString(1) + "\t" + rs.getString(2));
					}
			}
		} catch (SQLException ex) {
			System.out.println("Select error !" + ex.toString());
		}

		return vector;
	}

	// In ra man hinh vector
	public void print(Vector<String> vector) {
		for (String s : vector) {
			System.out.println(s);
		}
	}

	//  insert values vao trong table
	public boolean insert(String table, String[] values) {

		try {

			String sqlcommand = "insert into " + table;
			PreparedStatement pst = null;

			if (table.equals("phong")) {
				sqlcommand = sqlcommand + " values(" + "'" + values[0] + "','" + values[1] + "','" + values[2] + "','" + values[3] + "');";
			} else if (table.equals("nhanvien")) {
				sqlcommand = sqlcommand + " values(" + "'" + values[0] + "','" + values[1] + "','" + values[2] + "','" + values[3] + "','" + values[4] + "','" + values[5] + "','" + values[6] + "','" + values[7] + "');";
			} else if (table.equals("khachhang")) {
				sqlcommand = sqlcommand + " values (" + "'" + values[0] + "','" + values[1] + "','" + values[2] + "','" + values[3] + "','" + values[4] + "','" + values[5] + "','" + values[6] + "');";
			} else if (table.equals("hoadon")) {
				sqlcommand = sqlcommand + table + " values (" + "'" + values[0] + "','" + values[1] + "','" + values[2] + "','" + values[3] + "','" + values[4] + "');";
			} else if (table.equals("dichvu")) {
				sqlcommand = sqlcommand + table + " values (" + "'" + values[0] + "','" + values[1] + "','" + values[2] + "','" + values[3] + "');";
			} else if (table.equals("datphong")) {
				sqlcommand = sqlcommand + table + " values (" + "'" + values[0] + "','" + values[1] + "','" + values[2] + "','" + values[3] + "','" + values[4] + "','"
						+ values[5] + "','" + values[6] + "');";
			} else if (table.equals("chitietdichvu")) {
				sqlcommand = sqlcommand + table + " values (" + "'" + values[0] + "','" + values[1] + "','" + values[2] + "','" + values[3] + "');";
			} else if (table.equals("chitietdatphong")) {
				sqlcommand = sqlcommand + table + " values (" + "'" + values[0] + "','" + values[1] + "');";
			}
			System.out.println(sqlcommand);
			pst = connection.prepareStatement(sqlcommand);
			pst.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
		return true;
	}

	//xoa column co gia tri la key trong bang table
	public boolean delete(String table, String column, String key) {
		try {

			String sqlcommand = " delete from " + table + " where " + column + " ='" + key + "';";
			PreparedStatement pst = null;
			System.out.println(sqlcommand);

			pst = connection.prepareStatement(sqlcommand);
			pst.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
		return true;
	}
	
	public boolean delete(String table, String column1, String key1, String column2, String key2) {
		try {

			String sqlcommand = " delete from " + table + " where " + column1 + " ='" + key1 +  "' AND " + column2 + " ='" + key2 + "';";
			PreparedStatement pst = null;
			System.out.println(sqlcommand);

			pst = connection.prepareStatement(sqlcommand);
			pst.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
		return true;
	}

	//Update trong bang table nhung record co column= key
	public boolean update(String table, String[] values, String key) {
		try {

			String sqlcommand = " update " + table + " set ";
			PreparedStatement pst = null;

			for (int i = 0; i < values.length; i++) {
				sqlcommand += values[i];
				if (i < values.length - 1) {
					sqlcommand += ",";
				}
			}
			sqlcommand += " where " + key + ";";
			System.out.println(sqlcommand);

			pst = connection.prepareStatement(sqlcommand);
			pst.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
		return true;
	}

	public void close() {
		try {
			connection.close();
		} catch (SQLException ex) {
			Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
