package com.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.base.utils.LogUtils;

/**
 * Excel工具类 该工具类同时兼容97(.xls)格式与2003(.xlsx)格式的excel文件读写
 * 
 * @author jiangdong
 *
 */
public class ExcelUtils {
	private static Logger logger = LogUtils.getConsoleLogIns();

	/**
	 * 将执行开始行、列使用List结构返回,支持2003以下版本的EXCEL文件(.xls) 读取文件中指定的列信息
	 * 注意，行列的读取和自然读取不一样，最左上角的行号为0，列号为0
	 * 
	 * @param excelFilePath
	 *            文件路径
	 * @param excelFileName
	 *            文件名称
	 * @param sheetName
	 *            工作簿名称
	 * @param startRow
	 *            读取目标列 0开始，从左往右依次递增1
	 * @param startCol
	 *            开始行 从0开始，从上往下递增1
	 * @param endCol
	 *            结束行 从0开始，从上往下递增1
	 * @return
	 * @throws Exception
	 */
	

	/**
	 * 将执行开始行、列使用List结构返回支持2003以下版本的EXCEL文件(.xls)
	 * List结构中包含Map对象实例，每一个Map对象实例，key=列号 value=列内容 该方法的会将列内容通通保存为字符串类型
	 * 
	 * @param excelFilePath
	 *            文件路径
	 * @param excelFileName
	 *            文件名称
	 * @param sheetName
	 *            工作簿名称
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public static List<Map<Integer, String>> get2003ExcelContextByFile(String excelFilePath, String excelFileName,
			String sheetName) throws Exception {
		Cell cell = null;
		HSSFSheet sheet = null;
		HSSFWorkbook wb = null;
		HSSFRow rows = null;
		// 保存一行内容的数据结构
		Map<Integer, String> rowMap = null;
		wb = new HSSFWorkbook(new FileInputStream(excelFilePath + excelFileName));
		// 总列数
		int coloumNum = 0;
		// 总行数
		int rowNum = 0;
		if (sheetName != null && !"".equals(sheetName)) {
			// 如果工作簿名称不为空，则直接定位到工作簿
			sheet = wb.getSheetAt(wb.getSheetIndex(sheetName));
		} else {
			// 如果工作簿名称为空，则直接定位到第一个工作簿
			sheet = wb.getSheetAt(0);
		}
		// 获得总列数
		coloumNum = sheet.getRow(0).getPhysicalNumberOfCells();
		rowNum = sheet.getLastRowNum() + 1;// 获得总行数
		List<Map<Integer, String>> returnList = new ArrayList<Map<Integer, String>>(rowNum);
		for (int row = 0; row < rowNum; row++) {

			// 获取一行
			rows = sheet.getRow(row);
			// 循环每一列
			if (rows == null) {
				continue;
			}
			rowMap = new HashMap<Integer, String>(coloumNum);
			for (int i = 0; i < coloumNum; i++) {
				cell = rows.getCell(i);
				// 获取string格式的单元格内容
				if (cell != null) {
					rowMap.put(i, getCellContextToStr(cell));
				}

			}
			returnList.add(rowMap);
		}
		return returnList;
	}

	/**
	 * 获取2007版Excel文件值(.xlsx) 读取固定列上的所有内容，使用List数据结构返回
	 * 
	 * @param xlsFileIns
	 *            excel文件实例
	 * @param sheetName
	 *            sheet页名称
	 * @param rowNO
	 *            列数 从0开始，从左至右递增
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public static List<String> getColValue(File xlsFileIns, String sheetName, int rowNO) throws Exception {
		Cell cell = null;
		HSSFSheet sheet = null;
		HSSFWorkbook wb = null;
		wb = new HSSFWorkbook(new FileInputStream(xlsFileIns));
		if (sheetName != null && !"".equals(sheetName)) {
			sheet = wb.getSheetAt(wb.getSheetIndex(sheetName));
		} else {
			sheet = wb.getSheetAt(0);
		}
		HSSFRow rows = null;
		List<String> returnList = new ArrayList<String>();
		int size = sheet.getLastRowNum() + 1;
		int startCol = 0;
		for (; startCol < size; startCol++) {
			rows = sheet.getRow(startCol);
			cell = rows.getCell(rowNO);
			returnList.add(cell.getStringCellValue());
		}
		return returnList;
	}

	/**
	 * liyun
	 * 
	 * 获取2007版Excel文件 读取固定列上的所有内容，使用List数据结构返回
	 * 
	 * @param xlsFileIns
	 *            excel文件实例
	 * @param sheetName
	 *            sheet页名称
	 * @param rowNO
	 *            列数 从0开始，从左至右递增
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public static List<String> getColValue2007(File xlsFileIns, String sheetName, int rowNO) throws Exception {
		Cell cell = null;
		XSSFSheet sheet = null;
		XSSFRow rows = null;
		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(xlsFileIns));
		if (sheetName != null && !"".equals(sheetName)) {
			sheet = wb.getSheetAt(wb.getSheetIndex(sheetName));
		} else {
			sheet = wb.getSheetAt(0);
		}
		List<String> returnList = new ArrayList<String>();
		int size = sheet.getLastRowNum() + 1;
		int startCol = 1;
		for (; startCol < size; startCol++) {
			rows = sheet.getRow(startCol);
			cell = rows.getCell(rowNO);
			returnList.add(cell.getStringCellValue());
		}
		return returnList;
	}

	/**
	 * 将执行开始行、列使用List结构返回支持2007以下版本的EXCEL文件(.xlsx)
	 * List结构中包含Map对象实例，每一个Map对象实例，key=列号 value=列内容 该方法的会将列内容通通保存为字符串类型
	 * 
	 * @param excelFilePath
	 *            文件路径
	 * @param excelFileName
	 *            文件名称
	 * @param sheetName
	 *            工作簿名称
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public static List<Map<Integer, String>> get2007ExcelContextByFile(String excelFilePath, String excelFileName,
			String sheetName) throws Exception {
		Cell cell = null;
		XSSFSheet sheet = null;
		XSSFRow rows = null;
		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(excelFilePath + excelFileName));
		// 保存一行内容的数据结构
		Map<Integer, String> rowMap = null;
		wb = new XSSFWorkbook(new FileInputStream(excelFilePath + excelFileName));
		// 总列数
		int coloumNum = 0;
		// 总行数
		int rowNum = 0;
		if (sheetName != null && !"".equals(sheetName)) {
			// 如果工作簿名称不为空，则直接定位到工作簿
			sheet = wb.getSheetAt(wb.getSheetIndex(sheetName));
		} else {
			// 如果工作簿名称为空，则直接定位到第一个工作簿
			sheet = wb.getSheetAt(0);
		}
		// 获得总列数
		coloumNum = sheet.getRow(0).getPhysicalNumberOfCells();
		rowNum = sheet.getLastRowNum() + 1;// 获得总行数
		List<Map<Integer, String>> returnList = new ArrayList<Map<Integer, String>>(rowNum);
		for (int row = 0; row < rowNum; row++) {
			// 获取一行
			rows = sheet.getRow(row);
			// 循环每一列
			if (rows != null) {
				rowMap = new HashMap<Integer, String>(coloumNum);
				for (int i = 0; i < coloumNum; i++) {
					cell = rows.getCell(i);
					// 获取string格式的单元格内容
					if (cell != null) {
						rowMap.put(i, getCellContextToStr(cell));
					}
				}
				returnList.add(rowMap);
			}
		}
		return returnList;
	}

	/**
	 * 将数据结构输出到2003版Exccel文件中
	 * 
	 * @param filePath
	 *            文件路径
	 * @param fileName
	 *            文件名称
	 * @param fileContext
	 *            文件内容
	 * @param keyArray
	 *            文件列名数组
	 * @param isAppendHead
	 *            是否构造列头
	 * @param isNewFileFlag
	 *            是否新建文件
	 * @throws Exception
	 */
	@SuppressWarnings({ "resource", "deprecation" })
	public static void outPotToExcel(String filePath, String fileName, String sheetName,
			Map<String, List<String>> fileContext, String[] keyArray, boolean isAppendHead, boolean isNewFileFlag)
			throws Exception {
		// 工作簿对象
		HSSFSheet sheet = null;
		// 行对象
		HSSFRow row = null;
		// 单元格对象
		HSSFCell cell = null;
		// 数据的起始行数
		int startDataRow = 0;
		POIFSFileSystem ps = null;
		File excelFile = new File(filePath + fileName);
		// 如果文件不存在，则创建一个新的SHEET页,防止文件输入流读入文件头错误的文件
		if (!excelFile.isFile()) {
			excelFile.createNewFile();
			HSSFWorkbook wb = new HSSFWorkbook();
			wb.createSheet("DefSheet");
			FileOutputStream fout = new FileOutputStream(excelFile);
			fout.flush();
			wb.write(fout);
			fout.close();
		}
		ps = new POIFSFileSystem(new FileInputStream(filePath + fileName));
		// 取得Map数据结构的key数组
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook(ps);
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		sheet = wb.createSheet(sheetName);
		// 取得最大长度的list作为循环参数
		int listSize = getMaxLeng(fileContext);
		int size = keyArray.length;
		String key;
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数
		if (isAppendHead) {
			row = sheet.createRow((int) 0);
			// 第四步，创建单元格，并设置值表头 设置表头居中
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
			int index = 0;
			for (; index < size; index++) {
				cell = row.createCell(index);
				cell.setCellValue(keyArray[index]);
				cell.setCellStyle(style);
			}
			startDataRow = 1;
		}
		int index = 0;
		for (; index < listSize; index++) {
			row = sheet.createRow(startDataRow + index);
			// 第四步，创建单元格，并设置值
			for (int j = 0; j < size; j++) {
				key = keyArray[j];
				cell = row.createCell(j);
				cell.setCellValue(getStrByKeyAndIndex(fileContext, key, index, false));
			}
		}
		// 将文件存到指定位置
		FileOutputStream fout = new FileOutputStream(filePath + fileName);
		fout.flush();
		wb.write(fout);
		fout.close();
	}

	/**
	 * liyun
	 * 
	 * 将数据结构输出到2007版Excel文件中
	 * 
	 * @param filePath
	 *            文件路径
	 * @param fileName
	 *            文件名称
	 * @param fileContext
	 *            文件内容
	 * @param keyArray
	 *            文件列名数组
	 * @param isAppendHead
	 *            是否构造列头
	 * @param isNewFileFlag
	 *            是否新建文件
	 * @throws Exception
	 */
	@SuppressWarnings({ "resource", "deprecation" })
	public static void outPotToExcel2007(String filePath, String fileName, String sheetName,
			Map<String, List<String>> fileContext, String[] keyArray, boolean isAppendHead, boolean isNewFileFlag)
			throws Exception {
		// 工作簿对象
		XSSFSheet sheet = null;
		// 行对象
		XSSFRow row = null;
		// 单元格对象
		XSSFCell cell = null;
		// 数据的起始行数
		int startDataRow = 0;
		XSSFWorkbook wb = null;
		File excelFile = new File(filePath + fileName);
		// 如果文件不存在，则创建一个新的SHEET页,防止文件输入流读入文件头错误的文件
		if (!excelFile.isFile()) {
			excelFile.createNewFile();
			wb = new XSSFWorkbook();
			wb.createSheet("DefSheet");
			FileOutputStream fout = new FileOutputStream(excelFile);
			fout.flush();
			wb.write(fout);
			fout.close();
		}
		// POIFSFileSystem ps=new POIFSFileSystem(new
		// FileInputStream(filePath+fileName));
		FileInputStream fileInput = new FileInputStream(filePath + fileName);
		// 取得Map数据结构的key数组
		// 第一步，创建一个webbook，对应一个Excel文件
		wb = new XSSFWorkbook(fileInput);
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		sheet = wb.createSheet(sheetName);
		// 取得最大长度的list作为循环参数
		int listSize = getMaxLeng(fileContext);
		int size = keyArray.length;
		String key;
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数
		if (isAppendHead) {
			row = sheet.createRow((int) 0);
			// 第四步，创建单元格，并设置值表头 设置表头居中
			XSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
			int index = 0;
			for (; index < size; index++) {
				cell = row.createCell(index);
				cell.setCellValue(keyArray[index]);
				cell.setCellStyle(style);
			}
			startDataRow = 1;
		}
		int index = 0;
		for (; index < listSize; index++) {
			row = sheet.createRow(startDataRow + index);
			// 第四步，创建单元格，并设置值
			for (int j = 0; j < size; j++) {
				key = keyArray[j];
				cell = row.createCell(j);
				cell.setCellValue(getStrByKeyAndIndex(fileContext, key, index, false));
			}
		}
		// 将文件存到指定位置
		FileOutputStream fout = new FileOutputStream(filePath + fileName);
		fout.flush();
		wb.write(fout);
		fout.close();
	}

	/**
	 * 取得数据结构中长度最大的List.size
	 * 
	 * @param map
	 * @return
	 */
	private static int getMaxLeng(Map<String, List<String>> map) {
		int maxSize = 0;
		Iterator<Entry<String, List<String>>> iter = map.entrySet().iterator();
		List<String> value;
		int arraySize = 0;
		Entry<String, List<String>> entry;
		while (iter.hasNext()) {
			entry = iter.next();
			value = map.get(entry.getKey());
			arraySize = value.size();
			if (arraySize > maxSize) {
				maxSize = arraySize;
			}
		}
		return maxSize;
	}

	/**
	 * 取得数据结构中key对应的数组固定下标的值
	 * 
	 * @param map
	 *            数据结构
	 * @param key
	 *            key值
	 * @param index
	 *            key值对应的数据下标
	 * @param appendNull
	 *            如果为空，是否返回空字串
	 * @return
	 */
	private static String getStrByKeyAndIndex(Map<String, List<String>> map, String key, int index, boolean appendNull)
			throws Exception {
		List<String> contextArray;
		int arraySize = 0;
		if (map.containsKey(key)) {
			contextArray = map.get(key);
			arraySize = contextArray.size();
			// 如果下标不存在
			if (index > arraySize && appendNull) {
				return "";
			} else if (index > arraySize && !appendNull) {
				throw new Exception(key + "的指定下标为空");
			} else {
				return contextArray.get(index);
			}
		} else {
			throw new Exception(key + ":key值不存在");
		}
	}

	/**
	 * 从单元格对象实例中获取内容信息后转换为字符串对象实例返回 CELL_TYPE_NUMERIC 数值型 0 CELL_TYPE_STRING
	 * 字符串型 1 CELL_TYPE_FORMULA 公式型 2 CELL_TYPE_BLANK 空值 3 CELL_TYPE_BOOLEAN 布尔型
	 * 4 CELL_TYPE_ERROR 错误 5
	 * 
	 * @param cellIns
	 *            单元格实例
	 * @return 单元格内容的字符串形式
	 */
	private static String getCellContextToStr(Cell cellIns) {
		CellType cellType = cellIns.getCellTypeEnum();
		switch (cellType) {
		case STRING:// 字符类型
			return cellIns.getStringCellValue().trim();
		case NUMERIC:// 数字
			if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cellIns)) {// 日期类型处理
				SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				return dff.format(cellIns.getDateCellValue());
			} else {
				DecimalFormat df = new DecimalFormat("#.##");// 数据类型处理
				return df.format(cellIns.getNumericCellValue());
			}
		case BLANK:// 空
			return " ";
		case BOOLEAN:// 布尔值
			return String.valueOf(cellIns.getBooleanCellValue());
		case ERROR:// 错误
			return "";
		case FORMULA:// 公式类型
			try {
				return String.valueOf(cellIns.getNumericCellValue());// 尝试用数字类型读取
			} catch (IllegalStateException e) {
				return String.valueOf(cellIns.getRichStringCellValue());// 出现错误，则使用字符方式进行读取
			}
		default:
			return "";
		}
	}

	/**
	 * 将执行开始行、列使用List结构返回支持2003以下版本的EXCEL文件(.xls)
	 * List结构中包含Map对象实例，每一个Map对象实例，key=列号 value=列内容 该方法的会将列内容通通保存为字符串类型
	 * 
	 * @param excelFilePath
	 *            文件路径
	 * @param excelFileName
	 *            文件名称
	 * @param sheetIndex
	 *            工作簿下标
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public static List<Map<Integer, String>> get2003ExcelContextByFileSheetIndex(String excelFilePath,
			String excelFileName, Integer sheetIndex) throws Exception {
		Cell cell = null;
		HSSFSheet sheet = null;
		HSSFWorkbook wb = null;
		HSSFRow rows = null;
		// 保存一行内容的数据结构
		Map<Integer, String> rowMap = null;
		wb = new HSSFWorkbook(new FileInputStream(excelFilePath + excelFileName));
		// 总列数
		int coloumNum = 0;
		// 总行数
		int rowNum = 0;
		sheet = wb.getSheetAt(sheetIndex);
		// 获得总列数
		rowNum = sheet.getLastRowNum() + 1;// 获得总行数
		if (rowNum > 1) {
			coloumNum = sheet.getRow(0).getPhysicalNumberOfCells();
			List<Map<Integer, String>> returnList = new ArrayList<Map<Integer, String>>(rowNum);
			for (int row = 0; row < rowNum; row++) {

				// 获取一行
				rows = sheet.getRow(row);
				// 循环每一列
				if (rows == null) {
					continue;
				}
				rowMap = new HashMap<Integer, String>(coloumNum);
				for (int i = 0; i < coloumNum; i++) {
					cell = rows.getCell(i);
					// 获取string格式的单元格内容
					if (isMergedRegion(sheet, row, i)) {
						rowMap.put(i, getMergedRegionValue(sheet, row, i));
					}
					else {
						if (cell != null) {
							rowMap.put(i, getCellContextToStr(cell));
						}
					}
					

				}
				returnList.add(rowMap);
			}
			return returnList;
		}
		return null;
	}

	

	/**
	 * 判断指定的单元格是否是合并单元格
	 * 
	 * @param sheet
	 * @param row
	 *            行下标
	 * @param column
	 *            列下标
	 * @return
	 */
	private static boolean isMergedRegion(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range = sheet.getMergedRegion(i);
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			int firstRow = range.getFirstRow();
			int lastRow = range.getLastRow();
			if (row >= firstRow && row <= lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 获取合并单元格的值
	 * 
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	public static String getMergedRegionValue(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();

		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress ca = sheet.getMergedRegion(i);
			int firstColumn = ca.getFirstColumn();
			int lastColumn = ca.getLastColumn();
			int firstRow = ca.getFirstRow();
			int lastRow = ca.getLastRow();

			if (row >= firstRow && row <= lastRow) {

				if (column >= firstColumn && column <= lastColumn) {
					Row fRow = sheet.getRow(firstRow);
					Cell fCell = fRow.getCell(firstColumn);
					return getCellValue(fCell);
				}
			}
		}

		return null;
	}

	/**
	 * 获取单元格的值
	 * 
	 * @param cell
	 * @return
	 */
	public static String getCellValue(Cell cell) {
		if (cell == null)
			return "";
		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			return cell.getStringCellValue();
		} else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(cell.getBooleanCellValue());
		} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
			return cell.getCellFormula();
		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			return String.valueOf(cell.getNumericCellValue());
		}
		return "";
	}
	
	
}
