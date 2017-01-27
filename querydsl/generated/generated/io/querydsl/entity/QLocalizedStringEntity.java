package io.querydsl.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QLocalizedStringEntity is a Querydsl query type for LocalizedStringEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLocalizedStringEntity extends EntityPathBase<LocalizedStringEntity> {

    private static final long serialVersionUID = -1368804587L;

    public static final QLocalizedStringEntity localizedStringEntity = new QLocalizedStringEntity("localizedStringEntity");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath language = createString("language");

    public final StringPath text = createString("text");

    public QLocalizedStringEntity(String variable) {
        super(LocalizedStringEntity.class, forVariable(variable));
    }

    public QLocalizedStringEntity(Path<? extends LocalizedStringEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLocalizedStringEntity(PathMetadata metadata) {
        super(LocalizedStringEntity.class, metadata);
    }

}

