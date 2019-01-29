package cn.cebest.framework.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import cn.cebest.framework.validator.MobileValidator;
import java.lang.annotation.*;

/**
 *  自定义注解(手机格式验证)
  * @author maming  
  * @date 2018年8月29日
 */

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = MobileValidator.class)
public @interface Mobile {

	String message() default "手机号格式错误";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
	
	@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD})
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@interface List {
		Mobile[] value();
	}
	
}
