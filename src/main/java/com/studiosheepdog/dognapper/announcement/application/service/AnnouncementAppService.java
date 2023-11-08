package com.studiosheepdog.dognapper.announcement.application.service;

import com.studiosheepdog.dognapper.announcement.application.dto.AnnouncementDTO;
import com.studiosheepdog.dognapper.announcement.domain.aggregate.entity.Announcement;
import com.studiosheepdog.dognapper.announcement.domain.service.AnnouncementDomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AnnouncementAppService {

    private final AnnouncementDomService announcementDomService;

    @Transactional
    public Announcement createAnnouncement(AnnouncementDTO announcementDTO) {
        return announcementDomService.createAnnouncement(announcementDTO);
    }

    @Transactional(readOnly = true)
    public Page<Announcement> getAnnouncements(Pageable pageable) {
        return announcementDomService.getAnnouncements(pageable);
    }

    @Transactional(readOnly = true)
    public Announcement getAnnouncement(Long id) {
        return announcementDomService.getAnnouncement(id);
    }
}
