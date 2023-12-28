package com.blog.service;

import com.blog.constants.Constants;
import com.blog.util.RedisUtil;
import com.blog.verify.SimpleCharVerifyCodeGenImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

@Service
public class SendEmailService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JavaMailSenderImpl mailsend;

    @Value("${spring.mail.username}")
    private String serverEmail;

    @Value("${ylbmail.subject}")
    private String subject;

    @Value("${ylbmail.personal}")
    private String persional;

    @Value("${ylbmail.code.timeout}")
    private int timeout;

    @Value(("${ylbmail.captchalen}"))
    private int captchaLen;

    /**
     * 发送验证码到邮箱
     *
     * @throws Exception
     */
    public boolean sendVerify(String name, String email, HttpServletRequest request) throws Exception {
        //生成随机验证码
        SimpleCharVerifyCodeGenImpl verifyCodeGen = new SimpleCharVerifyCodeGenImpl();
        String code = verifyCodeGen.codestring(captchaLen);
        //存入redis中
        boolean isSet = redisUtil.set(Constants.CAPTCHA_CODE_KEY + request.getRemoteAddr() + ":" + name.trim(), code, timeout * 60);
        System.out.println(isSet);
        if (isSet) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<html><head><title></title></head><body>");
            stringBuilder.append("您好-感谢使用ylbnotesHub<br/>");
            stringBuilder.append("您的验证码是：<strong style='color:red'>").append(code).append("</strong><br/>");
            stringBuilder.append("此验证码用于验证您的邮箱账号。<br/>");
            stringBuilder.append("此验证码<strong>5分钟内</strong>有效。<br/>");
            stringBuilder.append("如果您没有进行上述操作，请忽略此邮件。");

            MimeMessage mimeMessage = mailsend.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setSubject(subject);

            helper.setText(stringBuilder.toString(), true);

            //发送者邮箱
//        helper.setFrom(serverEmail);
            InternetAddress internetAddress = new InternetAddress(serverEmail, persional, Constants.UTF8);
            helper.setFrom(internetAddress);
            //接收者邮箱
            helper.setTo(email);

            mailsend.send(mimeMessage);
            return true;
        }
        return false;
    }

}
