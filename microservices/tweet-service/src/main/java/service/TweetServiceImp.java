package service;

import com.fwishtter.common.BaseResponse;
import org.springframework.http.HttpStatus;

public class TweetServiceImp implements TweetService {

    public BaseResponse newTweets() {



        return BaseResponse.builder()
                .code(HttpStatus.CREATED)
                .status(HttpStatus.CREATED.value())
                .message("New Fweesht Created! Nice Fish Content")
                .build();
    }

}
