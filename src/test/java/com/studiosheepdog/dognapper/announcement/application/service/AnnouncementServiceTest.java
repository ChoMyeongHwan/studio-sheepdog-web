package com.studiosheepdog.dognapper.announcement.application.service;

import com.studiosheepdog.dognapper.announcement.application.dto.AnnouncementDTO;
import com.studiosheepdog.dognapper.announcement.domain.repository.AnnouncementRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AnnouncementServiceTest {

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private AnnouncementRepository announcementRepository;

    private AnnouncementDTO announcementDTO;

    @BeforeEach
    void setUp() {
        announcementDTO = new AnnouncementDTO();
        announcementDTO.setTitle("Test Title");
        announcementDTO.setContent("Test Content");
        announcementDTO.setWriter("Test Writer");
        announcementService.createAnnouncement(announcementDTO);
    }

    @AfterEach
    void tearDown() {
        announcementRepository.deleteAll();
    }

    @Test
    @DisplayName("공지사항 생성 서비스 테스트")
    void testCreateAnnouncement() {
        AnnouncementDTO created = announcementService.createAnnouncement(announcementDTO);
        assertNotNull(created.getId());
        assertEquals(announcementDTO.getTitle(), created.getTitle());
        assertEquals(announcementDTO.getContent(), created.getContent());
        assertEquals(announcementDTO.getWriter(), created.getWriter());
    }

    @Test
    @DisplayName("공지사항 전체 조회 서비스 테스트")
    void testGetAnnouncements() {
        Page<AnnouncementDTO> announcements = announcementService.getAnnouncements(PageRequest.of(0, 10));
        assertFalse(announcements.isEmpty());
    }

    @Test
    @DisplayName("공지사항 상세 조회 서비스 테스트")
    void testGetAnnouncement() {
        AnnouncementDTO created = announcementService.createAnnouncement(announcementDTO);
        AnnouncementDTO found = announcementService.getAnnouncement(created.getId());
        assertEquals(created.getId(), found.getId());
        assertEquals(created.getTitle(), found.getTitle());
        assertEquals(created.getContent(), found.getContent());
        assertEquals(created.getWriter(), found.getWriter());
    }

    @Test
    @DisplayName("공지사항 수정 서비스 테스트")
    void testUpdateAnnouncement() {
        AnnouncementDTO created = announcementService.createAnnouncement(announcementDTO);
        created.setTitle("Updated Title");
        created.setContent("Updated Content");
        created.setWriter("Updated Writer");
        AnnouncementDTO updated = announcementService.updateAnnouncement(created.getId(), created);
        assertEquals(created.getTitle(), updated.getTitle());
        assertEquals(created.getContent(), updated.getContent());
        assertEquals(created.getWriter(), updated.getWriter());
    }

    @Test
    @DisplayName("공지사항 삭제 서비스 테스트")
    void testDeleteAnnouncement() {
        AnnouncementDTO created = announcementService.createAnnouncement(announcementDTO);
        announcementService.deleteAnnouncement(created.getId());
        assertThrows(NoSuchElementException.class, () -> announcementService.getAnnouncement(created.getId()));
    }
}
