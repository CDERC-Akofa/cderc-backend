package com.cderc.backend.controller;

import com.cderc.backend.dto.MemberRequest;
import com.cderc.backend.dto.MemberResponse;
import com.cderc.backend.mapper.MemberMapper;
import com.cderc.backend.model.Member;
import com.cderc.backend.model.User;
import com.cderc.backend.service.MemberService;
import com.cderc.backend.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/members")
public class AdminMemberController {
    private final MemberService memberService;
    private final UserService userService;

    public AdminMemberController(MemberService memberService,
                                 UserService userService) {
        this.memberService = memberService;
        this.userService = userService;
    }

    @PostMapping
    public MemberResponse create(@RequestBody MemberRequest request,
                                 Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());

        Member saved = memberService.create(request, user.getOrganization());

        return MemberMapper.toResponse(saved);
    }

    @GetMapping
    public List<MemberResponse> findAll(Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());

        return memberService.findAllByOrganization(user.getOrganization().getId())
                .stream()
                .map(MemberMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public MemberResponse findById(@PathVariable Long id,
                                   Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());

        Member member = memberService.findByIdAndOrganization(
                id,
                user.getOrganization().getId());

        return MemberMapper.toResponse(member);
    }

    @PutMapping("/{id}")
    public MemberResponse update(@PathVariable Long id,
                                 @RequestBody MemberRequest request,
                                 Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());

        Member updated = memberService.update(
                id,
                user.getOrganization().getId(),
                request
        );

        return MemberMapper.toResponse(updated);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id,
                       Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());

        memberService.delete(id, user.getOrganization().getId());
    }
}