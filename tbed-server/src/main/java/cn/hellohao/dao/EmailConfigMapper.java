package cn.hellohao.dao;

import cn.hellohao.entity.EmailConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmailConfigMapper extends BaseMapper<EmailConfig> {
    EmailConfig getEmail();
    Integer updateEmail(EmailConfig emailConfig);
}
