package cn.hellohao.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yanni
 * @date time 2022/6/21 1:27
 * @modified By:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFind {

    String email;
    String retrieveCode;
}
