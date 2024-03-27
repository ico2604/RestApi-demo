package com.api.swagger3.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberSerchCondition {
    @Schema(description = "이름", defaultValue = "")
    private String name;
    @Schema(description = "성별", defaultValue = "")
    private String sex;
    @Schema(description = "회원타입", defaultValue = "")
    private String type;
}
