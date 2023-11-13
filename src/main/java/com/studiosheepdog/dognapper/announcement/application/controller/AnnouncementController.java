package com.studiosheepdog.dognapper.announcement.application.controller;

import com.studiosheepdog.dognapper.announcement.application.dto.AnnouncementDTO;
import com.studiosheepdog.dognapper.announcement.application.service.AnnouncementAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/announcement")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementAppService announcementAppService;
    private static final String ANNOUNCEMENT_REDIRECTION = "redirect:/announcement";
    private static final String ANNOUNCEMENT = "announcement";

    @GetMapping // 전체 조회
    public String listAnnouncements(Model model, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("createdDate").descending()); // 내림차순 정렬
        Page<AnnouncementDTO> announcements = announcementAppService.getAnnouncements(pageable);
        model.addAttribute("announcements", announcements);
        return "announcement/list";
    }

    @GetMapping("/{id}") // 상세 조회
    public String viewAnnouncement(@PathVariable Long id, Model model) {
        AnnouncementDTO announcementDTO = announcementAppService.getAnnouncement(id);
        model.addAttribute(ANNOUNCEMENT, announcementDTO);
        return "announcement/detail";
    }


    @GetMapping("/createForm") // 공지사항 작성 페이지 이동
    public String createAnnouncementForm(Model model) {
        model.addAttribute("announcementDTO", new AnnouncementDTO());
        return "announcement/create";
    }

    @PostMapping("/create") // 공지사항 작성
    public String createAnnouncement(@ModelAttribute AnnouncementDTO announcementDTO, Model model) {
        AnnouncementDTO createdAnnouncement = announcementAppService.createAnnouncement(announcementDTO);
        model.addAttribute(ANNOUNCEMENT, createdAnnouncement);
        return ANNOUNCEMENT_REDIRECTION;
    }

    @GetMapping("/updateForm/{id}") // 공지사항 수정 페이지 이동
    public String updateAnnouncementForm(@PathVariable Long id, Model model) {
        AnnouncementDTO announcementDTO = announcementAppService.getAnnouncement(id);
        model.addAttribute("announcementDTO", announcementDTO);
        return "announcement/update";
    }

    @PostMapping("/update/{id}") // 공지사항 수정
    public String updateAnnouncement(@PathVariable Long id, @ModelAttribute AnnouncementDTO announcementDTO, Model model) {
        AnnouncementDTO updatedAnnouncement = announcementAppService.updateAnnouncement(id, announcementDTO);
        model.addAttribute(ANNOUNCEMENT, updatedAnnouncement);
        return "redirect:/announcement/" + id;
    }

    @PostMapping("/delete/{id}") // 공지사항 삭제
    public String deleteAnnouncement(@PathVariable Long id) {
        announcementAppService.deleteAnnouncement(id);
        return ANNOUNCEMENT_REDIRECTION;
    }
}
