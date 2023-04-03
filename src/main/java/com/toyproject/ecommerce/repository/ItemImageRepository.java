package com.toyproject.ecommerce.repository;

import com.toyproject.ecommerce.domain.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {
}
