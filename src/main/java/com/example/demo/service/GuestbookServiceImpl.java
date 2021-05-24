package com.example.demo.service;

import com.example.demo.dto.GuestbookDTO;
import com.example.demo.dto.PageRequestDTO;
import com.example.demo.dto.PageResponseDTO;
import com.example.demo.entity.Guestbook;
import com.example.demo.repository.GuestbookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Log4j2
@RequiredArgsConstructor
@Service
public class GuestbookServiceImpl implements GuestbookService{
    @Autowired
    private final GuestbookRepository guestbookRepository;

    @Override
    public Long register(GuestbookDTO dto) {
        log.info("DTO===========");
        log.info(dto);

        Guestbook entity =dtoToEntity(dto);
        log.info(entity);

        guestbookRepository.save(entity);

        return entity.getGno();
    }

    //와 신기하다...
    @Override
    public PageResponseDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());

        Page<Guestbook> response = guestbookRepository.findAll(pageable);
        Function<Guestbook,GuestbookDTO> function = (this::entityToDto);

        return new PageResponseDTO<>(response,function);
    }

    @Override
    public void deleteAll() {
        guestbookRepository.deleteAll();
    }
}
