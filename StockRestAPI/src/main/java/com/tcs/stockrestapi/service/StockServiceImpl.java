package com.tcs.stockrestapi.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.tcs.stockrestapi.model.Stock;
import com.tcs.stockrestapi.respository.StockRepository;

@Transactional
@Service
public class StockServiceImpl implements StockService {

	@Autowired
	StockRepository stockRepository;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Override
	public Stock createOrUpdateStock(Stock stock) {
		// TODO Auto-generated method stub
		Stock stock2 = null;
		try {
			stock2 = stockRepository.save(stock);
			return stock2;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Optional<Stock> getStockById(int id) {
		// TODO Auto-generated method stub
		return stockRepository.findById(id);
	}

	@Override
	public void deleteStock(int id) {
		// TODO Auto-generated method stub
		stockRepository.deleteById(id);
	}

	@Override
	public Optional<List<Stock>> getStocks() {
		// TODO Auto-generated method stub
		return Optional.ofNullable(stockRepository.findAll());
	}

	@Override
	public String findByProductId(int productId) {
		// TODO Auto-generated method stub
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		return restTemplate.exchange("http://localhost:9006/api/v1/product/"+productId, HttpMethod.GET, entity, String.class).getBody();

	}

	@Override
	public boolean prouductExists(int productId) {
		// TODO Auto-generated method stub
		Stock stock = stockRepository.findByProductId(productId);
		if(stock!= null)
			return true;
		else 
			return false;
	}

}
