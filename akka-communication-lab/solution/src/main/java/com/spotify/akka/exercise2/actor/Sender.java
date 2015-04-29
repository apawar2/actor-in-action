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

	public Sender(){
		getContext().actorOf(Props.create(Receiver.class),"receiver");
	}

	@Override
	public void onReceive(Object o) throws Exception {
		if(o instanceof Start){
			getContext().children().iterator().next().tell(new Question("Hi"), getSelf());
		}
		else if(o instanceof Answer){
			Answer answer = (Answer) o;	// get the object of Answer Class
			log.info("Answer: " + answer.toString()); // log info: using Answer object toString()
			getContext().system().shutdown(); // shutdown the system
		}
	}
}
