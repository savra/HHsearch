package com.hvdbs.hhsearch.model.enums;

import lombok.Getter;

@Getter
public enum Experience {
    NO_EXPERIENCE("noExperience", "Нет опыта"),
    BETWEEN_1_AND_3("between1And3", "От 1 года до 3 лет"),
    BETWEEN_3_AND_6("between3And6", "От 3 до 6 лет"),
    MORE_THAN_6("moreThan6", "Более 6 лет");

    private final String id;
    private final String description;

    Experience(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public static Experience of(String id) {
        for(Experience experience : Experience.values()) {
            if (experience.getId().equals(id)) {
                return experience;
            }
        }

        throw new IllegalArgumentException(String.format("No such enum constant for passed id %s", id));
    }
}
