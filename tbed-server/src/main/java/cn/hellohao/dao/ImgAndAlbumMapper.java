package cn.hellohao.dao;

import cn.hellohao.entity.Images;
import cn.hellohao.entity.ImgAndAlbum;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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





    Page<Images> selectImgForAlbumkey(@Param("page")Page<Images> page,@Param("albumKey") String albumKey);
}
