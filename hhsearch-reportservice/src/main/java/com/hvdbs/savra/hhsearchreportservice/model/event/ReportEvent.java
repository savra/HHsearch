package com.hvdbs.savra.hhsearchreportservice.model.event;

import lombok.Getter;
import lombok.Setter;

import java.io.File;

@Setter
@Getter
public class ReportEvent {
    private String name;
    private File file;
}
