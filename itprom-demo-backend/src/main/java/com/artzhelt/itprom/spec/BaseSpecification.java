package com.artzhelt.itprom.spec;

import com.artzhelt.itprom.domain.BaseEntity;
import com.artzhelt.itprom.domain.BaseEntity_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

/**
 * Created by Artyom Zheltyshev on 31.10.2023
 * Базовая спецификация для сущностей. Задаёт фильтрацию по производственным площадкам.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BaseSpecification<T extends BaseEntity> implements Specification<T> {

    private Long placeId;

    @Override
    public final Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (placeId == null) {
            return predicatePart(root, query, criteriaBuilder);
        } else {
            Predicate placeIdIsNull = criteriaBuilder.isNull(root.get(BaseEntity_.placeId));
            Predicate placeIdEqual = criteriaBuilder.equal(root.get(BaseEntity_.placeId), placeId);
            Predicate basePredicate = criteriaBuilder.or(placeIdIsNull, placeIdEqual);
            return criteriaBuilder.and(basePredicate, predicatePart(root, query, criteriaBuilder));
        }
    }

    protected Predicate predicatePart(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.conjunction();
    }

}
