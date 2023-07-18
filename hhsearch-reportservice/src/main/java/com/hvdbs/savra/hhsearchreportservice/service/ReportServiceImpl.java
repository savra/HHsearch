package com.hvdbs.savra.hhsearchreportservice.service;

import com.hvdbs.savra.hhsearchreportservice.model.dto.CurrencyRs;
import com.hvdbs.savra.hhsearchreportservice.model.dto.VacancyDto;
import com.hvdbs.savra.hhsearchreportservice.model.event.ReportEvent;
import lombok.RequiredArgsConstructor;
import org.apache.poi.util.TempFile;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReportServiceImpl implements ReportService {
    private static final List<String> HEADER = List.of("Название вакансии", "Необходимое кол-во опыта", "URL",
            "Зарплата после вычета налога (чистыми) в рублях по курсу ЦБ на сегодня", "Список ключевых навыков");

    @Value(value = "${app.kafka.producer.topic}")
    private String kafkaTopic;
    private final VacancyService vacancyService;
    private final RestTemplate restTemplate;
    private final KafkaTemplate<String, ReportEvent> kafkaTemplate;

    @Override
    public byte[] getReport() throws IOException {
        //    CurrencyRs currencyRs = findCurrencies();  TODO Добавить получение ставок ЦБ с сайта, сейчас проблема с TLS
        List<VacancyDto> vacancyDtos = vacancyService.findAll();

        try (SXSSFWorkbook wb = new SXSSFWorkbook()) {
            SXSSFSheet sheet = wb.createSheet();
            sheet.trackAllColumnsForAutoSizing();

            int rowIndex = 0;

            SXSSFRow headerRow = sheet.createRow(rowIndex++);

            for (int i = 0; i < HEADER.size(); i++) {
                headerRow.createCell(i).setCellValue(HEADER.get(i));
                sheet.autoSizeColumn(i);
            }

            int columnIndex = 0;

            for (VacancyDto vacancyDto : vacancyDtos) {
                SXSSFRow row = sheet.createRow(rowIndex);
                row.createCell(columnIndex++).setCellValue(vacancyDto.getName());
                row.createCell(columnIndex++).setCellValue(vacancyDto.getExperience());
                row.createCell(columnIndex++).setCellValue(vacancyDto.getUrl());

                BigDecimal lowerBoundarySalary = vacancyDto.getLowerBoundarySalary();
                BigDecimal upperBoundarySalary = vacancyDto.getUpperBoundarySalary();
                String salary = "";

                if (lowerBoundarySalary != null) {
                    salary = "От " + lowerBoundarySalary;

                    if (upperBoundarySalary != null) {
                        salary += " до " + upperBoundarySalary;
                    }
                } else {
                    if (upperBoundarySalary != null) {
                        salary += "До " + upperBoundarySalary;
                    }
                }

                row.createCell(columnIndex++).setCellValue(salary);
                row.createCell(columnIndex).setCellValue(vacancyDto.getKeySkills());
                rowIndex++;
                columnIndex = 0;
            }

            File report = TempFile.createTempFile("report", ".tmp");

            try (FileOutputStream fileOutputStream = new FileOutputStream(report)) {
                wb.write(fileOutputStream);
                wb.dispose();
            }

            ReportEvent reportEvent = new ReportEvent();
            reportEvent.setFile(report);
            reportEvent.setName(report.getName());

            kafkaTemplate.send(kafkaTopic, reportEvent);
            return Files.readAllBytes(report.toPath());
        }
    }

    private CurrencyRs findCurrencies() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        return restTemplate.getForObject("https://www.cbr-xml-daily.ru/daily_json.js", CurrencyRs.class);
    }
}
