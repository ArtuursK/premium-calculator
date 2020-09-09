package com.premium.calculator.entities;

import com.premium.calculator.enums.PolicyStatus;

import java.util.List;
import java.util.Objects;

/*
 * A class for instantiating policies
 */
public class Policy {

    private String number;
    private PolicyStatus status;
    private List<PolicyObject> policyObjects;

    public Policy(String number, PolicyStatus status,
            List<PolicyObject> policyObjects) {
        this.number = number;
        this.status = status;
        this.policyObjects = policyObjects;
    }

    public Policy() { }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public PolicyStatus getStatus() {
        return status;
    }

    public void setStatus(PolicyStatus status) {
        this.status = status;
    }

    public List<PolicyObject> getPolicyObjects() {
        return policyObjects;
    }

    public void setPolicyObjects(List<PolicyObject> policyObjects) {
        this.policyObjects = policyObjects;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }
        final Policy that = (Policy) o;
        return Objects.equals(number, that.number) &&
                Objects.equals(status, that.status) &&
                Objects.equals(policyObjects, that.policyObjects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, status, policyObjects);
    }

    @Override
    public String toString() {
        return "Policy{" +
                "number='" + number + '\'' +
                ", status=" + status +
                ", policyObjects=" + policyObjects +
                '}';
    }
}
