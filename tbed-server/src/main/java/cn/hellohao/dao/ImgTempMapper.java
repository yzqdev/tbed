package cn.hellohao.dao;

import cn.hellohao.entity.Images;
import cn.hellohao.entity.ImgTemp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @Entity dao.pojo.Imgdataexp
*/
@Mapper
public interface ImgTempMapper {

    List<Images> selectDelImgUidList(@Param("datatime") String datatime);
    Integer delImgAndExp(@Param("imguid") String imguid);
    Integer insertImgExp(ImgTemp imgDataExp);

}
