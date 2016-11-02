/*
 * Project 1-T6-14h45-20161
 * Nhom 2-De 14
 * Phan Ngoc Lan
 * Le Thanh Loi
 * Tong Thi Hong
 */
package project1.nhom2.de14.controller;

import java.util.Vector;
import project1.nhom2.de14.model.HoaDon;
import project1.nhom2.de14.util.ConnectDB;
import project1.nhom2.de14.util.WriteRead;

/**
 *
 * @author WILL
 */
public class CHoaDon {
	private Vector<HoaDon> DS;
	private ConnectDB conn;
	
	public CHoaDon(String user, String pass){
		DS = new Vector<HoaDon>(100);
		conn = new ConnectDB();
		conn.connect("khachsan", user, pass);
		
		String[] key = null;

		Vector<String> vec = conn.select("nhanvien", key);
		for(String s : vec){
			DS.add(new HoaDon(s));
		}
	}
	
	public int searchIndex(String maHD) {
		int i = 0;
		for (HoaDon hd : DS) {
			if (hd.getMaHD().equals(maHD)) {
				return i;
			}
			i++;
		}
		return -1;
	}
	
	public boolean add(String record){
		HoaDon hd = new HoaDon(record);
		String [] values = hd.getValues();
		
		if(!conn.insert("hoadon", values))
			return false;
		
		DS.add(hd);
		return true;
	}
	
	public boolean delete(String key){
		if(!conn.delete("hoadon", "MaDP", key))
			return false;
		
		int pos = searchIndex(key);
		DS.remove(pos);
		return true;
	}
	
	public boolean edit(String record, String key){
		HoaDon hd = new HoaDon(record);
		String [] values = hd.getValues();
		
		values[0] = "MaHD=" + "'" + values[0] + "'";
		values[1] = "MaDatPhong=" + "'" + values[1] + "'";
		values[2] = "TGThanhToan=" +"STR_TO_DATE(" + "'"+ values[2]+"',"+"'%d-%m-%Y')";
		values[4] = "TienPhong=" + values[4];
		values[5] = "TienDichVu=" + values[5];
		
		String pkey = "MaHD=" + "'" + key + "'";
		if (!conn.update("hoadon", values, pkey)) {
			return false;
		}

		int pos = searchIndex(key);
		DS.remove(pos);
		DS.insertElementAt(hd, pos);
		return true;
	}
	
	public Vector<String> addFile(String fileName){
		Vector<String> vec = WriteRead.read(fileName);
		
		for (String s : vec){
			HoaDon hd = new HoaDon(s);
			String [] values = hd.getValues();
			if (!conn.insert("hoadon", values)) {
				continue;
			}
			DS.add(hd);
		}
		
		return vec;
	}
	
	public Vector<HoaDon> getDS(){
		return DS;
	}
	
	public Object [][] getTableData(){
		if (DS == null) {
			return new Object[0][6];
		}
		Object[][] data = new Object[DS.size()][6];
		
		int i = 0;
		for(HoaDon hd : DS){
			data[i] = hd.getValues();
			i++;
		}
		
		return data;
	}
}
