package com.example.boardPrac2.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;


@Getter
@Setter
@ToString
public class Criteria {

    private int pageNum;            // 보여줄 페이지 번호
    private int amount;   // 페이지당 보여줄 게시글의 개수

    public Criteria() {
        this(1, 10);
    }

    public Criteria(int pageNum, int amount) {
        this.pageNum = pageNum;
        this.amount = amount;
    }
}