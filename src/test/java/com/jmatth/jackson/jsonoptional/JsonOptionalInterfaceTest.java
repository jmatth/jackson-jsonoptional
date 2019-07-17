package com.jmatth.jackson.jsonoptional;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import org.junit.Test;

public class JsonOptionalInterfaceTest {

    @Test
    public void testJsonOptionalImplementsOptional() throws Exception {
        final Method[] optionalMethods = Optional.class.getDeclaredMethods();

        final Method[] jsonOptionalMethods = JsonOptional.class.getDeclaredMethods();

        for (final Method optionalMethod : optionalMethods) {
            assertTrue(Arrays.stream(jsonOptionalMethods).anyMatch(m -> areMethodsEqual(optionalMethod, m)));
        }
    }

    private static boolean areMethodsEqual(final Method opt, final Method jsonOpt) {
        if (opt == null || jsonOpt == null) {
            throw new IllegalArgumentException("Expected two methods, got one or more nulls");
        }
        if (opt.getName() != jsonOpt.getName()) {
            return false;
        }
        if (!opt.getReturnType().equals(jsonOpt.getReturnType())) {
            if (opt.getReturnType().equals(Optional.class) && !jsonOpt.getReturnType().equals(JsonOptional.class))
                return false;
        }
        return areParamTypesEqual(opt.getParameterTypes(), jsonOpt.getParameterTypes());
    }

    private static boolean areParamTypesEqual(Class<?>[] optParams, Class<?>[] jsonOptParams) {
        if (optParams.length == jsonOptParams.length) {
            for (int i = 0; i < optParams.length; i++) {
                if (optParams[i] != jsonOptParams[i]) {
                    if (optParams[i].equals(Optional.class) && !jsonOptParams[i].equals(JsonOptional.class)) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
}
