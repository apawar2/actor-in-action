package com.spotify.akka.exercise2.actor;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.spotify.akka.exercise2.message.Start;
import com.spotify.akka.exercise2.message.Answer;
import com.spotify.akka.exercise2.message.Question;
import com.spotify.akka.exercise2.actor.Receiver;

public class Sender extends UntypedActor {

	private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	/*
	* Sender(): is a no-argument constructor which
	*	instantiates child actor of class Receiver
	*/
	public Sender(){
		getContext().actorOf(Props.create(Receiver.class),"receiver");
	}

	/*
	* onReceive(Object): is the message receiver function
	*	it processes two types of Messages: Start & Answer
	*	based on type of message it does some processing
	*/
	@Override
	public void onReceive(Object o) throws Exception {
		if(o instanceof Start){
			//Send Question to receiver actor
			// which is child actor of class Sender
			ActorRef receiver = getContext().children().iterator().next();
			receiver.tell(new Question("Hi"), getSelf());
		}
		else if(o instanceof Answer) {
			// get the object of Answer Class
			Answer answer = (Answer) o;
			// log info: using Answer object toString()
			log.info("Answer: " + answer.toString());
			// shutdown the system
			getContext().system().shutdown();
		}
		else {
			// if the receiver actor behavior does not match with the object message,
			// call unhandled
			unhandled(o);
		}
	}
}
