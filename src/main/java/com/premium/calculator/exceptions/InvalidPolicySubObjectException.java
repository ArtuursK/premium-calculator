package com.premium.calculator.exceptions;

/**
 * This is custom exception class
 */
public class InvalidPolicySubObjectException extends Exception {
    public InvalidPolicySubObjectException(String message){
        super(message);
    }
}