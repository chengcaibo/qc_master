package com.qichong.dao;

import com.qichong.entity.ActivityState;
import com.qichong.entity.AwardWinnerInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PrizeDrawDao {
    /**
     * 将中奖者的名单保存到数据库中
     * @param awardWinnerInfo 中奖者的名单
     * @return 返回是否插入成功
     */
    @Insert("INSERT INTO award_winner VALUES(null,#{userId},#{telephone},#{winningCode},#{winningAmount})")
    public Integer insertAwardWinner(AwardWinnerInfo awardWinnerInfo);

    /**
     *
     * @param amount 根据中奖的金额来查询获奖者的信息
     * @return
     */
    @Select("select * from award_winner where winningAmount=#{amount}")
    List<AwardWinnerInfo> queryAwardWinnersByWinningAmount(Integer amount);

    /**
     *
     * @param userId 根据用户的userId查询用户是否获得奖金
     * @return
     */
    @Select("select id,userId,telephone,winningCode,a.winningAmount,wxcode from award_winner a , (select wxcode, winningAmount from winningamount_wxcode) w where a.winningAmount=w.winningAmount and a.userId=#{userId}")
    List<AwardWinnerInfo> queryAwardWinnerStateByUserId(Integer userId);

    /**
     *
     * @param winningAmount 根据中奖金额查询出该中奖金额对应需要添加的微信号
     * @return
     */
    @Select("select wxcode from winningamount_wxcode where winningAmount=#{winningAmount}")
    String queryWxCodeByWinningAmount(Integer winningAmount);

    @Select("select * from activitystart where id=1")
    ActivityState queryState();
}
