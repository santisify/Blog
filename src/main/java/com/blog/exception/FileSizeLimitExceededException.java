package com.blog.exception;

public class FileSizeLimitExceededException extends RuntimeException {

    public FileSizeLimitExceededException(long defaultMaxSize) {
        super("文件大小超出最大限制" + defaultMaxSize);
    }
}