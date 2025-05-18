package com.example.shortenurl.services;

import com.example.shortenurl.exceptions.UrlNotFoundException;
import com.example.shortenurl.exceptions.UserNotFoundException;
import com.example.shortenurl.models.ShortenedUrl;
import com.example.shortenurl.models.UrlAccessLog;
import com.example.shortenurl.models.User;
import com.example.shortenurl.models.UserPlan;
import com.example.shortenurl.repositories.ShortenedUrlRepository;
import com.example.shortenurl.repositories.UrlAccessLogRepository;
import com.example.shortenurl.repositories.UserRepository;
import com.example.shortenurl.utils.ShortUrlGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UrlServiceImpl implements UrlService{

    @Autowired
    ShortenedUrlRepository shortUrlRepo;

    @Autowired
    UrlAccessLogRepository urlRepo;

    @Autowired
    UserRepository userRepo;

    @Override
    public ShortenedUrl shortenUrl(String url, int userId) throws UserNotFoundException {
        Optional<User> userOptional = userRepo.findById(userId);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User not found!");
        }

        int day = 0;
        if(userOptional.get().getUserPlan().equals(UserPlan.FREE)){
            day = 1;
        } else if (userOptional.get().getUserPlan().equals(UserPlan.TEAM)) {
            day = 7;
        } else if (userOptional.get().getUserPlan().equals(UserPlan.BUSINESS)) {
            day = 30;
        } else if (userOptional.get().getUserPlan().equals(UserPlan.ENTERPRISE)) {
            day = 365;
        }

        long planTime = (long) day * 24 * 60 * 60 * 1000;

        ShortenedUrl shortenedUrl = new ShortenedUrl();
        shortenedUrl.setOriginalUrl(url);
        shortenedUrl.setShortUrl(ShortUrlGenerator.generateShortUrl());
        shortenedUrl.setExpiresAt(System.currentTimeMillis()+planTime);
        shortenedUrl.setUser(userOptional.get());
        return shortUrlRepo.save(shortenedUrl);
    }

    @Override
    public String resolveShortenedUrl(String shortUrl) throws UrlNotFoundException {
        Optional<ShortenedUrl> urlOptional = shortUrlRepo.findByShortUrl(shortUrl);
        if(urlOptional.isEmpty()){
            throw new UrlNotFoundException("Url not found!");
        }

        ShortenedUrl registeredUrl = urlOptional.get();

        if(registeredUrl.getExpiresAt() - System.currentTimeMillis() <0){
            throw new UrlNotFoundException("Url has been expired.!");
        }

        if(registeredUrl.getShortUrl().equals(shortUrl)){
            UrlAccessLog urlAccessLog = new UrlAccessLog();
            urlAccessLog.setShortenedUrl(registeredUrl);
            urlAccessLog.setAccessedAt(System.currentTimeMillis());
            urlRepo.save(urlAccessLog);
        }
        return registeredUrl.getOriginalUrl();
    }
}
