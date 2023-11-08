package com.studiosheepdog.dognapper.announcement.application.controller;

import com.studiosheepdog.dognapper.announcement.application.dto.AnnouncementDTO;
import com.studiosheepdog.dognapper.announcement.application.service.AnnouncementAppService;
import com.studiosheepdog.dognapper.announcement.domain.aggregate.entity.Announcement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/announcement")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementAppService announcementAppService;

    @GetMapping// 전체 조회
    public String listAnnouncements(Model model, Pageable pageable) {
        Page<Announcement> announcements = announcementAppService.getAnnouncements(pageable);
        model.addAttribute("announcements", announcements);
        return "announcement/list";
    }

    @GetMapping("/createForm") // 공지사항 작성 페이지 이동
    public String createAnnouncementForm(Model model) {
        model.addAttribute("announcementDTO", new AnnouncementDTO());
        return "announcement/create";
    }
    @PostMapping("/create") // 공지사항 작성
    public String createAnnouncement(@ModelAttribute AnnouncementDTO announcementDTO, Model model) {
        Announcement announcement = announcementAppService.createAnnouncement(announcementDTO);
        model.addAttribute("announcement", announcement);
        return "redirect:/announcement";
    }
}
