package com.tcs.productrestapi.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tcs.productrestapi.model.Product;
import com.tcs.productrestapi.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Override
	public Product createOrUpdateProduct(Product product) {
		// TODO Auto-generated method stub
		Product product2 = null;
		try {
			product2 = productRepository.save(product);
			return product2;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Optional<Product> getProductById(int id) {
		// TODO Auto-generated method stub
			return productRepository.findById(id);
	}

	@Override
	public void deleteProduct(int id) {
		// TODO Auto-generated method stub
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		restTemplate.exchange("http://localhost:9008/api/v1/price/product/"+id, HttpMethod.DELETE, entity, String.class).getBody();
		restTemplate.exchange("http://localhost:9007/api/v1/stock/product/"+id, HttpMethod.DELETE, entity, String.class).getBody();	
		restTemplate.exchange("http://localhost:9009/api/v1/review/product/"+id, HttpMethod.DELETE, entity, String.class).getBody();
		productRepository.deleteById(id);
	}

	@Override
	public Optional<List<Product>> getProducts() {
		// TODO Auto-generated method stub
		return Optional.ofNullable(productRepository.findAll());
	}

	@Override
	public Optional<List<Product>> getProductsByCategory(String catName) {
		// TODO Auto-generated method stub
		return Optional.ofNullable(productRepository.findByCategory(catName));
	}

}
