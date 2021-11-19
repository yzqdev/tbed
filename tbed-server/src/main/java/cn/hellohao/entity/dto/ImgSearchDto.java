package cn.hellohao.entity.dto;

import lombok.Data;

/**
 * @author yanni
 * @date time 2021/11/19 19:27
 * @modified By:
 */
@Data
public class ImgSearchDto {
    private Integer pageNum;
    private  Integer pageSize;
    private String username;
    private Integer source;
    private String startTime;
    private String stopTime;
    private Integer selectType;
    private  String[] classifulids;
    private Boolean violation;
    private Integer userId;
}
