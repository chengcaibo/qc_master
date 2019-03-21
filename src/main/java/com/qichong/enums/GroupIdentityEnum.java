package com.qichong.enums;

public enum GroupIdentityEnum {

	/** 1 = 团长，可添加删除管理员、修改团队信息、添加删除队员和删除团队，但团长不一定是创建者 */
	GROUP_HEAD(1),
	/** 2 = 可以添加和删除队员、修改团队信息 */
	GROUP_ADMIN(2),
	/** 3 = 加入团队默认身份 */
	GROUP_PLAYER(3);

	private int id;

	private GroupIdentityEnum(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

}
