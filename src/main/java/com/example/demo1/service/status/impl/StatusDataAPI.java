package com.example.demo1.service.status.impl;

import com.alibaba.fastjson2.JSONObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StatusDataAPI {

    //Status
    @GET("/api/v5/system/status")
    Call<JSONObject> getStatus(@Query("state") String state);
}
