package com.cemiltokatli.passwordgenerate;

import java.util.ArrayList;
import java.util.List;

/**
 * An object of this class represents a single password generation process.
 */
public class Password {
    private PasswordType type;
    private int minLength;
    private int maxLength;
    private List<Character> excludedChars;
    private static char[] characters = {
            '~','`','!','@','#','£','€','$','(',')','*','^','&','°','%','§','¥','¢','?','.',',','<','>','\'','"',';',':','/','\\','|','[',']','{','}','=','+','_','-',' ',
            '0','1','2','3','4','5','6','7','8','9',
            'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
    };

    private Password(){
        type = PasswordType.ALL;
        minLength = 5;
        excludedChars = new ArrayList<>();
    }

    private Password(PasswordType type){
        this.type = type;
    }

    private Password(PasswordType type, int minLength){
        this.type = type;
        if(minLength > 0)
            this.minLength = minLength;
    }

    /**
     * Creates a new object with initial values.
     * Initial values:
     *      - type : All
     *      - minLength : 5
     *      - maxLength : 0
     *      - excludedChars : Empty
     */
    public static Password createPassword(){
        return new Password();
    }

    /**
     * Creates a new object with given type and initial values for other attributes.
     *
     * @param type type of the password to be generated
     */
    public static Password createPassword(PasswordType type){
        return new Password(type);
    }

    /**
     * Creates a new object with the given type and initial values for other attributes
     * but it also sets the minimum length of the password.
     *
     * @param type type of the password to be generated
     * @param minLength minimum length of the password to be generated
     */
    public static Password createPassword(PasswordType type, int minLength){
        return new Password(type, minLength);
    }

    /**
     * Sets the password's type.
     *
     * @param type type of the password
     * @return the Password object
     */
    public Password setType(PasswordType type){
        this.type = type;
        return this;
    }

    /**
     * Returns the type of the password.
     *
     * @return type of the password
     */
    public PasswordType getType(){
        return this.type;
    }

    /**
     * Sets the minimum length of the password.
     *
     * @param minLength minimum length of the password
     * @return the Password object
     */
    public Password setMinLength(int minLength){
        if(minLength > 0)
            this.minLength = minLength;

        return this;
    }

    /**
     * Gets the minimum length of the password.
     *
     * @return minimum of the password
     */
    public int getMinLength(){
        return this.minLength;
    }

    /**
     * Sets the maximum length of the password.
     *
     * @param maxLength maximum length of the password.
     * @return the Password object
     */
    public Password setMaxLength(int maxLength){
        if(maxLength > 0)
            this.maxLength = maxLength;

        return this;
    }

    /**
     * Returns the maximum length of the password.
     *
     * @return maximum length of the password.
     */
    public int getMaxLength(){
        return this.maxLength;
    }

    /**
     * Adds the given character in the excluded list.
     * This character is not presented in the password.
     *
     * @param chr character to be excluded
     */
    public void addExcludedChar(char chr){
        if(!this.excludedChars.contains(chr))
            this.excludedChars.add(chr);
    }

    /**
     * Removes the given character from the excluded list.
     * This character may be added in the password.
     *
     * @param chr character to be removed from the excluded list
     */
    public void removeExcludedChar(char chr){
        this.excludedChars.remove(this.excludedChars.indexOf(chr));
    }

    /**
     * Generates the password and returns it.
     *
     * @return generated password
     */
    public String generate(){
        
    }
}
