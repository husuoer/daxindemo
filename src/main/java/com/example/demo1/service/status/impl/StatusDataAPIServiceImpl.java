package com.example.demo1.service.status.impl;

import com.alibaba.fastjson2.JSONObject;
import com.example.demo1.client.APIClient;
import com.example.demo1.config.APIConfiguration;
import com.example.demo1.service.status.StatusDataAPIService;


public class StatusDataAPIServiceImpl implements StatusDataAPIService {

    private final APIClient client;
    private final StatusDataAPI statusDataAPI;

    public StatusDataAPIServiceImpl(final APIConfiguration config) {
        this.client = new APIClient(config);
        this.statusDataAPI = this.client.createService(StatusDataAPI.class);
    }

    //status
    @Override
    public JSONObject getStatus(String state) {
        return this.client.executeSync(this.statusDataAPI.getStatus(state));
    }
}
