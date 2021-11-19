package cn.hellohao.service;


import java.util.List;

import cn.hellohao.entity.User;

import cn.hellohao.entity.Images;
import cn.hellohao.entity.dto.ImgSearchDto;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

@Service
public interface ImgService extends IService<Images> {
    List<Images> selectimg(ImgSearchDto imgSearchDto);

    Integer deleimg(Integer id);

    Integer deleimgForImgUid(String imguid);

    Integer countimg(Integer userid);

    Images selectByPrimaryKey(Integer id);

    Integer counts(Integer userid);

    Integer setImg(Images images);

    Integer deleimgname(String imgname);

    Integer deleall(Integer id);

    List<Images> gettimeimg(String time);

    Long getusermemory(Integer userid);

    Long getsourcememory(Integer source);

    Integer md5Count(Images images);

    Images selectImgUrlByMD5(String md5key);

    List<Images> RecentlyUploaded(Integer userid);

    List<User> RecentlyUser();

    List<String> getyyyy(Integer userid);

    List<Images> countByM(Images images);

    Images selectImgUrlByImgUID( String imguid);

}
