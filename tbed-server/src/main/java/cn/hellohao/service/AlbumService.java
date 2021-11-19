package cn.hellohao.service;

import cn.hellohao.entity.Album;
import cn.hellohao.entity.dto.AlbumDto;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Hellohao
 * @version 1.0
 * @date 2019-12-18 22:15
 */
@Service
public interface AlbumService extends IService<Album> {

    JSONArray getAlbumList(String[] array);

    Album selectAlbum(AlbumDto albumDto);

    Integer addAlbum(Album  album );

    Integer deleteAlbum(String albumkey);

    List<Album> selectAlbumURLList(AlbumDto albumDto);

    Integer selectAlbumCount(Integer userid);
}
