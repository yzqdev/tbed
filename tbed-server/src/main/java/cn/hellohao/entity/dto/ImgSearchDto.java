package cn.hellohao.entity.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * @author yanni
 * @date time 2021/11/19 19:27
 * @modified By:
 */
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Data
public class ImgSearchDto<T> extends Page<T> {
    @Serial
    private static final long serialVersionUID = 1L;

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
    public ImgSearchDto(Integer pageNum,Integer pageSize){
        super(pageNum,pageSize);
    }
}
