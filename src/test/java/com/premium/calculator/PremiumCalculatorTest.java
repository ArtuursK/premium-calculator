package com.premium.calculator;

import static org.junit.Assert.assertEquals;

import com.premium.calculator.entities.Policy;
import com.premium.calculator.entities.PolicyObject;
import com.premium.calculator.entities.PolicySubObject;
import com.premium.calculator.enums.PolicyStatus;
import com.premium.calculator.enums.RiskType;
import com.premium.calculator.exceptions.InvalidPolicyObjectException;
import com.premium.calculator.exceptions.InvalidPolicySubObjectException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PremiumCalculatorTest {

    private Policy policy;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void init() {
        policy = new Policy();
        policy.setNumber("LV20-02-100000-5");
        policy.setStatus(PolicyStatus.REGISTERED);
    }

    @Test
    public void calculatePolicy_Positive() throws Exception {

        PolicySubObject robot = new PolicySubObject(
                "Robot Dexter",
                new BigDecimal("100.00"),
                RiskType.FIRE);
        PolicySubObject hat = new PolicySubObject(
                "Cowboy Hat",
                new BigDecimal("8.00"),
                RiskType.THEFT);
        List<PolicySubObject> policySubObjects = new ArrayList<>();
        policySubObjects.add(robot);
        policySubObjects.add(hat);

        PolicyObject countrysideVilla = new PolicyObject("Countryside Villa", policySubObjects);
        List<PolicyObject> policyObjects = new ArrayList<>();
        policyObjects.add(countrysideVilla);

        policy.setPolicyObjects(policyObjects);
        assertEquals(PremiumCalculator.calculate(policy), "2.28 EUR");

    }

    @Test
    public void calculatePolicy_Positive2() throws Exception {

        PolicySubObject coffeeMachine = new PolicySubObject(
                "Espresso",
                new BigDecimal("500.00"),
                RiskType.FIRE);
        PolicySubObject golfEquipment = new PolicySubObject(
                "SLAZENGER",
                new BigDecimal("102.51"),
                RiskType.THEFT);
        List<PolicySubObject> policySubObjects = new ArrayList<>();
        policySubObjects.add(coffeeMachine);
        policySubObjects.add(golfEquipment);

        PolicyObject countrysideVilla = new PolicyObject("Countryside Villa", policySubObjects);
        List<PolicyObject> policyObjects = new ArrayList<>();
        policyObjects.add(countrysideVilla);

        policy.setPolicyObjects(policyObjects);
        assertEquals(PremiumCalculator.calculate(policy), "17.13 EUR");

    }

    @Test
    public void calculatePolicy_Negative_InvalidPolicySubObject() throws Exception {
        expectedEx.expect(InvalidPolicySubObjectException.class);
        expectedEx.expectMessage("Invalid insured sum in policy subobject");

        PolicySubObject robot = new PolicySubObject(
                "Robot Dexter",
                new BigDecimal("-1.00"),
                RiskType.FIRE);
        List<PolicySubObject> policySubObjects = new ArrayList<>();
        policySubObjects.add(robot);

        PolicyObject countrysideVilla = new PolicyObject("Countryside Villa", policySubObjects);
        List<PolicyObject> policyObjects = new ArrayList<>();
        policyObjects.add(countrysideVilla);

        policy.setPolicyObjects(policyObjects);
        PremiumCalculator.calculate(policy);

    }

    @Test
    public void calculatePolicy_Negative_InvalidPolicySubObject2() throws Exception {
        expectedEx.expect(NumberFormatException.class);

        PolicySubObject coffeeMachine = new PolicySubObject(
                "Espresso",
                new BigDecimal("1,1"),
                RiskType.FIRE);
        List<PolicySubObject> policySubObjects = new ArrayList<>();
        policySubObjects.add(coffeeMachine);

        PolicyObject countrysideVilla = new PolicyObject("Countryside Villa", policySubObjects);
        List<PolicyObject> policyObjects = new ArrayList<>();
        policyObjects.add(countrysideVilla);

        policy.setPolicyObjects(policyObjects);

        PremiumCalculator.calculate(policy);
    }

    @Test
    public void calculatePolicy_Negative_MissingPolicySubObjects() throws Exception {

        PolicyObject countrysideVilla = new PolicyObject("Countryside Villa", null);
        List<PolicyObject> policyObjects = new ArrayList<>();
        policyObjects.add(countrysideVilla);

        policy.setPolicyObjects(policyObjects);
        assertEquals(PremiumCalculator.calculate(policy), "0.00 EUR");
    }

    @Test
    public void calculatePolicy_Negative_MissingPolicyObjects() throws Exception {
        expectedEx.expect(InvalidPolicyObjectException.class);
        expectedEx.expectMessage("No policy objects defined");

        policy.setPolicyObjects(null);
        PremiumCalculator.calculate(policy);
    }

    @Test
    public void calculatePolicy_Negative_MissingPolicy() throws Exception {
        expectedEx.expect(NullPointerException.class);
        expectedEx.expectMessage("No policy defined");

        PremiumCalculator.calculate(null);
    }


}
