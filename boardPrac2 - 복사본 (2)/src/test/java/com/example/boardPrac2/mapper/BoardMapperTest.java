package com.example.boardPrac2.mapper;

//import com.example.boardPrac2.controller.BoardController;
import com.example.boardPrac2.dto.BoardDto;
import com.example.boardPrac2.dto.Criteria;
import com.example.boardPrac2.dto.PageDTO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@Rollback(value = false)
class BoardMapperTest {

    @Autowired
    BoardMapper boardMapper;



    @Test
    @DisplayName("R test")
    void selectTest() {
        List<BoardDto> boardDto = boardMapper.getFileBoardList();
        for (BoardDto boardDtos : boardDto) {
            System.out.println("boardDto = " + boardDtos);
        }

    }

    @Test
    @DisplayName("C test")
    void insertTest() {
        BoardDto boardDto = new BoardDto();

        boardDto.setContent("test");
        boardDto.setWriter("tes");
        boardDto.setTitle("test");

        boardMapper.fileBoardInsert(boardDto);
    }

    @Test
    @DisplayName("u Test")
    void editTest() {
        BoardDto boardDto = new BoardDto();

        boardDto.setTitle("happy");
        boardDto.setWriter("dog");
        boardDto.setContent("메롱");

        boardMapper.fileBoardUpdate(boardDto);
    }

    @Test
    @DisplayName("d Tesdt")
    void deleteTest() {
        BoardDto boardDto = new BoardDto();

        boardMapper.fileBoardDetail(125);

    }

    @Test
    public void testPaging() {

        Criteria cri = new Criteria();
        List<BoardDto> list = boardMapper.getListWithPaging(cri);
    }

    @Test
    public void testPageDTO() {
        Criteria cri = new Criteria();
        cri.setPageNum(25);

        PageDTO pageDTO = new PageDTO(cri, 251);
        System.out.println("pageDTO = " + pageDTO);

    }





}