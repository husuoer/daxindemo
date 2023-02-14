package com.example.demo1.service.status;

import com.alibaba.fastjson2.JSONObject;

public interface StatusDataAPIService {

    //Status
    JSONObject getStatus(String state);
}
