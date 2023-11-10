package com.studiosheepdog.dognapper.announcement.application.controller;

import com.studiosheepdog.dognapper.announcement.application.dto.AnnouncementDTO;
import com.studiosheepdog.dognapper.announcement.application.service.AnnouncementAppService;
import com.studiosheepdog.dognapper.announcement.domain.aggregate.entity.Announcement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    void testCreateAnnouncement() throws Exception {
        AnnouncementDTO announcementDTO = new AnnouncementDTO();
        announcementDTO.setTitle("Test Title");
        announcementDTO.setContent("Test Content");
        announcementDTO.setWriter("Test Writer");

        Announcement announcement = Announcement.builder()
                .title(announcementDTO.getTitle())
                .content(announcementDTO.getContent())
                .writer(announcementDTO.getWriter())
                .build();

        // announcementAppService의 createAnnouncement 메서드가 호출되면 미리 만든 announcementDTO 객체를 반환하도록 설정
        when(announcementAppService.createAnnouncement(any(AnnouncementDTO.class))).thenReturn(announcementDTO);

        // /announcement/create로 POST 요청을 보내고, 응답 상태와 리다이렉트 URL을 검증
        mockMvc.perform(post("/announcement/create") // MockMvc 인스턴스를 통해 HTTP 요청을 시뮬레이션
                        .param("title", "Test Title") // 요청 파라미터를 설정
                        .param("content", "Test Content")
                        .param("writer", "Test Writer"))
                .andExpect(status().is3xxRedirection()) // 3xx (Redirection) 상태 코드를 반환하는지 확인
                .andExpect(redirectedUrl("/announcement")); // 리다이렉트 URL을 검증
    }

    @Test
    @DisplayName("특정 ID의 공지사항 조회 테스트")
    void testGetAnnouncement() throws Exception {
        Long id = 1L;
        AnnouncementDTO announcementDTO = new AnnouncementDTO();
        announcementDTO.setTitle("Test Title");
        announcementDTO.setContent("Test Content");
        announcementDTO.setWriter("Test Writer");

        // announcementAppService의 getAnnouncement 메서드가 호출되면 미리 만든 announcement 객체를 반환하도록 설정
        when(announcementAppService.getAnnouncement(id)).thenReturn(announcementDTO);

        // /announcement/{id}로 GET 요청을 보내고, 응답 상태와 응답 본문을 검증
        mockMvc.perform(get("/announcement/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string(containsString("Test Title")))
                .andExpect(content().string(containsString("Test Content")))
                .andExpect(content().string(containsString("Test Writer")));
    }

    @Test
    @DisplayName("모든 공지사항 페이지 단위 조회 테스트")
    void testGetAnnouncements() throws Exception {
        AnnouncementDTO announcementDTO1 = new AnnouncementDTO();
        announcementDTO1.setTitle("Test Title 1");
        announcementDTO1.setContent("Test Content 1");
        announcementDTO1.setWriter("Test Writer 1");
        announcementDTO1.setCreatedDate(LocalDateTime.now()); // createdDate 설정

        AnnouncementDTO announcementDTO2 = new AnnouncementDTO();
        announcementDTO2.setTitle("Test Title 2");
        announcementDTO2.setContent("Test Content 2");
        announcementDTO2.setWriter("Test Writer 2");
        announcementDTO2.setCreatedDate(LocalDateTime.now()); // createdDate 설정

        List<AnnouncementDTO> announcementList = Arrays.asList(announcementDTO1, announcementDTO2);
        Page<AnnouncementDTO> announcementPage = new PageImpl<>(announcementList);

        // announcementAppService의 getAnnouncements 메서드가 호출되면 미리 만든 announcementPage 객체를 반환하도록 설정
        when(announcementAppService.getAnnouncements(any(Pageable.class))).thenReturn(announcementPage);

        // /announcement로 GET 요청을 보내고, 응답 상태와 응답 본문을 검증
        mockMvc.perform(get("/announcement"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string(containsString("Test Title 1")))
                .andExpect(content().string(containsString("Test Title 2")));
    }

    @Test
    @DisplayName("공지사항 수정 페이지 이동 테스트")
    void testUpdateAnnouncementForm() throws Exception {
        Long id = 1L;
        AnnouncementDTO announcementDTO = new AnnouncementDTO();
        announcementDTO.setTitle("Test Title");
        announcementDTO.setContent("Test Content");
        announcementDTO.setWriter("Test Writer");

        // announcementAppService의 getAnnouncement 메서드가 호출되면 미리 만든 announcementDTO 객체를 반환하도록 설정
        when(announcementAppService.getAnnouncement(id)).thenReturn(announcementDTO);

        // /announcement/updateForm/{id}로 GET 요청을 보내고, 응답 상태와 응답 본문을 검증
        mockMvc.perform(get("/announcement/updateForm/" + id))
                .andExpect(status().isOk())
                .andExpect(model().attribute("announcementDTO", announcementDTO))
                .andExpect(view().name("announcement/update"));
    }

    @Test
    @DisplayName("공지사항 수정 테스트")
    void testUpdateAnnouncement() throws Exception {
        Long id = 1L;
        AnnouncementDTO announcementDTO = new AnnouncementDTO();
        announcementDTO.setTitle("Updated Test Title");
        announcementDTO.setContent("Updated Test Content");
        announcementDTO.setWriter("Updated Test Writer");

        // announcementAppService의 updateAnnouncement 메서드가 호출되면 미리 만든 announcementDTO 객체를 반환하도록 설정
        when(announcementAppService.updateAnnouncement(anyLong(), any(AnnouncementDTO.class))).thenReturn(announcementDTO);

        // /announcement/update/{id}로 POST 요청을 보내고, 응답 상태와 리다이렉트 URL을 검증
        mockMvc.perform(post("/announcement/update/" + id)
                        .param("title", "Updated Test Title")
                        .param("content", "Updated Test Content")
                        .param("writer", "Updated Test Writer"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/announcement/" + id));
    }

}
