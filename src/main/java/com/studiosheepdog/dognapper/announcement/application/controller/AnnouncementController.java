package com.studiosheepdog.dognapper.announcement.application.controller;

import com.studiosheepdog.dognapper.announcement.application.dto.AnnouncementDTO;
import com.studiosheepdog.dognapper.announcement.application.service.AnnouncementAppService;
import com.studiosheepdog.dognapper.announcement.domain.aggregate.entity.Announcement;
import lombok.RequiredArgsConstructor;
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

    @GetMapping
    public String listAnnouncements() {
        //전체 공지사항이 보이는 로직 추가하기
        return "announcement/list";
    }

    @GetMapping("/createForm")
    public String createAnnouncementForm(Model model) {
        model.addAttribute("announcementDTO", new AnnouncementDTO());
        return "announcement/create";
    }
    @PostMapping("/create")
    public String createAnnouncement(@ModelAttribute AnnouncementDTO announcementDTO, Model model) {
        Announcement announcement = announcementAppService.createAnnouncement(announcementDTO);
        model.addAttribute("announcement", announcement);
        return "redirect:/announcement";
    }
}
