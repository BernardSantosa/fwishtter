package com.fwishtter.registry.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BasePagingRequest {

    private Integer currentPage = 1;
    private Integer pageSize = 10;
    private String sortBy = "updatedTime";
    private String sortOrder = "DESC";
    private Boolean pageable = true;

}
