package com.example.demo.controller;

import com.example.demo.dto.GuestbookDTO;
import com.example.demo.dto.PageRequestDTO;
import com.example.demo.service.GuestbookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/guestbook")
@Controller
public class GuestBookController {
    private final GuestbookService guestbookService;

    @GetMapping("/")
    public String index(){
        return "redirect:/guestbook/list";
    }

    @GetMapping({"/list"})
    public void list(PageRequestDTO pageRequestDTO, Model model){
        log.info("list........."+pageRequestDTO);
        model.addAttribute("response",guestbookService.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register(){
        log.info("/register로 get 요청이 들어왔습니다.");
    }

    @PostMapping("/register")
    public String register(GuestbookDTO guestbookDTO, RedirectAttributes redirectAttributes){
        log.info("guestbookDTO= "+guestbookDTO);
        Long gno = guestbookService.register(guestbookDTO);

        redirectAttributes.addFlashAttribute("msg",gno);

        return "redirect:/guestbook/list";
    }


}
