package com.sea.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
   
public class ExcelUtils {
	private String errorMessage;
	private File excelFile = null; //创建文件对象
    private FileInputStream fis = null;
    private Workbook workbook = null;
    private Sheet sheet = null;
    private ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
    private int rowNum       = 0;
    private int currentRowNo = 0;
    
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	//直接读取文件	
	public ArrayList<ArrayList<String>> readExcel(String filePath) {
		
        excelFile = new File(filePath); //创建文件对象
        try {
        	fis = new FileInputStream(excelFile);
            //生成Excel工作薄对象
        	workbook = WorkbookFactory.create(fis);
            
            //获得工作薄中的第一个工作表
            sheet = workbook.getSheetAt(0);

            //调用readRows方法，逐行解析工作表中内容
            list = readRows(sheet); 

            //解析Excel文件结束后，关闭输入流
            fis.close();
        } catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
	
	//通过文件对象读取
	public ArrayList<ArrayList<String>> readExcel(FileInputStream fileInputStream) throws Exception {

        //声明并初始化一个工作薄对象
        //存储解析后的Excel中的内容的二维数组
        try {
            //生成Excel工作薄对象
        	workbook = WorkbookFactory.create(fileInputStream);
            
            //获得工作薄中的第一个工作表
            sheet = workbook.getSheetAt(0);

            //调用readRows方法，逐行解析工作表中内容
            list = readRows(sheet); 

            //解析Excel文件结束后，关闭输入流
            fileInputStream.close();
        
        } catch(Exception e){
            e.printStackTrace();
        }

        return list;
    }

    /**
     * 逐行解析工作表中的内容
     * @param sheet
     * @return 解析工作表后生成的二维数组
     * @throws Exception
     */
    private ArrayList<ArrayList<String>> readRows(Sheet sheet) {

        //获得该工作表的行数
        int rows = sheet.getPhysicalNumberOfRows();
        int rowIndex = 1;        //每行索引
        int notnullRowIndex = 0; //非空行索引
        while (notnullRowIndex < rows) {
            //解析该行中的每个单元格
            Row row = sheet.getRow(rowIndex);
            rowIndex++;
            if (row != null && rowIndex <= rows) {
                //将每行解析出的一维数组加入到list中
                list.add(readCells(row));
                notnullRowIndex++;   
            } else {
            	break;
            }
        }
        return list;
    }   

    /**
     * 解析工作表中的一行数据
     * @param row
     * @return 本行解析后的一维数组
     * @throws Exception
     */
    private ArrayList<String> readCells(Row row) {

        //获得本行所有单元格
        int cells  = row.getPhysicalNumberOfCells();
        int cellIndex = 0;         //单元格索引
        int notnullCellIndex = 0;  //非空单元格索引

        //声明并初始化一个一维数组，用于存储改行解析后的数据
        ArrayList<String> rowlist = new ArrayList<String>();

        //逐个解析该行的单元格
        while(notnullCellIndex < cells) {
            //取得一个单元格
            Cell cell = row.getCell(cellIndex);
            cellIndex++;
            if (cell != null) {
              //将该单元格的内容存入一维数组
                rowlist.add(this.getCellValue(cell));
                notnullCellIndex++;
            }
        } 
        return rowlist;
    }
	    
	
	//表格中数据解析
	 private String getCellValue(Cell cell) {
		 SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		 DecimalFormat df = new DecimalFormat("#");
		 int cellType = cell.getCellType();
         String cellValue = null;
         switch(cellType) {
           //This case to get the data and get the value as strings.
             case Cell.CELL_TYPE_STRING:           	
                 cellValue = cell.getRichStringCellValue().getString().trim();
                 break;
               //This case to get the data and get the value as boolean.
             case Cell.CELL_TYPE_BOOLEAN:
                 cellValue = String.valueOf(cell.getBooleanCellValue()).trim();
                 break;
             case Cell.CELL_TYPE_BLANK:    
             	cellValue = "";    
                 break;    
               //This case to get the data and get the value as number.
             case Cell.CELL_TYPE_NUMERIC: //数字、日期
                 if(DateUtil.isCellDateFormatted(cell)) {
                     cellValue = fmt.format(cell.getDateCellValue()); //日期型
                 }
                 else {
                     cellValue = String.format("%.0f", cell.getNumericCellValue()); //数字
                 }
                 break;
             case Cell.CELL_TYPE_ERROR: //错误
                 cellValue = "错误";
                 break;
             case Cell.CELL_TYPE_FORMULA: //公式
            	 cellValue = cell.getCellFormula();   
                 break; 
             default: {
                 //若单元格内容的格式与常规类型不符，则记录下单元格位置，并跳过该行的读取，继续解析下一行
                 return null;
             }  
         }
         return cellValue;
	 }
	 
	   private boolean isEmpty(final Row row) {
	        Cell firstCell = row.getCell(0);   
	        boolean rowIsEmpty = (firstCell == null) || (firstCell.getCellType() == Cell.CELL_TYPE_BLANK);    
	        return rowIsEmpty; 
	       }
	       
       private int countNonEmptyColumns(final Sheet sheet) {   
	       Row firstRow = sheet.getRow(0);
	       return firstEmptyCellPosition(firstRow);
       }
	       
       private int firstEmptyCellPosition(final Row cells) {  
    	   int columnCount = 0;
    	   for (Cell cell : cells) {     
    		   if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
    			   break;
    		   }
    		   columnCount++;
    	   }    
    	   return columnCount;
       }
	       
       /**  
        * 返回sheet表数目  
        *   
        * @return int  
        */    
       public int getSheetCount(Workbook wb) {    
           int sheetCount = -1;
           if (wb != null){
        	   sheetCount = wb.getNumberOfSheets();
           }
           return sheetCount; 
       }
	       
       /**  
        * sheetNum下的记录行数  
        *   
        * @return int  
        */    
       public int getRowCount(Sheet sheet) {    
           int rowCount = -1;    
           rowCount = sheet.getLastRowNum();    
           return rowCount;    
       }
	       
       public boolean hasNext() {
    	   
           if (this.rowNum == 0 || this.currentRowNo >= this.rowNum) {

               try {
            	   workbook.close();
               } catch (Exception e) {
                   e.printStackTrace();
               }
               return false;
           } else {
               // sheet涓嬩竴琛屽唴瀹逛负绌哄垽瀹氱粨鏉�
               if ((sheet.getRow(currentRowNo)).getCell(0).equals(""))
                   return false;
               return true;
           }
       }
	       
        //This method is to set the rowcount of the excel.
        public int excel_get_rows(Sheet sheet) {
        	return sheet.getPhysicalNumberOfRows();           
        }
        
        public int excel_get_columns(Iterator it)
        {
        	int columnNum=0;
        	while (it.hasNext()) {
         	  columnNum ++; 
            }
        	return columnNum;
        }
	 
}