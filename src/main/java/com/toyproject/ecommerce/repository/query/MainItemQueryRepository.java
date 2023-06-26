package com.toyproject.ecommerce.repository.query;

import com.querydsl.core.annotations.QueryProjection;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toyproject.ecommerce.entity.Item;
import com.toyproject.ecommerce.entity.QItem;
import com.toyproject.ecommerce.entity.QItemImage;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.toyproject.ecommerce.entity.QItem.*;
import static com.toyproject.ecommerce.entity.QItemImage.*;

/**
 * 메인 페이지용 리포지토리
 */
@Repository
public class MainItemQueryRepository {

    private final JPAQueryFactory queryFactory;

    public MainItemQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    public Page<MainItemDto> findMainItem(Pageable pageable) {

        List<MainItemDto> content = queryFactory
                .select(new QMainItemDto(
                        item.id,
                        item.name,
                        item.price,
                        itemImage.storeName
                ))
                .from(item)
                .join(item.itemImageList, itemImage)
                .where(itemImage.firstImage.eq("Y"))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        //COUNTQUERY 에서 조인 할 필요가 있을까? 대표 이미지
        JPAQuery<Long> total = queryFactory
                .select(item.count())
                .from(item);

        return PageableExecutionUtils.getPage(content, pageable, total::fetchOne);  //CountQuery 최적화


        /**
         * SELECT COUNT(I.ITEM_ID) FROM ITEM I
         * JOIN ITEM_IMAGE IM
         * ON I.ITEM_ID = IM.ITEM_ID
         * WHERE IM.FIRST_IMAGE='Y';
         *
         * SELECT COUNT(I.ITEM_ID) FROM ITEM I;
         *
         * SELECT * FROM ITEM I
         * JOIN ITEM_IMAGE IM
         * ON I.ITEM_ID = IM.ITEM_ID;
         */
    }
}
