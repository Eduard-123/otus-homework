package ru.otus;

import java.util.Arrays;

public class HelloOtus {

    public static void main(String[] args) {
        int[] numbers = {64, 34, 25, 12, 22, 11, 90};
        bubbleSort(numbers);
        System.out.println(Arrays.toString(numbers));
    }

    public static void bubbleSort(int[] numbers)
    {
        for (int i = 0; i < numbers.length-1; i++)
            for (int j = 0; j < numbers.length-i-1; j++)
                if (numbers[j] > numbers[j+1])
                {
                    int temp = numbers[j];
                    numbers[j] = numbers[j+1];
                    numbers[j+1] = temp;
                }
    }
}
