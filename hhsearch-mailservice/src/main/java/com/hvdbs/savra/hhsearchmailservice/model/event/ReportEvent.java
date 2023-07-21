package com.hvdbs.savra.hhsearchmailservice.model.event;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReportEvent {
    private String name;
    private byte[] file;
}
