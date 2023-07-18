package com.hvdbs.savra.hhsearchmailservice.service;

import com.hvdbs.savra.hhsearchmailservice.model.entity.Report;
import com.hvdbs.savra.hhsearchmailservice.model.event.ReportEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
@EnableKafka
@Slf4j
public class KafkaConsumer {
    private final ReportService reportService;

    @KafkaListener(topics = "${app.kafka.consumer.topic}", groupId = "${app.kafka.consumer.group-id}")
    public void listenGroupVacancies(ReportEvent reportEvent) throws IOException, SQLException {
        log.info("Received Message report with name: " + reportEvent.getName());

        Report report = new Report();
        report.setReportDate(LocalDateTime.now());
        report.setName(reportEvent.getName());
        report.setReportFile(new SerialBlob(Files.readAllBytes(reportEvent.getFile().toPath())));

        reportService.save(report);
    }
}
