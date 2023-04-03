package com.toyproject.ecommerce.repository;

import com.toyproject.ecommerce.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
