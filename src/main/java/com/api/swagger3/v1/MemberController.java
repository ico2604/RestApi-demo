package com.api.swagger3.v1;

import org.springframework.web.bind.annotation.RestController;

import com.api.swagger3.config.JwtProvider;
import com.api.swagger3.model.dto.MemberDTO;
import com.api.swagger3.model.dto.MemberSaveDTO;
import com.api.swagger3.model.request.MemberPageRequest;
import com.api.swagger3.model.response.BadRequestResponseBody;
import com.api.swagger3.model.response.ErrorResponseBody;
import com.api.swagger3.model.response.NotFoundResponseBody;
import com.api.swagger3.model.response.SeccessResponseBody;
import com.api.swagger3.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@CrossOrigin("*")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "member", description = "member API")
@RequestMapping("/api/v1/member")
public class MemberController {
    
    private final MemberService memberService;

    private final JwtProvider jwtProvider;

    @Operation(summary = "회원 등록", description = "<b>회원</b>을 등록하는<br>API입니다.", responses = {
        @ApiResponse(responseCode = "200", description = "회원 등록 성공", content = @Content(schema = @Schema(implementation = SeccessResponseBody.class))),
        @ApiResponse(responseCode = "500", description = "회원 등록 오류 발생", content = @Content(schema = @Schema(implementation = ErrorResponseBody.class))),
        @ApiResponse(responseCode = "400", description = "서버 오류 발생", content = @Content(schema = @Schema(implementation = BadRequestResponseBody.class))),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = NotFoundResponseBody.class)))
    })
    @PostMapping(value = "/setMember")
    public ResponseEntity<?> setUser(@Valid @RequestBody MemberSaveDTO req) {
        ObjectNode resultBody = null;
        ObjectMapper mapper = new ObjectMapper();
        SeccessResponseBody seccessResponseBody;
        ErrorResponseBody errorResponseBody;

        try{
            memberService.setMember(req);
            seccessResponseBody = new SeccessResponseBody();
            seccessResponseBody.setResultObject(req);
            resultBody = mapper.valueToTree(seccessResponseBody);
            return ResponseEntity.status(HttpStatus.OK).body(resultBody);
        }catch(Exception e){
            errorResponseBody = new ErrorResponseBody();
            resultBody = mapper.valueToTree(errorResponseBody);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultBody);
        } 
    }

    @Operation(summary = "회원 리스트(페이징)", description = "<b></b>.", responses = {
        @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = SeccessResponseBody.class))),
        @ApiResponse(responseCode = "500", description = "조회 오류 발생", content = @Content(schema = @Schema(implementation = ErrorResponseBody.class))),
        @ApiResponse(responseCode = "400", description = "서버 오류 발생", content = @Content(schema = @Schema(implementation = BadRequestResponseBody.class))),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = NotFoundResponseBody.class)))
    })
    @PostMapping(value = "/getMembersPage")
    public ResponseEntity<?> getMembersPage(HttpServletRequest req, @RequestBody MemberPageRequest memberPageRequest) {
        ObjectNode resultBody = null;
        ObjectMapper mapper = new ObjectMapper();
        SeccessResponseBody seccessResponseBody;
        ErrorResponseBody errorResponseBody;
        
        try{
            Pageable pageable = PageRequest.of(memberPageRequest.getPage(), memberPageRequest.getSize());

            Page<MemberDTO> result = memberService.setMembersPage(memberPageRequest.getCondition(), pageable);
            seccessResponseBody = new SeccessResponseBody();
            seccessResponseBody.setResultObject(result);
            resultBody = mapper.valueToTree(seccessResponseBody);
            return ResponseEntity.status(HttpStatus.OK).body(resultBody);
        }catch(Exception e){
            log.error("회원 리스트(페이징)", e);
            errorResponseBody = new ErrorResponseBody();
            resultBody = mapper.valueToTree(errorResponseBody);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultBody);
        } 
    }
}
