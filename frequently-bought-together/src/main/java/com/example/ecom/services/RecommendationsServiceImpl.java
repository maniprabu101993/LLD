package com.example.ecom.services;

import com.example.ecom.exceptions.ProductNotFoundException;
import com.example.ecom.models.Product;
import com.example.ecom.models.ProductGroup;
import com.example.ecom.repositories.ProductGroupsRepository;
import com.example.ecom.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RecommendationsServiceImpl implements RecommendationsService {
    ProductRepository productRepository;
    ProductGroupsRepository productGroupsRepository;

    public RecommendationsServiceImpl(ProductRepository productRepository, ProductGroupsRepository productGroupsRepository) {
        this.productRepository = productRepository;
        this.productGroupsRepository = productGroupsRepository;
    }

    @Override
    public List<Product> getRecommendations(int productId) throws ProductNotFoundException {
        Product pr = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        List<ProductGroup> list = productGroupsRepository.findAll();
        Set<Product> productSet = new HashSet<>();
        for (ProductGroup group : list) {
            if (group.getProducts().contains(pr)) {
                productSet.addAll(group.getProducts());
            }
        }
        productSet.remove(pr);
        return new ArrayList<>(productSet);
    }
}
