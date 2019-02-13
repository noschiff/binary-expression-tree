package com.noschiff;

/**
 * All operators that can be used in an expression
 *
 * @author noschiff
 */
public enum Operator {

    add(new char[]{'+'}, 1, Associativity.LEFT) {},
    sub(new char[]{'-'}, 1, Associativity.LEFT) {},
    mult(new char[]{'×', '*', '·'}, 2, Associativity.LEFT) {},
    div(new char[]{'÷', '/'}, 2, Associativity.LEFT) {},
    exp(new char[]{'^', '↑'}, 3, Associativity.RIGHT) {};

    //the different representations of the operator in char format; index 0 is default
    private char[] charValues;
    //used to calculate which operation is performed first
    //higher value is higher precedence; done first
    private int precedence;
    //determines how operators of the same precedence are grouped
    private Associativity associativity;

    /**
     * Constructor
     *
     * @param charValues - all accepted char representations of the operator
     * @param precedence - mathematical precedence
     * @param associativity - value from Associativity enum
     */
    Operator(char[] charValues, int precedence, Associativity associativity) {
        this.charValues = charValues;
        this.precedence = precedence;
        this.associativity = associativity;
    }

    /**
     * @return - the default char representation of the operator
     */
    @Override
    public String toString() {
        return Character.toString(charValues[0]);
    }

    /**
     * Determines whether the character is one of the
     * possible representations of thelisted operators
     *
     * @param c - the character to be checked
     * @return - if the character is a valid operator
     */
    public static boolean isOperator(char c) {
        //Checks if c is equal to any of the operators
        //Iterates through every representation of every operator
        //and checks if the characters are equal
        for (Operator operator : Operator.values()) {
            for (char charRepresentation : operator.charValues) {
                if (c == charRepresentation) {
                    return true;
                }
            }

        }
        return false;
    }

    /**
     * Finds the appropriate Operator enum value
     *
     * @param c - the character to be checked
     * @return - the Operator enum value, if there is one
     */
    public static Operator valueOfChar(char c) {
        for (Operator operator : Operator.values()) {
            for (char charRepresentation : operator.charValues) {
                if (c == charRepresentation) {
                    return operator;
                }
            }
        }
        return add;
    }

    public boolean greaterPrecedenceThan(Operator o) {
        if (o == null) return true;
        return (this.precedence > o.precedence);
    }

    public boolean equalPrecedence(Operator o) {
        if (o == null) return false;
        return (this.precedence == o.precedence);
    }

    /**
     * @return - the associativity of the operator
     */
    public Associativity getAssociativity() {
        return associativity;
    }
}