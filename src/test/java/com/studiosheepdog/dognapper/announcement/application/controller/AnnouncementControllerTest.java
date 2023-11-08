package com.studiosheepdog.dognapper.announcement.application.controller;

import com.studiosheepdog.dognapper.announcement.application.dto.AnnouncementDTO;
import com.studiosheepdog.dognapper.announcement.application.service.AnnouncementAppService;
import com.studiosheepdog.dognapper.announcement.domain.aggregate.entity.Announcement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest // Spring Boot의 모든 Bean들을 로드하여 통합 테스트 환경을 만듬
@AutoConfigureMockMvc // MockMvc를 자동 설정하여 사용할 수 있게 함
class AnnouncementControllerTest {

    @Autowired
    private MockMvc mockMvc; // 웹 서버를 구동하지 않고도 Spring MVC의 동작을 재현할 수 있게 해주는 객체

    @MockBean
    private AnnouncementAppService announcementAppService; // 테스트에서 사용할 AnnouncementAppService를 모킹

    @Test
    @DisplayName("공지사항 생성 테스트")
    void createAnnouncementTest() throws Exception {
        AnnouncementDTO announcementDTO = new AnnouncementDTO();
        announcementDTO.setTitle("Test Title");
        announcementDTO.setContent("Test Content");
        announcementDTO.setWriter("Test Writer");

        Announcement announcement = Announcement.builder()
                .title(announcementDTO.getTitle())
                .content(announcementDTO.getContent())
                .writer(announcementDTO.getWriter())
                .build();

        // announcementAppService의 createAnnouncement 메서드가 호출되면 미리 만든 announcement 객체를 반환하도록 설정
        when(announcementAppService.createAnnouncement(any(AnnouncementDTO.class))).thenReturn(announcement);

        // /announcement/create로 POST 요청을 보내고, 응답 상태와 리다이렉트 URL을 검증
        mockMvc.perform(post("/announcement/create") // MockMvc 인스턴스를 통해 HTTP 요청을 시뮬레이션
                        .param("title", "Test Title") // 요청 파라미터를 설정
                        .param("content", "Test Content")
                        .param("writer", "Test Writer"))
                .andExpect(status().is3xxRedirection()) // 3xx (Redirection) 상태 코드를 반환하는지 확인
                .andExpect(redirectedUrl("/announcement")); // 리다이렉트 URL을 검증
    }
}
