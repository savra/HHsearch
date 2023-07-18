package com.hvdbs.savra.hhsearchreportservice.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
public class CurrencyRs {
    private OffsetDateTime lastDate;
    private OffsetDateTime previousDate;
    private String previousUrl;
    private List<Valute> valutes = new ArrayList<>();

    private static final class Valute {
        private String code;
        private String numCode;
        private int nominal;
        private String name;
        private double value;
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
