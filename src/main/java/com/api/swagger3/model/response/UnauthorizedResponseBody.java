package com.api.swagger3.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "응답바디 401")
public class UnauthorizedResponseBody {
    @Schema(description = "응답 코드", nullable = false, example = "401")
    private String responseCode;

    @Schema(description = "결과 코드", nullable = false, example = "Unauthorized")
    private String resultCode;

    @Schema(description = "결과 메시지", example = "유효성 검사 통과 실패")
    private String serverMessage;

    public UnauthorizedResponseBody() {
        this.setResponseCode("401");
        this.setResultCode("Unauthorized");
        this.setServerMessage("유효성 검사 통과 실패");
    }
    
}
