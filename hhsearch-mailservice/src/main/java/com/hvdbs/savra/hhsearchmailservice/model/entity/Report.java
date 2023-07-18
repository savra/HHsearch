package com.hvdbs.savra.hhsearchmailservice.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Table(name = "report")
@Entity
public class Report {
    @Id
    @GeneratedValue(generator = "report_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "report_id_seq", sequenceName = "report_id_seq", allocationSize = 1)
    private Long id;
    private String name;
    private byte[] reportFile;
    private LocalDateTime reportDate;
}
