package com.test.bxbdemo;

import android.util.Log;

import java.util.Iterator;
import java.util.List;

public class CommonUtils {
    private static String TAG = "CommonUtils";

    public static <E> boolean compareList(List<E> list1, List<E> list2) {
        if (list1 == list2) {
            return true;
        }

        if ((list1 == null && list2 != null && list2.size() == 0)
                || (list2 == null && list1 != null && list1.size() == 0)) {
            return true;
        }

        if (list1.size() != list2.size()) {
            return false;
        }


        if (list1.containsAll(list2)) {
            return true;
        }

        return false;
    }

    public static <E> void removeDuplicate(List<E> beRemovedList, List<E> baseList) {
        if (beRemovedList == null || beRemovedList.isEmpty()) {
            return;
        }

        if (baseList == null || baseList.isEmpty()) {
            return;
        }

        Iterator<E> itr = beRemovedList.iterator();

        while (itr.hasNext()) {
            E item = itr.next();
            if (baseList.contains(item)) {
                Log.d(TAG, "remove duplicated item:" + item.toString());
                itr.remove();
            }
        }
    }
}
