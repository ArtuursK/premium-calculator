package com.premium.calculator.enums;

import java.math.BigDecimal;

public enum RiskType {

    FIRE {
        @Override
        public BigDecimal calculateCoefficient(BigDecimal totalSumInsured) {
            final BigDecimal DEFAULT_COEFFICIENT = new BigDecimal("0.014");
            final BigDecimal COEFFICIENT = new BigDecimal("0.024");
            final BigDecimal SUMINSURED_THRESHOLD = new BigDecimal("100.00");

            return totalSumInsured.compareTo(SUMINSURED_THRESHOLD) > 0 ?
                    COEFFICIENT : DEFAULT_COEFFICIENT;
        }
    },
    THEFT {
        @Override
        public BigDecimal calculateCoefficient(BigDecimal totalSumInsured) {
            final BigDecimal DEFAULT_COEFFICIENT = new BigDecimal("0.11");
            final BigDecimal COEFFICIENT = new BigDecimal("0.05");
            final BigDecimal SUMINSURED_THRESHOLD = new BigDecimal("15.00");

            return totalSumInsured.compareTo(SUMINSURED_THRESHOLD) >= 0 ?
                    COEFFICIENT : DEFAULT_COEFFICIENT;
        }
    };

    public abstract BigDecimal calculateCoefficient(BigDecimal totalSumInsured);

}
