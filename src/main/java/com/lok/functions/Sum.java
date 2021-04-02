package com.lok.functions;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Sum {
    public static void main(String args[]){
        Scanner in = new Scanner(System.in);

        System.out.println("Introduce un número entero:");
        try {
            int number = in.nextInt();
            if (number > 0) {
                System.out.println(calculateSum(number));
            } else {
                System.out.println("Entrada incorrecta! Ingrese solo números enteros positivos.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada incorrecta! Ingrese solo números enteros positivos.");
        }

    }

    public static int calculateSum(int number){
        int result = 0;

        for (int i = 1 ; i <= number ; i++){
            result += i;
        }

        return result;
    }
}
