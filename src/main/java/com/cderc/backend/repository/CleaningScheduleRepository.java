package com.cderc.backend.repository;

import com.cderc.backend.model.CleaningSchedule;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CleaningScheduleRepository extends JpaRepository<CleaningSchedule, Long> {

    List<CleaningSchedule> findByOrganizationIdOrderByWeekStartAsc(Long organizationId);
}

