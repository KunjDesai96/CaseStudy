package com.tcs.reviewrestapi.contoller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

import com.tcs.reviewrestapi.exception.ReviewIdNotFoundException;
import com.tcs.reviewrestapi.model.Review;
import com.tcs.reviewrestapi.service.ReviewService;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewContoller {

	@Autowired
	ReviewService reviewService;
		
	@GetMapping
	public List<Review> getReview() {
		return reviewService.getReviews().get();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Review> getReviewById(@PathVariable("id") int reviewId) throws ReviewIdNotFoundException {
		Review review = reviewService.getReviewById(reviewId).orElseThrow(()-> new ReviewIdNotFoundException("Review not found"));
		return ResponseEntity.ok().body(review);
	}
	
	@PostMapping
	public ResponseEntity<?> createOrUpdateReview(@RequestBody Review review,UriComponentsBuilder uriComponentsBuilder,HttpServletRequest request) {
		Review review2 = reviewService.createOrUpdateReview(review);
		UriComponents uriComponents = uriComponentsBuilder
				.path(request.getRequestURI()+"/{id}")
				.buildAndExpand(review2.getReviewId());
		 return ResponseEntity.created(uriComponents.toUri()).body(review2);
	}
	
	@DeleteMapping("/{id}")
	public Map<String, Boolean> deleteReviewById(@PathVariable int id) throws ReviewIdNotFoundException { 
		reviewService.getReviewById(id).orElseThrow(()-> new ReviewIdNotFoundException("Review not found"));
		reviewService.deleteReview(id);
		HashMap<String, Boolean> hashMap = new HashMap<>();
		hashMap.put("deleted", Boolean.TRUE);
		return hashMap;
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<Review> updateReview(@PathVariable("id") Integer id,
			@Valid @RequestBody Review review ) throws ReviewIdNotFoundException {
		reviewService.getReviewById(id)
				.orElseThrow(()-> new ReviewIdNotFoundException("Review not found"));
		review.setReviewId(id);
		return ResponseEntity.ok(reviewService.createOrUpdateReview(review));
	}
}
