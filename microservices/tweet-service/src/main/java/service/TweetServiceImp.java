package service;

import com.fwishtter.common.BaseResponse;
import com.fwishtter.entity.fweesht.Tweet;
import com.fwishtter.entity.fweesht.TweetMedia;
import com.fwishtter.entity.user.User;
import com.fwishtter.model.BaseException;
import com.fwishtter.repository.TweetRepository;
import com.fwishtter.repository.UserRepository;
import com.fwishtter.security.JwtInterceptor;
import com.fwishtter.tweet.CreateTweetRequestDto;
import com.fwishtter.tweet.GetTweetResponseDto;
import com.fwishtter.tweet.TweetMediaListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TweetServiceImp implements TweetService {

    private final JwtInterceptor jwtInterceptor;
    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;
    private final ObjectMapper objectMapper;

    @Override
    public BaseResponse createTweets(CreateTweetRequestDto tweetReq) {

        Optional<User> user = userRepository.findUserByHandle(jwtInterceptor.getUserName());

        if(user.isEmpty()) {
            throw new BaseException(
              HttpStatus.NOT_FOUND.value(),
              HttpStatus.NOT_FOUND,
              "user not found",
              null
            );
        }

        User userData = user.get();

        Tweet newTweet = new Tweet();
        newTweet.setAuthorId(userData.getId());

        if(tweetReq.getParentId() != null && !tweetReq.getParentId().isBlank()) {
            newTweet.setParentId(UUID.fromString(tweetReq.getParentId()));
        } else {
            newTweet.setParentId(null);
        }

        newTweet.setContent(tweetReq.getContent());
        newTweet.setType(tweetReq.getType());

        if(tweetReq.getMediaList() != null && !tweetReq.getMediaList().isEmpty()) {

            List<TweetMedia> mediaList =  tweetReq.getMediaList().stream()
                    .map(dto -> {
                        TweetMedia media = new TweetMedia();
                        media.setMediaUrl(dto.getMediaUrl());
                        media.setTweet(newTweet);
                        return media;
                    }).toList();

            newTweet.setMediaList(mediaList);
        }

        tweetRepository.save(newTweet);

        return BaseResponse.builder()
                .code(HttpStatus.CREATED)
                .status(HttpStatus.CREATED.value())
                .message("New Fweesht Created! Nice Fish Content")
                .data(newTweet)
                .build();
    }

    @Override
    public BaseResponse getAllTweet() {

        List<Tweet> tweetList = tweetRepository.findAll();

        List<GetTweetResponseDto> tweetResponseList = tweetList.stream()
                .map(tweet ->  {

                    List<TweetMediaListDto> mediaList = new ArrayList<>();
                    if(tweet.getMediaList() != null) {
                        mediaList = tweet.getMediaList().stream()
                                .map(media ->
                                        TweetMediaListDto.builder()
                                                .mediaUrl(media.getMediaUrl())
                                                .build()
                                ).toList();
                    }

                    return GetTweetResponseDto.builder()
                            .content(tweet.getContent())
                            .displayName()
                            .handle()
                            .createdTime(tweet.getCreatedTime().toString())
                            .updatedTime(tweet.getUpdatedTime().toString())
                            .createdBy(tweet.getCreatedBy())
                            .updatedBy(tweet.getUpdatedBy())
                            .mediaList(mediaList)
                            .build();
                }).toList();

        return BaseResponse.builder()
                .code(HttpStatus.OK)
                .status(HttpStatus.OK.value())
                .data(tweetResponseList)
                .message("success retrieved all tweets")
                .build();
    }

}
