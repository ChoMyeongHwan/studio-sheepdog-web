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
    public AnnouncementDTO createAnnouncement(AnnouncementDTO announcementDTO) {
        Announcement announcement = announcementDomService.createAnnouncement(announcementDTO);
        return new AnnouncementDTO(announcement);
    }

    @Transactional(readOnly = true)
    public Page<AnnouncementDTO> getAnnouncements(Pageable pageable) {
        Page<Announcement> announcements = announcementDomService.getAnnouncements(pageable);
        return announcements.map(AnnouncementDTO::new);
    }

    @Transactional(readOnly = true)
    public AnnouncementDTO getAnnouncement(Long id) {
        Announcement announcement = announcementDomService.getAnnouncement(id);
        return new AnnouncementDTO(announcement);
    }

    @Transactional
    public AnnouncementDTO updateAnnouncement(Long id, AnnouncementDTO announcementDTO) {
        Announcement announcement = announcementDomService.updateAnnouncement(id, announcementDTO);
        return new AnnouncementDTO(announcement);
    }
}

