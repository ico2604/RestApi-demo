package com.api.swagger3.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "응답바디 500")
public class ErrorResponseBody {
    @Schema(description = "응답 코드", nullable = false, example = "500")
    private String responseCode;

    @Schema(description = "결과 코드", nullable = false, example = "ERROR")
    private String resultCode;
    
    @Schema(description = "결과 메시지", example = "서버 처리 중 오류가 발생했습니다.")
    private String serverMessage;

    public ErrorResponseBody() {
        this.setResponseCode("500");
        this.setResultCode("ERROR");
        this.setServerMessage("서버 처리 중 오류가 발생했습니다.");
    }
    
}
