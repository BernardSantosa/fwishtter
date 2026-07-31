package com.fwishtter.repository;

import com.fwishtter.entity.fweesht.Tweet;
import com.fwishtter.tweet.GetTweetResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TweetRepository extends JpaRepository<Tweet, Long> {

}
