package com.hvdbs.savra.hhsearchmailservice.service;

import com.hvdbs.savra.hhsearchmailservice.model.entity.Report;
import com.hvdbs.savra.hhsearchmailservice.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;

    @Transactional
    @Override
    public void save(Report report) {
        reportRepository.save(report);
    }
}