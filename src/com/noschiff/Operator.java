package com.noschiff;

/**
 * All operators that can be used in an expression
 * @author noschiff
 */
public enum Operator {

    add(1, Associativity.LEFT) {
        @Override
        public String toString() {
            return "+";
        }
    },
    sub(1, Associativity.LEFT) {
        @Override
        public String toString() {
            return "-";
        }
    },
    mult(2, Associativity.LEFT) {
        @Override
        public String toString() {
            return "×";
        }
    },
    div(2, Associativity.LEFT) {
        @Override
        public String toString() {
            return "/";
        }
    },
    exp(3, Associativity.RIGHT) {
        @Override
        public String toString() {
            return "^";
        }
    };

    private int precedence;
    private Associativity associativity;

    Operator(int precedence, Associativity associativity) {
        this.precedence = precedence;
        this.associativity = associativity;
    }

    /**
     * Determines whether or not the character is one of the listed operators
     *
     * @param c - the character to be checked
     * @return - if the character is a valid operator
     */
    public static boolean isOperator(char c) {
        //Checks if c is equal to any of the operators
        //Iterates through every operator and checks if the strings are equal
        for (Operator operator : Operator.values()) {
            if (Character.toString(c).equals(operator.toString())) {
                return true;
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
    public static Operator charToOperator(char c) {
        for (Operator operator : Operator.values()) {
            if (Character.toString(c).equals(operator.toString())) {
                return operator;
            }
        }
        return null;
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