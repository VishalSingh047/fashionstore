package com.fashionstore.fashionstore.specification;

import com.fashionstore.fashionstore.entity.Product;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {

    public static Specification<Product> filterProducts(
            String query,
            String category,
            String brand,
            String size,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Boolean inStock,
            Boolean featured,
            Boolean newArrival
    ) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Active products only by default
            predicates.add(criteriaBuilder.equal(root.get("active"), true));

            // Search query (keyword match on name, description, category, brand)
            if (query != null && !query.trim().isEmpty()) {
                String pattern = "%" + query.trim().toLowerCase() + "%";
                Predicate nameLike = criteriaBuilder.like(criteriaBuilder.lower(root.get("productName")), pattern);
                Predicate descLike = criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), pattern);
                Predicate categoryLike = criteriaBuilder.like(criteriaBuilder.lower(root.get("category")), pattern);
                Predicate brandLike = criteriaBuilder.like(criteriaBuilder.lower(root.get("brand")), pattern);

                predicates.add(criteriaBuilder.or(nameLike, descLike, categoryLike, brandLike));
            }

            // Category Filter
            if (category != null && !category.trim().isEmpty()) {
                predicates.add(criteriaBuilder.equal(
                        criteriaBuilder.lower(root.get("category")),
                        category.trim().toLowerCase()
                ));
            }

            // Brand Filter
            if (brand != null && !brand.trim().isEmpty()) {
                predicates.add(criteriaBuilder.equal(
                        criteriaBuilder.lower(root.get("brand")),
                        brand.trim().toLowerCase()
                ));
            }

            // Size Filter
            if (size != null && !size.trim().isEmpty()) {
                predicates.add(criteriaBuilder.equal(
                        criteriaBuilder.lower(root.get("size")),
                        size.trim().toLowerCase()
                ));
            }

            // Min Price
            if (minPrice != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
            }

            // Max Price
            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
            }

            // In Stock Filter
            if (Boolean.TRUE.equals(inStock)) {
                predicates.add(criteriaBuilder.equal(root.get("soldOut"), false));
                predicates.add(criteriaBuilder.greaterThan(root.get("stock"), 0));
            }

            // Featured Filter
            if (featured != null) {
                predicates.add(criteriaBuilder.equal(root.get("featured"), featured));
            }

            // New Arrival Filter
            if (newArrival != null) {
                predicates.add(criteriaBuilder.equal(root.get("newArrival"), newArrival));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
