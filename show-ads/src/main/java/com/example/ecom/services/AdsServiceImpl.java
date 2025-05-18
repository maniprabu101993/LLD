package com.example.ecom.services;

import com.example.ecom.exceptions.UserNotFoundException;
import com.example.ecom.models.Advertisement;
import com.example.ecom.models.Preference;
import com.example.ecom.models.User;
import com.example.ecom.repositories.AdvertisementRepository;
import com.example.ecom.repositories.PreferencesRepository;
import com.example.ecom.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdsServiceImpl implements AdsService {
    private AdvertisementRepository advertisementRepository;
    private PreferencesRepository preferencesRepository;
    private UserRepository userRepository;

    public AdsServiceImpl(AdvertisementRepository advertisementRepository,
                          PreferencesRepository preferencesRepository,
                          UserRepository userRepository) {
        this.advertisementRepository = advertisementRepository;
        this.preferencesRepository = preferencesRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Advertisement getAdvertisementForUser(int userId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        List<Preference> list = user.getPreferences();
        for (Preference pref : list) {
            List<Advertisement> ads = advertisementRepository.findByPreferenceId(pref.getId());
            if (!ads.isEmpty()) {
                return ads.get(0);
            }
        }
        return advertisementRepository.findAll().get(0);
    }
}
