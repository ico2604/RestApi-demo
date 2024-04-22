package com.api.swagger3.model.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.api.swagger3.model.dto.QMemberDTO is a Querydsl Projection type for MemberDTO
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QMemberDTO extends ConstructorExpression<MemberDTO> {

    private static final long serialVersionUID = -1152829939L;

    public QMemberDTO(com.querydsl.core.types.Expression<Long> memberKey, com.querydsl.core.types.Expression<String> memberId, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<String> type, com.querydsl.core.types.Expression<String> email, com.querydsl.core.types.Expression<String> sex, com.querydsl.core.types.Expression<String> birthDate, com.querydsl.core.types.Expression<String> phoneNumber, com.querydsl.core.types.Expression<Long> teamKey, com.querydsl.core.types.Expression<String> teamName) {
        super(MemberDTO.class, new Class<?>[]{long.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, long.class, String.class}, memberKey, memberId, name, type, email, sex, birthDate, phoneNumber, teamKey, teamName);
    }

}

