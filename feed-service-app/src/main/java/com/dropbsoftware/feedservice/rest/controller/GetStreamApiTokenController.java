package com.dropbsoftware.feedservice.rest.controller;

import com.dropbsoftware.feedservice.rest.client.GetStreamApiClient;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import io.getstream.client.model.activities.SimpleActivity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
@Api(tags = "GetStreamApiTokenService")
public class GetStreamApiTokenController {

    private GetStreamApiClient getStreamApiClient;

    public GetStreamApiTokenController(GetStreamApiClient getStreamApiClient) {
        this.getStreamApiClient = getStreamApiClient;
    }

    @GetMapping(value = "/token", produces = MediaType.TEXT_PLAIN_VALUE)
    @ApiOperation("token")
    public String getToken(@RequestParam Long userId) {
        String token = getStreamApiClient.getToken(userId);
        System.out.println(token);
        System.out.println(token);
        System.out.println(token);
        System.out.println(token);
        System.out.println(token);
        return token;
    }

    @GetMapping("/post")
    @ApiOperation("post")
    public void createPost(@RequestParam Long userId) {
        getStreamApiClient.createPost(userId);
    }

    @GetMapping("/activities")
    @ApiOperation("activities")
    public List<SimpleActivity> getActivities(@RequestParam Long userId, @RequestParam String id) {
        return getStreamApiClient.getActivities(userId, id);
    }
}
