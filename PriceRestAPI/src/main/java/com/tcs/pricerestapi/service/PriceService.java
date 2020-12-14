package com.tcs.pricerestapi.service;

import java.util.List;
import java.util.Optional;

import com.tcs.pricerestapi.model.Price;


public interface PriceService {
	
	public Price createOrUpdatePrice(Price priec);
	public Optional<Price> getPriceById(int id);
	public void deletePrice(int id);
	public Optional<List<Price>>  getPrices();
	public List<Price> findByProductId(int productId);
}

