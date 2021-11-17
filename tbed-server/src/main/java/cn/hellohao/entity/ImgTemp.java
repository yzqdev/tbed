package cn.hellohao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * By Hellohao
 *
 * @TableName ImgTemp
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ImgTemp {
    /**
     *
     */
    private Integer id;

    /**
     *
     */
    private String imguid;


    /**
     *
     */
    private String deltime;


}