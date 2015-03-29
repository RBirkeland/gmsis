/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GMsis.VehicleRecords;

import java.util.regex.Pattern;
import javafx.stage.Stage;
import org.controlsfx.dialog.DialogStyle;
import org.controlsfx.dialog.Dialogs;

/**
 *
 * @author Rhys Field
 */
public class ErrorChecking {
    
    /**
     * Checks a string to make sure that it is not less than zero or greater than the maximum length
     * Outputs an error message on failure
     * @param name - name of field being checked
     * @param content - content of field to be checked
     * @param length - max length
     * @param dialog - parent stage 
     * @return boolean
     */
    public static boolean checkString(String name, String content, int length, Stage dialog) {
        if(content.trim().length() > length) {
            Dialogs.create()
                    .owner(dialog)
                    .style(DialogStyle.NATIVE)
                    .title("Error")
                    .masthead(name + " length too long")
                    .message("Please enter a shorter " + name + ".")
                    .showError();
            return false;
        } else if(content.length() <= 0) {
            Dialogs.create()
                    .owner(dialog)
                    .style(DialogStyle.NATIVE)
                    .title("Error")
                    .masthead(name + " not entered")
                    .message("Please enter a " + name + ".")
                    .showError();
            return false;
        }
        return true;
    }
    
    /**
     * Checks a string to make sure that it is greater than the maximum length
     * Outputs an error message on failure
     * @param name - name of field being checked
     * @param content - content of field to be checked
     * @param length - max length
     * @param dialog - parent stage 
     * @return boolean
     */
    public static boolean simpleCheckString(String name, String content, int length, Stage dialog) {
        if(content.length() > length) {
            Dialogs.create()
                    .owner(dialog)
                    .style(DialogStyle.NATIVE)
                    .title("Error")
                    .masthead(name + " length too long")
                    .message("Please enter a shorter " + name + ".")
                    .showError();
            return false;
        }
        return true;
    }
    
    /**
     * Checks a given postcode to see if it matches the format of valid UK postcodes
     * Outputs an error message on failure
     * @param postcode - postcode to be checked
     * @param dialog - parent stage
     * @return boolean
     */
    public static boolean postcodeCheck(String postcode, Stage dialog) {
        String postcodePattern = "^[a-zA-Z]{1,2}[0-9][0-9A-Za-z]{0,1} {0,1}[0-9][A-Za-z]{2}$";
        if(postcode.trim().length() <= 0) {
            Dialogs.create()
                    .owner(dialog)
                    .style(DialogStyle.NATIVE)
                    .title("Error")
                    .masthead("No postcode entered")
                    .message("Please enter a postcode.")
                    .showError();
            return false;
        } else if(!postcode.matches(postcodePattern)) {
            Dialogs.create()
                    .owner(dialog)
                    .style(DialogStyle.NATIVE)
                    .title("Error")
                    .masthead("Postcode is not of a valid format")
                    .message("Please enter a valid postcode.")
                    .showError();
            return false;
        }
        return true;
    }
    
    /**
     * Checks a given phone number String to make sure that it is of a valid format
     * Outputs an error message on failure
     * @param phoneNumber - phone number to be checked
     * @param dialog - parent dialog
     * @return boolean
     */
    public static boolean phoneNumberCheck(String phoneNumber, Stage dialog) {
        String phonePattern = "^(([(]?(\\d{2,4})[)]?)|(\\d{2,4})|([+1-9]+\\d{1,2}))?[-\\s]?(\\d{2,3})?[-\\s]?((\\d{7,8})|(\\d{3,4}[-\\s]\\d{3,4}))$";
        if(phoneNumber.trim().length() <= 0) {
            Dialogs.create()
                    .owner(dialog)
                    .style(DialogStyle.NATIVE)
                    .title("Error")
                    .masthead("No phone number entered")
                    .message("Please enter a phone number.")
                    .showError();
            return false;
        } else if(!phoneNumber.matches(phonePattern)) {
            Dialogs.create()
                    .owner(dialog)
                    .style(DialogStyle.NATIVE)
                    .title("Error")
                    .masthead("Phone number is not of a valid format")
                    .message("Please enter a valid phone number.")
                    .showError();
            return false;
        }
        return true;
    }
    
    /**
     * Checks if the engine size given matches the correct format
     * Outputs an error on failure
     * @param engineSize
     * @param dialog
     * @return boolean
     */
    public static boolean engineSizeCheck(String engineSize, Stage dialog) {
        String enginePattern = "^\\d{3,5}\\s[c]{2}$";
        if(engineSize.length() <= 0) {
            Dialogs.create()
                    .owner(dialog)
                    .style(DialogStyle.NATIVE)
                    .title("Error")
                    .masthead("No engine size entered")
                    .message("Please enter an engine size.")
                    .showError();
            return false;
        } else if(!engineSize.matches(enginePattern)) {
            Dialogs.create()
                    .owner(dialog)
                    .style(DialogStyle.NATIVE)
                    .title("Error")
                    .masthead("Engine size is not of a valid format")
                    .message("Please enter an engine size with the format: xxxx cc.")
                    .showError();
            return false;
        }
        return true;
    }
    
    public static boolean checkDigit(String name, String content, int length, Stage dialog) {
        String digitPattern = "[0-9]+";
        if(content.length() <= 0) {
            Dialogs.create()
                    .owner(dialog)
                    .style(DialogStyle.NATIVE)
                    .title("Error")
                    .masthead("No " + name + " entered")
                    .message("Please enter a " + name + ".")
                    .showError();
            return false;
        } else if(!content.matches(digitPattern)) {
            Dialogs.create()
                    .owner(dialog)
                    .style(DialogStyle.NATIVE)
                    .title("Error")
                    .masthead(name + " is not of the correct format")
                    .message("Please ensure that you only use digits.")
                    .showError();
            return false;
        }
        return true;
    }
    
}
