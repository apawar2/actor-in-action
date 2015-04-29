package com.spotify.akka.exercise4.util;

public class PrimeChecker {

	public static boolean isPrime(long number) {
		if (number <= 1) return false; // 1 is not a prime number..
		long i = 2;
		while (i * i <= number) {
			if (number % i == 0) {
				return false;
			}
			i += 1;
		}
		return true;
	}

	public static boolean fasterIsPrime(long number) {
		// TODO: implement faster prime checker
		int n=1; //TODO: Dummy Variable n for compilation
		return isPrime(n);
	}

}
