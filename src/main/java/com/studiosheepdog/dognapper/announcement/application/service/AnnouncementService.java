package com.studiosheepdog.dognapper.announcement.application.service;

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
@Transactional
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    // 공지사항 작성
    public AnnouncementDTO createAnnouncement(AnnouncementDTO announcementDTO) {
        Announcement announcement = Announcement.builder()
                .title(announcementDTO.getTitle())
                .content(announcementDTO.getContent())
                .writer(announcementDTO.getWriter())
                .build();

        announcementRepository.save(announcement);

        return announcement.toDTO();
    }

    // 전체 조회
    @Transactional(readOnly = true)
    public Page<AnnouncementDTO> getAnnouncements(Pageable pageable) {
        Page<Announcement> announcements = announcementRepository.findAll(pageable);;
        return announcements.map(Announcement::toDTO);
    }

    // 상세 조회
    @Transactional(readOnly = true)
    public AnnouncementDTO getAnnouncement(Long id) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 아이디를 가진 공지사항이 없습니다: " + id));
        return announcement.toDTO();
    }

    // 공지사항 수정
    public AnnouncementDTO updateAnnouncement(Long id, AnnouncementDTO announcementDTO) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 아이디를 가진 공지사항이 없습니다: " + id));
        announcement.update(announcementDTO.getTitle(), announcementDTO.getContent(), announcementDTO.getWriter());
        return announcement.toDTO();
    }

    // 공지사항 삭제
    public void deleteAnnouncement(Long id) {
        if (!announcementRepository.existsById(id)) {
            throw new NoSuchElementException("해당 아이디를 가진 공지사항이 없습니다: " + id);
        }
        announcementRepository.deleteById(id);
    }
}
