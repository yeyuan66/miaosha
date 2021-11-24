package com.imooc.miaosha.exception;

import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(Exception e){
        if(e instanceof GlobalException){


            GlobalException gex = (GlobalException)e;
            return Result.error(gex.getCm());
        }
        else if(e instanceof BindException){
            BindException bex = (BindException)e;
            List<ObjectError> errors = bex.getAllErrors();
            String message = errors.get(0).getDefaultMessage();
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(message));

        }
        else {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }
}
