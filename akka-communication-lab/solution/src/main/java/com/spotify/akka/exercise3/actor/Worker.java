package com.spotify.akka.exercise3.actor;

import akka.actor.UntypedActor;
import java.util.Set;
import java.util.TreeSet;
import com.spotify.akka.exercise3.message.Input;
import com.spotify.akka.exercise3.message.Output;
import com.spotify.akka.exercise3.util.PrimeChecker;

import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Worker extends UntypedActor {

	/*
	* onReceive(Object): is the message receiver function
	* it processes only one type of Message: Input
	* Input Message: Extracts the interval from Input Message, traverses
	* over the given interval to check prime numbers, once completed
	* returns the results from 'primes' to Master
	*/
	@Override
	public void onReceive(Object o) throws Exception {
		if(o instanceof Input) {

			Input input = (Input) o;
			Set<Long> primes = new TreeSet<Long>();
			for(long i = input.getInterval().from(); i <= input.getInterval().to(); i++) {
				if(PrimeChecker.isPrime(i)) { // compute the prime numbers in the range
					primes.add(i);	// if prime number then add to primes: Set<Long>
				}
			}
			getSender().tell(new Output(primes), getSelf()); // Send output: Set<Long> to Master
		}
	}
}
