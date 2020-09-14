package com.premium.calculator.exceptions;

/**
 * This is custom exception class
 */
public class InvalidPolicyObject extends Exception {
    public InvalidPolicyObject(String message){
        super(message);
    }
}