package com.tcs.stockrestapi.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tcs.stockrestapi.model.Stock;


@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {
	Stock findByProductId(int productId);
	List<Stock> deleteByProductId(int productId);
}
