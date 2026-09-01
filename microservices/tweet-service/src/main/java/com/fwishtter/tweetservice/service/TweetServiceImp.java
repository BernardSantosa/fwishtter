package com.fwishtter.tweetservice.service;

import com.fwishtter.common.BaseResponse;
import com.fwishtter.entity.fweesht.Tweet;
import com.fwishtter.entity.fweesht.TweetMedia;
import com.fwishtter.entity.user.User;
import com.fwishtter.model.BaseException;
import com.fwishtter.repository.TweetRepository;
import com.fwishtter.repository.UserRepository;
import com.fwishtter.security.JwtInterceptor;
import com.fwishtter.service.RedisUserService;
import com.fwishtter.specification.TweetSpecification;
import com.fwishtter.tweet.CreateTweetRequestDto;
import com.fwishtter.tweet.GetTweetResponseDto;
import com.fwishtter.tweet.TweetMediaListDto;
import com.fwishtter.user.GetUserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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
    private final RedisUserService redisUserService;

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

        newTweet.setCreatedBy(jwtInterceptor.getUserName());

        tweetRepository.save(newTweet);

        GetTweetResponseDto response = GetTweetResponseDto.builder()
                .content(newTweet.getContent())
                .createdBy(newTweet.getCreatedBy())
                .mediaList(
                        newTweet.getMediaList() == null
                                ? List.of()
                                : newTweet.getMediaList().stream()
                                .map(media -> TweetMediaListDto.builder()
                                        .mediaUrl(media.getMediaUrl())
                                        .build())
                                .toList()
                )
                .build();

        return BaseResponse.builder()
                .code(HttpStatus.CREATED)
                .status(HttpStatus.CREATED.value())
                .message("New Fweesht Created! Nice Fish Content")
                .data(response)
                .build();
    }

    @Override
    public BaseResponse getTweets(String search, int page, int size) {
        return null;
    }

    public BaseResponse tweetListAll() {

    }

    public BaseResponse tweetListPage(String search, int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage-1, pageSize, Sort.by("createdTime").descending());
        List<UUID> matchesUserIds = new ArrayList<>();
        if(StringUtils.hasText(search)) {
            matchesUserIds = userRepository.findUserIdBySearch(search);
        }

        Specification<Tweet> spec = TweetSpecification.getTweetResponseDtoSpecification(search, matchesUserIds);
        Page<Tweet> tweetList = tweetRepository.findAll(spec, pageable);
        Page<GetTweetResponseDto> tweetResponseList = tweetList.map(tweet ->  {
                    GetUserResponseDto cachedUser = redisUserService.getUserProfileCache(tweet.getAuthorId());

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
                            .displayName(cachedUser.getDisplayName())
                            .handle(cachedUser.getHandle())
                            .createdTime(tweet.getCreatedTime().toString())
                            .updatedTime(tweet.getUpdatedTime().toString())
                            .createdBy(tweet.getCreatedBy())
                            .updatedBy(tweet.getUpdatedBy())
                            .mediaList(mediaList)
                            .build();
                });

        return BaseResponse.builder()
                .code(HttpStatus.OK)
                .status(HttpStatus.OK.value())
                .data(tweetResponseList)
                .message("success retrieved all tweets")
                .build();
    }

}
