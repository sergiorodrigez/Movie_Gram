package main.java.controller;

import main.java.dao.ReviewDAO;
import main.java.domain.Customer;
import main.java.domain.Review;

import java.util.ArrayList;

public class ReviewController {

    public void getReviews(ArrayList<Review> reviews, Customer customer){
        ReviewDAO.getReviews(reviews, customer);
    }

    public void addReview(Review review){
        ReviewDAO.addReview(review);
    }
}
