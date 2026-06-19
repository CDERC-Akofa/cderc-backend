package com.cderc.backend.dto;

import com.cderc.backend.model.CleaningSchedule;

import java.time.LocalDate;

public class CleaningScheduleResponse {

    private Long id;
    private LocalDate weekStart;
    private LocalDate weekEnd;
    private Long personOneId;
    private String personOneName;
    private Long personTwoId;
    private String personTwoName;

    public CleaningScheduleResponse(Long id, LocalDate weekStart, LocalDate weekEnd,
                                    Long personOneId, String personOneName,
                                    Long personTwoId, String personTwoName) {
        this.id = id;
        this.weekStart = weekStart;
        this.weekEnd = weekEnd;
        this.personOneId = personOneId;
        this.personOneName = personOneName;
        this.personTwoId = personTwoId;
        this.personTwoName = personTwoName;
    }

    public Long getId() { return id; }
    public LocalDate getWeekStart() { return weekStart; }
    public LocalDate getWeekEnd() { return weekEnd; }
    public Long getPersonOneId() { return personOneId; }
    public String getPersonOneName() { return personOneName; }
    public Long getPersonTwoId() { return personTwoId; }
    public String getPersonTwoName() { return personTwoName; }

    public static CleaningScheduleResponse toResponse(CleaningSchedule schedule) {
        return new CleaningScheduleResponse(
                schedule.getId(),
                schedule.getWeekStart(),
                schedule.getWeekEnd(),
                schedule.getPersonOne().getId(),
                schedule.getPersonOne().getFirstName() + " " + schedule.getPersonOne().getLastName(),
                schedule.getPersonTwo().getId(),
                schedule.getPersonTwo().getFirstName() + " " + schedule.getPersonTwo().getLastName()
        );
    }
}
