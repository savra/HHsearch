package com.hvdbs.savra.hhsearchmailservice.service;

import java.io.File;

public interface EmailService {
    void sendMessageWithAttachment(String to, String subject, String text, File file);
}