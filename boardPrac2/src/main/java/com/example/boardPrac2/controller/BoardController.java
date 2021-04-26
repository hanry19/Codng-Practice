package com.example.boardPrac2.controller;


import com.example.boardPrac2.dto.BoardDto;
import com.example.boardPrac2.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@Controller
public class BoardController {

    // 스프링 빈 컨테테이너에 BoardService 클래스를 주입하여 하나의 객체를 공유하는 형식으로 사용한다 => single tone 방식
    // 스프링 프레임워크는 기존의 싱글톤 방식의 단점을 보완하여 사용할 수 있고, 그로인해 불필요한 객체 생성을 방지한다.
    @Autowired
    BoardService boardService;


    @RequestMapping("/")
    private String BoardList(Model model, HttpServletRequest request) {
        List<BoardDto> boardDtos = new ArrayList<>();

        // boardDtos(ArrayList) 는 서비스인터페이스의 fileBoardList를 불러온다.
        // boardService 는 인터페이스로 해당 인터페이스를 구현하는 클래스의 메서드의 getFileBoaradList를 실행시킨다.
        // 실행한 메서드는 BoardMapper 인터페이스의 getFileBoardList를 반환하며
        // 이 mapper는 mapper.xml과 매핑 되어 잇어, sql문을 실행시켜 값을 가져온다.

            /*     @Override
                      public List<BoardDto> getFileBoardList() {
                      return boardMapper.getFileBoardList();
                 }*/

        // 그러니까 ArrayList인 boardDtos는 db에서
        // 'getFileBoardList()"라는 ID를 가진 녀석의 쿼리문을 실행시킨 값이다!
        boardDtos = boardService.getFileBoardList();

        // 그리고 db에서 가져온 값을 model의 속성에 "boardDtos" 라는 이름으로 추가
        // 이 모델은 view단으로 연결이 된다!
        model.addAttribute("boardDtos", boardDtos);

        // 그리고 논리적 주소를 반환한다.
        // 이 의미는 "/" 로 호출이 오면 "fileBoarad/List"로 연결해주는 것이다.
        return "fileBoard/list";
    }

//    @PostMapping("/")
//    private String BoardListPost(Model model, HttpServletRequest request) {
//        List<BoardDto> boardDtos = new ArrayList<>();
//        boardDtos = boardService.getFileBoardList();
//        model.addAttribute("boardDtos", boardDtos);
//        return "fileBoard/list";
//    }


// 클라이언트가 게시판에서 디테일한 정보를 요청할 때 전달되는 url은 "/detail/b_no" 이다.
//    여기서 b_no라는 것은 쿼리문으로 게시글 번호마다 다르게 측정될 것이다
//    그리고 getMapping을 하는 이유 역시 서버에 데이터를 요청하는 것이기 때문이다.
//    get /post 같은 rest API도 추후 정리하여 올리겠다.

    @GetMapping("/detail/{b_no}")

//    @PathVariable은 경로 변수이다.
//    @RequsrtMapping은 URL 경로를 템플릿 화 할 수 있는데, @PathVariable 을 사용하면
//    매칭되는 부분을 편리하게 조회할 수 있다. 또한 이름과 파라미터 이름이 같으면 생략이 가능하다. (다중 적용가능)

    private String boardDetail(@PathVariable("b_no") int b_no, Model model) {

        // 클라이언트가 선택한 url의 쿼리문 중 "b_no" 에 해당하는 값의 이름을 파라미터와 동일하게 하여 가져온다.
        // 그리고 이전 list페이지에서 가지고 있는 model을 받아 "detail" 이란 객체이름으로 한 속성을 넣는다.
        // 이 역시 로직을 위해 service의 detail메서드로 전달하고,
        // 데이터베이스로부터 쿼리문을 실행시킨 값을 받아 리턴 받는다.
        // 그리고 "fileBoard/detail" 이라는 논리적 주소를 리턴한다.

        model.addAttribute("detail", boardService.fileBoardDetail(b_no));

        return "fileBoard/detail";

    }

    // 여기서 postMapping인 이유는 detail 페이지에서 수정 버튼을 눌리면 해당 페이지의 정보를 가져온 뒤
    // 수정을 진행하기 때문이다. get은 내가 정보가 없어도 받을 수 있고, post 는 가지고 잇는 정보를 주고, 값을 받는 형식임을 명심하자.4
    // 그래서 detail페이지 중 "수정" 버튼에 달아놓은 링크에 따라 아래 메서드가 호출된다. 이 역시 @PathVariable(경로변수)
    // 템플릿화 된 URL 에서 일치하는 부분의 값을 가져올 수 있다.
    @PostMapping("/edit/{b_no}")
    private String boardEdit(@PathVariable("b_no") int b_no, Model model) {
        model.addAttribute("detail", boardService.fileBoardDetail(b_no));
        return "fileBoard/update";
    }

    // 기존의 내용을 수정하는 거니까 post mapping이다. 게다가 model attribute까지 가져오니 빼박!
    @PostMapping("/update")
    private String boardEditDone(BoardDto boardDto) {
//        @ModelAttibute는 이름을 붙혀서 사용할 수  있는데 생략도 가능하다.
//        private String boardEditDone(@ModelAttribute BoardDto boardDto) ==> 생략 ㄴㄴ
//        생략 시 모델에 저정될 떄 클래스 명을 사용한다.. 이때 클래스의 첫 글자만 소문자로 변경해서 등록한다 .

        boardService.fileBoardUpdate(boardDto);

        int bno = boardDto.getB_no();
        String b_no = Integer.toString(bno);

        // 리다이렉트 할 때 논리적 주소를 적는게 아니라  URL을 적는 것이다.
        return "redirect:/detail/" + b_no;
    }

    @PostMapping("/delete/{b_no}")
    private String fileDelete(@PathVariable("b_no") int b_no) {
        boardService.fileBoardDelete(b_no);

        return "redirect:/";
    }

    @GetMapping("/write")
    private String fileWrite(@ModelAttribute BoardDto boardDto) {
        return "fileBoard/insert";
    }

    @PostMapping("/writeSub")
    private String fileWriteSub(@ModelAttribute BoardDto boardDto) {
        boardService.fileBoardInsert(boardDto);
        return "redirect:/";
    }

/*
    @RequestMapping("/")
    private String fileBoardList(Model model, HttpServletRequest request) {

        List<BoardDto> List = new ArrayList<>();
        List = boardService.getFileBoardList();
        model.addAttribute("List", List);
        return "/fileBoard/list";
    }

    @RequestMapping("/detail/{b_no}")
    private String fileBoardDetail(@PathVariable("b_no") int b_no, Model model) {
        model.addAttribute("detail", boardService.fileBoardDetail(b_no));
        return "fileBoard/detail";
    }

    @RequestMapping("/insert")
    private String fileBoardInsertForm(@ModelAttribute BoardDto board) {
        return "fileBoard/insert";
    }

    @RequestMapping("/insertProc")
    private String fileBoardInsertProc(@ModelAttribute BoardDto board, HttpServletRequest request) {
        boardService.fileBoardInsert(board);
        return "forward:/fileBoard/list"; //객체 재사용
    }

    @RequestMapping("/update/{b_no}")
    private String fileBoardUpdateForm(@PathVariable("b_no") int b_no, Model model) {
        model.addAttribute("detail", boardService.fileBoardDetail(b_no));
        return "fileBoard/update";
    }

    @RequestMapping("/updateProc")
    private String fileBoardUpdateProc(@ModelAttribute BoardDto board) {

        boardService.fileBoardUpdate(board);
        int bno = board.getB_no();
        String b_no = Integer.toString(bno);
        return "redirect:/fileBoard/detail/"+b_no;
    }

    @RequestMapping("/delete/{b_no}")
    private String fileBoardDelete(@PathVariable("b_no") int b_no) {
        boardService.fileBoardDelete(b_no);
        return "redirect:/fileBoard/list";
    }
*/

}