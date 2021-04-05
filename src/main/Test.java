package main;

import java.util.*;

import util.*;

public class Test {

    public static void pf(String str, Object... args) {
        System.out.printf(str, args);
    }

    public static void p(Object o) {
        System.out.println(o);
    }

    public static String b(int i) {
        return Integer.toBinaryString(i);
    }

    public static void main(String[] args) {
        pf("%f", Math.atan2(0, 0));
    }

}

class T {
    @Override
    public String toString() {
        return "hello";
    }
}
