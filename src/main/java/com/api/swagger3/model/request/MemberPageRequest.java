package com.api.swagger3.model.request;

import org.springframework.data.domain.Sort.Direction;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class MemberPageRequest {
    private MemberSerchCondition condition;
    private int page;
    private int size;
    private String sortField;
    private Direction direction;
}
