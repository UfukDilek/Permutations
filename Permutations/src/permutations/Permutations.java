package permutations;

import java.io.StringWriter;
import java.util.Arrays;

/**
 * @author ufukdilek
 */

public class Permutations {

    public static void main(String[] args) {
        String str1 = "ABCDEF";
        String str2 = "ABACBC";
        PermutationFinder.printPermutations(str1);
        PermutationFinder.printPermutations(str2);
    }
}//class Permutations


class PermutationFinder{
    
    private PermutationFinder() {}
    
    /*
    The algorithm that does the job is here:
    a_0, a_1, a_2, ...
    j is the largest subscript with a_j < a_(j+1)
    a_k is the smallest integer greater than a_j to the right of a_j
    Interchange a_j and a_k
    Put the tail end of the permutation after the jth position in increasing order --> a_r and a_s
    */
    private static int calculateNextPermutation(int[] arr){
        if(arr.length < 2 || arr.length > 20){
            throw new RuntimeException("Permutation length out of the allowed interval [2,20]");
        }
        if(finished(arr)){
            return -1;
        }
        int j = arr.length - 2;
        while(arr[j] >= arr[j+1])
            j--;
        int k = arr.length - 1;
        while(arr[j] >= arr[k])
            k--;
        int a_k = arr[k];
        arr[k] = arr[j];
        arr[j] = a_k;
        int r = arr.length - 1;
        int s = j+ 1;
        while(r > s){
            int a_r = arr[r];
            arr[r] = arr[s];
            arr[s] = a_r;
            r--;
            s++;
        }
        return 1;
    }
    
    // Prints all permutations in lexicographic order
    public static void printPermutations(String str){
        int[] arr = getSortedArray(str);
        String previousStr = intArrayToString(arr);
        String initialStr = previousStr; 
        System.out.println(previousStr);
        int count = 1;
        while(true){
            int result = calculateNextPermutation(arr);
            if(result < 0)
                break;
            String currentStr = intArrayToString(arr);
            if(!lexicographicalIntegrity(previousStr, currentStr, arr, initialStr)){
                throw new RuntimeException("Problem in the algorithm...");
            }
            System.out.println(currentStr);
            previousStr = currentStr;
            count++;
        }
        System.out.println("Permutation count: " + count);
        System.out.println("-------------------------");
    }
    
    // Checks for bugs in the algorithm
    // Also check the permutation count manually by comparing with the value you calculate.
    private static boolean lexicographicalIntegrity(String previousStr, String currentStr,
                                            int[] currentArr, String initialStr){
        int[] arr2 = Arrays.copyOf(currentArr, currentArr.length);
        Arrays.sort(arr2);
        String currentStrSorted = intArrayToString(arr2);
        return previousStr.compareTo(currentStr) < 0 &&
                initialStr.compareTo(currentStrSorted) == 0;
    }
    
    private static String intArrayToString(int[] array){
        StringWriter sw = new StringWriter();
        Arrays.stream(array).forEach(sw::write);
        return sw.toString();
    }
    
    // Checks if last string to be printed is reached
    private static boolean finished(int[] arr){
        for(int i = 0; i <= arr.length - 2; i++){
            if(arr[i] < arr[i + 1]){
                return false;
            }
        }
        return true;
    }
    
    // String to sorted int array
    private static int[] getSortedArray(String str){
        int[] arrNew = str.chars().toArray();
        Arrays.sort(arrNew);
        return arrNew;
    }
    
}//class PermutationFinder


