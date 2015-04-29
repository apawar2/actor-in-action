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

	/*
		* Master(int): is a one argument constructor which takes
		*	number of workers ActorRef as argument and saves in field
		*	receiver for later use
	*/
	public Master(int numberOfWorkers) {
		this.numberOfWorkers = numberOfWorkers;
		this.workerCount = 0;
		this.finalOutput = new TreeSet<Long>();

		//Instantiate the workers
		for(long i=0; i < this.numberOfWorkers; i++) {
			String workerName = "worker" + i; 														//assign unique name to each worker
			getContext().actorOf(Props.create(Worker.class), workerName); // instantiate the worker
		}
	}

	/*
		* onReceive(Object): is the message receiver function
		*	it processes two types of Messages: Input & Output
		*	Input Message: Extracts the interval from Input Message, Divides it into
		* equal size Intervals & Distributes them for processing among the workers
	*/
	@Override
	public void onReceive(Object o) throws Exception {
		/*
			*	Input Message: Extracts the interval from Input Message, Divides it into
			* equal size Intervals & Distributes them for processing among the workers
		*/
		if(o instanceof Input) {

			Input input = (Input) o;
			List<Interval> workerIntervalsList = input.getInterval().divide(this.numberOfWorkers);
			Iterator workListIterator = workerIntervalsList.iterator();
			Iterable<ActorRef> workers = getContext().getChildren();
			Iterator workerIterator = workers.iterator();

			while(workListIterator.hasNext() && workerIterator.hasNext()) {

				Interval tmp = (Interval) workListIterator.next();
				ActorRef worker = (ActorRef) workerIterator.next();
			 	worker.tell(new Input(tmp), getSelf());
				workerCount++;

			}
		}
		/*
			*	Output Message: Appends the primes list obtained from this message
			* to Final Output Set<Long> and decreases the counter: workerCount
			* if(workerCount == 0) all the worker actors have returned their
			* output primes Set<Long> now it shuts down
		*/
		else if(o instanceof Output) {
				Output output = (Output) o;
				finalOutput.addAll(output.getPrimes());		// append the results received to finalOutput
				workerCount--; 														// decrease counter by 1

			if(workerCount == 0) { 											// all worker actors have returned results
				System.out.println("Primes for Range: " + finalOutput.toString());
				getContext().system().shutdown();					// shutdown the system
			}
		}
		else {
				// if the receiver actor behavior does not match with the object message,
				// call unhandled
				unhandled(o);
		}
	}
}
