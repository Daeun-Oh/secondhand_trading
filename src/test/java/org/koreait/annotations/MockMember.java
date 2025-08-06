package org.koreait.annotations;

import org.koreait.member.constants.Authority;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 테스트 시 사용할 임시 멤버 정보
 */
@Target(ElementType.METHOD)         // method 위에만 사용
@Retention(RetentionPolicy.RUNTIME) // 런타임까지 애너테이션 정보 유지 (리플렉션으로 조회 가능)
@WithSecurityContext(factory = MockSecurityContextFactory.class)
public @interface MockMember {
    long seq() default 1L;
    String email() default "user01@test.org";
    String password() default "_aA123456";
    String name() default "사용자01";
    String mobile() default "01010001000";
    Authority authority() default Authority.MEMBER;
    boolean termsAgree() default true;
    boolean locked() default false;
    String expired() default "";
    String credentialChangedAt() default "";
}
