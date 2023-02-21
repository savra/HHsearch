package com.hvdbs.hhsearch.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hvdbs.hhsearch.model.enums.Employment;
import com.hvdbs.hhsearch.model.enums.Experience;
import com.hvdbs.hhsearch.model.enums.Schedule;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @JsonProperty(value = "shedule.id")
    private Schedule schedule;
    @JsonProperty(value = "accept_handicapped")
    private boolean acceptHandicapped;
    @JsonProperty(value = "accept_kids")
    private boolean acceptKids;
    @JsonProperty(value = "experience.id")
    private Experience experience;
    @JsonProperty(value = "alternate_url")
    @NotNull
    private String alternateUrl;
    @JsonProperty(value = "apply_alternate_url")
    @NotNull
    private String applyAlternateUrl;
    private String code;
    @JsonProperty(value = "employment.id")
    private Employment employment;

    @Getter
    public static class KeySkill {
        private String name;
    }
}
