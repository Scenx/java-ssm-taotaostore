package com.scen.portal.service.impl;

import com.scen.pojo.ScenResult;
import com.scen.common.utils.HttpClientUtil;
import com.scen.common.utils.JsonUtils;
import com.scen.pojo.TbContent;
import com.scen.portal.service.ContentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 调用内容服务业务层实现类
 *
 * @author Scen
 * @date 2018/4/3 14:09
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${REST_INDEX_AD_URL}")
    private String REST_INDEX_AD_URL;

    @Override
    public String getContentList() {
//        调用服务层的服务
        String result = HttpClientUtil.doGet(REST_BASE_URL + REST_INDEX_AD_URL);
//        字符串转换成scenResult
        try {
            ScenResult scenResult = ScenResult.formatToList(result, TbContent.class);
            List<TbContent> list = (List<TbContent>) scenResult.getData();
            List<Map> resultList = new ArrayList<>();
//            创建一个jsp要求的pojo格式
            for (TbContent tbContent : list) {
                Map map = new HashMap();
                map.put("src", tbContent.getPic());
                map.put("height", 240);
                map.put("width", 670);
                map.put("srcB", tbContent.getPic2());
                map.put("widthB", 550);
                map.put("heightB", 240);
                map.put("href", tbContent.getUrl());
                map.put("alt", tbContent.getSubTitle());
                resultList.add(map);
            }
            return JsonUtils.objectToJson(resultList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
