package com.blog.controller;

import com.blog.pojo.RegisterForm;
import com.blog.result.MsgResult;
import com.blog.service.SendEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class EmailCodeController {

    private static final Logger logger = LoggerFactory.getLogger(EmailCodeController.class);

    @Autowired
    private SendEmailService emailService;

    @ResponseBody
    @PostMapping("/sendemailcode")
    public MsgResult sendRegisterCode(@Validated RegisterForm form , BindingResult result, HttpServletRequest request){

        if(result.hasErrors()){
            Map<String,Object> map = new HashMap<>();
            //JSR校验失败，返回失败，显示错误信息
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (FieldError fieldError:fieldErrors) {
                //将错误信息添加到map中
                map.put(fieldError.getField(),fieldError.getDefaultMessage());
            }
            return MsgResult.fail().add("errorFields",map);
        }else {
            //发送验证码到注册邮箱
            try {
                boolean isSend = emailService.sendVerify(form.getUsername(), form.getEmail(),request);
                if (!isSend) {
                    return MsgResult.fail();
                }
            } catch (Exception e) {
                logger.error("发送验证码失败"+e);
            }
            return MsgResult.success();
        }
    }
}
