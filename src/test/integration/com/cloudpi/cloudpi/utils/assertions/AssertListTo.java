package com.cloudpi.cloudpi.utils.assertions;

import java.util.List;

public class AssertListTo<T1> {
    private final List<T1> list;

    public AssertListTo(List<T1> list) {
        this.list = list;
    }

    public <T2> ListAssertionBuilder<T1, T2> hasSameElementsAs(List<T2> list) {
        return new ListAssertionBuilder<>(this.list, list) {
            @Override
            public void check() {
                if (this.list1.size() != this.list2.size()) {
                    throw listsHaveDifferentLengths();
                }

                boolean exist;
                for (var obj : this.list1) {
                    exist = false;
                    for (var soughtObj : this.list2) {
                        if (this.objectAreEqual(obj, soughtObj)) {
                            exist = true;
                            break;
                        }
                    }

                    if (!exist) {
                        throw objectNotFound(obj);
                    }
                }
            }
        };
    }


    public <T2> ListAssertionBuilder<T1, T2> matchExactly(List<T2> list) {
        return new ListAssertionBuilder<T1, T2>(this.list, list) {
            @Override
            public void check() {
                if (this.list1.size() != this.list2.size()) {
                    throw listsHaveDifferentLengths();
                }
                for (int i = 0; i < list1.size(); i++) {
                    if (!this.objectAreEqual(list1.get(i), list2.get(i))) {
                        throw objectsAreDifferent(i, list1.get(i), list2.get(i));
                    }
                }
            }
        };
    }

    private static AssertionError listsHaveDifferentLengths() {
        return new AssertionError("Lists have different lengths");
    }

    private static AssertionError objectNotFound(Object obj1) {
        return new AssertionError("Object: " + obj1.toString() + " could not be found in second list.");
    }

    private static AssertionError objectsAreDifferent(int index, Object obj1, Object obj2) {
        return new AssertionError("Objects at index " + index + " are different:\n" +
                "obj1: " + obj1.toString() +
                "\nobj2: " + obj2.toString());
    }
}
