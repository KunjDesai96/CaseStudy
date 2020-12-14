package com.tcs.reviewrestapi.service;


import java.util.List;
import java.util.Optional;

import com.tcs.reviewrestapi.model.Review;

public interface ReviewService {

	public Review createOrUpdateReview(Review review);
	public Optional<Review> getReviewById(int id);
	public void deleteReview(int id);
	public Optional<List<Review>> getReviews();
	public String findByProductId(int productId);
}
