package cn.hellohao.mapper;

import java.util.List;

import cn.hellohao.entity.dto.HomeImgDto;
import cn.hellohao.entity.dto.ImgSearchDto;
import cn.hellohao.entity.vo.ImageVo;
import cn.hellohao.entity.vo.RecentUserVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.hellohao.entity.Images;

@Mapper
public interface ImgMapper extends BaseMapper<Images> {

  List<Images>  selectImageData(@Param("img") ImgSearchDto imgSearchDto);





    Integer deleimgForImgUid(@Param("imguid") String imguid);

    Images selectByPrimaryKey(@Param("id") String id);

    Integer counts(@Param("userId") String userId);

    Integer setImg(Images images);

    Integer deleimgname(@Param("imgname") String imgname);
    Integer deleall(@Param("id") String id);

    List<Images> getTimeImg(@Param("time") String time);

    Long getUserMemory(@Param("userid") String userid);

    Long getSourceMemory(@Param("source") String source);

    Integer md5Count(Images images);

    Images selectImgUrlByMD5(@Param("md5key") String md5key);

    List<Images> RecentlyUploaded(@Param("userId") String userId);

    List<RecentUserVo> RecentlyUser();

    List<String> getyyyy(@Param("userId") String userId);

    List<ImageVo> countByM(HomeImgDto homeImgDto);


}
