package com.cderc.backend.repository;

import com.cderc.backend.model.Member;
import com.cderc.backend.model.MemberStatus;
import com.cderc.backend.model.MemberType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByOrganizationId(Long organizationId);

    Optional<Member> findByIdAndOrganizationId(Long id, Long organizationId);

    boolean existsByEmailAndOrganizationId(String email, Long organizationId);
    List<Member> findByOrganizationIdAndStatus(Long organizationId, MemberStatus status);

    long countByOrganizationIdAndStatus(Long organizationId, MemberStatus status);

    long countByOrganizationIdAndType(Long organizationId, MemberType type);

    List<Member> findByOrganizationIdAndType(Long organizationId, MemberType type);

}

