package com.geekchic.base.db.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * @ClassName: Id
 * @Descritpion: 主键注解
 * @author evil
 * @date May 4, 2014
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { java.lang.annotation.ElementType.FIELD })
public @interface Id {
}