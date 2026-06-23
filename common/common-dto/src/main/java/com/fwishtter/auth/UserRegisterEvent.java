package com.fwishtter.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterEvent {

    private String userId;
    private String username;
    private String email;

}
