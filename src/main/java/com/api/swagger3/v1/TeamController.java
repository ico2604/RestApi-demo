package com.api.swagger3.v1;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.swagger3.model.dto.TeamDTO;
import com.api.swagger3.model.dto.TeamSelectDTO;
import com.api.swagger3.model.response.BadRequestResponseBody;
import com.api.swagger3.model.response.ErrorResponseBody;
import com.api.swagger3.model.response.NotFoundResponseBody;
import com.api.swagger3.model.response.SeccessResponseBody;
import com.api.swagger3.service.TeamService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//@SecurityRequirement(name = "Bearer Authentication")
@RestController
@CrossOrigin("*")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "team", description = "team API")
@RequestMapping("/api/v1/team")
public class TeamController {

    private final TeamService teamService;

    @Operation(summary = "팀 등록", description = "<b>팀</b>을 등록하는<br>API입니다.", responses = {
        @ApiResponse(responseCode = "200", description = "팀 등록 성공", content = @Content(schema = @Schema(implementation = SeccessResponseBody.class))),
        @ApiResponse(responseCode = "500", description = "팀 등록 오류 발생", content = @Content(schema = @Schema(implementation = ErrorResponseBody.class))),
        @ApiResponse(responseCode = "400", description = "서버 오류 발생", content = @Content(schema = @Schema(implementation = BadRequestResponseBody.class))),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = NotFoundResponseBody.class)))
    })
    @PostMapping(value = "/setTeam")
    public ResponseEntity<?> setTeam(@Valid @RequestBody TeamDTO teamDTO) {
        ObjectNode resultBody = null;
        ObjectMapper mapper = new ObjectMapper();
        SeccessResponseBody seccessResponseBody;
        ErrorResponseBody errorResponseBody;

        try{
            teamService.setTeam2(teamDTO);
            seccessResponseBody = new SeccessResponseBody();
            seccessResponseBody.setResultObject(teamDTO);
            resultBody = mapper.valueToTree(seccessResponseBody);
            return ResponseEntity.status(HttpStatus.OK).body(resultBody);
        }catch(Exception e){
            log.error("팀등록 에러", e);
            errorResponseBody = new ErrorResponseBody();
            resultBody = mapper.valueToTree(errorResponseBody);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultBody);
        } 
    }

    @Operation(summary = "팀 수정", description = "<b>팀</b>을 수정하는<br>API입니다.", responses = {
        @ApiResponse(responseCode = "200", description = "팀 수정 성공", content = @Content(schema = @Schema(implementation = SeccessResponseBody.class))),
        @ApiResponse(responseCode = "500", description = "팀 수정 오류 발생", content = @Content(schema = @Schema(implementation = ErrorResponseBody.class))),
        @ApiResponse(responseCode = "400", description = "서버 오류 발생", content = @Content(schema = @Schema(implementation = BadRequestResponseBody.class))),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = NotFoundResponseBody.class)))
    })
    @PutMapping(value = "/modTeam")
    public ResponseEntity<?> modTeam(@Valid @RequestBody TeamDTO teamDTO) {
        ObjectNode resultBody = null;
        ObjectMapper mapper = new ObjectMapper();
        SeccessResponseBody seccessResponseBody;
        ErrorResponseBody errorResponseBody;

        try{
            teamService.updateTeam1(teamDTO);
            seccessResponseBody = new SeccessResponseBody();
            seccessResponseBody.setResultObject(teamDTO);
            resultBody = mapper.valueToTree(seccessResponseBody);
            return ResponseEntity.status(HttpStatus.OK).body(resultBody);
        }catch(Exception e){
            log.error("팀 수정 에러", e);
            errorResponseBody = new ErrorResponseBody();
            resultBody = mapper.valueToTree(errorResponseBody);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultBody);
        } 
    }

    @Operation(summary = "팀 삭제", description = "<b>팀</b>을 삭제하는<br>API입니다.", responses = {
        @ApiResponse(responseCode = "200", description = "팀 수정 성공", content = @Content(schema = @Schema(implementation = SeccessResponseBody.class))),
        @ApiResponse(responseCode = "500", description = "팀 수정 오류 발생", content = @Content(schema = @Schema(implementation = ErrorResponseBody.class))),
        @ApiResponse(responseCode = "400", description = "서버 오류 발생", content = @Content(schema = @Schema(implementation = BadRequestResponseBody.class))),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = NotFoundResponseBody.class)))
    })
    @DeleteMapping(value = "/removeTeam")
    public ResponseEntity<?> removeTeam(@Valid @RequestParam Long teamKey) {
        ObjectNode resultBody = null;
        ObjectMapper mapper = new ObjectMapper();
        SeccessResponseBody seccessResponseBody;
        ErrorResponseBody errorResponseBody;

        try{
            teamService.removeTeam(teamKey);
            seccessResponseBody = new SeccessResponseBody();
            seccessResponseBody.setResultObject(teamKey);
            resultBody = mapper.valueToTree(seccessResponseBody);
            return ResponseEntity.status(HttpStatus.OK).body(resultBody);
        }catch(Exception e){
            log.error("팀 삭제 에러", e);
            errorResponseBody = new ErrorResponseBody();
            resultBody = mapper.valueToTree(errorResponseBody);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultBody);
        } 
    }

    @Operation(summary = "팀 리스트", description = "<b>팀 리스트 조회 및 하위 회원 카운트</b>.", responses = {
        @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = SeccessResponseBody.class))),
        @ApiResponse(responseCode = "500", description = "조회 오류 발생", content = @Content(schema = @Schema(implementation = ErrorResponseBody.class))),
        @ApiResponse(responseCode = "400", description = "서버 오류 발생", content = @Content(schema = @Schema(implementation = BadRequestResponseBody.class))),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = NotFoundResponseBody.class)))
    })
    @PostMapping(value = "/getTeamList")
    public ResponseEntity<?> getTeamList() {
        ObjectNode resultBody = null;
        ObjectMapper mapper = new ObjectMapper();
        SeccessResponseBody seccessResponseBody;
        ErrorResponseBody errorResponseBody;

        try{
            List<TeamSelectDTO> teamList = teamService.getTeamList();
            seccessResponseBody = new SeccessResponseBody();
            seccessResponseBody.setResultObject(teamList);
            resultBody = mapper.valueToTree(seccessResponseBody);
            return ResponseEntity.status(HttpStatus.OK).body(resultBody);
        }catch(Exception e){
            log.error("팀 리스트 조회 에러", e);
            errorResponseBody = new ErrorResponseBody();
            resultBody = mapper.valueToTree(errorResponseBody);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultBody);
        } 
    }
}
