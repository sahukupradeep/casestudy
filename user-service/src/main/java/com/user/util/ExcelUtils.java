package com.user.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.user.entity.Audit;

@Component
public class ExcelUtils {

	public ByteArrayInputStream auditsToExcel(List<Audit> audits) throws IOException {
		String[] COLUMNs = { "Id", "UserName", "Activity", "Message" };
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			CreationHelper createHelper = workbook.getCreationHelper();

			Sheet sheet = workbook.createSheet("Audits");

			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.BLUE.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);

			// Row for Header
			Row headerRow = sheet.createRow(0);

			// Header
			for (int col = 0; col < COLUMNs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(COLUMNs[col]);
				cell.setCellStyle(headerCellStyle);
			}

			// CellStyle for Age
			CellStyle ageCellStyle = workbook.createCellStyle();
			ageCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));

			int rowIdx = 1;
			for (Audit audit : audits) {
				Row row = sheet.createRow(rowIdx++);

				row.createCell(0).setCellValue(audit.getId());
				row.createCell(1).setCellValue(audit.getUserName());
				row.createCell(2).setCellValue(audit.getActivity());
				row.createCell(3).setCellValue(audit.getMessage());
				// row.createCell(4).setCellValue(audit.getCreatedDate());

				/*
				 * Cell ageCell = row.createCell(4); ageCell.setCellValue(audit.getAge());
				 * ageCell.setCellStyle(ageCellStyle);
				 */
			}

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		}
	}

}