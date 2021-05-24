package com.example.demo.service;

import com.example.demo.dto.GuestbookDTO;
import com.example.demo.dto.PageRequestDTO;
import com.example.demo.dto.PageResponseDTO;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GuestbookServiceImplTest {

    @Autowired
    private GuestbookService guestbookService;

    @After
    public void clean(){
        guestbookService.deleteAll();
    }

    @Test
    public void 등록(){
        GuestbookDTO guestbookDTO
                = GuestbookDTO.builder()
                                .title("sample title")
                                .content("sample content")
                                .writer("user0")
                                .build();

        System.out.println(guestbookService.register(guestbookDTO));
    }

    @Test
    public void 목록처리(){
        GuestbookDTO guestbookDTO
                = GuestbookDTO.builder()
                .title("sample title")
                .content("sample content")
                .writer("user0")
                .build();

        System.out.println(guestbookService.register(guestbookDTO));

        PageRequestDTO requestDTO =PageRequestDTO.builder()
                                                .page(1)
                                                .size(10)
                                                .build();

        PageResponseDTO responseDTO=guestbookService.getList(requestDTO);

        System.out.println("prev : "+responseDTO.isPrev()); //이전 페이지 링크 필요?
        System.out.println("next : "+responseDTO.isNext()); //다음 페이지 링크 필요?
        System.out.println("total : "+responseDTO.getTotalPage());

        responseDTO.getDtoList().stream()
                                .forEach(System.out::println);

        responseDTO.getPageList().stream()
                                .forEach(System.out::println);


    }

}