package com.spotify.akka.exercise3.actor;

import java.util.Set;
import java.util.TreeSet;
import java.util.List;
import java.lang.Iterable;
import akka.actor.ActorRef;
import java.util.Iterator;
import scala.collection.JavaConversions.*;
import akka.actor.UntypedActor;
import akka.actor.Props;
import com.spotify.akka.exercise3.message.Input;
import com.spotify.akka.exercise3.message.Output;
import com.spotify.akka.exercise3.actor.Worker;
import com.spotify.akka.exercise3.util.Interval;

import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Master extends UntypedActor {

	private final int numberOfWorkers;
	private int workerCount;
	private final Set<Long> finalOutput;

	public Master(int numberOfWorkers) {
		this.numberOfWorkers = numberOfWorkers;
		this.workerCount = 0;
		this.finalOutput = new TreeSet<Long>();

		for(long i=0; i < this.numberOfWorkers; i++){
			String workerName = "worker" + i;
			getContext().actorOf(Props.create(Worker.class), workerName);
		}
	}

	@Override
	public void onReceive(Object o) throws Exception {
		if(o instanceof Input) {

			Input input = (Input) o;
			List<Interval> workerIntervalsList = input.getInterval().divide(this.numberOfWorkers);
			Iterator workListIterator = workerIntervalsList.iterator();
			Iterable<ActorRef> workers = getContext().getChildren();
			Iterator workerIterator = workers.iterator();
			while(workListIterator.hasNext() && workerIterator.hasNext())
			{
				Interval tmp = (Interval) workListIterator.next();
				ActorRef worker = (ActorRef) workerIterator.next();
			 	worker.tell(new Input(tmp), getSelf());
			}
		}
		if(o instanceof Output) {
				Output output = (Output) o;
				finalOutput.addAll(output.getPrimes());
				workerCount++;

			if(workerCount == numberOfWorkers)
			{
				System.out.println("Primes for Range: " + finalOutput.toString());
				getContext().system().shutdown(); // shutdown the system
			}
		}
	}
}
