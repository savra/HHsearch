package com.hvdbs.savra.hhsearchreportservice.service;

import com.hvdbs.savra.hhsearchreportservice.model.event.VacancyEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@EnableKafka
@Slf4j
public class KafkaConsumer {
    private final VacancyService vacancyService;
    @KafkaListener(topics = "${app.kafka.topic}", groupId = "${app.kafka.group-id}")
    public void listenGroupVacancies(VacancyEvent vacancyEvent) {
        log.info("Received Message about vacancy with name: " + vacancyEvent.getName());

        vacancyService.save(vacancyEvent);
    }
}
