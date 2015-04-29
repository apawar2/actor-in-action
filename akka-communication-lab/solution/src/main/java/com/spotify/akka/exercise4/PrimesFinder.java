package com.spotify.akka.exercise4;

import akka.actor.ActorSystem;
import akka.actor.ActorRef;
import akka.actor.Props;

import com.spotify.akka.exercise4.message.Input;
import com.spotify.akka.exercise4.util.Interval;
import com.spotify.akka.exercise4.actor.Master;


public class PrimesFinder {

	public static void main(String[] args) {
		long startTime = System.nanoTime();
		if(args.length == 3)
		{
			int numberOfWorkers = Integer.parseInt(args[0]);

			long from = Long.parseLong(args[1], 10);
			long to = Long.parseLong(args[2], 10);

			Interval interval = new Interval(from, to);
			// create an 'insecure'(literally) ActorSystem
			ActorSystem system = ActorSystem.create("ActorSystem");
			// Use the 'insecure' actor system to generate a Master Actor
			// and return us only ActorRef to this Master
			ActorRef master = system.actorOf(Props.create(Master.class, numberOfWorkers), "Master");
			//Give the Master Actor Interval for which we want to compute the primes
			master.tell(new Input(interval), ActorRef.noSender());
		}
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		System.out.println("Main: " + duration); // print the time required to complete this operation
	}
}
