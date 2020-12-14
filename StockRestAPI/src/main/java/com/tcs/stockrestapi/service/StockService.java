package com.tcs.stockrestapi.service;

import java.util.List;
import java.util.Optional;
import com.tcs.stockrestapi.model.Stock;


public interface StockService {
	public Stock createOrUpdateStock(Stock product);
	public Optional<Stock> getStockById(int id);
	public void deleteStock(int id);
	public Optional<java.util.List<Stock>> getStocks();
	public List<Stock> findByProductId(int productId);
}
