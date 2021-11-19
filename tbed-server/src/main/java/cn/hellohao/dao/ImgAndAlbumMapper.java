package cn.hellohao.dao;

import cn.hellohao.entity.Images;
import cn.hellohao.entity.ImgAndAlbum;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Hellohao
 * @version 1.0
 * @date 2019-12-18 22:15
 */
@Mapper
public interface ImgAndAlbumMapper extends BaseMapper<ImgAndAlbum> {


    List<ImgAndAlbum> getAlbumForImgname(@Param("imgname") String imgname);





    List<Images> selectImgForAlbumkey(@Param("albumKey") String albumKey);
}
