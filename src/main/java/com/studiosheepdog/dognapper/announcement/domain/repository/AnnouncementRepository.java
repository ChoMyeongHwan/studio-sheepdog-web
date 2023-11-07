package com.studiosheepdog.dognapper.announcement.domain.repository;

import com.studiosheepdog.dognapper.announcement.domain.aggregate.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
}
