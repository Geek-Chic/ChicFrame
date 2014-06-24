package com.geekchic.base.db.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: Table
 * @Descritpion: 表名注解
 * @author evil
 * @date May 4, 2014
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { java.lang.annotation.ElementType.TYPE })
public @interface Table {
	/**
	 * 表名注解
	 * @return
	 */
	public abstract String name();
}