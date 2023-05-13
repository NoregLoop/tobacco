package com.example.tacco.service;

import com.aliyun.oss.model.OSSObjectSummary;

import java.util.List;

public interface IPIcService {
    List<OSSObjectSummary> getImages() throws Exception;
}
