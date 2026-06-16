package com.cderc.backend.service;

import com.cderc.backend.dto.MemberRequest;
import com.cderc.backend.mapper.MemberMapper;
import com.cderc.backend.model.Member;
import com.cderc.backend.model.Organization;
import com.cderc.backend.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member create(MemberRequest request, Organization organization) {

        if (request.getEmail() != null &&
                memberRepository.existsByEmailAndOrganizationId(request.getEmail(), organization.getId())) {
            throw new RuntimeException("Member email already exists in this organization");
        }

        Member member = MemberMapper.toEntity(request);
        member.setOrganization(organization);

        return memberRepository.save(member);
    }
    public List<Member> findAllByOrganization(Long organizationId) {
        return memberRepository.findByOrganizationId(organizationId);
    }

    public Member findByIdAndOrganization(Long id, Long organizationId) {
        return memberRepository.findByIdAndOrganizationId(id, organizationId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
    }

    public Member update(Long id, Long organizationId, MemberRequest request) {
        Member member = findByIdAndOrganization(id, organizationId);

        MemberMapper.updateEntity(member, request);

        return memberRepository.save(member);
    }

    public void delete(Long id, Long organizationId) {
        Member member = findByIdAndOrganization(id, organizationId);
        memberRepository.delete(member);
    }
}
