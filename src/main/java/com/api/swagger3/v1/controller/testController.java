package com.api.swagger3.v1.controller;

import org.springframework.web.bind.annotation.RestController;

import com.api.swagger3.model.User;
import com.api.swagger3.model.response.BadRequestResponseBody;
import com.api.swagger3.model.response.ErrorResponseBody;
import com.api.swagger3.model.response.NotFoundResponseBody;
import com.api.swagger3.model.response.SeccessResponseBody;
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
import lombok.extern.slf4j.Slf4j;

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
@Tag(name = "test", description = "test API")
@RequestMapping("/api/v1/")
public class testController {

    public User createUser(){
        User user = new User(1L, "adcd1234", "수호", "1", "hwangboosuho@naver.com", "1", "19940829", "01011112222");
        return user;
    }

    @Operation(summary = "회원 등록", description = "<b>회원</b>을 등록하는<br>API입니다.", responses = {
        @ApiResponse(responseCode = "200", description = "회원 등록 성공", content = @Content(schema = @Schema(implementation = SeccessResponseBody.class))),
        @ApiResponse(responseCode = "500", description = "회원 등록 오류 발생", content = @Content(schema = @Schema(implementation = ErrorResponseBody.class))),
        @ApiResponse(responseCode = "400", description = "서버 오류 발생", content = @Content(schema = @Schema(implementation = BadRequestResponseBody.class))),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = NotFoundResponseBody.class)))
    })
    @PostMapping(value = "/setUser")
    public ResponseEntity<?> setUser(@Valid @RequestBody User user) {
        ObjectNode resultBody = null;
        ObjectMapper mapper = new ObjectMapper();
        SeccessResponseBody seccessResponseBody;
        ErrorResponseBody errorResponseBody;

        try{
            seccessResponseBody = new SeccessResponseBody();
            seccessResponseBody.setResultObject(user);
            resultBody = mapper.valueToTree(seccessResponseBody);
            return ResponseEntity.status(HttpStatus.OK).body(resultBody);
        }catch(Exception e){
            errorResponseBody = new ErrorResponseBody();
            resultBody = mapper.valueToTree(errorResponseBody);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultBody);
        }
        
    }
    @Operation(summary = "회원 불러오기", description = "<b>회원</b>을 조회하는<br>API입니다.", responses = {
        @ApiResponse(responseCode = "200", description = "회원 불러오기 성공", content = @Content(schema = @Schema(implementation = SeccessResponseBody.class))),
        @ApiResponse(responseCode = "500", description = "회원 불러오기 오류 발생", content = @Content(schema = @Schema(implementation = ErrorResponseBody.class))),
        @ApiResponse(responseCode = "400", description = "서버 오류 발생", content = @Content(schema = @Schema(implementation = BadRequestResponseBody.class))),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = NotFoundResponseBody.class)))
    })
    @GetMapping(value = "/getUser/{id}")
    public ResponseEntity<?> getUser(@Parameter(name = "id", description = "posts 의 id", in = ParameterIn.PATH) @PathVariable Long id) {
        ObjectNode resultBody = null;
        ObjectMapper mapper = new ObjectMapper();
        SeccessResponseBody seccessResponseBody;
        ErrorResponseBody errorResponseBody;
        
        try{
            seccessResponseBody = new SeccessResponseBody();
            User user = createUser();
            seccessResponseBody.setResultObject(user);
            resultBody = mapper.valueToTree(seccessResponseBody);
            return ResponseEntity.status(HttpStatus.OK).body(resultBody);
        }catch(Exception e){
            errorResponseBody = new ErrorResponseBody();
            resultBody = mapper.valueToTree(errorResponseBody);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultBody);
        }
        
    }

    
}
