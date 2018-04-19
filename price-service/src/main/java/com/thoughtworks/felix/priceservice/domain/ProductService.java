package com.thoughtworks.felix.priceservice.domain;

import com.thoughtworks.felix.priceservice.infrastructure.ProductClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    public final ProductClient productClient;

    @Autowired
    public ProductService(ProductClient productClient) {
        this.productClient = productClient;
    }

    public boolean isProductExist(Long productId) {
        return productClient.showProduct(productId).getStatusCode().is2xxSuccessful();
    }
}
