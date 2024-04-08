package com.api.swagger3.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "토큰 재발급")
public class TokenReIssue {
    private String accessToken;
    private String refreshToken;
    private String message;
    private Long claimMemberKey;
}
