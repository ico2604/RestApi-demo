package com.api.swagger3.model.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEducation is a Querydsl query type for Education
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEducation extends EntityPathBase<Education> {

    private static final long serialVersionUID = -218226330L;

    public static final QEducation education = new QEducation("education");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    public final NumberPath<Long> eduKey = createNumber("eduKey", Long.class);

    public final StringPath eduName = createString("eduName");

    public final NumberPath<Integer> eduType = createNumber("eduType", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> modDate = createDateTime("modDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> regDate = createDateTime("regDate", java.time.LocalDateTime.class);

    public QEducation(String variable) {
        super(Education.class, forVariable(variable));
    }

    public QEducation(Path<? extends Education> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEducation(PathMetadata metadata) {
        super(Education.class, metadata);
    }

}

