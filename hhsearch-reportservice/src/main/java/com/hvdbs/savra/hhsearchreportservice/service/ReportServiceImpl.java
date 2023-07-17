package com.hvdbs.savra.hhsearchreportservice.service;

import lombok.RequiredArgsConstructor;
import org.apache.poi.util.TempFile;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReportServiceImpl implements ReportService {
    private static final List<String> HEADER = List.of("Название вакансии", "Необходимое кол-во опыта", "url",
            "Зарплата после вычета налога (чистыми) в рублях по курсу ЦБ на сегодня", "Список ключевых навыков");

    private final VacancyService vacancyService;

    @Override
    public byte[] getReport() throws IOException {

        try (SXSSFWorkbook wb = new SXSSFWorkbook()) {
            SXSSFSheet sheet = wb.createSheet();
            sheet.trackAllColumnsForAutoSizing();

            int rowIndex = 0;

            SXSSFRow headerRow = sheet.createRow(rowIndex);

            for (int i = 0; i < HEADER.size(); i++) {
                headerRow.createCell(i).setCellValue(HEADER.get(i));
                sheet.autoSizeColumn(i);
            }

            File report = TempFile.createTempFile("report", ".tmp");

            try (FileOutputStream fileOutputStream = new FileOutputStream(report)) {
                wb.write(fileOutputStream);
                wb.dispose();
            }

            return Files.readAllBytes(report.toPath());
        }
    }
}
