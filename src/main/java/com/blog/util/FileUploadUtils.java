package com.blog.util;

import com.blog.constants.Constants;
import com.blog.exception.FileNameLengthLimitExceededException;
import com.blog.exception.FileSizeLimitExceededException;
import com.blog.pojo.BlogConfig;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

/**
 * @author: ylb
 * @create: 2021/8/14
 */
public class FileUploadUtils {

    /**
     * 默认大小 5M
     */
    public static final long DEFAULT_MAX_SIZE = 5 * 1024 * 1024;

    /**
     * 默认的文件名最大长度 100
     */
    public static final int DEFAULT_FILE_NAME_LENGTH = 100;

    /**
     * 默认上传的地址
     */
    private static String defaultBaseDir = BlogConfig.getProfile();

    public static final String upload(String baseDir, MultipartFile file) throws IOException {

        int fileNameLength = file.getOriginalFilename().length();
        long size = file.getSize();
        if (fileNameLength > DEFAULT_FILE_NAME_LENGTH) {
            throw new FileNameLengthLimitExceededException(DEFAULT_FILE_NAME_LENGTH);
        }

        if (size > DEFAULT_MAX_SIZE) {
            throw new FileSizeLimitExceededException(DEFAULT_MAX_SIZE / 1024 / 1024);
        }

        String fileName = formatName(file);
        File absoluteFile = getAbsoluteFile(baseDir, fileName);
        file.transferTo(absoluteFile);
        String name = getPathFileName(baseDir, fileName);
        return name;
    }

    private static final File getAbsoluteFile(String path,String fileName) throws IOException {
        try {
            //按照年月份进行分类：
            Calendar instance = Calendar.getInstance();
            instance.get(Calendar.YEAR);
            String year = String.valueOf(instance.get(Calendar.YEAR));
            String month = (instance.get(Calendar.MONTH) + 1)+"月";
            path = path + File.separator +  year + File.separator + month;

            File realPath = new File(path + File.separator + fileName);
            if (!realPath.getParentFile().exists()) {
                realPath.getParentFile().mkdirs();
            }
            if (!realPath.exists()) {
                realPath.createNewFile();
            }
            return realPath;
        } catch (IOException e) {
            throw new IOException(e.getMessage(),e);
        }
    }

    private static final String formatName(MultipartFile file) {
        String filename = UUID.randomUUID().toString().replaceAll("-", "");
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        int i = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(i + 1);
        String outFilename = filename + "."+suffix;
        return outFilename;
    }

    private static final String getPathFileName(String uploadDir, String fileName) throws IOException
    {
        int i = BlogConfig.getProfile().length() +1;
        String currentDir = uploadDir.substring(i);
        Calendar instance = Calendar.getInstance();
        instance.get(Calendar.YEAR);
        String year = String.valueOf(instance.get(Calendar.YEAR));
        String month = (instance.get(Calendar.MONTH) + 1)+"月";
        String pathFileName = Constants.RESOURCE_PREFIX + "/" + currentDir + "/" +  year + "/" + month + "/" + fileName;
        return pathFileName;
    }
}
