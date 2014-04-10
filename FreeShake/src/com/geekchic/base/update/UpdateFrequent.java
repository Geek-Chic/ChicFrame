/**
 * @Title:UpdateFrequent.java
 * @Package com.geekchic.base.update
 * @Description:[用一句话描述做什么]
 * @author:evil
 * @date:Apr 12, 2014
 * Copyright (c) 2014,Evilester All Rights Reserved. 
 */
package com.geekchic.base.update;

/**
 * @ClassName: UpdateFrequent
 * @Descritpion:[用一句话描述作用]
 * @author evil
 * @date Apr 12, 2014
 */
public class UpdateFrequent {
	/**
	 * 第次启动检查
	 */
	public static final int EACH_TIME = 0;
	/**
	 * 每天一次
	 */
	public static final int EACH_ONE_DAY = 1;

	/**
	 * 每两天一次
	 */
	public static final int EACH_TWO_DAYS = 2;

	/**
	 * 每三天一次
	 */
	public static final int EACH_THREE_DAYS = 3;

	/**
	 * 每周一次
	 */
	public static final int EACH_ONE_WEEK = 7;

	/**
	 * 每两周一次
	 */
	public static final int EACH_TWO_WEEKS = 14;

	/**
	 * 每三周一次
	 */
	public static final int EACH_THREE_WEEKS = 21;

	/**
	 * 每月一次
	 */
	public static final int EACH_ONE_MONTH = 30;

	/**
	 * 最小间隔
	 */
	public static final int EACH_MIN = 0;

	/**
	 * 最大间隔
	 */
	public static final int EACH_MAX = 365;

	private int frequent = -1;

	public UpdateFrequent(int frequent) {
		if (frequent >= EACH_MIN && frequent <= EACH_MAX) {
			this.frequent = frequent;
		} else if (frequent < EACH_MIN) {
			this.frequent = EACH_MIN;
		} else {
			this.frequent = EACH_MAX;
		}
	}

	public int getFrequent() {
		return frequent;
	}

	public void setFrequent(int frequent) {
		this.frequent = frequent;
	}

	/**
	 * 返回间隔毫秒数
	 * 
	 * @return 毫秒数
	 */
	public long getFrequentMillis() {
		return this.frequent * 24 * 60 * 60 * 1000;
	}

}
