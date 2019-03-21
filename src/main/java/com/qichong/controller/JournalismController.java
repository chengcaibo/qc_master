package com.qichong.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.qichong.entity.JournalismInfo;
import com.qichong.service.JournalismService;

/***
 * 
 * 新闻视图层
 * @创建人：陈文瑾
 *	@时间：2017年12月13日13:01:11
 *
 */

@Controller
public class JournalismController {
	
	@Autowired
	JournalismService journalismService;
	
	@RequestMapping(path = "/byId", method = RequestMethod.GET)
	public String byUserIdAppintmentTime(Integer id,Model model) {
		JournalismInfo journalismInfo = journalismService.queryJournalimsById(id);
		model.addAttribute("journalismInfo", journalismInfo);
		return "search/news";
	}
}












