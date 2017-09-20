package com.dropbsoftware.feedservice.service;

import com.dropbsoftware.feedservice.rest.client.GetStreamApiClient;

import org.springframework.stereotype.Component;

import java.util.List;

import io.getstream.client.model.activities.SimpleActivity;

@Component
public class PostService {

    private GetStreamApiClient getStreamApiClient;

    public PostService(GetStreamApiClient getStreamApiClient) {
        this.getStreamApiClient = getStreamApiClient;
    }

    public String getToken(Long userId) {
        return getStreamApiClient.getToken(userId);
    }

    public void createPost(Long userId) {
        getStreamApiClient.createPost(userId);
    }

    public List<SimpleActivity> getActivities(Long userId, String id) {
        return getStreamApiClient.getActivities(userId, id);
    }
}
