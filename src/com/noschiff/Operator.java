package com.noschiff;

/**
 * All operators that can be used in an expression
 * @author noschiff
 */
public enum Operator {
    add {
        @Override
        public String toString() {
            return "+";
        }
    },
    sub {
        @Override
        public String toString() {
            return "-";
        }
    },
    mult {
        @Override
        public String toString() {
            return "×";
        }
    },
    div {
        @Override
        public String toString() {
            return "/";
        }
    },
    exp {
        @Override
        public String toString() {
            return "^";
        }
    };

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
}
