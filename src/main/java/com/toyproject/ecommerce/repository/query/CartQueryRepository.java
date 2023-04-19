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
                        "select new com.toyproject.ecommerce.repository.query.CartQueryDto(ci.id, i.name, ci.count, i.price)" +
                                " from CartItem ci" +
                                " join ci.item i" +
                                " where ci.cart.id = :cartId", CartQueryDto.class)
                .setParameter("cartId", cartId)
                .getResultList();

        return cartQueryDtoList;
    }
}
