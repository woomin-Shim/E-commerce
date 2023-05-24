package com.toyproject.ecommerce.repository.query;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CartQueryRepository {

    private final EntityManager em;

    public List<CartQueryDto> findCartQueryDtos(Long cartId) {
        List<CartQueryDto> cartQueryDtoList = em.createQuery(
                        "select new com.toyproject.ecommerce.repository.query.CartQueryDto(ci.id, i.name, i.stockQuantity, ci.count, i.price, im.storeName)" +
                                " from CartItem ci" +
                                " join ci.item i" +
                                " join i.itemImageList im" +
                                " where ci.cart.id = :cartId and im.firstImage='Y'", CartQueryDto.class)
                .setParameter("cartId", cartId)
                .getResultList();

        return cartQueryDtoList;
    }
}
