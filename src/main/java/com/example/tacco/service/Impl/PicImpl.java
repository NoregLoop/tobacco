package com.example.tacco.service.Impl;

import com.aliyun.oss.model.OSSObjectSummary;
import com.example.tacco.entity.directory;
import com.example.tacco.service.IPIcService;
import com.example.tacco.utils.GetOssImages;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PicImpl implements IPIcService {

    @Override
    public List<OSSObjectSummary> getImages(String key) throws Exception {
        GetOssImages handlImg = new GetOssImages();
        List<OSSObjectSummary> res = handlImg.getImages(key);
        return res;
    }

    @Override
    public List<directory> getDirectory() throws Exception {
        GetOssImages handlImg = new GetOssImages();
        List<directory> res = handlImg.getDirectory();
        return res;
    }
}
