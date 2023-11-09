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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // Mockito 확장을 사용하도록 설정
class AnnouncementDomServiceTest {

    @InjectMocks // 테스트할 대상
    private AnnouncementDomService announcementDomService;

    @Mock // 생성된 모의 객체는 실제 객체의 행동을 모방
    private AnnouncementRepository announcementRepository;

    @Test
    @DisplayName("AnnouncementDomService의 createAnnouncement 메서드 테스트")
    void createAnnouncementTest() {
        // 테스트에 사용할 데이터를 생성
        AnnouncementDTO announcementDTO = new AnnouncementDTO();
        announcementDTO.setTitle("Test Title");
        announcementDTO.setContent("Test Content");
        announcementDTO.setWriter("Test Writer");

        // announcementRepository가 반환할 가짜 Announcement 객체를 생성
        Announcement announcement = Announcement.builder()
                .title(announcementDTO.getTitle())
                .content(announcementDTO.getContent())
                .writer(announcementDTO.getWriter())
                .build();

        // announcementRepository.save()가 호출될 때 위에서 생성한 announcement를 반환하도록 설정
        when(announcementRepository.save(any(Announcement.class))).thenReturn(announcement);

        // 실제 테스트 대상인 메서드를 호출
        Announcement createdAnnouncement = announcementDomService.createAnnouncement(announcementDTO);

        // 반환된 결과가 예상한 결과와 일치하는지 확인
        assertEquals(announcement.getTitle(), createdAnnouncement.getTitle());
        assertEquals(announcement.getContent(), createdAnnouncement.getContent());
        assertEquals(announcement.getWriter(), createdAnnouncement.getWriter());
    }

    @Test
    @DisplayName("AnnouncementDomService의 getAnnouncements 메서드 테스트")
    void getAnnouncementsTest() {
        // 테스트에 사용할 데이터를 생성
        Announcement announcement1 = Announcement.builder()
                .title("Test Title 1")
                .content("Test Content 1")
                .writer("Test Writer 1")
                .build();
        Announcement announcement2 = Announcement.builder()
                .title("Test Title 2")
                .content("Test Content 2")
                .writer("Test Writer 2")
                .build();

        List<Announcement> announcementList = Arrays.asList(announcement1, announcement2);
        Page<Announcement> announcementPage = new PageImpl<>(announcementList);

        // announcementRepository.findAll()이 호출될 때 위에서 생성한 announcementPage를 반환하도록 설정
        when(announcementRepository.findAll(any(Pageable.class))).thenReturn(announcementPage);

        // 실제 테스트 대상인 메서드를 호출
        Page<Announcement> foundAnnouncements = announcementDomService.getAnnouncements(PageRequest.of(0, 10));

        // 반환된 결과가 예상한 결과와 일치하는지 확인
        assertEquals(announcementList.size(), foundAnnouncements.getContent().size());
        assertEquals(announcement1.getTitle(), foundAnnouncements.getContent().get(0).getTitle());
        assertEquals(announcement2.getTitle(), foundAnnouncements.getContent().get(1).getTitle());
    }


    @Test
    @DisplayName("AnnouncementDomService의 getAnnouncement 메서드 테스트")
    void getAnnouncementTest() {
        // 테스트에 사용할 데이터를 생성
        Announcement announcement = Announcement.builder()
                .title("Test Title")
                .content("Test Content")
                .writer("Test Writer")
                .build();

        // announcementRepository.findById()가 호출될 때 위에서 생성한 announcement를 반환하도록 설정
        // 어떤 Long 객체든 받아들이도록 any() 메소드를 사용
        when(announcementRepository.findById(any())).thenReturn(Optional.of(announcement));

        // 실제 테스트 대상인 메서드를 호출
        Announcement foundAnnouncement = announcementDomService.getAnnouncement(1L);

        // 반환된 결과가 예상한 결과와 일치하는지 확인
        assertEquals(announcement.getTitle(), foundAnnouncement.getTitle());
        assertEquals(announcement.getContent(), foundAnnouncement.getContent());
        assertEquals(announcement.getWriter(), foundAnnouncement.getWriter());
    }

    @Test
    @DisplayName("AnnouncementDomService의 getAnnouncement 메서드 테스트 - 존재하지 않는 ID")
    void getAnnouncementNoSuchElementTest() {
        // 존재하지 않는 ID를 설정
        Long id = 1L;

        // announcementRepository.findById()가 호출될 때 빈 Optional 객체를 반환하도록 설정
        when(announcementRepository.findById(id)).thenReturn(Optional.empty());

        // 실제 테스트 대상인 메서드를 호출하면 NoSuchElementException이 발생해야 함
        assertThrows(NoSuchElementException.class, () -> announcementDomService.getAnnouncement(id));
    }

    @Test
    @DisplayName("AnnouncementDomService의 updateAnnouncement 메서드 테스트")
    void updateAnnouncementTest() {
        // 테스트에 사용할 데이터를 생성
        Long id = 1L;
        AnnouncementDTO announcementDTO = new AnnouncementDTO();
        announcementDTO.setTitle("Updated Test Title");
        announcementDTO.setContent("Updated Test Content");
        announcementDTO.setWriter("Updated Test Writer");

        Announcement announcement = Announcement.builder()
                .title("Test Title")
                .content("Test Content")
                .writer("Test Writer")
                .build();

        // announcementRepository.findById()가 호출될 때 위에서 생성한 announcement를 반환하도록 설정
        when(announcementRepository.findById(id)).thenReturn(Optional.of(announcement));

        // 실제 테스트 대상인 메서드를 호출
        Announcement updatedAnnouncement = announcementDomService.updateAnnouncement(id, announcementDTO);

        // 반환된 결과가 예상한 결과와 일치하는지 확인
        assertEquals(announcementDTO.getTitle(), updatedAnnouncement.getTitle());
        assertEquals(announcementDTO.getContent(), updatedAnnouncement.getContent());
        assertEquals(announcementDTO.getWriter(), updatedAnnouncement.getWriter());
    }
}
