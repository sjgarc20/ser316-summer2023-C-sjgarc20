/**
 * @file UnderAgeException.java
 * @author Sam
 * @date 6/12/2023
 * 
 * @description for creating under age exception messages
 */

package main.java;

public class UnderAgeException extends Exception {
    public UnderAgeException(String errorMessage) {
        super(errorMessage);
    }
}
