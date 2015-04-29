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

	public Sender(ActorRef receiver) {
		this.receiver = receiver;
	}

	@Override
	public void onReceive(Object o) throws Exception {
		if(o instanceof Start){
			receiver.tell(new Question("Hi"), getSelf());
		}
		else if(o instanceof Answer){
			Answer answer = (Answer) o;	// get the object of Answer Class
			log.info("Answer: " + answer.toString()); // log info: using Answer object toString()
			getContext().system().shutdown(); // shutdown the system
		}
	}
}
