package com.hvdbs.savra.hhsearchmailservice.service;

import com.hvdbs.savra.hhsearchmailservice.model.entity.Report;
import com.hvdbs.savra.hhsearchmailservice.model.event.ReportEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
@EnableKafka
@Slf4j
public class KafkaConsumer {
    private final ReportService reportService;
    private final EmailService emailService;

    @KafkaListener(topics = "${app.kafka.consumer.topic}", groupId = "${app.kafka.consumer.group-id}")
    public void listenGroupVacancies(ReportEvent reportEvent) throws IOException {
        log.info("Received Message report with name: " + reportEvent.getName());

        Report report = new Report();
        report.setReportDate(LocalDateTime.now());
        report.setName(reportEvent.getName());

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        IOUtils.copy(new FileInputStream(reportEvent.getFile()), output);
        byte[] fileContent = output.toByteArray();

        report.setReportFile(fileContent);

        reportService.save(report);

        emailService.sendMessageWithAttachment("savra.sv@yandex.ru",
                "Статистика по HH.ru",
                "Статистика на " + report.getReportDate(),
                reportEvent.getFile());
    }
}
