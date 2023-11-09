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
        model.addAttribute("announcement", announcementDTO);
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
        model.addAttribute("announcement", createdAnnouncement);
        return "redirect:/announcement";
    }
}

