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
    Integer addImgAndAlbum(ImgAndAlbum imgAndAlbum);

    List<ImgAndAlbum> getAlbumForImgname(@Param("imgname") String imgname);

    Integer deleteImgAndAlbum(@Param("imgname") String imgname);

    Integer deleteImgAndAlbumForKey(@Param("albumkey") String albumkey);

    List<Images> selectImgForAlbumkey(@Param("albumkey") String albumkey);
}
