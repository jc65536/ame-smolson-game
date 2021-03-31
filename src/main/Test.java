package main;

import java.util.*;

import util.*;

public class Test {
    
    String currentPhrase = "";

    int findNthOccurrence(String s, int n) {return 0;}

    public void replace(String[] matches, String[] replacements) {
        ArrayList<ArrayList<Integer>> allIndices = new ArrayList<>();
        for (int i = 0; i < matches.length; i++) {
            ArrayList<Integer> indices = new ArrayList<>();
            int n = 1;
            int j = findNthOccurrence(matches[i], n);
            while (j >= 0) {
                indices.add(j);
                n++;
                j = findNthOccurrence(matches[i], n);
            }
            allIndices.add(indices);
        }

        for (int i = 0; i < matches.length; i++) {
            for (int j : allIndices.get(i)) {
                currentPhrase = currentPhrase.substring(0, j) + replacements[i] + currentPhrase.substring(j + matches[i].length());
            }
        }
    }

    public static void p(String str, Object... args) {
        System.out.printf(str, args);
    }

    public static void main(String[] args) {
        Logger.log("hello");
        Logger.log(new T());
        Logger.logf("%d %d %d", 1, 2, 3);
    }

}

class T {
    @Override
    public String toString() {
        return "hello";
    }
}
