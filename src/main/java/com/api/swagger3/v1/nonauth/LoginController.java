package com.api.swagger3.v1.nonauth;

import org.springframework.web.bind.annotation.RestController;

import com.api.swagger3.config.JwtProvider;
import com.api.swagger3.model.dto.LoginDTO;
import com.api.swagger3.model.dto.MemberDTO;
import com.api.swagger3.model.dto.MemberSaveDTO;
import com.api.swagger3.model.request.MemberPageRequest;
import com.api.swagger3.model.response.BadRequestResponseBody;
import com.api.swagger3.model.response.ErrorResponseBody;
import com.api.swagger3.model.response.NotFoundResponseBody;
import com.api.swagger3.model.response.SeccessResponseBody;
import com.api.swagger3.model.response.UnauthorizedResponseBody;
import com.api.swagger3.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@CrossOrigin("*")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "login", description = "login API")
@RequestMapping("/api/nonauth/login")
public class LoginController {
    
    private final MemberService memberService;

    private final JwtProvider jwtProvider;

    @Operation(summary = "로그인", description = "<b>회원로그인후 회원데이터를 불러오는 API입니다.</b>", responses = {
        @ApiResponse(responseCode = "200", description = "회원 불러오기 성공", content = @Content(schema = @Schema(implementation = SeccessResponseBody.class))),
        @ApiResponse(responseCode = "500", description = "회원 불러오기 오류 발생", content = @Content(schema = @Schema(implementation = ErrorResponseBody.class))),
        @ApiResponse(responseCode = "400", description = "서버 오류 발생", content = @Content(schema = @Schema(implementation = BadRequestResponseBody.class))),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = NotFoundResponseBody.class)))
    })
    @GetMapping(value = "/login/{memberId}/{memberPw}")
    public ResponseEntity<?> login(@Parameter(name = "memberId", description = "회원의 로그인 아이디", in = ParameterIn.PATH) @PathVariable String memberId,
                                        @Parameter(name = "memberPw", description = "회원의 로그인 비밀번호", in = ParameterIn.PATH) @PathVariable String memberPw) {
        ObjectNode resultBody = null;
        ObjectMapper mapper = new ObjectMapper();
        SeccessResponseBody seccessResponseBody;
        ErrorResponseBody errorResponseBody;
        UnauthorizedResponseBody unauthorizedResponseBody;
        try{
            LoginDTO loginDTO = memberService.loginMember(memberId, memberPw);//조회

            //로그인 성공 후 AccessToken, RefreshToken 발급
            loginDTO.setAccessToken(jwtProvider.createAccessToken(loginDTO));
            loginDTO.setRefreshToken(jwtProvider.createRefreshToken(loginDTO));

            seccessResponseBody = new SeccessResponseBody();
            seccessResponseBody.setResultObject(loginDTO);//결과값
            resultBody = mapper.valueToTree(seccessResponseBody);
            return ResponseEntity.status(HttpStatus.OK).body(resultBody);
        }catch(Exception e){
            String msg = e.getMessage();
            log.error(msg);

            //로그인 예외처리
            if(msg.equals("NORESULT")){
                unauthorizedResponseBody = new UnauthorizedResponseBody();
                unauthorizedResponseBody.setServerMessage("아이디 또는 비밀번호를 잘못입력했습니다. 입력하신 내용을 다시 확인해주세요.");
                resultBody = mapper.valueToTree(unauthorizedResponseBody);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(resultBody);
            }

            //로그인 오류
            if(msg.equals("SERVICE ERROR")){
                errorResponseBody = new ErrorResponseBody();
                resultBody = mapper.valueToTree(errorResponseBody);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultBody);
            } else {
                unauthorizedResponseBody = new UnauthorizedResponseBody();
                unauthorizedResponseBody.setServerMessage(msg);
                resultBody = mapper.valueToTree(unauthorizedResponseBody);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(resultBody);
            }
            
        }
    }

}
