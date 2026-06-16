package com.cderc.backend.mapper;

import com.cderc.backend.dto.MemberRequest;
import com.cderc.backend.dto.MemberResponse;
import com.cderc.backend.model.Member;

public class MemberMapper {
    public static Member toEntity(MemberRequest request) {
        Member member = new Member();

        member.setFirstName(request.getFirstName());
        member.setLastName(request.getLastName());
        member.setEmail(request.getEmail());
        member.setPhone(request.getPhone());
        member.setAddress(request.getAddress());
        member.setCity(request.getCity());
        member.setCountry(request.getCountry());
        member.setBirthDate(request.getBirthDate());
        member.setJoinedAt(request.getJoinedAt());
        member.setStatus(request.getStatus());
        member.setType(request.getType());

        return member;
    }

    public static MemberResponse toResponse(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getFirstName(),
                member.getLastName(),
                member.getEmail(),
                member.getPhone(),
                member.getAddress(),
                member.getCity(),
                member.getCountry(),
                member.getBirthDate(),
                member.getJoinedAt(),
                member.getStatus(),
                member.getType(),
                member.getOrganization().getId()
        );
    }

    public static void updateEntity(Member member, MemberRequest request) {
        member.setFirstName(request.getFirstName());
        member.setLastName(request.getLastName());
        member.setEmail(request.getEmail());
        member.setPhone(request.getPhone());
        member.setAddress(request.getAddress());
        member.setCity(request.getCity());
        member.setCountry(request.getCountry());
        member.setBirthDate(request.getBirthDate());
        member.setJoinedAt(request.getJoinedAt());
        member.setStatus(request.getStatus());
        member.setType(request.getType());
    }
}
