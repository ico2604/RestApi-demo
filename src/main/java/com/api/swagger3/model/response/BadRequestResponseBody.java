package com.api.swagger3.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "응답바디 400")
public class BadRequestResponseBody {
    @Schema(description = "응답 코드", nullable = false, example = "400")
    private String responseCode;

    @Schema(description = "결과 코드", nullable = false, example = "Bad Request")
    private String resultCode;
    
    @Schema(description = "결과 메시지", example = "잘못된 문법으로 인하여 서버가 요청하여 이해할 수 없음")
    private String serverMessage;

    public BadRequestResponseBody() {
        this.setResponseCode("400");
        this.setResultCode("Bad Request");
        this.setServerMessage("잘못된 문법으로 인하여 서버가 요청하여 이해할 수 없음");
    }
    
}
