package com.blog.exception;

public class FileNameLengthLimitExceededException extends RuntimeException {

    public FileNameLengthLimitExceededException(int defaultFileNameLength) {
        super("文件名超出最大长度" + defaultFileNameLength);
    }
}