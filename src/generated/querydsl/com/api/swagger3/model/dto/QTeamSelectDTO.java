package com.api.swagger3.model.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.api.swagger3.model.dto.QTeamSelectDTO is a Querydsl Projection type for TeamSelectDTO
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QTeamSelectDTO extends ConstructorExpression<TeamSelectDTO> {

    private static final long serialVersionUID = -1585038706L;

    public QTeamSelectDTO(com.querydsl.core.types.Expression<Long> teamKey, com.querydsl.core.types.Expression<String> teamName, com.querydsl.core.types.Expression<Long> memberCount) {
        super(TeamSelectDTO.class, new Class<?>[]{long.class, String.class, long.class}, teamKey, teamName, memberCount);
    }

}

