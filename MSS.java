package EC2;

import java.util.Random;
import java.util.Scanner;

public class MSS {
	public static void main(String[] args) {
		int min = -100;
		int max = 100;	
		
		Scanner in = new Scanner(System.in);
		System.out.print("Please enter a positive integer: ");
		int n = in.nextInt();
		
		int a[] = null;
		a = generateRandomarray(n, min, max);
		
		System.out.print("\n[");
		for (int i=0; i<n-1; i++) // To print the right commas
			System.out.print(a[i] + ", ");
		System.out.print(a[n-1] + "]\n");
		
		int MssSum = mss(a);
		System.out.print("\nMax sum with running time O(n) is: " + MssSum + "\n");
		
		int maxSum = MaxSubsequentSum(a, 0, n-1);
		System.out.print("\nMax sum with running time O(nlogn) is: " + maxSum);
	}
	
	public static int[] generateRandomarray(int n, int min, int max){
        int list[] = new int[n];
        Random random = new Random();    
        for (int i = 0; i < n; i++)
            list[i] = random.nextInt((max - min) + 1) + min;
       return list;
    } 
	
	public static int mss(int[] a) { //O(n)
		int len = a.length;
		int maxPositiveSum = 0; //Max sum of numbers
		int total = 0; //total of numbers in array
		for(int i=0; i<len; i++) {
			total += a[i];
			if(total > maxPositiveSum) //replace with greater value
				maxPositiveSum = total;
			else if(total < 0) //total is negative, then change to 0
				total = 0;
		}
		return maxPositiveSum; //return MSS of array
	}
	
	public static int maxOfThree(int num1, int num2, int num3) {
		if (num1 >= num2 && num1 >= num3)
			return num1;
	     else if (num2 >= num1 && num2 >= num3)
	    	 return num2;
	     else
	    	 return num3;
	}
	
	public static int MaxSubsequentSum(int[] a, int start, int end) { //O(nlog(n))
		int mid;
		if(start == end) //Only one element
			return a[start];
		if(end == start + 1) //TWO elements in array
			return maxOfThree(a[start], a[end], a[start] + a[end]);
		else {
			mid = (start + end)/2; //calculate middle point
			
			int mssLeft = MaxSubsequentSum(a, start, mid);
			int mssRight = MaxSubsequentSum(a, mid+1, end);
			int mssMiddle = MaxCrossingSum(a, start, mid, end);
			return maxOfThree(mssLeft, mssRight, mssMiddle);
		//return max of 1. left subarray 2. right subarray, 
			//3. subarray that intersects both the left and right halves
		}
	}
	
	public static int MaxCrossingSum(int[] a, int start, int mid, int end) {
		int maxLeftSum = Integer.MIN_VALUE;
		int sum = 0;
		for(int i = mid; i >= start; i--) { //for elements on left of the middle
			sum += a[i];
			if(sum > maxLeftSum)
				maxLeftSum = sum;
		}
		
		int maxRightSum = Integer.MIN_VALUE;
		sum = 0;
		for(int i = mid+1; i <= end; i++) {
			sum += a[i];
			if(sum > maxRightSum)
				maxRightSum = sum;
		}
		return maxLeftSum + maxRightSum;
	}
}