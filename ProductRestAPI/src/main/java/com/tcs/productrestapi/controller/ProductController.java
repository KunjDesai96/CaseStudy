package com.tcs.productrestapi.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.tcs.productrestapi.exception.ExpiryDateException;
import com.tcs.productrestapi.exception.ProductIdNotFoundException;
import com.tcs.productrestapi.model.Product;
import com.tcs.productrestapi.service.ProductService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
	@Autowired
	ProductService productService;
		
	@GetMapping
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public List<Product> getProducts() {
		return productService.getProducts().get();
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Product> getProductById(@PathVariable("id") int productId) throws ProductIdNotFoundException {
		Product product = productService.getProductById(productId).orElseThrow(()-> new ProductIdNotFoundException("Product not found"));
		return ResponseEntity.ok().body(product);
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> createOrUpdateProduct(@RequestBody Product product,UriComponentsBuilder uriComponentsBuilder,HttpServletRequest request) throws ExpiryDateException, ParseException {
		Date expiryDate = new SimpleDateFormat("dd/MM/yyyy").parse(product.getExpiryDate()); 
		String now = new SimpleDateFormat("dd/MM/yyyy").format(new Date()); 
		Date today = new SimpleDateFormat("dd/MM/yyyy").parse(now);
		if(expiryDate.before(today))
			throw new ExpiryDateException("Invalid Expiry Date");
		Product product2 = productService.createOrUpdateProduct(product);
		UriComponents uriComponents = uriComponentsBuilder
				.path(request.getRequestURI()+"/{id}")
				.buildAndExpand(product2.getProductId());
		
		
	  return ResponseEntity.created(uriComponents.toUri()).body(product2);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public Map<String, Boolean> deleteProductById(@PathVariable int id) throws ProductIdNotFoundException { 
		productService.getProductById(id).orElseThrow(()-> new ProductIdNotFoundException("Product not found"));
		
		productService.deleteProduct(id);
		HashMap<String, Boolean> hashMap = new HashMap<>();
		hashMap.put("deleted", Boolean.TRUE);
		
		return hashMap;
	}
	
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Product> updateProduct(@PathVariable("id") Integer id,
			@Valid @RequestBody Product product ) throws ProductIdNotFoundException, ExpiryDateException, ParseException {
		productService.getProductById(id)
				.orElseThrow(()-> new ProductIdNotFoundException("Product not found"));
		Date expiryDate = new SimpleDateFormat("dd/MM/yyyy").parse(product.getExpiryDate()); 
		String now = new SimpleDateFormat("dd/MM/yyyy").format(new Date()); 
		Date today = new SimpleDateFormat("dd/MM/yyyy").parse(now);
		if(expiryDate.before(today))
			throw new ExpiryDateException("Invalid Expiry Date");
		product.setProductId(id);
		return ResponseEntity.ok( productService.createOrUpdateProduct(product));
	}
	
	
}
