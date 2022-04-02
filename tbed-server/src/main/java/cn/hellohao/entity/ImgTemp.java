package cn.hellohao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * By Hellohao
 *
 * @TableName ImgTemp
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ImgTemp {
    /**
     *
     */
    private Integer id;

    /**
     *
     */
    private String imgUid;


    /**
     *
     */
    private LocalDateTime delTime;


}