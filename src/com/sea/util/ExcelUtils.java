package com.sea.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;

import com.sea.model.User;

public class ExcelUtils {
	private String errorMessage;
	private File excelFile = null; // 创建文件对象
	private FileInputStream fis = null;
	private Workbook workbook = null;
	private Sheet sheet = null;
	private ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
	private int rowNum = 0;
	private int currentRowNo = 0;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	// 直接读取文件
	public ArrayList<ArrayList<String>> readExcel(String filePath) {

		excelFile = new File(filePath); // 创建文件对象
		try {
			fis = new FileInputStream(excelFile);
			// 生成Excel工作薄对象
			workbook = WorkbookFactory.create(fis);

			// 获得工作薄中的第一个工作表
			sheet = workbook.getSheetAt(0);

			// 调用readRows方法，逐行解析工作表中内容
			list = readRows(sheet);

			// 解析Excel文件结束后，关闭输入流
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 通过文件对象读取
	public ArrayList<ArrayList<String>> readExcel(
			FileInputStream fileInputStream) throws Exception {

		// 声明并初始化一个工作薄对象
		// 存储解析后的Excel中的内容的二维数组
		try {
			// 生成Excel工作薄对象
			workbook = WorkbookFactory.create(fileInputStream);

			// 获得工作薄中的第一个工作表
			sheet = workbook.getSheetAt(0);

			// 调用readRows方法，逐行解析工作表中内容
			list = readRows(sheet);

			// 解析Excel文件结束后，关闭输入流
			fileInputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 逐行解析工作表中的内容
	 * 
	 * @param sheet
	 * @return 解析工作表后生成的二维数组
	 * @throws Exception
	 */
	private ArrayList<ArrayList<String>> readRows(Sheet sheet) {

		// 获得该工作表的行数
		int rows = sheet.getPhysicalNumberOfRows();
		int rowIndex = 1; // 每行索引
		int notnullRowIndex = 0; // 非空行索引
		while (notnullRowIndex < rows) {
			// 解析该行中的每个单元格
			Row row = sheet.getRow(rowIndex);
			rowIndex++;
			if (row != null && rowIndex <= rows) {
				// 将每行解析出的一维数组加入到list中
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
	 * 
	 * @param row
	 * @return 本行解析后的一维数组
	 * @throws Exception
	 */
	private ArrayList<String> readCells(Row row) {

		// 获得本行所有单元格
		int cells = row.getPhysicalNumberOfCells();
		int cellIndex = 0; // 单元格索引
		int notnullCellIndex = 0; // 非空单元格索引

		// 声明并初始化一个一维数组，用于存储改行解析后的数据
		ArrayList<String> rowlist = new ArrayList<String>();

		// 逐个解析该行的单元格
		while (notnullCellIndex < cells) {
			// 取得一个单元格
			Cell cell = row.getCell(cellIndex);
			cellIndex++;
			if (cell != null) {
				// 将该单元格的内容存入一维数组
				rowlist.add(this.getCellValue(cell));
				notnullCellIndex++;
			}
		}
		return rowlist;
	}

	// 表格中数据解析
	private String getCellValue(Cell cell) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat df = new DecimalFormat("#");
		int cellType = cell.getCellType();
		String cellValue = null;
		switch (cellType) {
		// This case to get the data and get the value as strings.
		case Cell.CELL_TYPE_STRING:
			cellValue = cell.getRichStringCellValue().getString().trim();
			break;
		// This case to get the data and get the value as boolean.
		case Cell.CELL_TYPE_BOOLEAN:
			cellValue = String.valueOf(cell.getBooleanCellValue()).trim();
			break;
		case Cell.CELL_TYPE_BLANK:
			cellValue = "";
			break;
		// This case to get the data and get the value as number.
		case Cell.CELL_TYPE_NUMERIC: // 数字、日期
			if (DateUtil.isCellDateFormatted(cell)) {
				cellValue = fmt.format(cell.getDateCellValue()); // 日期型
			} else {
				cellValue = String.format("%.0f", cell.getNumericCellValue()); // 数字
			}
			break;
		case Cell.CELL_TYPE_ERROR: // 错误
			cellValue = "错误";
			break;
		case Cell.CELL_TYPE_FORMULA: // 公式
			cellValue = cell.getCellFormula();
			break;
		default: {
			// 若单元格内容的格式与常规类型不符，则记录下单元格位置，并跳过该行的读取，继续解析下一行
			return null;
		}
		}
		return cellValue;
	}

	private boolean isEmpty(final Row row) {
		Cell firstCell = row.getCell(0);
		boolean rowIsEmpty = (firstCell == null)
				|| (firstCell.getCellType() == Cell.CELL_TYPE_BLANK);
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
		if (wb != null) {
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

	// This method is to set the rowcount of the excel.
	public int excel_get_rows(Sheet sheet) {
		return sheet.getPhysicalNumberOfRows();
	}

	public int excel_get_columns(Iterator it) {
		int columnNum = 0;
		while (it.hasNext()) {
			columnNum++;
		}
		return columnNum;
	}

	/*
	 * 导出数据
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ByteArrayInputStream exportExcel(String title, String[] headers,String[] data,
			List<User> dataList) {
		HSSFWorkbook workbook = null;
		
		// String[] headers = new String[] { "序号", "姓名", "手机号", "性别", "住址",
		// "所属区域", "最后访问时间" };
		try {
			workbook = new HSSFWorkbook(); // 创建工作簿对象
			HSSFSheet sheet = workbook.createSheet(title); // 创建工作表

			// 产生表格标题行
			HSSFRow rowm = sheet.createRow(0);
			HSSFCell cellTiltle = rowm.createCell(0);

			// sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面 - 可扩展】
			HSSFCellStyle columnTopStyle = getColumnTopStyle(workbook);// 获取列头样式对象
			HSSFCellStyle style = this.getStyle(workbook); // 单元格样式对象
			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0,
					(headers.length - 1)));
			cellTiltle.setCellStyle(columnTopStyle);
			cellTiltle.setCellValue(title);

			// 定义所需列数
			int columnNum = headers.length;
			HSSFRow row = sheet.createRow(2); // 在索引2的位置创建行(最顶端的行开始的第二行)

			// 将列头设置到sheet的单元格中
			for (int n = 0; n < columnNum; n++) {
				HSSFCell cellRowName = row.createCell(n); // 创建列头对应个数的单元格
				cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING); // 设置列头单元格的数据类型
				HSSFRichTextString text = new HSSFRichTextString(headers[n]);
				cellRowName.setCellValue(text); // 设置列头单元格的值
				cellRowName.setCellStyle(columnTopStyle); // 设置列头单元格样式
			}
			HSSFCell cell;
			// 创建数据
			for (int i = 0; i < dataList.size(); i++) {
				row = sheet.createRow(i + 3);
				for (int ci = 0; ci < data.length; ci++) {
					cell = row.createCell(ci);
					Object value = dataList.get(i).getField(data[ci]);
					String textValue = null;
					if (value instanceof Date) {
						Date date = (Date) value;
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd hh:ss:mm");
						textValue = sdf.format(date);
					} else if (value == null) {
						textValue = "";
					} else {
						// 其它数据类型都当作字符串简单处理
						textValue = value.toString();
					}
					cell.setCellValue(textValue);
				}
			}

			// 让列宽随着导出的列长自动适应
			for (int colNum = 0; colNum < columnNum; colNum++) {
				int columnWidth = sheet.getColumnWidth(colNum) / 256;
				for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
					HSSFRow currentRow;
					// 当前行未被使用过
					if (sheet.getRow(rowNum) == null) {
						currentRow = sheet.createRow(rowNum);
					} else {
						currentRow = sheet.getRow(rowNum);
					}
					if (currentRow.getCell(colNum) != null) {
						HSSFCell currentCell = currentRow.getCell(colNum);
						if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							int length = currentCell.getStringCellValue()
									.getBytes().length;
							if (columnWidth < length) {
								columnWidth = length;
							}
						}
					}
				}
				if (colNum == 0) {
					sheet.setColumnWidth(colNum, (columnWidth - 2) * 256);
				} else {
					sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			baos.flush();
			workbook.write(baos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] ba = baos.toByteArray();
		ByteArrayInputStream bais = new ByteArrayInputStream(ba);
		return bais;

	}

	/*
	 * 列头单元格样式
	 */
	public HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {

		// 设置字体
		HSSFFont font = workbook.createFont();
		// 设置字体大小
		font.setFontHeightInPoints((short) 11);
		// 字体加粗
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 设置字体名字
		font.setFontName("Courier New");
		// 设置样式;
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置底边框;
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// 设置底边框颜色;
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		// 设置左边框;
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		// 设置左边框颜色;
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		// 设置右边框;
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// 设置右边框颜色;
		style.setRightBorderColor(HSSFColor.BLACK.index);
		// 设置顶边框;
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		// 设置顶边框颜色;
		style.setTopBorderColor(HSSFColor.BLACK.index);
		// 在样式用应用设置的字体;
		style.setFont(font);
		// 设置自动换行;
		style.setWrapText(false);
		// 设置水平对齐的样式为居中对齐;
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		return style;

	}

	/*
	 * 列数据信息单元格样式
	 */
	public HSSFCellStyle getStyle(HSSFWorkbook workbook) {
		// 设置字体
		HSSFFont font = workbook.createFont();
		// 设置字体大小
		// font.setFontHeightInPoints((short)10);
		// 字体加粗
		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 设置字体名字
		font.setFontName("Courier New");
		// 设置样式;
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置底边框;
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// 设置底边框颜色;
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		// 设置左边框;
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		// 设置左边框颜色;
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		// 设置右边框;
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// 设置右边框颜色;
		style.setRightBorderColor(HSSFColor.BLACK.index);
		// 设置顶边框;
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		// 设置顶边框颜色;
		style.setTopBorderColor(HSSFColor.BLACK.index);
		// 在样式用应用设置的字体;
		style.setFont(font);
		// 设置自动换行;
		style.setWrapText(false);
		// 设置水平对齐的样式为居中对齐;
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		return style;

	}

}