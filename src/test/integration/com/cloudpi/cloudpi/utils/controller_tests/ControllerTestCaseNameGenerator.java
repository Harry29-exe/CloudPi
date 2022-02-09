package com.cloudpi.cloudpi.utils.controller_tests;

import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.platform.commons.util.ClassUtils;
import org.junit.platform.commons.util.Preconditions;

import java.lang.reflect.Method;

public class ControllerTestCaseNameGenerator implements DisplayNameGenerator {

    @Override
    public String generateDisplayNameForClass(Class<?> testClass) {
        var templateClass = testClass.getSuperclass();
        String prefix = "";
        if (templateClass != null) {
            var name = templateClass.getSimpleName();
            var ttIndex = name.indexOf("TestTemplate");
            if (ttIndex > 0) {
                prefix = name.substring(0, ttIndex);
            }
        }

        String className = testClass.getSimpleName();
        if (className.startsWith("Test")) {
            className = className.substring("Test".length());
        }

        String name = prefix + "_" + className;
//        System.out.println("\n\n"+name+"\n\n");
        return name;
    }

    @Override
    public String generateDisplayNameForNestedClass(Class<?> nestedClass) {
        return nestedClass.getSimpleName() + "XD?";
    }

    @Override
    public String generateDisplayNameForMethod(Class<?> testClass, Method testMethod) {
        return testMethod.getName() + parameterTypesAsString(testMethod);
    }

    static String parameterTypesAsString(Method method) {
        Preconditions.notNull(method, "Method must not be null");
        return '(' + ClassUtils.nullSafeToString(Class::getSimpleName, method.getParameterTypes()) + ')';
    }

}
