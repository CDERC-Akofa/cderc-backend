package com.cderc.backend.service;

import com.cderc.backend.dto.CategoryReportResponse;
import com.cderc.backend.dto.EventTotalReportResponse;
import com.cderc.backend.dto.MemberResponse;
import com.cderc.backend.dto.YearReportResponse;
import com.cderc.backend.mapper.MemberMapper;
import com.cderc.backend.model.Event;
import com.cderc.backend.model.MemberStatus;
import com.cderc.backend.model.MemberType;
import com.cderc.backend.repository.EventExpenseRepository;
import com.cderc.backend.repository.EventRepository;
import com.cderc.backend.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Slf4j
@Service
public class ReportService {

    private final EventExpenseRepository eventExpenseRepository;
    private final EventRepository eventRepository;
    private final MemberRepository memberRepository;

    public ReportService(EventExpenseRepository eventExpenseRepository,
                         EventRepository eventRepository, MemberRepository memberRepository) {
        this.eventExpenseRepository = eventExpenseRepository;
        this.eventRepository = eventRepository;
        this.memberRepository = memberRepository;
    }

    public EventTotalReportResponse getEventTotal(Long eventId, Long organizationId) {
        log.info("getEventTotal {} ", eventId + "-" + organizationId);
        Event event = eventRepository.findByIdAndOrganizationId(eventId, organizationId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        BigDecimal total = eventExpenseRepository.sumByEventAndOrganization(eventId, organizationId);

        return new EventTotalReportResponse(
                event.getId(),
                event.getTitle(),
                total
        );
    }

    public List<CategoryReportResponse> getEventCostsByCategory(Long eventId, Long organizationId) {
        log.info("getEventCostsByCategory {} ", eventId + "-" + organizationId);

        return eventExpenseRepository.sumByCategoryForEvent(eventId, organizationId)
                .stream()
                .map(row -> new CategoryReportResponse(
                        row[0].toString(),
                        (BigDecimal) row[1]
                ))
                .toList();
    }

    public YearReportResponse getCostsByYear(int year, Long organizationId) {
        log.info("getCostsByYear {} ", year + "-" + organizationId);

        BigDecimal total = eventExpenseRepository.sumByYear(organizationId, year);
        return new YearReportResponse(year, total);
    }

    public List<YearReportResponse> compareYears(int year1, int year2, Long organizationId) {
        return List.of(
                getCostsByYear(year1, organizationId),
                getCostsByYear(year2, organizationId)
        );
    }

    public BigDecimal getOrganizationTotal(Long organizationId) {
        return eventExpenseRepository.sumByOrganization(organizationId);
    }

    //member reporting start
    public long countActiveMembers(Long organizationId) {
        log.info("countActiveMembers {} ", organizationId);

        return memberRepository.countByOrganizationIdAndStatus(
                organizationId,
                MemberStatus.ACTIVE
        );
    }

    public long countSupportingMembers(Long organizationId) {
        return memberRepository.countByOrganizationIdAndType(
                organizationId,
                MemberType.SUPPORTING_MEMBER
        );
    }

    public List<MemberResponse> findBoardMembers(Long organizationId) {
        return memberRepository.findByOrganizationIdAndType(
                        organizationId,
                        MemberType.BOARD_MEMBER
                )
                .stream()
                .map(MemberMapper::toResponse)
                .toList();
    }

    public List<MemberResponse> findVolunteers(Long organizationId) {
        return memberRepository.findByOrganizationIdAndType(
                        organizationId,
                        MemberType.VOLUNTEER
                )
                .stream()
                .map(MemberMapper::toResponse)
                .toList();
    }

    public List<MemberResponse> findInactiveMembers(Long organizationId) {
        return memberRepository.findByOrganizationIdAndStatus(
                        organizationId,
                        MemberStatus.INACTIVE
                )
                .stream()
                .map(MemberMapper::toResponse)
                .toList();

    }
}
