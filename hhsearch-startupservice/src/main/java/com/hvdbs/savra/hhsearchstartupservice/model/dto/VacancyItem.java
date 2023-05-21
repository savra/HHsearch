package com.hvdbs.savra.hhsearchstartupservice.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hvdbs.savra.hhsearchstartupservice.model.enums.Employment;
import com.hvdbs.savra.hhsearchstartupservice.model.enums.Experience;
import com.hvdbs.savra.hhsearchstartupservice.model.enums.Schedule;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class VacancyItem {
    @JsonProperty(value = "id")
    @NotNull
    private String vacancyId;
    @NotNull
    private String name;
    private boolean premium;
    private String description;
    @JsonProperty(value = "key_skills")
    private List<KeySkill> keySkills = new ArrayList<>();

    @JsonProperty("schedule")
    private void unpackSchedule(Map<String, Object> schedule) {
        if (schedule != null) {
            this.schedule = Schedule.of((String) schedule.get("id"));
        }
    }

    private Schedule schedule;
    @JsonProperty(value = "accept_handicapped")
    private boolean acceptHandicapped;
    @JsonProperty(value = "accept_kids")
    private boolean acceptKids;

    @JsonProperty("experience")
    private void unpackExperience(Map<String, Object> experience) {
        this.experience = Experience.of((String) experience.get("id"));
    }

    private Experience experience;
    @JsonProperty(value = "alternate_url")
    @NotNull
    private String alternateUrl;
    @JsonProperty(value = "apply_alternate_url")
    @NotNull
    private String applyAlternateUrl;

    private String code;

    private Salary salary;

    @JsonProperty("employment")
    private void unpackEmployment(Map<String, Object> employment) {
        this.employment = Employment.of((String) employment.get("id"));
    }

    private Employment employment;

    @Getter
    @Setter
    public static class KeySkill {
        private String name;
    }

    @Getter
    @Setter
    public static class Salary {
        private BigDecimal from;

        private BigDecimal to;

        private boolean gross;

        private String currency;
    }
}
