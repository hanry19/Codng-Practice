package com.example.boardPrac2.controller;


import com.example.boardPrac2.dto.BoardDto;
import com.example.boardPrac2.dto.Criteria;
import com.example.boardPrac2.dto.FileVO;
import com.example.boardPrac2.dto.PageDTO;
import com.example.boardPrac2.service.BoardService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.spring5.util.FieldUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Controller
public class BoardController {

    // 스프링 빈 컨테테이너에 BoardService 클래스를 주입하여 하나의 객체를 공유하는 형식으로 사용한다 => single tone 방식
    // 스프링 프레임워크는 기존의 싱글톤 방식의 단점을 보완하여 사용할 수 있고, 그로인해 불필요한 객체 생성을 방지한다.

    @Autowired
    BoardService boardService;

    @RequestMapping("/")
    private String BoardList(Criteria cri, Model model) {
        List<BoardDto> boardDtos = new ArrayList<>();

        boardDtos = boardService.getFileBoardList(cri);

        model.addAttribute("boardDtos", boardDtos);
        model.addAttribute("pageMaker", new PageDTO(cri, boardService.getTotal(cri)));


        return "fileBoard/list";
    }

//    @PathVariable은 경로 변수이다.
//    @RequsrtMapping은 URL 경로를 템플릿 화 할 수 있는데, @PathVariable 을 사용하면
//    매칭되는 부분을 편리하게 조회할 수 있다. 또한 이름과 파라미터 이름이 같으면 생략이 가능하다. (다중 적용가능)

    @RequestMapping("/detail/{b_no}")
    private String boardDetail(@PathVariable("b_no") int b_no, Model model) {

        // 클라이언트가 선택한 url의 쿼리문 중 "b_no" 에 해당하는 값의 이름을 파라미터와 동일하게 하여 가져온다.
        // 그리고 이전 list페이지에서 가지고 있는 model을 받아 "detail" 이란 객체이름으로 한 속성을 넣는다.
        // 이 역시 로직을 위해 service의 detail메서드로 전달하고,
        // 데이터베이스로부터 쿼리문을 실행시킨 값을 받아 리턴 받는다.
        // 그리고 "fileBoard/detail" 이라는 논리적 주소를 리턴한다.
        model.addAttribute("detail", boardService.fileBoardDetail(b_no));

/* 2021.04.29 파일 다운 로드 로직 추가 */

        if (boardService.fileDown(b_no) == null) {
            return "fileBoard/detail";
        } else {
            model.addAttribute("files", boardService.fileDown(b_no));
            return "fileBoard/detail";
        }
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

/*
2021.04.29 첨부파일 삽입 기능 추가 구현



*/
    @GetMapping("/write")
    private String fileWrite(@ModelAttribute BoardDto boardDto) {
        return "fileBoard/insert";
    }

    //    파일 업로드
/*    @PostMapping("/uploadFile")
    private String fileUpload(@ModelAttribute FileVO fileVO,
                              @RequestParam MultipartFile files)
            throws IllegalAccessException, IOException,Exception{

        File file = new File("C:/Temp/");
        if(file.exists()) {
            file.mkdir();
        }
        String fileName = "C:/Temp/" + UUID.randomUUID() + files.getOriginalFilename();
        File file1 = new File(fileName);
        files.transferTo(file1);

        return "/";
    }*/
    @RequestMapping("/writeSub")
    private String fileBoardInsertProc(@ModelAttribute BoardDto boardDto, @RequestPart MultipartFile
            files, HttpServletRequest request) throws IllegalStateException, IOException, Exception {

        if(files.isEmpty()) {
            boardService.fileBoardInsert(boardDto);

        } else {
            String fileName = files.getOriginalFilename(); // 사용자 컴에 저장된 파일명 그대로
            //확장자
            String fileNameExtension = FilenameUtils.getExtension(fileName).toLowerCase();
            File destinationFile; // DB에 저장할 파일 고유명
            String destinationFileName;
            //절대경로 설정 안해주면 지 맘대로 들어가버려서 절대경로 박아주었습니다.
            String fileUrl = "D:\\HSJ\\temp\\";

            do { //우선 실행 후
                //고유명 생성
//                destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + fileNameExtension;
                destinationFileName = UUID.randomUUID() + "." + fileNameExtension;      //내가 수정한거
                destinationFile = new File(fileUrl + destinationFileName); //합쳐주기
            } while (destinationFile.exists());

            destinationFile.getParentFile().mkdirs(); //디렉토리
            files.transferTo(destinationFile);

            boardService.fileBoardInsert(boardDto);



            FileVO file = new FileVO();
            file.setB_no(boardDto.getB_no());
            file.setFileName(destinationFileName);
            file.setFileOriginName(fileName);
            file.setFileUrl(fileUrl);

            boardService.fileInsert(file);
        }

        return "forward:/"; //객체 재사용
    }




}