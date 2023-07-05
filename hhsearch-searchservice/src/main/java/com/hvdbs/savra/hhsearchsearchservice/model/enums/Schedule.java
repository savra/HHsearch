package com.hvdbs.savra.hhsearchsearchservice.model.enums;

import lombok.Getter;

@Getter
public enum Schedule {
    FULL_DAY("fullDay", "Полный день"),
    SHIFT("shift", "Сменный график"),
    FLEXIBLE("flexible", "Гибкий график"),
    REMOTE("remote", "Удаленная работа"),
    FLY_IN_FLY_OUT("flyInFlyOut", "Вахтовый метод");

    private final String id;
    private final String description;
    Schedule(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public static Schedule of(String id) {
        for(Schedule schedule : Schedule.values()) {
            if (schedule.getId().equals(id)) {
                return schedule;
            }
        }

        throw new IllegalArgumentException(String.format("No such enum constant for passed id %s", id));
    }
}
