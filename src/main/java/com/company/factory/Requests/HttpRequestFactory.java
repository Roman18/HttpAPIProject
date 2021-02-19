package com.company.factory.Requests;

import java.net.http.HttpRequest;

public interface HttpRequestFactory {


    HttpRequest createGetRequest(String url);
    HttpRequest createPostRequest(String url, Object o);
}
