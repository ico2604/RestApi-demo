package com.api.swagger3.v1.controller;

import org.springframework.web.bind.annotation.RestController;

import com.api.swagger3.dto.MemberDTO;
import com.api.swagger3.model.Entity.Member;
import com.api.swagger3.model.response.BadRequestResponseBody;
import com.api.swagger3.model.response.ErrorResponseBody;
import com.api.swagger3.model.response.NotFoundResponseBody;
import com.api.swagger3.model.response.SeccessResponseBody;
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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@CrossOrigin("*")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "member", description = "member API")
@RequestMapping("/api/v1/member")
public class testController {
    
    @Autowired
    MemberService memberService;

    @Operation(summary = "회원 등록", description = "<b>회원</b>을 등록하는<br>API입니다.", responses = {
        @ApiResponse(responseCode = "200", description = "회원 등록 성공", content = @Content(schema = @Schema(implementation = SeccessResponseBody.class))),
        @ApiResponse(responseCode = "500", description = "회원 등록 오류 발생", content = @Content(schema = @Schema(implementation = ErrorResponseBody.class))),
        @ApiResponse(responseCode = "400", description = "서버 오류 발생", content = @Content(schema = @Schema(implementation = BadRequestResponseBody.class))),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = NotFoundResponseBody.class)))
    })
    @PostMapping(value = "/setUser")
    public ResponseEntity<?> setUser(@Valid @RequestBody MemberDTO memberDTO) {
        ObjectNode resultBody = null;
        ObjectMapper mapper = new ObjectMapper();
        SeccessResponseBody seccessResponseBody;
        ErrorResponseBody errorResponseBody;

        try{
            memberService.setMember(memberDTO);
            seccessResponseBody = new SeccessResponseBody();
            seccessResponseBody.setResultObject(memberDTO);
            resultBody = mapper.valueToTree(seccessResponseBody);
            return ResponseEntity.status(HttpStatus.OK).body(resultBody);
        }catch(Exception e){
            errorResponseBody = new ErrorResponseBody();
            resultBody = mapper.valueToTree(errorResponseBody);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultBody);
        }
        
    }

    @Operation(summary = "로그인", description = "<b>회원로그인후 회원데이터를 불러오는 API입니다.</b>", responses = {
        @ApiResponse(responseCode = "200", description = "회원 불러오기 성공", content = @Content(schema = @Schema(implementation = SeccessResponseBody.class))),
        @ApiResponse(responseCode = "500", description = "회원 불러오기 오류 발생", content = @Content(schema = @Schema(implementation = ErrorResponseBody.class))),
        @ApiResponse(responseCode = "400", description = "서버 오류 발생", content = @Content(schema = @Schema(implementation = BadRequestResponseBody.class))),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = NotFoundResponseBody.class)))
    })
    @GetMapping(value = "/getUser/{memberId}/{memberPw}")
    public ResponseEntity<?> getUser(@Parameter(name = "memberId", description = "회원의 로그인 아이디", in = ParameterIn.PATH) @PathVariable String memberId,
                                        @Parameter(name = "memberPw", description = "회원의 로그인 비밀번호", in = ParameterIn.PATH) @PathVariable String memberPw) {
        ObjectNode resultBody = null;
        ObjectMapper mapper = new ObjectMapper();
        SeccessResponseBody seccessResponseBody;
        ErrorResponseBody errorResponseBody;
        
        try{
            MemberDTO m = memberService.loginMember(memberId, memberPw);//조회
            seccessResponseBody = new SeccessResponseBody();
            seccessResponseBody.setResultObject(m);//결과값
            resultBody = mapper.valueToTree(seccessResponseBody);
            return ResponseEntity.status(HttpStatus.OK).body(resultBody);
        }catch(Exception e){
            log.error("controller", e.getMessage());
            if(e.getMessage() == "SERVICE ERROR"){
                errorResponseBody = new ErrorResponseBody();
                resultBody = mapper.valueToTree(errorResponseBody);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultBody);
            }else {
                seccessResponseBody = new SeccessResponseBody();
                seccessResponseBody.setServerMessage(e.getMessage());
                resultBody = mapper.valueToTree(seccessResponseBody);
                return ResponseEntity.status(HttpStatus.OK).body(resultBody);
            }
            
        }
        
    }

    
}
