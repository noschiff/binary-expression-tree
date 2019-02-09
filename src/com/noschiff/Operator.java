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
            return "÷";
        }
    },
    exp {
        @Override
        public String toString() {
            return "^";
        }
    }
}
