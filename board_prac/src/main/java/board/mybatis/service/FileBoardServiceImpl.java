package board.mybatis.service;


import board.mybatis.bean.FileBoardVO;
import board.mybatis.mapper.FileBoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileBoardServiceImpl implements FileBoardService{

    @Autowired
    FileBoardMapper fileboardmapper;

    @Override
    public List<FileBoardVO> getFileBoardList() {
        return fileboardmapper.getFileBoardList();
    }

    @Override
    public FileBoardVO fileBoardDetail(int b_no) {
        return fileboardmapper.fileBoardDetail(b_no);
    }

    @Override
    public int fileBoardInsert(FileBoardVO fileBoard) {
        return fileboardmapper.fileBoardInsert(fileBoard);
    }

    @Override
    public int fileBoardUpdate(FileBoardVO fileBoard) {
        return fileboardmapper.fileBoardUpdate(fileBoard);
    }

    @Override
    public int fileBoardDelete(int bno) {
        return fileboardmapper.fileBoardDelete(bno);
    }

}