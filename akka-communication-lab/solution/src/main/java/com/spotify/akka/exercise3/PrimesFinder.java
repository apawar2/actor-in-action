package com.spotify.akka.exercise3;

import akka.actor.ActorSystem;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.spotify.akka.exercise3.message.Input;
import com.spotify.akka.exercise3.util.Interval;
import com.spotify.akka.exercise3.actor.Master;


public class PrimesFinder {

	public static void main(String[] args) {

		int numberOfWorkers = 100000;
		Interval interval = new Interval(1000000L, 9999999L);
		// create an 'insecure'(literally) ActorSystem
		ActorSystem system = ActorSystem.create("ActorSystem");
		// Use the 'insecure' actor system to generate a Master Actor
		// and return us only ActorRef to this Master
		ActorRef master = system.actorOf(Props.create(Master.class, numberOfWorkers), "Master");
		//Give the Master Actor Interval for which we want to compute the primes
		master.tell(new Input(interval), ActorRef.noSender());
	}
}
