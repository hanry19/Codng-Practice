package com.example.boardPrac2.mapper;

import com.example.boardPrac2.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface MainMapper {


    public List<MemberDTO> testlist(int pagenum, int contentnum);

    public int testcount();

}
