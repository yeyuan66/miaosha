package com.imooc.miaosha.validator;

import com.imooc.miaosha.util.ValidatorUtil;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    private boolean required = false;
    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //非必需且为空不用校验
        if(!required&&StringUtils.isEmpty(value))return true;

        return ValidatorUtil.isMobile(value);
    }
}
