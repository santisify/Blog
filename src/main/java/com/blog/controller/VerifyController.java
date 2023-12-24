package com.blog.controller;

import com.blog.pojo.VerifyCode;
import com.blog.util.RedisUtil;
import com.blog.verify.IVerifyCodeGen;
import com.blog.verify.SimpleCharVerifyCodeGenImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class VerifyController {

    private static final Logger logger = LoggerFactory.getLogger(VerifyController.class);

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/verifyCode")
    public void verifyCode(@RequestParam("username") String username, HttpServletRequest request, HttpServletResponse response) {
        IVerifyCodeGen iVerifyCodeGen = new SimpleCharVerifyCodeGenImpl();
        try {
            //设置长宽
            VerifyCode verifyCode = iVerifyCodeGen.generate(80, 28);
            String code = verifyCode.getCode();

//            System.out.println("---------"+code);
//            LOGGER.info(code);
            //将VerifyCode绑定session
//            request.getSession().setAttribute("VerifyCode", code);
            redisUtil.set("verifycode" + username.trim(), code, 60 * 30);
            //设置响应头
            response.setHeader("Pragma", "No-cache");
            //设置响应头
            response.setHeader("Cache-Control", "no-cache");
            //设置HttpOnly属性,防止Xss攻击
            response.setHeader("Set-Cookie", "name=value; HttpOnly");
            //在代理服务器端防止缓冲
            response.setDateHeader("Expires", 0);
            //设置响应内容类型
            response.setContentType("image/jpeg");
            response.getOutputStream().write(verifyCode.getImgBytes());
            response.getOutputStream().flush();
        } catch (IOException e) {
            logger.error("/verifyCode 发生异常", e);
        } finally {
            try {
                response.getOutputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
