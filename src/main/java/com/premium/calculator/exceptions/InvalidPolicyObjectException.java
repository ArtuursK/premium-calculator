package com.premium.calculator.exceptions;

/**
 * This is custom exception class
 */
public class InvalidPolicyObjectException extends Exception {
    public InvalidPolicyObjectException(String message){
        super(message);
    }
}