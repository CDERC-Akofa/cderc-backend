package com.cderc.backend.service;

import com.cderc.backend.model.CleaningSchedule;
import com.cderc.backend.model.Member;
import com.cderc.backend.model.MemberStatus;
import com.cderc.backend.model.Organization;
import com.cderc.backend.repository.CleaningScheduleRepository;
import com.cderc.backend.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Service
public class CleaningScheduleService {

    private final CleaningScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;

    public CleaningScheduleService(CleaningScheduleRepository scheduleRepository,
                                   MemberRepository memberRepository) {
        this.scheduleRepository = scheduleRepository;
        this.memberRepository = memberRepository;
    }

    public List<CleaningSchedule> generate(LocalDate startDate,
                                           int numberOfWeeks,
                                           Organization organization) {

        List<Member> members = memberRepository.findByOrganizationIdAndStatus(
                organization.getId(),
                MemberStatus.ACTIVE
        );

        if (members.size() < 2) {
            throw new RuntimeException("At least two active members are required");
        }

        List<CleaningSchedule> schedules = new ArrayList<>();

        int index = 0;
        for (int i = 0; i < numberOfWeeks; i++) {
            Member personOne = members.get(index % members.size());
            Member personTwo = members.get((index + 1) % members.size());

            CleaningSchedule schedule = new CleaningSchedule();
            schedule.setWeekStart(startDate.plusWeeks(i));
            schedule.setWeekEnd(startDate.plusWeeks(i).plusDays(6));
            schedule.setPersonOne(personOne);
            schedule.setPersonTwo(personTwo);
            schedule.setOrganization(organization);

            schedules.add(schedule);

            index += 2;
        }

        return scheduleRepository.saveAll(schedules);
    }

    public List<CleaningSchedule> findAll(Long organizationId) {
        return scheduleRepository.findByOrganizationIdOrderByWeekStartAsc(organizationId);
    }
}
