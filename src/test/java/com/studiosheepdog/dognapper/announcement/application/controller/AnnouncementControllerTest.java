package com.studiosheepdog.dognapper.announcement.application.controller;

import com.studiosheepdog.dognapper.announcement.application.dto.AnnouncementDTO;
import com.studiosheepdog.dognapper.announcement.application.service.AnnouncementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class) // Mockito 확장 기능을 JUnit5에 적용
class AnnouncementControllerTest {

    @InjectMocks // 테스트 대상인 AnnouncementController에 Mock 객체를 주입
    private AnnouncementController announcementController;

    @Mock // AnnouncementService를 Mocking
    private AnnouncementService announcementService;

    private MockMvc mockMvc; // 웹 서버를 구동하지 않고도 Spring MVC의 동작을 재현할 수 있게 해주는 객체
    private AnnouncementDTO announcementDTO;

    // 테스트를 실행하기 전에 실행되는 메서드
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(announcementController).build(); // MockMvc 인스턴스 생성
        announcementDTO = createAnnouncementDTO("Test Title", "Test Content", "Test Writer"); // 테스트에 사용할 AnnouncementDTO 생성
    }

    // AnnouncementDTO를 생성하는 메서드
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
        // announcementService의 createAnnouncement 메서드가 호출되면 미리 만든 announcementDTO 객체를 반환하도록 설정
        when(announcementService.createAnnouncement(any(AnnouncementDTO.class))).thenReturn(announcementDTO);

        // /announcement/create로 POST 요청을 보내고, 응답 상태와 리다이렉트 URL을 검증
        mockMvc.perform(post("/announcement/create") // MockMvc 인스턴스를 통해 HTTP 요청을 시뮬레이션
                        .param("title", "Test Title") // 요청 파라미터를 설정
                        .param("content", "Test Content")
                        .param("writer", "Test Writer"))
                .andExpect(status().is3xxRedirection()) // 3xx (Redirection) 상태 코드를 반환하는지 확인
                .andExpect(redirectedUrl("/announcement")); // 리다이렉트 URL을 검증

        // announcementService의 createAnnouncement 메서드가 한 번 호출되었는지 확인
        verify(announcementService, times(1)).createAnnouncement(any(AnnouncementDTO.class));
    }

    @Test
    @DisplayName("특정 ID의 공지사항 조회 테스트")
    void testGetAnnouncement() throws Exception {
        Long id = 1L;

        // announcementService의 getAnnouncement 메서드가 호출되면 미리 만든 announcementDTO 객체를 반환하도록 설정
        when(announcementService.getAnnouncement(id)).thenReturn(announcementDTO);

        // /announcement/{id}로 GET 요청을 보내고, 응답 상태를 검증
        mockMvc.perform(get("/announcement/" + id))
                .andExpect(status().isOk()); // 200 (OK) 상태 코드를 반환하는지 확인

        // announcementService의 getAnnouncement 메서드가 한 번 호출되었는지 확인
        verify(announcementService, times(1)).getAnnouncement(id);
    }

    @Test
    @DisplayName("공지사항 수정 테스트")
    void testUpdateAnnouncement() throws Exception {
        Long id = 1L;

        // announcementService의 updateAnnouncement 메서드가 호출되면 미리 만든 announcementDTO 객체를 반환하도록 설정
        when(announcementService.updateAnnouncement(anyLong(), any(AnnouncementDTO.class))).thenReturn(announcementDTO);

        // /announcement/update/{id}로 POST 요청을 보내고, 응답 상태와 리다이렉트 URL을 검증
        mockMvc.perform(post("/announcement/update/" + id)
                        .param("title", "Updated Test Title")
                        .param("content", "Updated Test Content")
                        .param("writer", "Updated Test Writer"))
                .andExpect(status().is3xxRedirection()) // 3xx (Redirection) 상태 코드를 반환하는지 확인
                .andExpect(redirectedUrl("/announcement/" + id)); // 리다이렉트 URL을 검증

        // announcementService의 updateAnnouncement 메서드가 한 번 호출되었는지 확인
        verify(announcementService, times(1)).updateAnnouncement(anyLong(), any(AnnouncementDTO.class));
    }

    @Test
    @DisplayName("공지사항 삭제 테스트")
    void testDeleteAnnouncement() throws Exception {
        Long id = 1L;

        // announcementService의 deleteAnnouncement 메서드가 호출되면 아무런 동작도 수행하지 않도록 설정
        doNothing().when(announcementService).deleteAnnouncement(id);

        // /announcement/delete/{id}로 POST 요청을 보내고, 응답 상태와 리다이렉트 URL을 검증
        mockMvc.perform(post("/announcement/delete/" + id))
                .andExpect(status().is3xxRedirection()) // 3xx (Redirection) 상태 코드를 반환하는지 확인
                .andExpect(redirectedUrl("/announcement")); // 리다이렉트 URL을 검증

        // announcementService의 deleteAnnouncement 메서드가 한 번 호출되었는지 확인
        verify(announcementService, times(1)).deleteAnnouncement(id);
    }
}
