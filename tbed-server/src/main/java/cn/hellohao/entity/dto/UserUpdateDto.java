package cn.hellohao.entity.dto;

import cn.hellohao.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yanni
 * @date time 2021/11/19 23:01
 * @modified By:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserUpdateDto extends User {
    private Long counts;
    private String groupName;

}
