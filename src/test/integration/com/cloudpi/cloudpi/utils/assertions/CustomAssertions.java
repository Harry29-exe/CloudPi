package com.cloudpi.cloudpi.utils.assertions;

import java.util.List;

public class CustomAssertions {

    public static <T1> AssertListTo<T1> assertThat(List<T1> t1) {
        return new AssertListTo<>(t1);
    }

}
