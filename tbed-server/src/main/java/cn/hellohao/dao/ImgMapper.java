package cn.hellohao.dao;

import java.util.List;

import cn.hellohao.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.hellohao.entity.Images;

@Mapper
public interface ImgMapper extends BaseMapper<Images> {

    List<Images> selectimg(Images images);

    Integer countimg(@Param("userid") Integer userid);

    Integer deleimg(@Param("id") Integer id);

    Integer deleimgForImgUid(@Param("imguid") String imguid);

    Images selectByPrimaryKey(@Param("id") Integer id);

    Integer counts(@Param("userid") Integer userid);

    Integer setImg(Images images);

    Integer deleimgname(@Param("imgname") String imgname);
    Integer deleall(@Param("id") Integer id);

    List<Images> gettimeimg(@Param("time") String time);

    Long getusermemory(@Param("userid") Integer userid);

    Long getsourcememory(@Param("source") Integer source);

    Integer md5Count(Images images);

    Images selectImgUrlByMD5(@Param("md5key") String md5key);

    List<Images> RecentlyUploaded(@Param("userid") Integer userid);

    List<User> RecentlyUser();

    List<String> getyyyy(@Param("userid") Integer userid);

    List<Images> countByM(Images images);

    Images selectImgUrlByImgUID(@Param("imguid") String imguid);

}
