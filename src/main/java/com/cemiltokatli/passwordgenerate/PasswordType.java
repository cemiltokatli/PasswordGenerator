package com.cemiltokatli.passwordgenerate;

import java.util.List;
import java.util.Random;

/**
 * Password Types
 */
public enum PasswordType {
    /**
     * Letters, numbers and symbols
     */
    ALL (0, 99),
    /**
     * Only letters
     */
    ALPHA (0, 51),
    /**
     * Only numbers
     */
    NUMERIC (52, 61),
    /**
     * Letters and numbers
     */
    ALPHANUMERIC (0, 61),
    /**
     * Only symbols
     */
    SYMBOLS (62, 99);

    private int minIndex;
    private int maxIndex;
    private Random rand;
    private static char[] characters = {
            'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
            '0','1','2','3','4','5','6','7','8','9',
            '~','`','!','@','#','£','€','$','(',')','*','^','&','°','%','§','¥','¢','?','.',',','<','>','\'','"',';',':','/','\\','|','[',']','{','}','=','+','_','-'
    };

    PasswordType(int minIndex, int maxIndex) {
        this.minIndex = minIndex;
        this.maxIndex = maxIndex;
        this.rand = new Random();
    }

    /**
     * Returns a character randomly selected.
     * The characters given with the argument are excluded.
     *
     * @param excluded characters to be excluded
     * @param lastChar previously generated character, this char also is excluded to prevent repeating
     * @return character
     */
    char getRandomChar(List<Character> excluded, Character lastChar){
        int index;
        Character chr;
        while(true) {
            index = rand.nextInt((maxIndex - minIndex) + 1) + minIndex;
            chr = characters[index];

            if(lastChar != null && lastChar.equals(chr))
                continue;

            if(!excluded.contains(chr))
                return chr;
        }
    }
}