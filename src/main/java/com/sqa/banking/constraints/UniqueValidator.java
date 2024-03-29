package com.sqa.banking.constraints;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueValidator implements ConstraintValidator<Unique, Object> {
    @PersistenceContext
    private EntityManager entityManager;

    private String fieldName;
    private String table;

    @Override
    public void initialize(Unique constraintAnnotation) {
        this.fieldName = constraintAnnotation.fieldName();
        this.table = constraintAnnotation.table();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        String queryString = "SELECT COUNT(*) FROM " + table + " WHERE " + fieldName + " = :value";
        Long count = entityManager.createQuery(queryString, Long.class)
                .setParameter("value", value)
                .getSingleResult();

        return count == 0;
    }
}
