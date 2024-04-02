package com.api.swagger3.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "응답바디 200")
public class SeccessResponseBody {
    @Schema(description = "응답 코드", nullable = false, example = "200")
    private String responseCode;

    @Schema(description = "결과 코드", nullable = false, example = "SUCCESS")
    private String resultCode;

    @Schema(description = "결과 메시지", example = "결과 메시지")
    private String serverMessage;

    @Schema(description = "결과물", example = "결과물")
    private Object resultObject;

    public SeccessResponseBody() {
        this.setResponseCode("200");
        this.setResultCode("SUCCESS");
        this.setServerMessage("성공");
    }
    
}
