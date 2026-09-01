package com.fwishtter.tweetservice.service;

import com.fwishtter.common.BaseResponse;
import com.fwishtter.tweet.CreateTweetRequestDto;

public interface TweetService {

    BaseResponse createTweets(CreateTweetRequestDto tweetReq);

    BaseResponse getTweets(String search, int page, int size);
}
