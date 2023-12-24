package com.blog.service;

import com.blog.mapper.DownloadMapper;
import com.blog.pojo.Download;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DownloadService {

    @Autowired
    private DownloadMapper downloadMapper;

    public List<Download> getDownSources(){
        return downloadMapper.selectAll();
    }
}

