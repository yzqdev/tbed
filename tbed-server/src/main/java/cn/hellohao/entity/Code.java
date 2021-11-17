package cn.hellohao.entity;

import lombok.ToString;

/**
 * @author Hellohao
 * @version 1.0
 * @date 2019-08-11 14:09
 */
@ToString
public class Code {
    private Integer id;
    private String value;
    private String code;

    public Code() {
    }

    public Code(Integer id, String value, String code) {
        this.id = id;
        this.value = value;
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
