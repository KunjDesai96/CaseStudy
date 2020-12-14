package com.tcs.pricerestapi.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.tcs.pricerestapi.model.Price;
import com.tcs.pricerestapi.repository.PriceRepository;

@Transactional
@Service
public class PriceServiceImpl implements PriceService {

	@Autowired
	PriceRepository priceRepository;
	
	@Autowired
	RestTemplate restTemplate;

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
	public String findByProductId(int productId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		return restTemplate.exchange("http://localhost:9006/api/v1/product/"+productId, HttpMethod.GET, entity, String.class).getBody();
	}

}
