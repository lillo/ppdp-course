package it.imtlucca.lecture0;

import java.util.Scanner;

public class PalindromeWithLoop {
    public static void main(String[] args){
        String reverseString="";
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a string to check if it is a palindrome:");
        String inputString = scanner.nextLine();

        int length = inputString.length();

        for ( int i = length - 1 ; i >= 0 ; i-- )
            reverseString = reverseString + inputString.charAt(i);

        if (inputString.equals(reverseString))
            System.out.println("Input string is a palindrome.");
        else
            System.out.println("Input string is not a palindrome.");
    }
}
