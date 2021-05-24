package com.example.demo.service;

import com.example.demo.dto.GuestbookDTO;
import com.example.demo.dto.PageRequestDTO;
import com.example.demo.dto.PageResponseDTO;
import com.example.demo.entity.Guestbook;

public interface GuestbookService {
    Long register(GuestbookDTO dto);

    //dto -> entity, JPA only can save entity, so need conversion
    default Guestbook dtoToEntity(GuestbookDTO dto){
        Guestbook entity
                =Guestbook.builder()
                        .gno(dto.getGno())
                        .title(dto.getTitle())
                        .content(dto.getContent())
                        .writer(dto.getWriter())
                        .build();
        return entity;
    }

    PageResponseDTO<GuestbookDTO,Guestbook> getList(PageRequestDTO dto);

    // entity -> dto, for using information of entity
    default GuestbookDTO entityToDto(Guestbook entity){
        GuestbookDTO dto
                = GuestbookDTO.builder()
                                .gno(entity.getGno())
                                .title(entity.getTitle())
                                .content(entity.getContent())
                                .writer(entity.getWriter())
                                .regDate(entity.getRegDate())
                                .modDate(entity.getModDate())
                                .build();

        return dto;
    }

    void deleteAll();
}
