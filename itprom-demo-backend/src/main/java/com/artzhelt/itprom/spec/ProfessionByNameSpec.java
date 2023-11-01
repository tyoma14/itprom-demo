package com.artzhelt.itprom.spec;

import com.artzhelt.itprom.domain.Profession;
import com.artzhelt.itprom.domain.Profession_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Artyom Zheltyshev on 31.10.2023
 * Спецификация профессий по наименованию
 */
@NoArgsConstructor
@Getter
@Setter
public class ProfessionByNameSpec extends BaseSpecification<Profession> {

    private String name;

    public ProfessionByNameSpec(Long placeId, String name) {
        super(placeId);
        this.name = name;
    }

    @Override
    protected Predicate predicatePart(Root<Profession> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get(Profession_.name), name);
    }
}
