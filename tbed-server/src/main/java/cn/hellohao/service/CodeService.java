package cn.hellohao.service;

import cn.hellohao.entity.Code;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Hellohao
 * @version 1.0
 * @date 2019-08-11 14:20
 */
@Service
public interface CodeService extends IService<Code> {
    //查询扩容码
    Page<Code> selectCode(String value);
    Code selectCodekey(String code);

    /**
     * 添加代码
     *
     * @param code 代码
     * @return {@link Integer}
     */
    Integer addCode(Code code);
    //删除扩容码
    Integer deleteCode(String code);

}
