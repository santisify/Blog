// 定义开关变量
var flagUsername = false;
var flagPwd = false;
var flagEmail = false;
var flagCode = false;

$(function () {

    $("#userName").focus(function () {
        $("#userNameErr").text("");
        $("#userNameErr2").text("");
    });
    $("#userName").blur(function () {
        var regExp = /^[a-zA-Z0-9]{4,10}$/;
        var username = $("#userName").val();
        if(username.trim()==""){
            $("#userNameErr").text("请输入账号");
            flagUsername = false;
        }else if(!regExp.test(username)){
            $("#userNameErr").text("账号由4-10字母或数字组成");
            flagUsername = false;
        }else{
            flagUsername = true;
        }
    });

    $("#pwd").focus(function () {
        $("#passwordErr2").text("");
    });

    $("#repwd").focus(function () {
        $("#passwordErr").text("");
        $("#passwordErr3").text("");
    });

    $("#repwd").blur(function () {
        var pwd = $("input[name='password']").val();
        var repwd = $("input[name='repassword']").val();
        var reg = /^[A-Za-z0-9]{6,12}$/;
        if(pwd.trim()==""){
            $("#passwordErr").text("请输入密码");
            flagPwd = false;
        }else if(pwd.trim()!=repwd.trim()){
            $("#passwordErr").text("密码不一致!");
            flagPwd = false;
        }else if(!reg.test(pwd)){
            $("#passwordErr").text("只能是6-12位字母、数字!");
            flagPwd = false;
        }else if(pwd.trim()==repwd.trim()){
            $("#passwordErr").text("");
            flagPwd = true;
        }
    });

    $("#email").focus(function () {
        $("#emailErr").text("");
    });
    $("#email").blur(function () {
        var regExp = /^[a-z0-9][\w\.\-]*@[a-z0-9\-]+(\.[a-z]{2,5}){1,2}$/i;
        var emailVal = $("#email").val();
        if (emailVal.trim()=="") {
            $('#emailErr').html('请输入邮箱账号');
            flagEmail = false;
        }else if(!regExp.test(emailVal)) {
            $('#emailErr').html('邮箱格式错误');
            flagEmail = false;
        } else {
            flagEmail = true;
        }
    });

    $("#verifyval").blur(function () {
        var code = $("input[name='verifycode']").val();
        if (code.trim()==""){
            $("#verifyErr").text("请输入验证码");
            flagCode = false;
        } else if(code.trim()!=""){
            $("#verifyErr").text("");
            flagCode = true;
        }
    });

    $("#btnFind").click(function () {
        $("#formBtn input").trigger('blur');
        if(flagUsername==false||flagPwd==false||flagEmail==false||flagCode==false||flagCode==false){
            return false;
        }else{
            $("#formBtn").submit();
        }
    });


})