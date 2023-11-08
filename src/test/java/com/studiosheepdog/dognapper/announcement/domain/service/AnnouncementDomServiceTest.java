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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}
