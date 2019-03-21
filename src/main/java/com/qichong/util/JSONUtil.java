package com.qichong.util;

import java.util.Arrays;
import java.util.Set;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JSONUtil {

	/**
	 * 获取JsonObject中所有的key
	 * 
	 * @param json
	 *            需要获取的JSONObject
	 * @param sort
	 *            是否将key按照ASSIC码排序
	 * @return 返回String数组，包含了JsonObject中所有的key
	 */
	public static String[] getJsonObjectKeys(JsonObject json, boolean sort) {
		Set<Entry<String, JsonElement>> entrys = json.entrySet();
		String[] keys = new String[entrys.size()];
		// 遍历赋值
		int i = 0;
		for (Entry<String, JsonElement> entry : json.entrySet()) {
			keys[i++] = entry.getKey();
		}
		// 是否按照参数名ASCII字典序排序
		if (sort)
			Arrays.sort(keys);
		return keys;
	}

}
