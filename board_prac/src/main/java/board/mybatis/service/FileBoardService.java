package board.mybatis.service;

import board.mybatis.bean.FileBoardVO;
import board.mybatis.bean.FileVO;

import java.util.List;

public interface FileBoardService {

    List<FileBoardVO> getFileBoardList();
    FileBoardVO fileBoardDetail(int b_no);
    int fileBoardInsert(FileBoardVO fileBoard);
    int fileBoardUpdate(FileBoardVO fileBoard);
    int fileBoardDelete(int bno);

    //파일 업로드 및 다운로드 메서드 추가
    int fileInsert(FileVO file);
    FileVO fileDetail(int b_no);
}