package it.imtlucca.lecture0;

public class PowerTwoNumbers {
    public static void usingForLoop() {
        //Here number is the base and p is the exponent
        int number = 2, p = 5;
        long result = 1;

        //Copying the exponent value to the loop counter
        int i = p;
        for (;i != 0; --i)
        {
            result *= number;
        }

        //Displaying the output
        System.out.println(number+"^"+p+" = "+result);
    }

    public static void usingWhileLoop() {
        int number = 5, p = 2;
        long result = 1;

        int i=p;
        while (i != 0)
        {
            result *= number;
            --i;
        }
        System.out.println(number+"^"+p+" = "+result);
    }

    public static void usingPowFunction() {
        int number = 10, p = 3;
        double result = Math.pow(number, p);
        System.out.println(number+"^"+p+" = "+result);
    }

    public static void main(String[] args){
        System.out.println("Using a for loop");
        usingForLoop();
        System.out.println("Using a while loop");
        usingWhileLoop();
        System.out.println("Using a for Math.pow function");
        usingPowFunction();
    }
}
