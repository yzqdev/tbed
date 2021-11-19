package cn.hellohao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @author Hellohao
 * @version 1.0
 * @date 2019-12-18 22:13
 */
@Data
@AllArgsConstructor
public class Album {
    /**
     * albumkey
     */
    private String albumKey;
    /**
     * 专辑名称
     */
    private String albumTitle;
    /**
     * 创建日期
     */
    private String createDate;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户标识
     */
    private Integer userId;
    private String username;


    public Album() {
    }

}
