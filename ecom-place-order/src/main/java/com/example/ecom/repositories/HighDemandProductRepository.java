package com.example.ecom.repositories;

import com.example.ecom.models.HighDemandProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HighDemandProductRepository extends JpaRepository<HighDemandProduct, Integer> {

    Optional<HighDemandProduct> findByProductId(Integer product);

}
