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

	@Override
	public void onReceive(Object o) throws Exception {
		if(o instanceof Input) {
			Input input = (Input) o;
			Set<Long> primes = new TreeSet<Long>();

			for(long i=input.getInterval().from(); i <= input.getInterval().to(); i++) {
				if(PrimeChecker.isPrime(i)) {
					primes.add(i);
				}
			}

			getSender().tell(new Output(primes), getSelf());
		}
	}
}
