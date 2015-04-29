package com.spotify.akka.exercise1.actor;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.spotify.akka.exercise1.message.Answer;
import com.spotify.akka.exercise1.message.Question;

public class Receiver extends UntypedActor {

	private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	/*
	* onReceive(Object): is the message receiver function
	*	it processes only one type of Message: Question
	*	based on type of message it does some processing
	*/
	@Override
	public void onReceive(Object o) throws Exception {

		if(o instanceof Question) {													//check if the object o is of type Question
			Question question = (Question) o;	// get the object of Question Class
			log.info("Question: " + question.toString());	// log info: using question object toString()
			getSender().tell(new Answer("Hello"), getSelf());	//Send a message to Sender using tell()
		}
		else {
			// if the receiver actor behavior does not match with the object message,
			// call unhandled
			unhandled(o);
		}
	}
}
