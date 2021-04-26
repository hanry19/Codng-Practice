package com.example.boardPrac2.service;

import com.example.boardPrac2.dto.MemberDTO;
import org.springframework.ui.Model;

import java.util.List;

public interface ServiceMember {

    public int execute(Model model, String pagenum, String contentnum);
}