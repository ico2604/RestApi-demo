package com.api.swagger3.model.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class MemberPageRequest {
    private MemberSerchCondition condition;

    @Schema(description = "현재페이지", defaultValue = "0")
    private int page;
    @Schema(description = "한페이지의 아이탬 갯수", defaultValue = "10")
    private int size;
    //private String sortField;
    //private Direction direction;
}
