package com.tcs.pricerestapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcs.pricerestapi.model.Price;
import com.tcs.pricerestapi.repository.PriceRepository;



@Transactional
@Service
public class PriceServiceImpl implements PriceService {

	@Autowired
	PriceRepository priceRepository;
	
	@Override
	public Price createOrUpdatePrice(Price price) {	
		try {
			return priceRepository.save(price);
		} catch (Exception e) {	
			e.printStackTrace();
			return null;
		}
	}



	@Override
	public void deletePrice(int id) {
		priceRepository.deleteById(id);
	}

	@Override
	public Optional<Price> getPriceById(int id) {
		// TODO Auto-generated method stub
		return priceRepository.findById(id);
	}



	@Override
	public Optional<List<Price>> getPrices() {
		// TODO Auto-generated method stub
		return Optional.ofNullable(priceRepository.findAll());
	}



	@Override
	public List<Price> findByProductId(int productId) {
		// TODO Auto-generated method stub
		return null;
	}



}
