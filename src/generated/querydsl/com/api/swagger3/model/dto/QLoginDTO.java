package com.api.swagger3.model.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.api.swagger3.model.dto.QLoginDTO is a Querydsl Projection type for LoginDTO
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QLoginDTO extends ConstructorExpression<LoginDTO> {

    private static final long serialVersionUID = 1665153934L;

    public QLoginDTO(com.querydsl.core.types.Expression<Long> memberKey, com.querydsl.core.types.Expression<String> memberPw) {
        super(LoginDTO.class, new Class<?>[]{long.class, String.class}, memberKey, memberPw);
    }

    public QLoginDTO(com.querydsl.core.types.Expression<Long> memberKey, com.querydsl.core.types.Expression<String> memberId, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<String> type, com.querydsl.core.types.Expression<String> email, com.querydsl.core.types.Expression<String> sex, com.querydsl.core.types.Expression<String> birthDate, com.querydsl.core.types.Expression<String> phoneNumber, com.querydsl.core.types.Expression<Long> teamKey, com.querydsl.core.types.Expression<String> teamName) {
        super(LoginDTO.class, new Class<?>[]{long.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, long.class, String.class}, memberKey, memberId, name, type, email, sex, birthDate, phoneNumber, teamKey, teamName);
    }

}

