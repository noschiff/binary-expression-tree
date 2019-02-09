package com.noschiff;

/**
 * All operators that can be used in an expression
 * @author noschiff
 */
public enum Operator {

    add (1, 1){
        @Override
        public String toString() {
            return "+";
        }
    },
    sub (1, 1){
        @Override
        public String toString() {
            return "-";
        }
    },
    mult (2, 1){
        @Override
        public String toString() {
            return "×";
        }
    },
    div (2, 1){
        @Override
        public String toString() {
            return "/";
        }
    },
    exp (3, 1){
        @Override
        public String toString() {
            return "^";
        }
    };

    public int prescedence;
    public int associativity;

    Operator(int prescedence, int associativity) {
        this.prescedence = prescedence;
        this.associativity = associativity;
    }

    /**
     * Determines whether or not the character is one of the listed operators
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

    public boolean greaterPrescedenceThan(Operator o) {
        if (o == null) return true;
        return (this.prescedence > o.prescedence);
    }
    public boolean equalPrescedence(Operator o) {
        if (o == null) return false;
        return (this.prescedence == o.prescedence);
    }
    public boolean leftAssociative() {
        return this.associativity == 1;
    }
}
