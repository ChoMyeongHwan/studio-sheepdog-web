package com.studiosheepdog.dognapper.announcement.domain.service;

import com.studiosheepdog.dognapper.announcement.application.dto.AnnouncementDTO;
import com.studiosheepdog.dognapper.announcement.domain.aggregate.entity.Announcement;
import com.studiosheepdog.dognapper.announcement.domain.repository.AnnouncementRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnnouncementDomServiceTest {

    @InjectMocks // 테스트할 대상
    private AnnouncementDomService announcementDomService;

    @Mock // 생성된 모의 객체는 실제 객체의 행동을 모방
    private AnnouncementRepository announcementRepository;

    private static final String TEST_TITLE = "Test Title";
    private static final String TEST_CONTENT = "Test Content";
    private static final String TEST_WRITER = "Test Writer";
    private static final String UPDATED_TEST_TITLE = "Updated Test Title";
    private static final String UPDATED_TEST_CONTENT = "Updated Test Content";
    private static final String UPDATED_TEST_WRITER = "Updated Test Writer";

    @Test
    @DisplayName("AnnouncementDomService의 createAnnouncement 메서드 테스트")
    void testCreateAnnouncement() {
        AnnouncementDTO announcementDTO = createAnnouncementDTO(TEST_TITLE, TEST_CONTENT, TEST_WRITER);
        Announcement announcement = createAnnouncement(TEST_TITLE, TEST_CONTENT, TEST_WRITER);

        when(announcementRepository.save(any(Announcement.class))).thenReturn(announcement);

        Announcement createdAnnouncement = announcementDomService.createAnnouncement(announcementDTO);

        assertEquals(announcement.getTitle(), createdAnnouncement.getTitle());
        assertEquals(announcement.getContent(), createdAnnouncement.getContent());
        assertEquals(announcement.getWriter(), createdAnnouncement.getWriter());
    }

    @Test
    @DisplayName("AnnouncementDomService의 getAnnouncements 메서드 테스트")
    void testGetAnnouncements() {
        Announcement announcement1 = createAnnouncement("Test Title 1", "Test Content 1", "Test Writer 1");
        Announcement announcement2 = createAnnouncement("Test Title 2", "Test Content 2", "Test Writer 2");

        List<Announcement> announcementList = Arrays.asList(announcement1, announcement2);
        Page<Announcement> announcementPage = new PageImpl<>(announcementList);

        when(announcementRepository.findAll(any(Pageable.class))).thenReturn(announcementPage);

        Page<Announcement> foundAnnouncements = announcementDomService.getAnnouncements(PageRequest.of(0, 10));

        assertEquals(announcementList.size(), foundAnnouncements.getContent().size());
        assertEquals(announcement1.getTitle(), foundAnnouncements.getContent().get(0).getTitle());
        assertEquals(announcement2.getTitle(), foundAnnouncements.getContent().get(1).getTitle());
    }

    @Test
    @DisplayName("AnnouncementDomService의 getAnnouncement 메서드 테스트")
    void testGetAnnouncement() {
        Announcement announcement = createAnnouncement(TEST_TITLE, TEST_CONTENT, TEST_WRITER);

        when(announcementRepository.findById(any())).thenReturn(Optional.of(announcement));

        Announcement foundAnnouncement = announcementDomService.getAnnouncement(1L);

        assertEquals(announcement.getTitle(), foundAnnouncement.getTitle());
        assertEquals(announcement.getContent(), foundAnnouncement.getContent());
        assertEquals(announcement.getWriter(), foundAnnouncement.getWriter());
    }

    @Test
    @DisplayName("AnnouncementDomService의 getAnnouncement 메서드 테스트 - 존재하지 않는 ID")
    void testGetAnnouncementNoSuchElement() {
        Long id = 1L;

        when(announcementRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> announcementDomService.getAnnouncement(id));
    }

    @Test
    @DisplayName("AnnouncementDomService의 updateAnnouncement 메서드 테스트")
    void testUpdateAnnouncement() {
        Long id = 1L;
        AnnouncementDTO announcementDTO = createAnnouncementDTO(UPDATED_TEST_TITLE, UPDATED_TEST_CONTENT, UPDATED_TEST_WRITER);

        Announcement announcement = createAnnouncement(TEST_TITLE, TEST_CONTENT, TEST_WRITER);

        when(announcementRepository.findById(id)).thenReturn(Optional.of(announcement));

        Announcement updatedAnnouncement = announcementDomService.updateAnnouncement(id, announcementDTO);

        assertEquals(announcementDTO.getTitle(), updatedAnnouncement.getTitle());
        assertEquals(announcementDTO.getContent(), updatedAnnouncement.getContent());
        assertEquals(announcementDTO.getWriter(), updatedAnnouncement.getWriter());
    }

    @Test
    @DisplayName("AnnouncementDomService의 deleteAnnouncement 메서드 테스트")
    void testDeleteAnnouncement() {
        Long id = 1L;

        announcementDomService.deleteAnnouncement(id);

        verify(announcementRepository, times(1)).deleteById(id);
    }

    private AnnouncementDTO createAnnouncementDTO(String title, String content, String writer) {
        AnnouncementDTO announcementDTO = new AnnouncementDTO();
        announcementDTO.setTitle(title);
        announcementDTO.setContent(content);
        announcementDTO.setWriter(writer);
        return announcementDTO;
    }

    private Announcement createAnnouncement(String title, String content, String writer) {
        return Announcement.builder()
                .title(title)
                .content(content)
                .writer(writer)
                .build();
    }
}
