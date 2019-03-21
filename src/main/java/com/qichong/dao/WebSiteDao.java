package com.qichong.dao;

import com.qichong.entity.WebSite;

public interface WebSiteDao {
	/**
	 * 新增企业产品信息
	 * @param webSite
	 * @return
	 */
  boolean insertWebSite(WebSite webSite);
  /**
   * 更新企业的产品的信息
   * @param webSite
   * @return
   */
   boolean updateWebSite(WebSite webSite);
}
