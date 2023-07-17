package com.hvdbs.savra.hhsearchreportservice.service;

import com.hvdbs.savra.hhsearchreportservice.model.event.VacancyEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@EnableKafka
@Slf4j
public class KafkaConsumer {
    @KafkaListener(topics = "${app.kafka.topic}", groupId = "${app.kafka.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    public void listenGroupVacancies(VacancyEvent message) {
        log.info("Received Message in group: " + message);
    }
}
