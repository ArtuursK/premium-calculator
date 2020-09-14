package com.premium.calculator.exceptions;

/**
 * This is custom exception class
 */
public class InvalidPolicySubObject extends Exception {
    public InvalidPolicySubObject(String message){
        super(message);
    }
}