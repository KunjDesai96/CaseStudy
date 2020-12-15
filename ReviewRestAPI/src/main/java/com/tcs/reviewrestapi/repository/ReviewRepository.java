package com.tcs.reviewrestapi.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcs.reviewrestapi.model.Review;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
	List<Review> findByProductId(int productId);
	List<Review> deleteByProductId(int productId);
}
