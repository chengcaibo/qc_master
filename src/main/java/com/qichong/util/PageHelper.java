package com.qichong.util;


import java.util.HashMap;
import java.util.Map;

/**
 * 构造时候给它传入row和page
 *
 * 构造之后调用getParams就能返回map啦
 *
 */

public class PageHelper {

    Integer page;
    Integer limit;


    /**
     * 设置分页信息
     * 【需求】从第page页返回rows行信息
     * 【结果】从第offset位置返回（最多）limit条信息
     */
    public Map<String, Object> getParams() {

        Map<String, Object> params = new HashMap();

        Integer limits = limit;//行数是limit
        Integer offset = limit * (page - 1);//根据行数计算偏移量

        //参数放进来
        params.put("limit", limits);
        params.put("offset",offset);

        return params;
    }



    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public PageHelper(Integer page, Integer limit) {
        this.page = page;
        this.limit = limit;
    }

    public PageHelper() {
    }
}
