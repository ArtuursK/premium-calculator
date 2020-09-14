package com.premium.calculator.entities;

import java.util.List;
import java.util.Objects;

/*
 * A class for instantiating policy objects for example: House
 */
public class PolicyObject {

    private String name;
    private List<PolicySubObject> policySubObjects;

    public PolicyObject(String name, List<PolicySubObject> policySubObjects) {
        this.name = name;
        this.policySubObjects = policySubObjects;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PolicySubObject> getPolicySubObjects() {
        return policySubObjects;
    }

    public void setPolicySubObjects(List<PolicySubObject> policySubObjects) {
        this.policySubObjects = policySubObjects;
    }

    @Override
    public boolean equals(Object o) {
        if (! super.equals(o)) {
            return false;
        }
        final PolicyObject that = (PolicyObject) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(policySubObjects, that.policySubObjects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, policySubObjects);
    }

    @Override
    public String toString() {
        return "PolicyObject{" +
                "name='" + name +
                ", policySubObjects=" + policySubObjects +
                '}';
    }
}
