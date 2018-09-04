package com.auvx.melloo.util;

import android.content.SharedPreferences;
import android.util.Log;

import com.auvx.melloo.constant.HttpAttributeName;
import com.auvx.melloo.constant.LogTag;
import com.auvx.melloo.constant.MimePattern;
import com.auvx.melloo.context.Melloo;
import com.auvx.melloo.exception.DataProcessingException;
import com.auvx.melloo.exception.NetworkIOException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
//header
////for example : content-type...
//cookie
//url
//body, parameter
//method
//connection properties
////for example connect-timeout, content compress,

public class HttpOperator {
    private static final OkHttpClient client = new OkHttpClient().newBuilder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .build();
    //content-type JSON的Request

    private static  RequestBody getRequestBody(MediaType mediaType, String content) {
       return  RequestBody.create(null, content);
    }
    private static RequestBody getRequestBody(String content) {
        return RequestBody.create(null, content);
    }

    public static Request buildJsonEncodedPostRequest(Map<String, String> inherentHeaders,
                                                  Map<String, String> extendHeaders,
                                                  String url,
                                                  Object load){
        ObjectMapper mapper = JsonOperator.getMapper();
        String bodyContent = null;
        try {
            bodyContent = mapper.writeValueAsString(load);
        } catch (JsonProcessingException e) {
            Log.d("", "", new DataProcessingException());
        }
        Request.Builder builder = new Request.Builder();
        builder.method("POST", getRequestBody(bodyContent)).url(url)
                .header(HttpAttributeName.Inherent.CONTENT_TYPE, MimePattern.MIME_JSON);
        if (inherentHeaders != null && !inherentHeaders.isEmpty()) {
            for (Map.Entry<String, String> entry : inherentHeaders.entrySet()) {
                builder.header(entry.getKey(), entry.getValue());
            }
        }
        if (extendHeaders != null && !extendHeaders.isEmpty()){
            for (Map.Entry<String, String> entry : extendHeaders.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        Request request = builder.build();
        return request;

    }

    public static Request buildJsonEncodePostRequest(Map<String, String> extendHeaders,
                                                     String url, Object load) {
        return buildJsonEncodedPostRequest(null, extendHeaders, url, load);
    }

    public static Request buildJsonEncodePostRequest(String url, Object load) {
        return buildJsonEncodedPostRequest(null, null, url, load);
    }

    public static Request buildMultipartPostRequest() {
        return null;
    }


    public static String sendRequest(Request request){
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()){

                String onlineClue = response.header(HttpAttributeName.Extend.ONLINE_CLUE);
                SharedPreferences appHandbook = Melloo.getSharedPreferences();
                SharedPreferences.Editor bookEditor = appHandbook.edit();
                bookEditor.putString(HttpAttributeName.Extend.ONLINE_CLUE, onlineClue);

                String bodyText = response.body().string();
                return bodyText;
            } else {
                return null;
            }
        } catch (IOException e) {
            Log.d(LogTag.NETWORK_PROCESS, "", new NetworkIOException());
            return null;
        }
    }
    //表单格式的请求

    //urlencoded 的请求

    //multipart 文件请求
}
