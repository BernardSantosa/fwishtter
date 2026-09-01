package com.fwishtter.specification;

import com.fwishtter.entity.fweesht.Tweet;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TweetSpecification {

    private TweetSpecification(){}

    public static Specification<Tweet> hasText(String text) {
        return ((root, query, criteriaBuilder) ->  {
            if(text == null || text.isBlank()) {
                return null;
            }

            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("content")), "%" + text.toLowerCase() + "%"));
            return  criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }

    public static Specification<Tweet> hasUser(List<UUID> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            return null;
        }

        return (root, query, criteriaBuilder) ->
                root.get("authorId").in(userIds);
    }

    public static Specification<Tweet> getTweetResponseDtoSpecification(String search, List<UUID> matchedUserId) {
//        return Specification.where(hasText(search)).and(hasUser(matchedUserId));
        Specification<Tweet> spec = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();

        if(StringUtils.hasText(search)) {
            spec = spec.and(hasText(search));
        }

        if(matchedUserId != null && !matchedUserId.isEmpty()) {
            spec = spec.and(hasUser(matchedUserId));
        }

        return spec;
    }
}
