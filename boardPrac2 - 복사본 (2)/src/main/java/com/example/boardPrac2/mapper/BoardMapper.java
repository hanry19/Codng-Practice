package com.example.boardPrac2.mapper;

import com.example.boardPrac2.dto.BoardDto;
import com.example.boardPrac2.dto.Criteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    // 읽기
    List<BoardDto> getFileBoardList();
    // 자세히 읽기
    BoardDto fileBoardDetail(int b_no);
    // 삽입하기
    int fileBoardInsert(BoardDto boardDto);
    // 수정하기
    int fileBoardUpdate(BoardDto boardDto);
    //삭제하기
    int fileBoardDelete(int b_no);

    List<BoardDto> getListWithPaging(Criteria cri);

    int countArticles();

}
