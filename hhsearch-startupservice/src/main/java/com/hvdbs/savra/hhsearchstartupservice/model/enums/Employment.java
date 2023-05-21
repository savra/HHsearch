package com.hvdbs.savra.hhsearchstartupservice.model.enums;

import lombok.Getter;

@Getter
public enum Employment {
    FULL("full", "Полная занятость"),
    PART("part", "Частичная занятость"),
    PROJECT("project", "Проектная работа"),
    VOLUNTEER("volunteer", "Волонтерство"),
    PROBATION("probation", "Стажировка");

    private final String id;
    private final String description;

    Employment(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public static Employment of(String id) {
        for(Employment employment : Employment.values()) {
            if (employment.getId().equals(id)) {
                return employment;
            }
        }

        throw new IllegalArgumentException(String.format("No such enum constant for passed id %s", id));
    }
}
