package com.example.bmsbookticket.services;

import com.example.bmsbookticket.exceptions.MovieNotFoundException;
import com.example.bmsbookticket.exceptions.UserNotFoundException;
import com.example.bmsbookticket.models.Movie;
import com.example.bmsbookticket.models.Rating;
import com.example.bmsbookticket.models.User;
import com.example.bmsbookticket.repositories.MovieRepository;
import com.example.bmsbookticket.repositories.RatingRepository;
import com.example.bmsbookticket.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class RatingServiceImpl implements RatingsService {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UserRepository userRepository;

    public Rating rateMovie(int userId, int movieId, int rating) throws UserNotFoundException, MovieNotFoundException {

        Movie mov = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException("Movie not found"));

        User us = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

        Optional<Rating> tar= ratingRepository.findByMovieAndUser(mov,us);
    
        if(tar.isPresent()){
            Rating temp= tar.get();
            temp.setRating(rating);
            return ratingRepository.save(temp);
        }
         Rating ratingObj = new Rating();
         ratingObj.setRating(rating);
         ratingObj.setMovie(mov);
         ratingObj.setUser(us);

        return ratingRepository.save(ratingObj);
    }

    public double getAverageRating(int movieId) throws MovieNotFoundException {
        double sum = 0.0;
        int count = 0;
        List<Rating> list = ratingRepository.findAll();
        for (Rating rat : list) {
         if (rat.getMovie().getId() == movieId) {
             sum += rat.getRating();
                count++;
            }
        }
        if(count==0){
            throw new MovieNotFoundException("Movie not found for the given id");
        }
        if (count > 0) {
            return (sum / count);
        }
        return 0;
    }
}
