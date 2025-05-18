package com.example.ecom.repositories;

import com.example.ecom.models.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Integer> {
    List<Advertisement> findByPreferenceId(int preferenceId);
}
