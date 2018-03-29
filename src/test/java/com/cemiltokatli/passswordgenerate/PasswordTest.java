package com.cemiltokatli.passswordgenerate;

import com.cemiltokatli.passwordgenerate.Password;
import com.cemiltokatli.passwordgenerate.PasswordType;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.io.File;
import java.util.regex.Pattern;

/**
 * This class is designed for testing the Password class under the "com.cemiltokatli.passwordgenerate" package.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PasswordTest {
    private List<PasswordGenerate> testData = new ArrayList<>();

    @BeforeAll
    public void initAll(){
        readTestData("PasswordTestData", testData);
    }

    /**
     * Reads the test data from the given file and assign it to the given source array
     *
     * @param fileName
     * @param source
     */
    private void readTestData(String fileName, List<PasswordGenerate> source){
        //Read the test data
        try{
            //Read file
            ClassLoader classLoader = getClass().getClassLoader();
            File testFile = new File(classLoader.getResource(fileName+".json").getFile());
            byte[] byteData = Files.readAllBytes(testFile.toPath());
            String data = new String(byteData, "utf-8");

            //Parse JSON
            JSONObject parsedData = new JSONObject(data);
            JSONArray items = parsedData.getJSONArray("data");
            JSONObject item;
            PasswordGenerate password;
            String type;

            //Set data
            for(int i = 0; i < items.length(); i++){
                item = items.getJSONObject(i);
                password = new PasswordGenerate();

                //Minimum Length
                password.minLength = item.getInt("min");

                //Maximum Length
                password.maxLength = item.getInt("max");

                //Type
                type = item.getString("type");
                if(type.equals("all")){
                    password.type = PasswordType.ALL;
                }
                else if(type.equals("alpha")){
                    password.type = PasswordType.ALPHA;
                }
                else if(type.equals("numeric")){
                    password.type = PasswordType.NUMERIC;
                }
                else if(type.equals("alphanumeric")){
                    password.type = PasswordType.ALPHANUMERIC;
                }
                else if(type.equals("symbols")){
                    password.type = PasswordType.SYMBOLS;
                }

                //Excluded Chars
                JSONArray excluded = item.getJSONArray("excluded");
                password.excluded = new Character[excluded.length()];
                for(int j = 0; j <  excluded.length(); j++){
                    password.excluded[j] = excluded.getString(j).charAt(0);
                }

                source.add(password);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Tests the generated password's length
     */
    @Test
    @DisplayName("Test generated password length")
    public void testLength(){
        if(!testData.isEmpty())
            readTestData("PasswordTestData", testData);

        String generatedPassword;
        PasswordGenerate password;

        for(int i = 0; i < testData.size(); i++){
            for(int j = 0; j < 20; j++){ //Test each of test criteria 20 different generation
                password = testData.get(i);

                if(password.excluded.length == 0){
                     generatedPassword = Password.createPassword(password.type, password.minLength, password.maxLength).generate();

                     if(generatedPassword.length() < password.minLength || generatedPassword.length() > password.maxLength)
                         fail("Wrong length. Actual : "+generatedPassword.length()+" Expected : "+password.minLength+" - "+password.maxLength);
                }
            }
        }
    }

    /**
     * Tests if the generated password contains only expected characters
     * without excluding any character
     */
    @Test
    @DisplayName("Test generated password type")
    public void testType(){
        if(!testData.isEmpty())
            readTestData("PasswordTestData", testData);

        String alphaPattern = "[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz]*";
        String numericPattern = "[0123456789]*";
        String alphanumericPattern = "[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789]*";
        String symbolPattern = "[~`!@#£€$()*^&°%§¥¢?.,<>'\";:/\\\\|\\[\\]{}=+_\\-]*";
        String allPattern = "[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#£€$()*^&°%§¥¢?.,<>'\";:/\\\\|\\[\\]{}=+_\\-]*";

        String generatedPassword;
        PasswordGenerate password;

        for(int i = 0; i < testData.size(); i++){
            for(int j = 0; j < 20; j++){ //Test each of test criteria 20 different generation
                password = testData.get(i);

                if(password.excluded.length == 0){
                    generatedPassword = Password.createPassword(password.type, password.minLength, password.maxLength).generate();

                    if(password.type == PasswordType.ALL){
                        assertEquals(true, Pattern.matches(allPattern, generatedPassword), "Password contains wrong characters. Password: "+generatedPassword+" Type: All");
                    }
                    else if(password.type == PasswordType.ALPHA){
                        assertEquals(true, Pattern.matches(alphaPattern, generatedPassword), "Password contains wrong characters. Password: "+generatedPassword+" Type: Alpha");
                    }
                    else if(password.type == PasswordType.NUMERIC){
                        assertEquals(true, Pattern.matches(numericPattern, generatedPassword), "Password contains wrong characters. Password: "+generatedPassword+" Type: Numeric");
                    }
                    else if(password.type == PasswordType.ALPHANUMERIC){
                        assertEquals(true, Pattern.matches(alphanumericPattern, generatedPassword), "Password contains wrong characters. Password: "+generatedPassword+" Type: Alphanumeric");
                    }
                    else if(password.type == PasswordType.SYMBOLS){
                        assertEquals(true, Pattern.matches(symbolPattern, generatedPassword), "Password contains wrong characters. Password: "+generatedPassword+" Type: Symbols");
                    }
                }
            }
        }
    }

    /**
     * Tests if the generated password contains only expected characters
     * with excluding some characters
     */
    @Test
    @DisplayName("Test generated password type and excluded chars")
    public void testTypeExclude(){
        if(!testData.isEmpty())
            readTestData("PasswordTestData", testData);

        String alphaPattern = "[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz]*";
        String numericPattern = "[0123456789]*";
        String alphanumericPattern = "[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789]*";
        String symbolPattern = "[~`!@#£€$()*^&°%§¥¢?.,<>'\";:/\\\\|\\[\\]{}=+_\\-]*";
        String allPattern = "[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#£€$()*^&°%§¥¢?.,<>'\";:/\\\\|\\[\\]{}=+_\\-]*";

        String generatedPassword;
        Password passwordGenerator;
        PasswordGenerate password;
        List<Character> excludedChars;

        for(int i = 0; i < testData.size(); i++){
            for(int j = 0; j < 20; j++){ //Test each of test criteria 20 different generation
                password = testData.get(i);
                passwordGenerator = Password.createPassword(password.type, password.minLength, password.maxLength);
                for(char c : password.excluded)
                    passwordGenerator.addExcludedChar(c);

                excludedChars = Arrays.asList(password.excluded);
                generatedPassword = passwordGenerator.generate();


                if(password.type == PasswordType.ALL){
                    assertEquals(true, Pattern.matches(allPattern, generatedPassword), "Password contains wrong characters. Password: "+generatedPassword+" Type: All");
                }
                else if(password.type == PasswordType.ALPHA){
                    assertEquals(true, Pattern.matches(alphaPattern, generatedPassword), "Password contains wrong characters. Password: "+generatedPassword+" Type: Alpha");
                }
                else if(password.type == PasswordType.NUMERIC){
                    assertEquals(true, Pattern.matches(numericPattern, generatedPassword), "Password contains wrong characters. Password: "+generatedPassword+" Type: Numeric");
                }
                else if(password.type == PasswordType.ALPHANUMERIC){
                    assertEquals(true, Pattern.matches(alphanumericPattern, generatedPassword), "Password contains wrong characters. Password: "+generatedPassword+" Type: Alphanumeric");
                }
                else if(password.type == PasswordType.SYMBOLS){
                    assertEquals(true, Pattern.matches(symbolPattern, generatedPassword), "Password contains wrong characters. Password: "+generatedPassword+" Type: Symbols");
                }
                
                for(int k = 0; k < generatedPassword.length(); k++){
                    if(excludedChars.contains(generatedPassword.charAt(k))){
                        fail("Password contains a character that has been excluded. Char: "+generatedPassword.charAt(k)+" Password: "+generatedPassword);
                    }
                }
            }
        }
    }


    /**
     * An object of this class represents a single password generating process for testing purposes.
     */
    private class PasswordGenerate{
        int minLength;
        int maxLength;
        Character[] excluded;
        PasswordType type;
    }
}
