package com.premium.calculator;


import com.premium.calculator.entities.Policy;
import com.premium.calculator.entities.PolicyObject;
import com.premium.calculator.entities.PolicySubObject;
import com.premium.calculator.enums.RiskType;
import com.premium.calculator.exceptions.InvalidPolicyObject;
import com.premium.calculator.exceptions.InvalidPolicySubObject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class PremiumCalculator {

    /**
     * This is the main method which calculates policy premium value.
     * @param policy - the policy for which the premium calculation is made.
     * @return String - calculated premium value and it's currency.
     * @exception InvalidPolicyObject thrown if policy object is invalid
     * @exception InvalidPolicySubObject thrown if policy subobject is invalid
     */
    public static String calculate(Policy policy)
            throws InvalidPolicySubObject, InvalidPolicyObject {

        if(policy == null)
            throw new NullPointerException("No policy defined");

        if(policy.getPolicyObjects() == null)
            throw new InvalidPolicyObject("No policy objects defined");

        BigDecimal policyPremiumTotal = BigDecimal.ZERO;

        for (PolicyObject policyObject : policy.getPolicyObjects()){
            if(policyObject.getPolicySubObjects() != null){
                validatePolicySubObjects(policyObject.getPolicySubObjects());
                policyPremiumTotal = policyPremiumTotal.add(calculateRiskTypePremium(policyObject.getPolicySubObjects()));
            }
        }
        //round the value to two decimal places and return it
        return policyPremiumTotal.setScale(2, RoundingMode.HALF_EVEN).toString();
    }

    /**
     * This method validates if the subobject sum insured values are not negative.
     * @param policySubObjects - list of policy object subobjects.
     * @exception InvalidPolicySubObject thrown if policy subobject is invalid
     */
    public static void validatePolicySubObjects(List<PolicySubObject> policySubObjects)
            throws InvalidPolicySubObject {

        for (PolicySubObject policySubObject : policySubObjects){
            if(policySubObject.getInsuredSum().compareTo(BigDecimal.ZERO) < 0){
                throw new InvalidPolicySubObject("Invalid insured sum in policy subobject");
            }
        }

    }

    /**
     * This method calculates sum of each risk type within policy subobjects.
     * @param policySubObjects - list of policy object subobjects.
     * @return BigDecimal - calculated premium sum of all risk types.
     */
    private static BigDecimal calculateRiskTypePremium(List<PolicySubObject> policySubObjects){

        BigDecimal riskTypePremiumTotal = BigDecimal.ZERO;

        for (RiskType riskType : RiskType.values()){
            BigDecimal totalSumInsured = calculateTotalSumInsured(policySubObjects, riskType);
            BigDecimal coeficient = riskType.calculateCoefficient(totalSumInsured);
            riskTypePremiumTotal = riskTypePremiumTotal.add(totalSumInsured.multiply(coeficient));
        }

        return riskTypePremiumTotal;
    }

    /**
     * This method calculates total sum insured of all policy's sub-objects with the provided riskType.
     * @param policySubObjects - list of policy object subobjects.
     * @param riskType - riskType: FIRE, THEFT, ...
     * @return BigDecimal - sum insured of all subobjects with the provided riskType.
     */
    private static BigDecimal calculateTotalSumInsured(List<PolicySubObject> policySubObjects, RiskType riskType){
        return policySubObjects.stream()
                .filter(policySubObject -> riskType.equals(policySubObject.getRiskType()))
                .map(PolicySubObject::getInsuredSum)
                .filter(insuredSum ->
                        (insuredSum != null) &&
                        (insuredSum.compareTo(BigDecimal.ZERO) >= 0)
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
