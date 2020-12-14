package com.tcs.reviewrestapi.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tcs.reviewrestapi.model.Review;
import com.tcs.reviewrestapi.repository.ReviewRepository;

@Transactional
@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	ReviewRepository reviewRepository;
	
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
	public List<Review> findByProductId(int productId) {
		// TODO Auto-generated method stub
		return null;
	}

}
