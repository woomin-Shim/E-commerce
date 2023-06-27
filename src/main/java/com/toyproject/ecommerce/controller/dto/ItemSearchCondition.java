package com.toyproject.ecommerce.controller.dto;

import lombok.Data;

/**
 *  queryDsl 동적 쿼리에 사용 되는 DTO
 *  아이템 검색
 *  기간 검색
 */
@Data
public class ItemSearchCondition {

    private String ItemName;
}
