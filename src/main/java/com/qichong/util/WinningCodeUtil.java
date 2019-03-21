package com.qichong.util;

import com.qichong.util.redisutil.JedisAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用来生成中奖码，中奖吗从1开始生成
 */
@Service
public class WinningCodeUtil {

    @Autowired
    JedisAdapter jedisAdapter;
    public  int createWinningCode(){
        String activity = jedisAdapter.get("code");
        if(activity==null){
            jedisAdapter.set("copy","1");
            jedisAdapter.set("code","1318"+"1");
            return 13181;
        }else{
            jedisAdapter.incr("copy");
            String ret = jedisAdapter.get("copy");
            jedisAdapter.set("code","1318"+ret);
            String code = jedisAdapter.get("code");
            return Integer.parseInt(code);
        }
    }
}
