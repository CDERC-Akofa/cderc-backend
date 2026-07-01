package com.cderc.backend.service;

import com.cderc.backend.dto.EventExpenseRequest;
import com.cderc.backend.dto.EventTotalReportResponse;
import com.cderc.backend.mapper.EventExpenseMapper;
import com.cderc.backend.model.Event;
import com.cderc.backend.model.EventExpense;
import com.cderc.backend.model.Organization;
import com.cderc.backend.repository.EventExpenseRepository;
import com.cderc.backend.repository.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import java.math.BigDecimal;
@Slf4j
@Service
public class EventExpenseService {
    private final EventExpenseRepository eventExpenseRepository;
    private final EventRepository eventRepository;

    public EventExpenseService(EventExpenseRepository eventExpenseRepository,
                               EventRepository eventRepository) {
        this.eventExpenseRepository = eventExpenseRepository;
        this.eventRepository = eventRepository;
    }

    public EventExpense create(Long eventId,
                               EventExpenseRequest request,
                               Organization organization) {
        log.info("Creating expense for the event{}", eventId);
        Event event = eventRepository.findByIdAndOrganizationId(eventId, organization.getId())
                .orElseThrow(() -> new RuntimeException("Event not found"));

        EventExpense expense = EventExpenseMapper.toEntity(request);
        expense.setEvent(event);
        expense.setOrganization(organization);

        return eventExpenseRepository.save(expense);
    }

    public List<EventExpense> findByEventAndOrganization(Long eventId, Long organizationId) {
        return eventExpenseRepository.findByEventIdAndOrganizationId(eventId, organizationId);
    }

    public EventExpense findByIdAndEventAndOrganization(Long expenseId,
                                                        Long eventId,
                                                        Long organizationId) {
        return eventExpenseRepository
                .findByIdAndEventIdAndOrganizationId(expenseId, eventId, organizationId)
                .orElseThrow(() -> new RuntimeException("Event expense not found"));
    }
    public EventExpense update(Long expenseId,
                               Long eventId,
                               EventExpenseRequest request,
                               Long organizationId) {
        log.info("Updating expense for the event{}", eventId);
        EventExpense expense = findByIdAndEventAndOrganization(expenseId, eventId, organizationId);
        EventExpenseMapper.updateEntity(expense, request);

        return eventExpenseRepository.save(expense);
    }

    public void delete(Long expenseId, Long eventId, Long organizationId) {
        EventExpense expense = findByIdAndEventAndOrganization(expenseId, eventId, organizationId);
        eventExpenseRepository.delete(expense);
    }

    public EventTotalReportResponse getTotalForEvent(Long eventId, Long organizationId) {
        log.info("getTotalForEvent expense for the event {}", eventId);
        Event event = eventRepository.findByIdAndOrganizationId(eventId, organizationId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        BigDecimal total = eventExpenseRepository.sumByEventAndOrganization(eventId, organizationId);
        log.info("Total for event expense {}", total);
        return new EventTotalReportResponse(
                event.getId(),
                event.getTitle(),
                total
        );
    }

}
