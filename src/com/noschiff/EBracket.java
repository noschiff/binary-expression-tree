package com.noschiff;

public enum EBracket {
    LEFT(new char[]{'(', '['}),
    RIGHT(new char[]{')', ']'});

    private char[] charValues;

    EBracket(char[] charValues) {
        this.charValues = charValues;
    }

    /**
     * @return - the default char representation of the bracket
     */
    @Override
    public String toString() {
        return Character.toString(charValues[0]);
    }

    /**
     * Determines whether the character is one of the
     * possible representations of the listed brackets
     *
     * @param c - the character to be checked
     * @return - if the character is a valid bracket
     */
    public static boolean isBracket(char c) {
        //Checks if c is equal to any of the operators
        //Iterates through every representation of every operator
        //and checks if the characters are equal
        for (EBracket bracket : EBracket.values()) {
            for (char charRepresentation : bracket.charValues) {
                if (c == charRepresentation) {
                    return true;
                }
            }

        }
        return false;
    }

    /**
     * Finds the appropriate EBracket enum value
     *
     * @param c - the character to be checked
     * @return - the EBracket enum value, if there is one
     */
    public static EBracket parseBracket(char c) {
        for (EBracket bracket : EBracket.values()) {
            for (char charRepresentation : bracket.charValues) {
                if (c == charRepresentation) {
                    return bracket;
                }
            }
        }
        return null;
    }
}
