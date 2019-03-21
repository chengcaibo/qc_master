package com.qichong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.qichong.entity.NewsInformation;
import com.qichong.service.NewsInformationService;

/**
 * 动态新闻
 * @创建者：陈文瑾
 * @创建时间：2017年12月14日15:53:10
 *
 */
@Controller
public class NewsInformationController {

	@Autowired
	NewsInformationService newsInformationService;
	
	@RequestMapping(path = "/NewsbyId", method = RequestMethod.GET)
	public String byIdNews(Integer id,Model model) {
		NewsInformation newsInfo = newsInformationService.queryNewsInformationById(id);
		model.addAttribute("newsInfo", newsInfo);
		return "search/news";
	}
	
}
