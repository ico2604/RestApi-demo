package com.api.swagger3.v1.controller;

import org.springframework.web.bind.annotation.RestController;

import com.api.swagger3.model.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@Tag(name = "test", description = "test API")
public class testController {
    @Operation(summary= "등록하기", description = "등록API 설명란입니다")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation", content = { 
            @Content(mediaType = "application/xml", schema = @Schema(implementation = User.class)), 
            @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) 
            }
        ),
        @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @PostMapping(value = "/user", consumes = { "application/json", "application/xml", "application/x-www-form-urlencoded" })
    public String getMethodName(@Parameter(description = "Create a new pet in the store", required = true) @Valid @RequestBody User user) {
        return new String();
    }

    
}
