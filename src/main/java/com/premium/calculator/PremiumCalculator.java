package com.premium.calculator;


import com.premium.calculator.entities.Policy;
import com.premium.calculator.entities.PolicyObject;
import com.premium.calculator.entities.PolicySubObject;
import com.premium.calculator.enums.RiskType;
import com.premium.calculator.exceptions.InvalidPolicyObjectException;
import com.premium.calculator.exceptions.InvalidPolicySubObjectException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class PremiumCalculator {

    private static final BigDecimal COEFFICIENT_THEFT_DEFAULT = new BigDecimal("0.11");
    private static final BigDecimal COEFFICIENT_THEFT_EXCEPTION = new BigDecimal("0.05");
    private static final BigDecimal COEFFICIENT_THEFT_THRESHOLD = new BigDecimal("15.00");

    private static final BigDecimal COEFFICIENT_FIRE_DEFAULT = new BigDecimal("0.014");
    private static final BigDecimal COEFFICIENT_FIRE_EXCEPTION = new BigDecimal("0.024");
    private static final BigDecimal COEFFICIENT_FIRE_THRESHOLD = new BigDecimal("100.00");

    private static final String CURRENCY = " EUR";

    /**
     * This is the main method which calculates policy premium value.
     * @param policy - the policy for which the premium calculation is made.
     * @return String - calculated premium value and it's currency.
     * @exception InvalidPolicyObjectException thrown if policy object is invalid
     * @exception InvalidPolicySubObjectException thrown if policy subobject is invalid
     */
    public static String calculate(Policy policy)
            throws InvalidPolicySubObjectException, InvalidPolicyObjectException {

        if(policy == null)
            throw new NullPointerException("No policy defined");

        if(policy.getPolicyObjects() == null)
            throw new InvalidPolicyObjectException("No policy objects defined");

        BigDecimal policyPremiumTotal = BigDecimal.ZERO;

        //go through each policy object and add the calculated risk type premium to policy premium
        for (PolicyObject policyObject : policy.getPolicyObjects()){
            if(policyObject.getPolicySubObjects() != null){
                //verify that each policy subobject is valid
                validatePolicySubObjects(policyObject.getPolicySubObjects());
                policyPremiumTotal = policyPremiumTotal.add(calculateRiskTypePremium(policyObject.getPolicySubObjects()));
            }
        }
        //round the value to two decimal places and return it together with currency
        return policyPremiumTotal.setScale(2, RoundingMode.HALF_EVEN).toString() + CURRENCY;
    }

    /**
     * This method validates if the subobject sum insured values are not negative.
     * @param policySubObjects - list of policy object subobjects.
     * @exception InvalidPolicySubObjectException thrown if policy subobject is invalid
     */
    public static void validatePolicySubObjects(List<PolicySubObject> policySubObjects)
            throws InvalidPolicySubObjectException {

        for (PolicySubObject policySubObject : policySubObjects){
            if(policySubObject.getInsuredSum().compareTo(BigDecimal.ZERO) < 0){
                throw new InvalidPolicySubObjectException("Invalid insured sum in policy subobject");
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
            BigDecimal coeficient = calculateCoefficient(totalSumInsured, riskType);
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
                .filter(policySubObject -> policySubObject.getRiskType().equals(riskType))
                .map(PolicySubObject::getInsuredSum)
                .filter(insuredSum ->
                        (insuredSum != null) &&
                        (insuredSum.compareTo(BigDecimal.ZERO) >= 0)
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * This method calculates risk type coefficient depending on total sum insured.
     * NOTE: More RiskType coeficient calculations can be added here as needed.
     * @param totalSumInsured - sum insured of all subobjects with the provided riskType.
     * @param riskType - riskType: FIRE, THEFT, ...
     * @return BigDecimal - coeficient for the provided riskType.
     */
    private static BigDecimal calculateCoefficient(BigDecimal totalSumInsured, RiskType riskType){

        BigDecimal coeficient;
        switch (riskType){
            case FIRE:
                coeficient = totalSumInsured.compareTo(COEFFICIENT_FIRE_THRESHOLD) > 0 ?
                        COEFFICIENT_FIRE_EXCEPTION : COEFFICIENT_FIRE_DEFAULT;
                break;
            case THEFT:
                coeficient = totalSumInsured.compareTo(COEFFICIENT_THEFT_THRESHOLD) >= 0 ?
                        COEFFICIENT_THEFT_EXCEPTION : COEFFICIENT_THEFT_DEFAULT;
                break;
            default:
                coeficient = BigDecimal.ONE;

        }
        return coeficient;
    }

}
