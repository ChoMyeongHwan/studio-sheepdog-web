package com.studiosheepdog.dognapper.announcement.application.controller;

import com.studiosheepdog.dognapper.announcement.application.dto.AnnouncementDTO;
import com.studiosheepdog.dognapper.announcement.application.service.AnnouncementAppService;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest // Spring Boot의 모든 Bean들을 로드하여 통합 테스트 환경을 만듬
@AutoConfigureMockMvc // MockMvc를 자동 설정하여 사용할 수 있게 함
class AnnouncementControllerTest {

    @Autowired
    private MockMvc mockMvc; // 웹 서버를 구동하지 않고도 Spring MVC의 동작을 재현할 수 있게 해주는 객체

    @MockBean
    private AnnouncementAppService announcementAppService; // 테스트에서 사용할 AnnouncementAppService를 모킹

    private static final String TEST_TITLE = "Test Title";
    private static final String TEST_CONTENT = "Test Content";
    private static final String TEST_WRITER = "Test Writer";

    private AnnouncementDTO announcementDTO;

    @BeforeEach
    void setUp() {
        announcementDTO = createAnnouncementDTO(TEST_TITLE, TEST_CONTENT, TEST_WRITER);
    }

    private AnnouncementDTO createAnnouncementDTO(String title, String content, String writer) {
        AnnouncementDTO announcementDTO = new AnnouncementDTO();
        announcementDTO.setTitle(title);
        announcementDTO.setContent(content);
        announcementDTO.setWriter(writer);
        announcementDTO.setCreatedDate(LocalDateTime.now());
        announcementDTO.setModifiedDate(LocalDateTime.now());

        return announcementDTO;
    }

    @Test
    @DisplayName("공지사항 생성 테스트")
    void testCreateAnnouncement() throws Exception {
        // announcementAppService의 createAnnouncement 메서드가 호출되면 미리 만든 announcementDTO 객체를 반환하도록 설정
        when(announcementAppService.createAnnouncement(any(AnnouncementDTO.class))).thenReturn(announcementDTO);

        // /announcement/create로 POST 요청을 보내고, 응답 상태와 리다이렉트 URL을 검증
        mockMvc.perform(post("/announcement/create") // MockMvc 인스턴스를 통해 HTTP 요청을 시뮬레이션
                        .param("title", TEST_TITLE) // 요청 파라미터를 설정
                        .param("content", TEST_CONTENT)
                        .param("writer", TEST_WRITER))
                .andExpect(status().is3xxRedirection()) // 3xx (Redirection) 상태 코드를 반환하는지 확인
                .andExpect(redirectedUrl("/announcement")); // 리다이렉트 URL을 검증

        verify(announcementAppService, times(1)).createAnnouncement(any(AnnouncementDTO.class));
    }

    @Test
    @DisplayName("특정 ID의 공지사항 조회 테스트")
    void testGetAnnouncement() throws Exception {
        Long id = 1L;

        when(announcementAppService.getAnnouncement(id)).thenReturn(announcementDTO);

        mockMvc.perform(get("/announcement/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string(containsString(TEST_TITLE)))
                .andExpect(content().string(containsString(TEST_CONTENT)))
                .andExpect(content().string(containsString(TEST_WRITER)));

        verify(announcementAppService, times(1)).getAnnouncement(id);
    }

    @Test
    @DisplayName("모든 공지사항 페이지 단위 조회 테스트")
    void testGetAnnouncements() throws Exception {
        AnnouncementDTO announcementDTO1 = createAnnouncementDTO("Test Title 1", "Test Content 1", "Test Writer 1");

        List<AnnouncementDTO> announcementList = Arrays.asList(announcementDTO, announcementDTO1);
        Page<AnnouncementDTO> announcementPage = new PageImpl<>(announcementList);

        when(announcementAppService.getAnnouncements(any(Pageable.class))).thenReturn(announcementPage);

        mockMvc.perform(get("/announcement"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string(containsString(TEST_TITLE)))
                .andExpect(content().string(containsString("Test Title 1")));

        verify(announcementAppService, times(1)).getAnnouncements(any(Pageable.class));
    }

    @Test
    @DisplayName("공지사항 수정 페이지 이동 테스트")
    void testUpdateAnnouncementForm() throws Exception {
        Long id = 1L;
        announcementDTO = createAnnouncementDTO(TEST_TITLE, TEST_CONTENT, TEST_WRITER); // 테스트 독립성을 위해 새로운 객체 생성

        when(announcementAppService.getAnnouncement(id)).thenReturn(announcementDTO);

        mockMvc.perform(get("/announcement/updateForm/" + id))
                .andExpect(status().isOk())
                .andExpect(model().attribute("announcementDTO", announcementDTO))
                .andExpect(view().name("announcement/update"));

        verify(announcementAppService, times(1)).getAnnouncement(id);
    }

    @Test
    @DisplayName("공지사항 수정 테스트")
    void testUpdateAnnouncement() throws Exception {
        Long id = 1L;
        announcementDTO = createAnnouncementDTO(TEST_TITLE, TEST_CONTENT, TEST_WRITER); // 테스트 독립성을 위해 새로운 객체 생성

        when(announcementAppService.updateAnnouncement(anyLong(), any(AnnouncementDTO.class))).thenReturn(announcementDTO);

        mockMvc.perform(post("/announcement/update/" + id)
                        .param("title", "Updated Test Title")
                        .param("content", "Updated Test Content")
                        .param("writer", "Updated Test Writer"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/announcement/" + id));

        verify(announcementAppService, times(1)).updateAnnouncement(anyLong(), any(AnnouncementDTO.class));
    }

    @Test
    @DisplayName("공지사항 삭제 테스트")
    void testDeleteAnnouncement() throws Exception {
        Long id = 1L;

        // announcementAppService의 deleteAnnouncement 메서드가 호출되면 아무런 동작도 수행하지 않도록 설정
        doNothing().when(announcementAppService).deleteAnnouncement(id);

        mockMvc.perform(post("/announcement/delete/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/announcement"));

        verify(announcementAppService, times(1)).deleteAnnouncement(id);
    }

}
