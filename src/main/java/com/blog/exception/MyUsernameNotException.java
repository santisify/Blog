package com.blog.exception;

import javax.naming.AuthenticationException;

public class MyUsernameNotException extends AuthenticationException {


    public MyUsernameNotException(String msg) {
        super(msg);
    }

}
