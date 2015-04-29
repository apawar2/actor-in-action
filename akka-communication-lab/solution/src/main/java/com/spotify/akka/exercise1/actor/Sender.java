package com.spotify.akka.exercise1.actor;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;
import com.spotify.akka.exercise1.message.Start;
import com.spotify.akka.exercise1.message.Answer;
import com.spotify.akka.exercise1.message.Question;

public class Sender extends UntypedActor {

	private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	private final ActorRef receiver;

	/*
		* Sender(ActorRef): is a one argument constructor which takes
		*	ActorRef as argument and saves in field
		*	receiver for later use
	*/
	public Sender(ActorRef receiver) {
		this.receiver = receiver;
	}

	/*
		* onReceive(Object): is the message receiver function
		*	it processes only two types of Messages: Start & Answer
		*	based on type of message it does some processing
	*/
	@Override
	public void onReceive(Object o) throws Exception {
		if(o instanceof Start) {												//check the object o instance type
			receiver.tell(new Question("Hi"), getSelf()); //Send a Question to receiver
		}
		else if(o instanceof Answer) {
			Answer answer = (Answer) o;	// keep reference to Answer object
			log.info("Answer: " + answer.toString());	// log info: using Answer object toString()
			getContext().system().shutdown();	// shutdown the system
		}
		else{
			// if the sender actor behavior does not match with the object message,
			// call unhandled
			unhandled(o);
		}
	}
}
