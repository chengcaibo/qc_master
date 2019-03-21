package com.qichong.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qichong.dao.BannerDao;
import com.qichong.entity.Banners;
import com.qichong.enums.StateEnum;
import com.qichong.enums.TypesEnum;

/**
 * Banner Service
 * 
 * @author 孙建雷
 *
 */
@Service
public class BannerService {

	@Autowired
	BannerDao bannerDao;

	/** 查询小程序首页Banner */
	public List<Banners> queryWeAppHomeBanner() {
		return this.queryBanner(TypesEnum.BANNER_WEAPP_HOME);
	}

	/** 根据TypesEnum查询Banner */
	public List<Banners> queryBanner(TypesEnum type) {
		return this.queryBanner(type.getId());
	}

	/** 根据typeId查询Banner */
	public List<Banners> queryBanner(int typeId) {
		return bannerDao.selectByTypeId(StateEnum.NORMAL.getId(), typeId);
	}

}
