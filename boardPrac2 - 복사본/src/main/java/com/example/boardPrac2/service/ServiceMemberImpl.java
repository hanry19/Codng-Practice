package com.example.boardPrac2.service;

import com.example.boardPrac2.dto.BoardPager;
import com.example.boardPrac2.dto.MemberDTO;
import com.example.boardPrac2.mapper.MainMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class ServiceMemberImpl implements ServiceMember{

    @Autowired
    MainMapper mainMapper;

    @Override
    public int execute(Model model, String pagenum, String contentnum) {

        BoardPager pagemaker = new BoardPager();

        int cpagenum = Integer.parseInt(pagenum);
        int ccontentnum = Integer.parseInt(contentnum);

        List<MemberDTO> testList = null;

        pagemaker.setTotalcount(mainMapper.testcount()); // mapper 전체 게시글 개수를 지정한다
        pagemaker.setPagenum(cpagenum-1);   // 현재 페이지를 페이지 객체에 지정한다 -1 을 해야 쿼리에서 사용할수 있다
        pagemaker.setContentnum(ccontentnum); // 한 페이지에 몇개씩 게시글을 보여줄지 지정한다.
        pagemaker.setCurrentblock(cpagenum); // 현재 페이지 블록이 몇번인지 현재 페이지 번호를 통해서 지정한다.
        pagemaker.setLastblock(pagemaker.getTotalcount()); // 마지막 블록 번호를 전체 게시글 수를 통해서 정한다.

        pagemaker.prevnext(cpagenum);//현재 페이지 번호로 화살표를 나타낼지 정한다.
        pagemaker.setStartPage(pagemaker.getCurrentblock()); // 시작 페이지를 페이지 블록번호로 정한다.
        pagemaker.setEndPage(pagemaker.getLastblock(),pagemaker.getCurrentblock());
        //마지막 페이지를 마지막 페이지 블록과 현재 페이지 블록 번호로 정한다.

        if(ccontentnum == 10){//선택 게시글 수
            testList = mainMapper.testlist(pagemaker.getPagenum()*10,pagemaker.getContentnum());
        }else if(ccontentnum == 20){
            testList = mainMapper.testlist(pagemaker.getPagenum()*20,pagemaker.getContentnum());
        }else if(ccontentnum ==30){
            testList = mainMapper.testlist(pagemaker.getPagenum()*30,pagemaker.getContentnum());
        }

        model.addAttribute("test",testList);
        model.addAttribute("page",pagemaker);

        return 0;
    }

}