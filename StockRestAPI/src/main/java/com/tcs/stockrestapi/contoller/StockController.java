package com.tcs.stockrestapi.contoller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.tcs.stockrestapi.exception.InvalidQuantityException;
import com.tcs.stockrestapi.exception.ProductIdNotFoundException;
import com.tcs.stockrestapi.exception.StockIdNotFoundException;
import com.tcs.stockrestapi.model.Stock;
import com.tcs.stockrestapi.service.StockService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/stock")
public class StockController {
	@Autowired
	StockService stockService;
		
	@GetMapping
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public List<Stock> getStock() {
		return stockService.getStocks().get();
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<Stock> getStockById(@PathVariable("id") int stockId) throws StockIdNotFoundException {
		Stock stock = stockService.getStockById(stockId).orElseThrow(()-> new StockIdNotFoundException("Stock not found"));
		return ResponseEntity.ok().body(stock);
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> createOrUpdateStock(@RequestBody Stock stock,UriComponentsBuilder uriComponentsBuilder,HttpServletRequest request) throws InvalidQuantityException, ProductIdNotFoundException {
		if((stockService.findByProductId(stock.getProductId()))== null)
			throw new ProductIdNotFoundException("Product not found");
		if(stockService.prouductExists(stock.getProductId()))
			throw new InvalidQuantityException("Quantity for the product already exists");
		if(stock.getQuantity()<=0)
			throw new InvalidQuantityException("Invalid Quantity Value");
		Stock stock2 = stockService.createOrUpdateStock(stock);
		UriComponents uriComponents = uriComponentsBuilder
				.path(request.getRequestURI()+"/{id}")
				.buildAndExpand(stock2.getStockId());
		 return ResponseEntity.created(uriComponents.toUri()).body(stock2);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public Map<String, Boolean> deleteStockById(@PathVariable int id) throws StockIdNotFoundException { 
		stockService.getStockById(id).orElseThrow(()-> new StockIdNotFoundException("Stock not found"));
		stockService.deleteStock(id);
		HashMap<String, Boolean> hashMap = new HashMap<>();
		hashMap.put("deleted", Boolean.TRUE);
		return hashMap;
	}
	
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Stock> updateStock(@PathVariable("id") Integer id,
			@Valid @RequestBody Stock stock ) throws StockIdNotFoundException, InvalidQuantityException, ProductIdNotFoundException {
		if((stockService.findByProductId(stock.getProductId()))== null)
			throw new ProductIdNotFoundException("Product not found");
		stockService.getStockById(id)
				.orElseThrow(()-> new StockIdNotFoundException("Stock not found"));
		if(stock.getQuantity()<=0)
			throw new InvalidQuantityException("Invalid Quantity Value");
		stock.setStockId(id);
		return ResponseEntity.ok(stockService.createOrUpdateStock(stock));
	}
}
