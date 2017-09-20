package com.dropbsoftware.feedservice.rest.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

import feedservice.exception.ApplicationException;
import io.getstream.client.StreamClient;
import io.getstream.client.exception.InvalidFeedNameException;
import io.getstream.client.exception.StreamClientException;
import io.getstream.client.model.activities.SimpleActivity;
import io.getstream.client.model.feeds.Feed;
import io.getstream.client.model.filters.FeedFilter;
import io.getstream.client.service.FlatActivityServiceImpl;

import static feedservice.exception.ApplicationStatus.ACTIVITIES_GETTING_ERROR;
import static feedservice.exception.ApplicationStatus.ACTIVITY_CREATING_ERROR;
import static feedservice.exception.ApplicationStatus.FEED_CREATING_GETTING_ERROR;

@Component
public class GetStreamApiClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetStreamApiClient.class);

    private StreamClient streamClient;

    public GetStreamApiClient(StreamClient streamClient) {
        this.streamClient = streamClient;
    }

    public void createPost(Long userId) {
        Feed feed = createOrGetExistingFeed(userId);
        FlatActivityServiceImpl<SimpleActivity> flatActivityService = feed.newFlatActivityService(SimpleActivity.class);
        SimpleActivity activity = new SimpleActivity();
        activity.setActor("user:1");
        activity.setVerb("post");
        activity.setObject("post:1");
        SimpleActivity response = null;
        try {
            response = flatActivityService.addActivity(activity);
        } catch (IOException | StreamClientException e) {
            LOGGER.error("Error creating activity for user {}", userId);
            throw new ApplicationException(e, ACTIVITY_CREATING_ERROR);
        } finally {
            LOGGER.error("actor: {}, foreign id: {}, id: {}, object: {}, target: {}, verb: {}",
                    response.getActor(), response.getForeignId(), response.getId(),
                    response.getObject(), response.getTarget(), response.getVerb());
        }
    }

    public List<SimpleActivity> getActivities(Long userId, String id) {
        Feed feed = createOrGetExistingFeed(userId);
        FlatActivityServiceImpl<SimpleActivity> flatActivityService = feed.newFlatActivityService(SimpleActivity.class);
        FeedFilter filter = new FeedFilter.Builder().withIdLowerThanEquals(id).withLimit(5).build();
        List<SimpleActivity> activities;
        try {
            activities = flatActivityService.getActivities(filter).getResults();
        } catch (IOException | StreamClientException e) {
            LOGGER.error("Error getting activities for user {}", userId);
            throw new ApplicationException(e, ACTIVITIES_GETTING_ERROR);
        }

        return activities;
    }

    private Feed createOrGetExistingFeed(Long userId) {
        Feed feed;
        try {
            feed = streamClient.newFeed("user", userId.toString());
        } catch (InvalidFeedNameException e) {
            LOGGER.error("Error creating/getting feed for user {}", userId);
            throw new ApplicationException(e, FEED_CREATING_GETTING_ERROR);
        }

        return feed;
    }

    public String getToken(Long userId) {
        Feed feed = createOrGetExistingFeed(userId);
        return feed.getToken();
    }
}
