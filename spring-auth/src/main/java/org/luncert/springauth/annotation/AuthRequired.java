package org.luncert.springauth.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface AuthRequired {

    /**
     * 访问级别，默认值为 0，允许访问级别大于等于 0 的用户访问
     */
    int accessLevel() default 0;

}