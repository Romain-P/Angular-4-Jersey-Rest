package com.ortec.gta.database.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * @Author: romain.pillot
 * @Date: 14/09/2017
 */
@Accessors(chain = true)
@AllArgsConstructor
@Getter @Setter
public final class StatDTO {
    private int employees;
    private int lastWeekValidEmployees;
    private int lastMounthValidEmployees;
    private Set<ActivityStatDTO> lastWeekTop;
    private Set<ActivityStatDTO> lastMonthTop;

    @AllArgsConstructor
    @Getter @Setter
    public static final class ActivityStatDTO {
        private String name;
        private int manDays;
    }
}