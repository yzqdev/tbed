package cn.hellohao.dao;

import cn.hellohao.entity.Album;
import cn.hellohao.entity.dto.AlbumDto;
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
public interface AlbumMapper extends BaseMapper<Album> {

    Album selectAlbum(AlbumDto albumDto);



    List<Album> selectAlbumURLList(AlbumDto albumDto);

    Integer selectAlbumCount(@Param("userId")  Integer userId);
}
