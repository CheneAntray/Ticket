package com.test;

import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import com.base.utils.LogUtils;
public class ExcelTest {

	/**
	 * 测试添加
	 */
	public static void main(String[] args) {
		// 设置提交参数
		org.apache.log4j.Logger logger = LogUtils.getConsoleLogIns();
		String filePath = "D:/";
		String fileName = "2017年1月1日-1月31日乘车日期票款（累计）.xls";
		try {
			HSSFWorkbook wb = null;
			wb = new HSSFWorkbook(new FileInputStream(filePath+fileName));
			int sheetIndex = wb.getNumberOfSheets();
			/*for (int i = 0; i < sheetIndex; i++) {
				List<Map<Integer, String>> list = ExcelUtils
						.get2003ExcelContextByFileSheetIndex(filePath, fileName, i);*/
				
				
				
				
				
				List<Map<Integer, String>> list = ExcelUtils
						.get2003ExcelContextByFileSheetIndex(filePath, fileName, 3);
				for (Map<Integer, String> contextColMap : list) {
					/*if (contextColMap != null && !contextColMap.isEmpty()) {
						if (contextColMap.get(0).equals("UNIT_ID")) {
							System.out.println("continue");
							continue;
						}
						if (contextColMap.get(0).equals("0")) {
							System.out.println("break");
							continue;
						}
					}*/
					System.out.println("--------------------");
					System.out.println(contextColMap.get(1));
					System.out.println(contextColMap.get(2));
					System.out.println(contextColMap.get(3));
					System.out.println(contextColMap.get(4));
					System.out.println(contextColMap.get(5));
					System.out.println(contextColMap.get(6));
					System.out.println(contextColMap.get(7));
					System.out.println("____________________");
				}
				System.out.println(list.size());
			/*}*/
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("EX:", e);
		}
	}

	
}
