package com.cderc.backend.controller;

import com.cderc.backend.dto.EventExpenseRequest;
import com.cderc.backend.dto.EventExpenseResponse;
import com.cderc.backend.dto.EventTotalReportResponse;
import com.cderc.backend.mapper.EventExpenseMapper;
import com.cderc.backend.model.EventExpense;
import com.cderc.backend.model.User;
import com.cderc.backend.service.EventExpenseService;
import com.cderc.backend.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/admin/events/{eventId}/expenses")
public class EventExpenseController {
    private final EventExpenseService eventExpenseService;
    private final UserService userService;

    public EventExpenseController(EventExpenseService eventExpenseService,
                                  UserService userService) {
        this.eventExpenseService = eventExpenseService;
        this.userService = userService;
    }

    @PostMapping
    public EventExpenseResponse create(@PathVariable Long eventId,
                                       @RequestBody EventExpenseRequest request,
                                       Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());

        EventExpense saved = eventExpenseService.create(
                eventId,
                request,
                user.getOrganization()
        );

        return EventExpenseMapper.toResponse(saved);
    }

    @GetMapping
    public List<EventExpenseResponse> findAll(@PathVariable Long eventId,
                                              Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());

        return eventExpenseService
                .findByEventAndOrganization(eventId, user.getOrganization().getId())
                .stream()
                .map(EventExpenseMapper::toResponse)
                .toList();
    }

    @GetMapping("/{expenseId}")
    public EventExpenseResponse findById(@PathVariable Long eventId,
                                         @PathVariable Long expenseId,
                                         Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());

        EventExpense expense = eventExpenseService.findByIdAndEventAndOrganization(
                expenseId,
                eventId,
                user.getOrganization().getId()
        );

        return EventExpenseMapper.toResponse(expense);
    }

    @PutMapping("/{expenseId}")
    public EventExpenseResponse update(@PathVariable Long eventId,
                                       @PathVariable Long expenseId,
                                       @RequestBody EventExpenseRequest request,
                                       Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());

        EventExpense updated = eventExpenseService.update(
                expenseId,
                eventId,
                request,
                user.getOrganization().getId()
        );

        return EventExpenseMapper.toResponse(updated);
    }

    @DeleteMapping("/{expenseId}")
    public void delete(@PathVariable Long eventId,
                       @PathVariable Long expenseId,
                       Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());

        eventExpenseService.delete(
                expenseId,
                eventId,
                user.getOrganization().getId()
        );
    }

    @GetMapping("/total")
    public EventTotalReportResponse total(@PathVariable Long eventId,
                                          Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());

        return eventExpenseService.getTotalForEvent(
                eventId,
                user.getOrganization().getId()
        );
    }
}
