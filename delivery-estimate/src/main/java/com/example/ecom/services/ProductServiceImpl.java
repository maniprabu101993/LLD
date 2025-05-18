package com.example.ecom.services;

import com.example.ecom.exceptions.AddressNotFoundException;
import com.example.ecom.exceptions.ProductNotFoundException;
import com.example.ecom.libraries.GoogleMapsApi;
import com.example.ecom.libraries.adapter.MapsApiAdapter;
import com.example.ecom.libraries.models.GLocation;
import com.example.ecom.models.Address;
import com.example.ecom.models.DeliveryHub;
import com.example.ecom.models.Product;
import com.example.ecom.repositories.*;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private DeliveryHubRepository deliveryHubRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MapsApiAdapter mapsApiadapter;

    public Date estimateDeliveryDate(int productId, int addressId) throws ProductNotFoundException, AddressNotFoundException {
        Address customerAdddress = addressRepository.findById(addressId).orElseThrow(() -> new AddressNotFoundException("Address not found"));
        Product prod = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        List<DeliveryHub> delvieryHib = deliveryHubRepository.findAll();

        Address sellerAddress = prod.getSeller().getAddress();
        double sellerLatitude = sellerAddress.getLatitude();
        double sellerLongitude = sellerAddress.getLongitude();

        double delihubLatitude = 0;
        double delihubLongitude = 0;
        for (DeliveryHub hub : delvieryHib) {
            if (hub.getAddress().getZipCode().equals(sellerAddress.getZipCode())) {
                delihubLatitude = hub.getAddress().getLatitude();
                delihubLongitude = hub.getAddress().getLongitude();
            }
        }

        double customerLatitude = customerAdddress.getLatitude();
        double customerLongitude = customerAdddress.getLongitude();

        GLocation sellerLocation = new GLocation();
        sellerLocation.setLatitude(sellerLatitude);
        sellerLocation.setLongitude(sellerLongitude);

        GLocation deliveryHubLocation = new GLocation();
        deliveryHubLocation.setLatitude(delihubLatitude);
        deliveryHubLocation.setLongitude(delihubLongitude);

        GLocation customerLocation = new GLocation();
        customerLocation.setLongitude(customerLongitude);
        customerLocation.setLatitude(customerLatitude);
        //seller ->  delivery hub
        // delivery hub -> customer
        int secondsTakenToHub = mapsApiadapter.estimate(sellerLocation, deliveryHubLocation);
        int secondsTakenToCustomer = mapsApiadapter.estimate(deliveryHubLocation, customerLocation);

        return new Date(System.currentTimeMillis() + (secondsTakenToHub+ secondsTakenToCustomer* 1000L));
    }
}
