package fr.uga.laguidonette.service.impl;

import fr.uga.laguidonette.domain.Product;
import fr.uga.laguidonette.domain.enumeration.Brand;
import fr.uga.laguidonette.domain.enumeration.Color;
import fr.uga.laguidonette.repository.ProductRepository;
import fr.uga.laguidonette.service.ProductService;
import fr.uga.laguidonette.service.dto.GetProductsPageResponseDto;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Product}.
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product save(Product product) {
        log.debug("Request to save Product : {}", product);
        return productRepository.save(product);
    }

    @Override
    public Product update(Product product) {
        log.debug("Request to update Product : {}", product);
        return productRepository.save(product);
    }

    @Override
    public List<Product> search(String query) {
        return productRepository.search(query);
    }

    @Override
    public Optional<Product> partialUpdate(Product product) {
        log.debug("Request to partially update Product : {}", product);

        return productRepository
            .findById(product.getId())
            .map(existingProduct -> {
                if (product.getLabel() != null) {
                    existingProduct.setLabel(product.getLabel());
                }
                if (product.getDescription() != null) {
                    existingProduct.setDescription(product.getDescription());
                }
                if (product.getPrice() != null) {
                    existingProduct.setPrice(product.getPrice());
                }
                if (product.getBrand() != null) {
                    existingProduct.setBrand(product.getBrand());
                }
                if (product.getModel() != null) {
                    existingProduct.setModel(product.getModel());
                }
                if (product.getColor() != null) {
                    existingProduct.setColor(product.getColor());
                }
                if (product.getQuantity() != null) {
                    existingProduct.setQuantity(product.getQuantity());
                }
                if (product.getImageName() != null) {
                    existingProduct.setImageName(product.getImageName());
                }
                if (product.getVersion() != null) {
                    existingProduct.setVersion(product.getVersion());
                }

                return existingProduct;
            })
            .map(productRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        log.debug("Request to get all Products");
        return productRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> filterProducts(List<String> categories, List<Color> colors, List<Brand> brands) {
        return productRepository.filterProducts(categories, colors, brands);
    }

    @Override
    @Transactional(readOnly = true)
    public GetProductsPageResponseDto getProductPage(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productsPage = productRepository.findAll(pageable);
        List<Product> products = productsPage.getContent();
        GetProductsPageResponseDto getProductsPageResponseDto = new GetProductsPageResponseDto();
        getProductsPageResponseDto.setProducts(products);
        getProductsPageResponseDto.setPage(productsPage.getNumber());
        getProductsPageResponseDto.setSize(productsPage.getSize());
        getProductsPageResponseDto.setLast(productsPage.isLast());
        getProductsPageResponseDto.setTotalPages(productsPage.getTotalPages());
        getProductsPageResponseDto.setTotalProducts(productsPage.getTotalElements());
        return getProductsPageResponseDto;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findOne(Long id) {
        log.debug("Request to get Product : {}", id);
        return productRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Product : {}", id);
        productRepository.deleteById(id);
    }
}
