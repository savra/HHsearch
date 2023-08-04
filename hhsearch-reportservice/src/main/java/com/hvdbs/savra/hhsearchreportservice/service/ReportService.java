package com.hvdbs.savra.hhsearchreportservice.service;

import com.hvdbs.savra.hhsearchreportservice.model.dto.CurrencyRs;
import com.hvdbs.savra.hhsearchreportservice.model.entity.Vacancy;
import com.hvdbs.savra.hhsearchreportservice.model.event.ReportEvent;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.util.TempFile;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReportService {
    private static final List<String> HEADER = List.of("Название вакансии", "Необходимое кол-во опыта", "URL",
            "Зарплата после вычета налога (чистыми) в рублях по курсу ЦБ на сегодня", "Список ключевых навыков");
    @Value(value = "${app.kafka.producer.topic}")
    private String kafkaTopic;
    private final VacancyService vacancyService;
    private final KafkaTemplate<String, ReportEvent> kafkaTemplate;

    public byte[] getReport() throws IOException {
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

            List<Vacancy> vacancies = vacancyService.findAll();

            /*Для тестов bulkhead и т.п.
            Stream.iterate(1, t -> t + 1)
                    .limit(10)
                    .parallel()
                    .forEach(integer -> vacancyService.findCurrencies());*/

            for (Vacancy vacancy : vacancies) {
                SXSSFRow row = sheet.createRow(rowIndex);
                row.createCell(columnIndex++).setCellValue(vacancy.getName());
                row.createCell(columnIndex++).setCellValue(vacancy.getExperience());
                row.createCell(columnIndex++).setCellValue(vacancy.getUrl());

                String currency = vacancy.getCurrency();

                CurrencyRs.Valute valute = null;

                if (currency != null && !"RUR".equals(currency)) {
                    if ("BYR".equals(currency)) {
                        currency = "BYN";
                    }

                    valute = vacancyService.findCurrencies().getValutes().get(currency);
                }

                row.createCell(columnIndex++).setCellValue(getSalary(vacancy, valute));
                row.createCell(columnIndex).setCellValue(vacancy.getKeySkills());
                rowIndex++;
                columnIndex = 0;
            }

            File report = TempFile.createTempFile("report", ".xlsx");

            try (FileOutputStream fileOutputStream = new FileOutputStream(report)) {
                wb.write(fileOutputStream);
                wb.dispose();
            }

            byte[] bytes = Files.readAllBytes(report.toPath());

            ReportEvent reportEvent = new ReportEvent();
            reportEvent.setFile(bytes);
            reportEvent.setName(FilenameUtils.removeExtension(report.getName()));

            kafkaTemplate.send(kafkaTopic, reportEvent);

            return bytes;
        }
    }

    private BigDecimal getNormalizeSalary(Vacancy vacancy, BigDecimal salary, CurrencyRs.Valute valute) {
        if (salary != null) {
            if (valute != null) {
                salary = salary.multiply(BigDecimal.valueOf(valute.getValue())
                        .divide(BigDecimal.valueOf(valute.getNominal()), RoundingMode.HALF_EVEN));

                if (vacancy.getGross()) {
                    salary = salary.subtract(salary.multiply(BigDecimal.valueOf(0.13)));
                }
            }
        }
        return salary;
    }

    private String getSalary(Vacancy vacancy, CurrencyRs.Valute valute) {
        BigDecimal lowerBoundarySalary = getNormalizeSalary(vacancy, vacancy.getLowerBoundarySalary(), valute);
        BigDecimal upperBoundarySalary = getNormalizeSalary(vacancy, vacancy.getUpperBoundarySalary(), valute);

        String salary = "";

        if (lowerBoundarySalary != null) {
            salary = "От " + lowerBoundarySalary.setScale(2, RoundingMode.HALF_EVEN);

            if (upperBoundarySalary != null) {
                salary += " до " + upperBoundarySalary.setScale(2, RoundingMode.HALF_EVEN);
            }
        } else {
            if (upperBoundarySalary != null) {
                salary += "До " + upperBoundarySalary.setScale(2, RoundingMode.HALF_EVEN);
            }
        }

        return salary;
    }
}
