package com.cemiltokatli.passwordgenerate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * An object of this class represents a single password generation process.
 */
public class Password {
    private PasswordType type;
    private int minLength;
    private int maxLength;
    private List<Character> excludedChars;

    private Password(){
        type = PasswordType.ALL;
        minLength = 5;
        maxLength = 10;
        excludedChars = new ArrayList<>();
    }

    private Password(PasswordType type){
        this();
        this.type = type;
    }

    private Password(PasswordType type, int minLength){
        this();
        this.type = type;

        if(minLength > 0)
            this.minLength = minLength;
    }

    private Password(PasswordType type, int minLength, int maxLength){
        this();
        this.type = type;

        if(minLength > 0)
            this.minLength = minLength;

        if(maxLength > 0)
            this.maxLength = maxLength;
    }

    /**
     * Creates a new object with initial values.
     * Initial values:
     *      - type : All
     *      - minLength : 5
     *      - maxLength : 10
     *      - excludedChars : Empty
     *
     * @return the created object
     */
    public static Password createPassword(){
        return new Password();
    }

    /**
     * Creates a new object with given type and initial values for other attributes.
     *
     * @param type type of the password to be generated
     * @return the created object
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
     * @return the created object
     */
    public static Password createPassword(PasswordType type, int minLength){
        return new Password(type, minLength);
    }

    /**
     *
     * @param type type of the password to be generated
     * @param minLength minimum length of the password to be generated
     * @param maxLength maximum length of the password to be generated
     * @return the created object
     */
    public static Password createPassword(PasswordType type, int minLength, int maxLength){
        return new Password(type, minLength, maxLength);
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
     * @return the Password object
     */
    public Password addExcludedChar(char chr){
        if(!this.excludedChars.contains(chr))
            this.excludedChars.add(chr);

        return this;
    }

    /**
     * Adds the given characters in the excluded list.
     *
     * @param chrs Characters to be excluded
     * @return the Password object
     */
    public Password addExcludedChars(char[] chrs){
        for(char c : chrs)
            addExcludedChar(c);

        return this;
    }

    /**
     * Removes the given character from the excluded list.
     * This character may be added in the password.
     *
     * @param chr character to be removed from the excluded list
     * @return the Password object
     */
    public Password removeExcludedChar(char chr){
        this.excludedChars.remove(this.excludedChars.indexOf(chr));

        return this;
    }

    /**
     * Generates a password and returns it.
     *
     * @return generated password
     */
    public String generate(){
        Random rand = new Random();

        //Length of the password to be created
        int length;
        if(minLength == maxLength) //Fixed length
            length = minLength;
        else{
            length = rand.nextInt((maxLength - minLength) + 1) + minLength;
        }

        //Generate
        StringBuilder generatedPassword = new StringBuilder();
        Character lastChar = null;

        for(int i = 0; i < length; i++){
            lastChar= type.getRandomChar(excludedChars, lastChar);
            generatedPassword.append(lastChar);
        }

        return generatedPassword.toString();
    }
}
