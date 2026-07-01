package com.cderc.backend.service;

import com.cderc.backend.dto.CategoryReportResponse;
import com.cderc.backend.dto.EventTotalReportResponse;
import com.cderc.backend.dto.YearReportResponse;
import com.cderc.backend.model.Event;
import com.cderc.backend.repository.EventExpenseRepository;
import com.cderc.backend.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Service
public class ReportService {

    private final EventExpenseRepository eventExpenseRepository;
    private final EventRepository eventRepository;

    public ReportService(EventExpenseRepository eventExpenseRepository,
                         EventRepository eventRepository) {
        this.eventExpenseRepository = eventExpenseRepository;
        this.eventRepository = eventRepository;
    }

    public EventTotalReportResponse getEventTotal(Long eventId, Long organizationId) {
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
        return eventExpenseRepository.sumByCategoryForEvent(eventId, organizationId)
                .stream()
                .map(row -> new CategoryReportResponse(
                        row[0].toString(),
                        (BigDecimal) row[1]
                ))
                .toList();
    }

    public YearReportResponse getCostsByYear(int year, Long organizationId) {
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
}
