package com.spotify.akka.exercise2.actor;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.spotify.akka.exercise2.message.Answer;
import com.spotify.akka.exercise2.message.Question;

public class Receiver extends UntypedActor {

	private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	@Override
	public void onReceive(Object o) throws Exception {
		if(o instanceof Question)
		{
			Question question = (Question) o;	// get the object of Question Class
			log.info("Question: " + question.toString()); // log info: using question object toString()
			getSender().tell(new Answer("Hello"), getSelf());
		}
		else
		{
			unhandled(o);
		}
	}
}
