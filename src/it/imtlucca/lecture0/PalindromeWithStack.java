package it.imtlucca.lecture0;

import java.util.Scanner;
import java.util.Stack;

public class PalindromeWithStack {
    public static void main(String[] args){
        System.out.println("Enter any string:");
        Scanner in=new Scanner(System.in);
        String inputString = in.nextLine();
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < inputString.length(); i++) {
            stack.push(inputString.charAt(i));
        }

        String reverseString = "";

        while (!stack.isEmpty()) {
            reverseString = reverseString+stack.pop();
        }

        if (inputString.equals(reverseString))
            System.out.println("The input String is a palindrome.");
        else
            System.out.println("The input String is not a palindrome.");

    }

}
