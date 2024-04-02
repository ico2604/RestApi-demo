package com.api.swagger3.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "응답바디 404")
public class NotFoundResponseBody {
    @Schema(description = "응답 코드", nullable = false, example = "404")
    private String responseCode;

    @Schema(description = "결과 코드", nullable = false, example = "Not Found")
    private String resultCode;

    @Schema(description = "결과 메시지", example = "서버는 요청받은 리소스를 찾을 수 없습니다")
    private String serverMessage;

    public NotFoundResponseBody() {
        this.setResponseCode("404");
        this.setResultCode("Not Found");
        this.setServerMessage("서버는 요청받은 리소스를 찾을 수 없습니다");
    }
    
}
