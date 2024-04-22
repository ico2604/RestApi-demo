package com.api.swagger3.model.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QScore is a Querydsl query type for Score
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QScore extends EntityPathBase<Score> {

    private static final long serialVersionUID = -767260848L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QScore score1 = new QScore("score1");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    public final QEducation education;

    public final QMember member;

    public final DateTimePath<java.time.LocalDateTime> modDate = createDateTime("modDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> regDate = createDateTime("regDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> score = createNumber("score", Integer.class);

    public final NumberPath<Long> scoreKey = createNumber("scoreKey", Long.class);

    public QScore(String variable) {
        this(Score.class, forVariable(variable), INITS);
    }

    public QScore(Path<? extends Score> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QScore(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QScore(PathMetadata metadata, PathInits inits) {
        this(Score.class, metadata, inits);
    }

    public QScore(Class<? extends Score> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.education = inits.isInitialized("education") ? new QEducation(forProperty("education")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

