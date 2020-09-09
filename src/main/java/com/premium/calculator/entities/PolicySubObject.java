package com.premium.calculator.entities;

import com.premium.calculator.enums.RiskType;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/*
 * A class for instantiating policy object subobjects for example: TV
 */
public class PolicySubObject {

    private String name;
    private BigDecimal insuredSum;
    private RiskType riskType;

    public PolicySubObject(String name, BigDecimal insuredSum, RiskType riskType) {
        this.name = name;
        this.insuredSum = insuredSum;
        this.riskType = riskType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getInsuredSum() {
        return insuredSum;
    }

    public void setInsuredSum(BigDecimal insuredSum) {
        this.insuredSum = insuredSum;
    }

    public RiskType getRiskType() {
        return riskType;
    }

    public void setRiskType(RiskType riskType) {
        this.riskType = riskType;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }
        final PolicySubObject that = (PolicySubObject) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(insuredSum, that.insuredSum) &&
                Objects.equals(riskType, that.riskType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, insuredSum, riskType);
    }

    @Override
    public String toString() {
        return "PolicySubObject{" +
                "name='" + name + '\'' +
                ", insuredSum=" + insuredSum +
                ", riskType=" + riskType +
                '}';
    }
}
