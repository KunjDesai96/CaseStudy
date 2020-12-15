package com.tcs.reviewrestapi.service;

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

import com.tcs.reviewrestapi.model.Review;
import com.tcs.reviewrestapi.repository.ReviewRepository;


@Transactional
@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	ReviewRepository reviewRepository;

	@Autowired
	RestTemplate restTemplate;

	@Override
	public Review createOrUpdateReview(Review review) {
		// TODO Auto-generated method stub
		try {
			return reviewRepository.save(review);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Optional<Review> getReviewById(int id) {
		// TODO Auto-generated method stub
		return reviewRepository.findById(id);
	}

	@Override
	public void deleteReview(int id) {
		// TODO Auto-generated method stub
		reviewRepository.deleteById(id);
	}

	@Override
	public Optional<List<Review>> getReviews() {
		// TODO Auto-generated method stub
		return Optional.ofNullable(reviewRepository.findAll());
	}

	@Override
	public String findByProductId(int productId) {
		// TODO Auto-generated method stub
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		return restTemplate
				.exchange("http://localhost:9006/api/v1/product/" + productId, HttpMethod.GET, entity, String.class)
				.getBody();

	}

	@Override
	public boolean deleteByProductId(int productId) {
		// TODO Auto-generated method stub
		List<Review> reviewL;
		if(prouductExists(productId))
		{
			reviewL = reviewRepository.deleteByProductId(productId);
			 if(reviewL.size()<=0)
				 return false;
			 else 
				 return true;
		}	
		return true;

	}

	@Override
	public boolean prouductExists(int productId) {
		// TODO Auto-generated method stub
		List<Review> stock = reviewRepository.findByProductId(productId);
		if(stock!= null)
			return true;
		else 
			return false;
	}

}
