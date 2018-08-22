package org.luncert.springauth.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.luncert.springauth.Identity;

@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface AuthRequired {

    /**
     * 合法的身份对象，默认值为NormalUser
     */
    Identity[] legalObjects() default Identity.NormalUser;

}