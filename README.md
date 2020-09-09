# Sample premium calculator application for insurance purposes

### A sample application which calculates premium price 
based on the sub object risk types and their insured sums

Built :
* using the build tool: gradle
* with Java version 8
* with JUnit 4

#### Implementation description:


* ##### calculate() method:

    * The calculation process takes place when the "calculate" method 
    in PremiumCalculator class is called with the Policy
    as method parameter.
    
        The policy can contain multiple policy objects and each
        policy object can contain multiple subobjects.
        The corresponding entitiy classes and their relations 
        can be seen in the "entities" package
    
    * The method "calculate" goes through each policyobject 
    within the given policy and sums up all the sumInsured values
    within each policy subobject
    
* ##### validatePolicySubObjects() method:
    
    * validates that none of the policy subobjects contains
    negative values and throws an exception accordingly
    
* ##### calculateRiskTypePremium() method:
    
    * calculates sum of each risk type within policy subobjects.
    
* ##### calculateTotalSumInsured() method:
    
    * calculates the total sum insured of all subobjects
    for the given risk type 
    
* ##### calculateCoefficient() method:
    
    * calculates the coefficient from the given risk type, and 
    the given sum insured
    
    
    

  

 
