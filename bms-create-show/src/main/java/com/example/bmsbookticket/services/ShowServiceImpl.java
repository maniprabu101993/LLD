package com.example.bmsbookticket.services;

import com.example.bmsbookticket.exceptions.*;
import com.example.bmsbookticket.models.*;
import com.example.bmsbookticket.repositories.*;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ShowServiceImpl implements ShowService {
    MovieRepository movieRepository;
    ScreenRepository screenRepository;
    SeatTypeShowRepository seatTypeShowRepository;
    SeatsRepository seatsRepository;
    ShowRepository showRepository;
    UserRepository userRepository;
    ShowSeatRepository showSeatRepository;

    public ShowServiceImpl(MovieRepository movieRepository, ScreenRepository screenRepository,
                           SeatTypeShowRepository seatTypeShowRepository, SeatsRepository seatsRepository,
                           ShowRepository showRepository, UserRepository userRepository, ShowSeatRepository showSeatRepository) {
        this.movieRepository = movieRepository;
        this.screenRepository = screenRepository;
        this.seatTypeShowRepository = seatTypeShowRepository;
        this.seatsRepository = seatsRepository;
        this.showRepository = showRepository;
        this.userRepository = userRepository;
        this.showSeatRepository = showSeatRepository;
    }

    @Override
    public Show createShow(int userId, int movieId, int screenId, Date startTime, Date endTime, List<Pair<SeatType, Double>> pricingConfig, List<Feature> features) throws MovieNotFoundException, ScreenNotFoundException, FeatureNotSupportedByScreen, InvalidDateException, UserNotFoundException, UnAuthorizedAccessException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Not found user"));
        if (user.getUserType() != UserType.ADMIN) {
            throw new UnAuthorizedAccessException("User is not authorized to create show");
        }

        Screen screen = screenRepository.findById(screenId).orElseThrow(() -> new ScreenNotFoundException("Screen not found"));
        for (Feature feat : features) {
            if (!screen.getFeatures().contains(feat)) {
                throw new FeatureNotSupportedByScreen("Feature not supported by screen");
            }
        }
        if (endTime.compareTo(startTime) <= 0) {
            throw new InvalidDateException("Endtime should be greater than start time");
        }
        if (startTime.compareTo(new Date()) < 0) {
            throw new InvalidDateException("Start time should be greater than current time");
        }
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException("Movie not found"));
        Show show = new Show();
        show.setMovie(movie);
        show.setStartTime(startTime);
        show.setEndTime(endTime);
        show.setScreen(screen);
        show.setFeatures(features);
        show = showRepository.save(show);

        for (Pair<SeatType, Double> pair : pricingConfig) {
            SeatTypeShow seatTypeShow = new SeatTypeShow();
            seatTypeShow.setShow(show);
            seatTypeShow.setSeatType(pair.getFirst());
            seatTypeShow.setPrice(pair.getSecond());
            seatTypeShowRepository.save(seatTypeShow);
        }
        for(Seat seat:screen.getSeats()){
            ShowSeat showSeat = new ShowSeat();
            showSeat.setShow(show);
            showSeat.setStatus(SeatStatus.AVAILABLE);
            showSeat.setSeat(seat);
            showSeatRepository.save(showSeat);
        }

        return show;
    }
}
