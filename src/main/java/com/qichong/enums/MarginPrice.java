package com.qichong.enums;

/** 保证金价位 */
public enum MarginPrice {
	/** 测试阶段 */
	TEST(0.01, -1, -1),
	/** 个人 */
	PERSONAL(300, -1, -1),
	/* 团队 */
	/** 3-10人 1000 元 */
	STAGE1(1000, 3, 10),
	/** 10-20人 3000 元 */
	STAGE2(3000, 10, 20),
	/** 20-40人 5000 元 */
	STAGE3(5000, 20, 40),
	/** 40-80人 8000 元 */
	STAGE4(8000, 40, 80),
	/** 80 人以上 10000 元 */
	STAGE5(10000, 80, -1);

	private MarginPrice(double price, int min, int max) {
		this.price = price;
		this.min = min;
		this.max = max;
	}

	private double price;
	private int min;
	private int max;

	public double getPrice() {
		return price;
	}

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}

}
