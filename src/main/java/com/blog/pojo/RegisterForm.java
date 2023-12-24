package com.blog.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@ApiModel("注册表单对象")
@Entity
public class RegisterForm implements Serializable {

    @ApiModelProperty("账号")
    @NotEmpty(message = "账号不能为空")
    @Length(min = 4,max = 10,message = "账号长度为4-10")
    private String username;

    @ApiModelProperty("密码")
    @NotEmpty(message = "密码不能为空")
    @Length(min = 6,max = 12,message = "密码长度为6-12")
    private String password;

    @ApiModelProperty("确认密码")
    @NotEmpty(message = "密码不能为空")
    @Length(min = 6,max = 12,message = "密码长度为6-12")
    private String repassword;

    @ApiModelProperty("邮箱")
    @Email(message = "必须为邮箱账号")
    @Pattern(regexp = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$", message = "邮箱格式不正确")//邮箱格式
    private String email;

    @ApiModelProperty("验证码")
    @NotNull
    private String verifycode;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerifycode() {
        return verifycode;
    }

    public void setVerifycode(String verifycode) {
        this.verifycode = verifycode;
    }

    @Override
    public String toString() {
        return "RegisterForm{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", repassword='" + repassword + '\'' +
                ", email='" + email + '\'' +
                ", verifycode='" + verifycode + '\'' +
                '}';
    }
}
