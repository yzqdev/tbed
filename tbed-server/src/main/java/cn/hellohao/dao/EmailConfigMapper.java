package cn.hellohao.dao;

import cn.hellohao.entity.EmailConfig;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmailConfigMapper {
    EmailConfig getemail();
    Integer updateemail(EmailConfig emailConfig);
}
