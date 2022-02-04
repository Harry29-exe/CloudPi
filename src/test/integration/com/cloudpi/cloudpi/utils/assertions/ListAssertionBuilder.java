package com.cloudpi.cloudpi.utils.assertions;

import java.util.List;

public abstract class ListAssertionBuilder<T1, T2> {
    private ModelComparator<T1, T2> comparator = null;
    protected List<T1> list1 = null;
    protected List<T2> list2 = null;

    public ListAssertionBuilder(List<T1> list1, List<T2> list2) {
        this.list1 = list1;
        this.list2 = list2;
    }

    public abstract void check();

    protected boolean objectAreEqual(T1 obj1, T2 obj2) {
        return comparator != null ? comparator.areEquals(obj1, obj2) :
                obj1.equals(obj2);
    }

    public void withCustomComparator(ModelComparator<T1, T2> comparator) {
        this.comparator = comparator;
    }


}
