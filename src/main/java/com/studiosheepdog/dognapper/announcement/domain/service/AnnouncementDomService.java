package com.studiosheepdog.dognapper.announcement.domain.service;

import com.studiosheepdog.dognapper.announcement.application.dto.AnnouncementDTO;
import com.studiosheepdog.dognapper.announcement.domain.aggregate.entity.Announcement;
import com.studiosheepdog.dognapper.announcement.domain.repository.AnnouncementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AnnouncementDomService {

    private final AnnouncementRepository announcementRepository;

    // 공지사항 작성
    @Transactional
    public Announcement createAnnouncement(AnnouncementDTO announcementDTO) {
        Announcement announcement = Announcement.builder()
                .title(announcementDTO.getTitle())
                .content(announcementDTO.getContent())
                .writer(announcementDTO.getWriter())
                .build();

        return announcementRepository.save(announcement);
    }

    // 공지사항 전체 조회 (페이지 단위)
    @Transactional(readOnly = true)
    public Page<Announcement> getAnnouncements(Pageable pageable) {
        return announcementRepository.findAll(pageable);
    }

    // 공지사항 상세 조회
    @Transactional(readOnly = true)
    public Announcement getAnnouncement(Long id) {
        return announcementRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No Announcement found with id: " + id));
    }

    // 공지사항 수정
    @Transactional
    public Announcement updateAnnouncement(Long id, AnnouncementDTO announcementDTO) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No Announcement found with id: " + id));
        announcement.update(announcementDTO.getTitle(), announcementDTO.getContent(), announcementDTO.getWriter());
        return announcement;
    }

    // 공지사항 삭제
    @Transactional
    public void deleteAnnouncement(Long id) {
        announcementRepository.deleteById(id);
    }
}