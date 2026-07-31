package service;

import com.fwishtter.common.BaseResponse;
import com.fwishtter.tweet.CreateTweetRequestDto;

public interface TweetService {

    BaseResponse createTweets(CreateTweetRequestDto tweetReq);

    BaseResponse getAllTweet();
}
