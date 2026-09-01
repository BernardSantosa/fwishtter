package com.fwishtter.tweetservice.controller;

import com.fwishtter.common.BaseResponse;
import com.fwishtter.fweesht.request.GetTweetListRequest;
import com.fwishtter.repository.TweetRepository;
import com.fwishtter.tweet.CreateTweetRequestDto;
import com.fwishtter.tweetservice.service.TweetService;
import com.fwishtter.tweetservice.service.TweetServiceImp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tweet")
@RequiredArgsConstructor
public class TweetController {

    private final TweetRepository tweetRepository;
    private final TweetService tweetService;

    @PostMapping
    public ResponseEntity<BaseResponse> createTweet(@Valid @RequestBody CreateTweetRequestDto reqDto) {
        BaseResponse response = tweetService.createTweets(reqDto);
        return new ResponseEntity<>(response, response.code());
    }

    @GetMapping
    public ResponseEntity<BaseResponse> getTweetList(
            @RequestBody GetTweetListRequest request
            ) {
        BaseResponse response = tweetService.getTweets(request.getSearch(), request.getCurrentPage(), request.getPageSize());
        return new ResponseEntity<>(response, response.code());
    }

}
