package com.example.tacco.service;

import com.aliyun.oss.model.OSSObjectSummary;
import com.example.tacco.entity.directory;

import java.util.List;

public interface IPIcService {
    List<OSSObjectSummary> getImages(String key) throws Exception;

    List<directory> getDirectory() throws  Exception;
}
