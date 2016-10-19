/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QuanLyKhachSan;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 *
 * @author LeThanhLoi
 */
public class WriteRead {

    public void read() {
        Workbook workbook;
        String fileName = "C:\\Users\\LeThanhLoi\\Desktop\\";
        Scanner scan = new Scanner(System.in);
        System.out.println("Nhap ten file: ");
        String st = scan.nextLine();
        fileName = fileName + st + ".xls";

        try {
            workbook = Workbook.getWorkbook(new File(fileName));
            Sheet sheet = workbook.getSheet(0);

            int rows = sheet.getRows();
            int cols = sheet.getColumns();

            System.out.println("Data in file: ");

            for (int row = 0; row < rows; row++) {
                int dem = 0;
                for (int col = 0; col < cols; col++) {
                    Cell cell = sheet.getCell(col, row);
                    System.out.print(cell.getContents() + "\t");
                    if (dem == cols - 1) {
                        System.out.println("\n");
                        break;
                    } else {
                        dem++;
                    }
                }

            }
            workbook.close();
        } catch (IOException ex) {
            Logger.getLogger(WriteRead.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(WriteRead.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void write(String table) {
        String fileName = "C:\\Users\\LeThanhLoi\\Desktop\\";
        ConnectDB conn = new ConnectDB();
        conn.connect("quanlykhachsan", "root", "dragonball");
        
        
        Scanner scan = new Scanner(System.in);
        System.out.println("Nhap ten file: "); 
        String st = scan.nextLine();
        fileName = fileName + st + ".xls";
        
        Vector<String> vector = new Vector<String>(100);
        vector = conn.select(table, null);
        System.out.println(vector);
        

        WritableWorkbook workbook;

        String[][] str = new String[vector.size()][];
        for (int i = 0; i < vector.size(); i++) {
            str[i] = vector.get(i).split("\t");
        }

        try {

            workbook = Workbook.createWorkbook(new File(fileName));
            WritableSheet sheet1 = workbook.createSheet("Sheet1", 0);
            sheet1.addCell(new Label(0, 0, "Thông tin bảng  " + table.toUpperCase()));

            int row = 3;
            int col = 0;

            String[] lableP = {"Mã Phòng", "Loại phòng", "Mức giá", "Trạng thái"};
            String[] lableNV = {"Mã Nhân viên", "Tên nhân viên", "Ngày sinh", "Giới tính", "Số CMND", "Địa chỉ", "SĐT","Chức vụ"};
            String[] lableKH = {"Mã khách hàng", "Tên Khách hàng", "Số CMTND", "Giới tính", "Địa chỉ", "Quốc tịch", "SĐT"};
            String[] lableHD = {"Mã Hóa đơn", "Mã đặt phòng", "Thời gian thanh toán","Tiền phòng","Tiền dịch vụ"};
            String[] lableDV = {"Mã dịch vụ", "Tên dịch vụ", "Đơn giá","Mã NV phụ trách"};
            String[] lableDP = {"Mã đặt phòng", "Mã khách hàng", "Thời gian nhận","Thời gian trả","Số phòng đặt","Tiền đặt cọc","Mã nhân viên"};
            String[] lableCTDV = {"Mã phòng", "Mã dich vụ", "Số lượng","Thành tiền"};
            String[] lableCTDP = {"Mã đặt phòng", "Mã phòng"};

            if (table.equals("phong")) {
                for (int i = 0; i < lableP.length; i++) {
                    sheet1.addCell(new Label(i, row - 1, lableP[i]));
                }
            } else if (table.equals("nhanvien")) {
                for (int i = 0; i < lableNV.length; i++) {
                    sheet1.addCell(new Label(i, row - 1, lableNV[i]));
                }
            }
            if (table.equals("khachhang")) {
                for (int i = 0; i < lableKH.length; i++) {
                    sheet1.addCell(new Label(i, row - 1, lableKH[i]));
                }
            }
            if (table.equals("hoadon")) {
                for (int i = 0; i < lableHD.length; i++) {
                    sheet1.addCell(new Label(i, row - 1, lableHD[i]));
                }
            }
            if (table.equals("dichvu")) {
                for (int i = 0; i < lableDV.length; i++) {
                    sheet1.addCell(new Label(i, row - 1, lableDV[i]));
                }
            }
            if (table.equals("datphong")) {
                for (int i = 0; i < lableDP.length; i++) {
                    sheet1.addCell(new Label(i, row - 1, lableDP[i]));
                }
            }
            if (table.equals("chitietdichvu")) {
                for (int i = 0; i < lableCTDV.length; i++) {
                    sheet1.addCell(new Label(i, row - 1, lableCTDV[i]));
                }
            }
            if (table.equals("chitietdatphong")) {
                for (int i = 0; i < lableCTDP.length; i++) {
                    sheet1.addCell(new Label(i, row - 1, lableCTDP[i]));
                }
            }

            for (int r = row, i = 0; r < str.length + row; r++, i++) {
                for (int c = col, j = 0; c < str[i].length + col; c++, j++) {
                    String obj = str[i][j];
                    sheet1.addCell(new Label(c, r, str[i][j]));
                }
            }
            workbook.write();
            workbook.close();

        } catch (IOException ex) {
            Logger.getLogger("ERROR create file" + ex);
        } catch (WriteException ex) {
            Logger.getLogger(WriteRead.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        WriteRead wr= new WriteRead();
        Scanner scan= new Scanner(System.in);
        System.out.println("Nhap ten table : ");
        String table= scan.nextLine();
        wr.write(table);
        //wr.read();
    }
}
