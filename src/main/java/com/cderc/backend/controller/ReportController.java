package com.cderc.backend.controller;

import com.cderc.backend.dto.CategoryReportResponse;
import com.cderc.backend.dto.EventTotalReportResponse;
import com.cderc.backend.dto.MemberResponse;
import com.cderc.backend.dto.YearReportResponse;
import com.cderc.backend.model.User;
import com.cderc.backend.service.ReportService;
import com.cderc.backend.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/admin/reports")
public class ReportController {
    private final ReportService reportService;
    private final UserService userService;

    public ReportController(ReportService reportService,
                            UserService userService) {
        this.reportService = reportService;
        this.userService = userService;
    }

    @GetMapping("/events/{eventId}/total")
    public EventTotalReportResponse eventTotal(@PathVariable Long eventId,
                                               Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        System.out.println(" ReportController: USER-MAIL = " + user.getEmail());
        System.out.println(" ReportController: USER-ORG = " + user.getOrganization());

        return reportService.getEventTotal(
                eventId,
                user.getOrganization().getId()
        );
    }
    @GetMapping("/events/{eventId}/by-category")
    public List<CategoryReportResponse> eventByCategory(@PathVariable Long eventId,
                                                        Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());

        return reportService.getEventCostsByCategory(
                eventId,
                user.getOrganization().getId()
        );
    }
    @GetMapping("/events/by-year/{year}")
    public YearReportResponse byYear(@PathVariable int year,
                                     Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());

        return reportService.getCostsByYear(
                year,
                user.getOrganization().getId()
        );
    }
    @GetMapping("/events/compare")
    public List<YearReportResponse> compareYears(@RequestParam int year1,
                                                 @RequestParam int year2,
                                                 Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());

        return reportService.compareYears(
                year1,
                year2,
                user.getOrganization().getId()
        );
    }
    @GetMapping("/organization/total")
    public BigDecimal organizationTotal(Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());

        return reportService.getOrganizationTotal(
                user.getOrganization().getId()
        );
    }
//start member reporting
@GetMapping("/members/active/count")
public long activeMembersCount(Authentication authentication) {
    User user = userService.findByEmail(authentication.getName());

    return reportService.countActiveMembers(
            user.getOrganization().getId()
    );
}
    @GetMapping("/members/supporting/count")
    public long supportingMembersCount(Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());

        return reportService.countSupportingMembers(
                user.getOrganization().getId()
        );
    }
    @GetMapping("/members/board")
    public List<MemberResponse> boardMembers(Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());

        return reportService.findBoardMembers(
                user.getOrganization().getId()
        );
    }
    @GetMapping("/members/volunteers")
    public List<MemberResponse> volunteers(Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());

        return reportService.findVolunteers(
                user.getOrganization().getId()
        );
    }

    @GetMapping("/members/inactive")
    public List<MemberResponse> inactiveMembers(Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());

        return reportService.findInactiveMembers(
                user.getOrganization().getId()
        );
    }
}
