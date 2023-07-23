package com.hvdbs.savra.hhsearchreportservice.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Setter
@Getter
public class CurrencyRs {
    @JsonProperty("Date")
    private OffsetDateTime lastDate;
    @JsonProperty("PreviousDate")
    private OffsetDateTime previousDate;
    @JsonProperty("PreviousURL")
    private String previousUrl;
    @JsonProperty("Valute")
    private Map<String, Valute> valutes = new HashMap<>();

    @Setter
    @Getter
    public static final class Valute {
        @JsonProperty("CharCode")
        private String code;
        @JsonProperty("NumCode")
        private String numCode;
        @JsonProperty("Nominal")
        private int nominal;
        @JsonProperty("Name")
        private String name;
        @JsonProperty("Value")
        private double value;
        @JsonProperty("Previous")
        private double previousValue;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Valute valute = (Valute) o;
            return code.equals(valute.code);
        }

        @Override
        public int hashCode() {
            return Objects.hash(code);
        }
    }
}
