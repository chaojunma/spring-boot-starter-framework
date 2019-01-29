package cn.cebest.framework.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import cn.cebest.framework.annotation.Mobile;
import cn.cebest.framework.util.ValidatorUtil;

/**
 *  手机格式校验
  * @author maming  
  * @date 2018年8月29日
 */
public class MobileValidator implements ConstraintValidator<Mobile, String>{

	
	// 赋值
	@Override
	public void initialize(Mobile constraintAnnotation) {
		
	}

	// 校验
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return ValidatorUtil.isMobile(value);
	}

}
