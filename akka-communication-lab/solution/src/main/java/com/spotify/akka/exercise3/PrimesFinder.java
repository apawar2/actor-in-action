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

		ActorSystem system = ActorSystem.create("ActorSystem");
		ActorRef master = system.actorOf(Props.create(Master.class, numberOfWorkers), "Master");
		master.tell(new Input(interval), ActorRef.noSender());
		// TODO: Find all prime numbers in interval using Akka
	}
}
