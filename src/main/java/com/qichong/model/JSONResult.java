package com.qichong.model;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qichong.enums.RetEnum;
import com.qichong.token.Token;

/**
 *
 * JSON 返回类
 *
 * @author 孙建雷
 * 
 *         2017-11-2 09:57:33
 *
 */
public class JSONResult {

	public static Gson gson = new GsonBuilder() //
			.setDateFormat("yyyy-MM-dd HH:mm:ss") //
			.create();

	private Integer ret; // ajax请求后执行的结果，英文单词“result”的缩写，各个值解释详见 “开发规范表格”
	private String msg; // 请求失败后返回的详细信息，英文单词“message”的缩写，若成功则可以忽略不写
	private String tokenKey;
	private List<?> list; // 若请求返回多个结果，则放入
	private Integer total; // 返回当前请求的总条目数，用于分页查询中，例如分页查询企业，总共有100条，每页显示20条，而total就是100
	private Object result;

	public JSONResult() {
	}

	public JSONResult(RetEnum retEnum) {
		this.ret = retEnum.getValue();
	}

	public JSONResult(RetEnum retEnum, String msg) {
		this.ret = retEnum.getValue();
		this.msg = msg;
	}

	public JSONResult(RetEnum retEnum, String msg, List<?> list, Integer total) {
		this.ret = retEnum.getValue();
		this.msg = msg;
		this.list = list;
		this.total = total;
	}

	public static JSONResult builder() {
		return new JSONResult();
	}

	public static JSONResult builder(RetEnum retEnum) {
		return builder().setRet(retEnum);
	}

	public static JSONResult builder(RetEnum retEnum, String msg) {
		return builder().setRet(retEnum).setMsg(msg);
	}
	public static String builder(Map map) {
		return builder().setMap(map);
	}

	/** 返回flag ret = 100 or 0 */
	public static JSONResult returnFlag(boolean flag, String... errorText) {
		if (flag) {
			return success();
		}
		String text = "error";
		if (errorText != null && errorText.length >= 1)
			text = errorText[0];
		return error(text);
	}

	/** 返回error ret = 100 */
	public static JSONResult error(String msg) {
		return builder(RetEnum.ERROR, msg);
	}

	/** 返回执行成功 ret = 0 */
	public static JSONResult success() {
		return builder(RetEnum.SUCCESS, "ok");
	}

	/** 返回执行成功 ret = 0 */
	public static JSONResult success(String msg) {
		return builder(RetEnum.SUCCESS, msg);
	}

	/** 返回执行成功 ret = 0 */
	public static JSONResult success(List<?> list) {
		return builder(RetEnum.SUCCESS, "ok").setList(list);
	}

	/** 返回执行成功 ret = 0 */
	public static JSONResult success(Object result) {
		return builder(RetEnum.SUCCESS, "ok").setResult(result);
	}

	/** 返回未登录 ret = 300 */
	public static JSONResult noLogin() {
		return builder(RetEnum.NO_LOGIN, "尚未登录");
	}

	/** 返回缺少参数 ret = 101 */
	public static JSONResult paramLack() {
		return paramLack("缺少参数");
	}

	/** 返回缺少参数 ret = 101 */
	public static JSONResult paramLack(String msg) {
		return builder(RetEnum.PARAM_LACK, msg);
	}

	/** 返回系统异常 ret = -1 */
	public static JSONResult exception() {
		return builder(RetEnum.EXCEPTION, "服务器异常，请稍后重试");
	}

	/**
	 * 设置返回值
	 * 
	 * @param retEnum
	 *            状态码枚举
	 * @return 直接返回JSON
	 */
	public String setValue(RetEnum retEnum) {
		this.ret = retEnum.getValue();

		return this.toJSON();
	}

	/**
	 * 设置返回值
	 * 
	 * @param retEnum
	 *            状态码枚举
	 * @param msg
	 *            详细信息
	 * @return 直接返回JSON
	 */
	public String setValue(RetEnum retEnum, String msg) {
		this.ret = retEnum.getValue();
		this.msg = msg;

		return this.toJSON();
	}

	/**
	 * 设置返回值
	 * 
	 * @param retEnum
	 *            状态码枚举
	 * @param msg
	 *            详细信息
	 * @param list
	 *            list集合
	 * @return 直接返回JSON
	 */
	public String setValue(RetEnum retEnum, String msg, List<?> list) {
		this.ret = retEnum.getValue();
		this.msg = msg;
		this.list = list;

		return this.toJSON();
	}

	/**
	 * 设置返回值
	 * 
	 * @param retEnum
	 *            状态码枚举
	 * @param list
	 *            list集合
	 * @return 直接返回JSON
	 */
	public String setValue(RetEnum retEnum, List<?> list) {
		this.ret = retEnum.getValue();
		this.list = list;

		return this.toJSON();
	}

	/**
	 * 设置返回值
	 * 
	 * @param retEnum
	 *            状态码枚举
	 * @param msg
	 *            详细信息
	 * @param result
	 * @return 直接返回JSON
	 */
	public String setValue(RetEnum retEnum, String msg, Object result) {
		this.ret = retEnum.getValue();
		this.msg = msg;
		this.result = result;

		return this.toJSON();
	}

	/**
	 * 设置返回值
	 * 
	 * @param retEnum
	 *            状态码枚举
	 * @param result
	 * @return 直接返回JSON
	 */
	public String setValue(RetEnum retEnum, Object result) {
		this.ret = retEnum.getValue();
		this.result = result;

		return this.toJSON();
	}

	/**
	 * 设置返回值
	 * 
	 * @param retEnum
	 *            状态码枚举
	 * @param result
	 * @return 直接返回JSON
	 */
	public String setValue(RetEnum retEnum, String msg, Token token, List<?> list, Object result) {
		this.ret = retEnum.getValue();
		this.msg = msg;
		this.tokenKey = token.getTokenKey();
		this.list = list;
		this.result = result;

		return this.toJSON();
	}

	/**
	 * 设置返回值
	 * 
	 * @param retEnum
	 *            状态码枚举
	 * @param msg
	 *            详细信息
	 * @param list
	 *            list集合
	 * @param total
	 *            总条目数
	 * @return 直接返回JSON
	 */
	public String setValue(RetEnum retEnum, String msg, List<?> list, Integer total) {
		this.ret = retEnum.getValue();
		this.msg = msg;
		this.list = list;
		this.total = total;

		return this.toJSON();
	}

	public int getRet() {
		return ret;
	}

	public JSONResult setRet(RetEnum retEnum) {
		this.ret = retEnum.getValue();
		return this;
	}

	public JSONResult setRet(int ret) {
		this.ret = ret;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public JSONResult setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public List<?> getList() {
		return list;
	}

	public JSONResult setList(List<?> list) {
		this.list = list;
		return this;
	}

	public Integer getTotal() {
		return total;
	}

	public JSONResult setTotal(Integer total) {
		this.total = total;
		return this;
	}

	public Object getResult() {
		return result;
	}

	public JSONResult setResult(Object result) {
		this.result = result;
		return this;
	}

	public String setMap(Map<?, ?> map) {

		return gson.toJson(map);
	}

	/* - - - - - 【SET】 - - - - - */
	public  JSONResult set(Map<?, ?> map) {
		this.result = map;
		return this;
	}

	public JSONResult set(List<?> list) {
		this.list = list;
		return this;
	}

	public JSONResult set(RetEnum retEnum) {
		this.ret = retEnum.getValue();
		return this;
	}
	/* - - - - - 【SET】 - - - - - */

	public String getTokenKey() {
		return tokenKey;
	}

	public JSONResult setTokenKey(Token token) {
		this.tokenKey = token.getTokenKey();
		return this;
	}

	public String toJSON() {
		return gson.toJson(this);
	}

	@Override
	public String toString() {
		return this.toJSON();
	}

}
