package cn.hellohao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * 配置
 *
 * @author yanni
 * @date 2021/11/17
 */
@Data
@AllArgsConstructor
public class Config {
    private Integer id;
    private Integer sourcekey;
    private Integer emails;
    private String webname;
    private String explain;
    private String video;
    private String links;
    private String notice;
    private String baidu;
    private String domain;
    private String background1;
    private String background2;
    private String webms;
    private String webkeywords;
    private String webfavicons;
    private String websubtitle;
    private String logo;
    private String aboutInfo;


}
