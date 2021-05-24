package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResponseDTO<DTO,ENTITY> {
    private List<DTO> dtoList;

    private int totalPage;

    private int nowPage;

    private int size;   //목록 사이즈

    private int start,end;

    private boolean prev,next;

    private List<Integer> pageList;

    public PageResponseDTO(Page<ENTITY> result, Function<ENTITY,DTO> function){
        dtoList = result.stream()
                        .map(function)
                        .collect(Collectors.toList());

        totalPage=result.getTotalPages();
        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable){
        this.nowPage=pageable.getPageNumber()+1;
        this.size=pageable.getPageSize();

        int tempEnd = (int)(Math.ceil(nowPage/10.0))*10;

        start  = tempEnd-9;
        prev = start>1;
        next = totalPage>tempEnd;
        end=totalPage>tempEnd ? tempEnd:totalPage;

        pageList = IntStream.rangeClosed(start,end)
                .boxed()    //Returns a Stream consisting of the elements of this stream, each boxed to an Integer.
                .collect(Collectors.toList());

    }
}
