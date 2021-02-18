package hospital_parking_system.hospital_parking.adminPage;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;

public class Excel {
    public static void main(String[] args) throws Exception {


        XSSFWorkbook xssfWb = null;
        XSSFSheet xssfSheet = null;
        XSSFRow xssfRow = null;
        XSSFCell xssfCell = null;

            try {
                int rowNo = 0;
                xssfWb = new XSSFWorkbook();
                xssfSheet = xssfWb.createSheet("워크 시트1");

                xssfRow = xssfSheet.createRow(rowNo++);
                xssfCell = xssfRow.createCell((short) 0);
                xssfCell.setCellValue("셀1");
                xssfCell = xssfRow.createCell((short) 1);
                xssfCell.setCellValue("셀2");
                xssfCell = xssfRow.createCell((short) 2);
                xssfCell.setCellValue("셀3");
                xssfCell = xssfRow.createCell((short) 3);
                xssfCell.setCellValue("셀4");
                xssfCell = xssfRow.createCell((short) 4);
                String localFile = "D:/gwanho/excel_test" + "excelDownTest" + ".xlsx";
                File file = new File(localFile);
                FileOutputStream fos = null;
                fos = new FileOutputStream(file);
                xssfWb.write(fos);
                if (fos != null) fos.close();
                System.out.println("출발 테스트");

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("실패");
            }
        System.out.println("test");
        }
    }
