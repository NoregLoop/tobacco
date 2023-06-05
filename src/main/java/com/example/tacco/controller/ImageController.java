package com.example.tacco.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.example.tacco.entity.directory;
import com.example.tacco.service.IPIcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/img")
public class ImageController {
    @Autowired
    private IPIcService imgService;

    @RequestMapping("/getImage")
    public JSON getImage(@RequestBody JSONObject request) throws Exception {
        System.out.println("開始獲取");
        List<OSSObjectSummary> res = this.imgService.getImages(request.getString("key"));
        JSONObject data = new JSONObject();
        data.put("imgList", res);
        return data;
    }

    @RequestMapping("/getDirectory")
    public JSON getDirectory() throws Exception {
        System.out.println("開始獲取");
        List<directory> res = this.imgService.getDirectory();
        JSONObject data = new JSONObject();
        data.put("directory", res);
        return data;
    }
}
