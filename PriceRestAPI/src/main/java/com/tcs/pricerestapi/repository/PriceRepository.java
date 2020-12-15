package com.tcs.pricerestapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcs.pricerestapi.model.Price;

@Repository
public interface PriceRepository extends JpaRepository<Price, Integer> {
	Price findByProductId(int productId);
	List<Price> deleteByProductId(int productId);
}
