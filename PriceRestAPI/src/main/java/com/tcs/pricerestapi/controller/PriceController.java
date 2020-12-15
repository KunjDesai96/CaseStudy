package com.tcs.pricerestapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import com.tcs.pricerestapi.exception.InvalidPriceException;
import com.tcs.pricerestapi.exception.PriceIdNotFoundException;
import com.tcs.pricerestapi.exception.ProductIdNotFoundException;
import com.tcs.pricerestapi.model.Price;
import com.tcs.pricerestapi.service.PriceService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/price")
public class PriceController {

	@Autowired
	PriceService priceService;
		
	@GetMapping
	public List<Price> getPrice() {
		return priceService.getPrices().get();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Price> getPriceById(@PathVariable("id") int priceId) throws PriceIdNotFoundException {
		Price price = priceService.getPriceById(priceId).orElseThrow(()-> new PriceIdNotFoundException("Price not found"));
		return ResponseEntity.ok().body(price);
	}
	
	@PostMapping
	public ResponseEntity<?> createOrUpdatePrice(@RequestBody Price price,UriComponentsBuilder uriComponentsBuilder,HttpServletRequest request) throws InvalidPriceException, ProductIdNotFoundException {
		if((priceService.findByProductId(price.getProductId()))== null)
			throw new ProductIdNotFoundException("Product not found");
		if(priceService.prouductExists(price.getProductId()))
			throw new InvalidPriceException("Price for the product already exists");
		if(price.getPriceValue()<=0)
			throw new InvalidPriceException("Invalid Price Value");
		Price price2 = priceService.createOrUpdatePrice(price);
		UriComponents uriComponents = uriComponentsBuilder
				.path(request.getRequestURI()+"/{id}")
				.buildAndExpand(price2.getPriceId());
		 return ResponseEntity.created(uriComponents.toUri()).body(price2);
	}
	
	@DeleteMapping("/{id}")
	public Map<String, Boolean> deletePriceById(@PathVariable int id) throws PriceIdNotFoundException { 
		priceService.getPriceById(id).orElseThrow(()-> new PriceIdNotFoundException("Price not found"));
		priceService.deletePrice(id);
		HashMap<String, Boolean> hashMap = new HashMap<>();
		hashMap.put("deleted", Boolean.TRUE);
		return hashMap;
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<Price> updatePrice(@PathVariable("id") Integer id,
			@Valid @RequestBody Price price ) throws PriceIdNotFoundException, InvalidPriceException, ProductIdNotFoundException {
		if((priceService.findByProductId(price.getProductId()))== null)
			throw new ProductIdNotFoundException("Product not found");
		priceService.getPriceById(id)
				.orElseThrow(()-> new PriceIdNotFoundException("Price not found"));
		if(price.getPriceValue()<=0)
			throw new InvalidPriceException("Invalid Price Value");
		price.setPriceId(id);
		return ResponseEntity.ok(priceService.createOrUpdatePrice(price));
	}
}
