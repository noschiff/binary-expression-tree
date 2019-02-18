package com.noschiff;

/**
 * An element that can be held in a Node object
 * Note: is abstract; only values that are a child classes are allowed
 * Passes type from child to parent
 *
 * @param <T> the type of value that is stored
 * @author noschiff
 */
public abstract class Value<T> extends Element<T> {

    public Value(T value) {
        super(value);
    }
}
