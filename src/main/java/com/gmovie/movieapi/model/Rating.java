package com.gmovie.movieapi.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Rating
{
    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    @Id
    String ratingId;
    String rating;
    String review;

    public Rating() {
    }

    public Rating(String rating, String review) {
        this.ratingId = UUID.randomUUID().toString();
        this.rating = rating;
        this.review = review;
    }
}
