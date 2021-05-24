package com.example.demo;

import com.example.demo.dto.GuestbookDTO;
import com.example.demo.entity.Guestbook;
import com.example.demo.repository.GuestbookRepository;
import com.example.demo.service.GuestbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
    @Autowired
    private GuestbookService guestbookService;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        LongStream.rangeClosed(1,30)
                    .forEach(i->{
                        guestbookService.register(
                                GuestbookDTO.builder()
                                        .gno(i)
                                        .title("title "+i)
                                        .content("content "+i)
                                        .writer("writer "+i)
                                        .regDate(LocalDateTime.now())
                                        .modDate(LocalDateTime.now())
                                        .build()
                        );
                    });
    }
}
