package com.noschiff;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Contains static methods that are not specific to a class
 *
 * @author noschiff
 */
public class Util {
    public static <E> List<E> flattenList(List<List<E>> list) {
        return list.stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    public static <E> String arrayToString(E[] array) {
        return Arrays.toString(array);
    }
}
