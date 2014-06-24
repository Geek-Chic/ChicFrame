package com.geekchic.base.db.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * @ClassName: Column
 * @Descritpion: 定义字段注解
 * @author evil
 * @date May 4, 2014
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { java.lang.annotation.ElementType.FIELD })
public @interface Column {
	/**
	 * 字段名
	 * @return
	 */
	public abstract String name();
    /**
     * 类型
     * @return
     */
	public abstract String type() default "";
     /**
      * 长度
      * @return
      */
	public abstract int length() default 0;
}