package board.mybatis.service;

import board.mybatis.bean.FileBoardVO;

import java.util.List;

public interface FileBoardService {

    List<FileBoardVO> getFileBoardList();
    FileBoardVO fileBoardDetail(int b_no);
    int fileBoardInsert(FileBoardVO fileBoard);
    int fileBoardUpdate(FileBoardVO fileBoard);
    int fileBoardDelete(int bno);

}