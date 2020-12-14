package com.tcs.pricerestapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.tcs.pricerestapi.exception.ProductIdNotFoundException;
import com.tcs.pricerestapi.model.Price;


public interface PriceService {
	
	public Price createOrUpdatePrice(Price priec);
	public Optional<Price> getPriceById(int id);
	public void deletePrice(int id);
	public Optional<List<Price>>  getPrices();
	public String findByProductId(int productId);
	public boolean prouductExists(int productId); 
}

