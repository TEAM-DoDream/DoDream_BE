package com.dodream.integration.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.test.context.support.WithSecurityContext;


@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithUserDetailsSecurityContextFactory.class)
public @interface TestCustomUser {


}