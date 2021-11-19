package cn.hellohao.entity.dto;

import cn.hellohao.entity.Config;
import cn.hellohao.entity.SysConfig;
import cn.hellohao.entity.UploadConfig;
import lombok.Data;

/**
 * @author yanni
 * @date time 2021/11/20 1:00
 * @modified By:
 */
@Data
public class ConfigDto {
    private UploadConfig uploadConfig;
    private SysConfig sysConfig;
    private Config config;
}
