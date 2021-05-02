package com.example.boardPrac2.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileVO {

    private int fno;
    private int b_no;
    private String fileName;
    private String fileOriginName;
    private String fileUrl;
    private Data uploadDate;

}
