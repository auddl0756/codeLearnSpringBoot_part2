package com.example.demo.repository;

import com.example.demo.entity.Guestbook;
import com.example.demo.entity.QGuestbook;
import com.querydsl.core.BooleanBuilder;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GuestbookRepositoryTest {
    @Autowired
    private GuestbookRepository guestbookRepository;

    @After
    public void clean(){
        guestbookRepository.deleteAll();
    }

    @Test
    public void 여러개저장후_출력() {
        IntStream.rangeClosed(1, 30).forEach(
                i -> {
                    Guestbook guestbook
                            = Guestbook.builder()
                            .title("title..." + i)
                            .content("content..." + i)
                            .writer("writer" + (i % 10))
                            .build();
//                    System.out.println(guestbookRepository.save(guestbook));
                    guestbookRepository.save(guestbook);
                }
        );

        LocalDateTime time=guestbookRepository.findAll().get(0)
                .getModDate();
        System.out.println(time);
    }

    @Test
    public void 저장후수정_수정시간변하는지(){
        guestbookRepository.save(
                Guestbook.builder()
                        .title("title")
                        .content("content")
                        .writer("writer")
                        .build()
        );

        String changedTitle="changed title";

        Guestbook guestBook = guestbookRepository.findAll().get(0);
        guestBook.changeTitle(changedTitle);
        guestbookRepository.save(guestBook);

        Guestbook guestbook_reget=guestbookRepository.findAll().get(0);

        assertThat(guestbook_reget.getTitle()).isEqualTo(changedTitle);

        assertThat(guestbook_reget.getRegDate()).isNotEqualTo(guestbook_reget.getModDate());
    }

    @Test
    public void 쿼리DSL_단일항목(){
        IntStream.rangeClosed(1, 30).forEach(
                i -> {
                    Guestbook guestbook
                            = Guestbook.builder()
                            .title("title..." + i)
                            .content("content..." + i)
                            .writer("writer" + (i % 10))
                            .build();
//                    System.out.println(guestbookRepository.save(guestbook));
                    guestbookRepository.save(guestbook);
                }
        );

        Pageable pageable = PageRequest.of(0,10, Sort.by("gno").descending());
        QGuestbook qGuestbook = QGuestbook.guestbook;

        String keyword="1";
        BooleanBuilder builder  = new BooleanBuilder();
        BooleanExpression expression = qGuestbook.title.contains(keyword);

        builder.and(expression);

        Page<Guestbook> result = guestbookRepository.findAll(builder,pageable);

        result.stream().forEach(System.out::println);
    }

    @Test
    public void QureyDSL_다중항목검색(){
        Pageable pageable = PageRequest.of(0,10,Sort.by("gno").descending());

        QGuestbook qGuestbook = QGuestbook.guestbook;
        String keyword="1";

        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression exTitle = qGuestbook.title.contains(keyword);
        BooleanExpression exContent =qGuestbook.content.contains(keyword);

        BooleanExpression exAll = exTitle.or(exContent);

        builder.and(exAll);
        builder.and(qGuestbook.gno.gt(0L));

        Page<Guestbook> result = guestbookRepository.findAll(builder,pageable);

        result.stream().forEach(System.out::println);
    }



}