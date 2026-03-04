package com.bussinessmanagement.Specifications;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.bussinessmanagement.managementSystem.Models.Event;
import com.bussinessmanagement.managementSystem.enums.EventStatus;

public class EventSpecification {

    public static Specification<Event> filtroDinamico(
            String nome,
            LocalDate data,
            EventStatus estado
    ) {
        return (root, query, criteriaBuilder) -> {

            var predicates = criteriaBuilder.conjunction();

            if (nome != null && !nome.isEmpty()) {
                predicates = criteriaBuilder.and(
                        predicates,
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("name")),
                                "%" + nome.toLowerCase() + "%"
                        )
                );
            }

            if (data != null) {
                predicates = criteriaBuilder.and(
                        predicates,
                        criteriaBuilder.equal(root.get("date"), data)
                );
            }

            if (estado != null) {
                predicates = criteriaBuilder.and(
                        predicates,
                        criteriaBuilder.equal(root.get("estado"), estado)
                );
            }

            return predicates;
        };
    }
}