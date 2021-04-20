package board.mybatis.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileBoardVO {

    private int b_no;
    private String title;
    private String content;
    private String writer;
    private Date reg_date;
}
