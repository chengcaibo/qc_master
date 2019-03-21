package com.qichong.dao;

import com.qichong.entity.AllegeInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface AllegeDao {
    @Insert("insert into allegeinfo values(null,#{allegeContent},#{allegeImage},#{userId},1,null,NOW(),0)")
    int insertAllegeInfo(AllegeInfo allegeInfo);
    @Select("select * from allegeinfo where expire=1")
    List<AllegeInfo> getAllInfo();

    /**获取为被审核的申诉信息
     * @param map
     * @return
     */
    @Select("select * from allegeinfo WHERE allegeState=#{allegeState} and userId=#{userId} limit #{limit} offset #{offset}")
    List<AllegeInfo> getAllegeInfoByUserId(Map map);

    /**
     * 管理员驳回某个用户的申诉信息，并将该信息的状态expire改为过期1
     *
     * @param userId 用户的userId，根据userId驳回该用户的申诉
     * @param allegeResult 驳回理由
     * @param allegeState 修改该申诉信息的状态
     * @return
     */
    @Update("update allegeinfo set allegeResult=#{allegeResult},allegeState=#{allegeState},expire=1 where userId=#{userId} and expire=0")
    int updateAllegeResult(@Param(value = "userId") Integer userId, @Param(value = "allegeResult") String allegeResult,  @Param(value = "allegeState")Integer allegeState);

    /**
     *
     *
     * @param userId 更新该条申诉的申诉状态，这里用来将改用户的申诉状态改为成功状态(2),并将该条信息的状态改为过期1
     * @param allegeState
     * @return
     */
    @Update("update allegeinfo set allegeState=#{allegeState} ,expire=1 where userId=#{userId} and expire=0")
    int updateAllegeStateToSuccess(@Param("userId") Integer userId,@Param("allegeState") Integer allegeState);
    @Update("update user_info set blackList=0 where user_id=#{userId}")
    int dragOutBlackListByUserId(Integer userId);
}
