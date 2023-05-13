package com.example.tacco.service.Impl;

import com.aliyun.oss.model.OSSObjectSummary;
import com.example.tacco.service.IPIcService;
import com.example.tacco.utils.GetOssImages;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PicImpl implements IPIcService {

    @Override
    public List<OSSObjectSummary> getImages() throws Exception {
        GetOssImages handlImg = new GetOssImages();
        List<OSSObjectSummary> res = handlImg.getImages();
        return res;
    }
}
