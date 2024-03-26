package com.api.swagger3.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberSerchCondition {
    private String name;
    private String sex;
    private String type;
}
