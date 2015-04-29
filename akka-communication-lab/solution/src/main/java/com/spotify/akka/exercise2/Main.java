package com.spotify.akka.exercise2;

import akka.actor.ActorSystem;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.spotify.akka.exercise2.actor.Sender;
import com.spotify.akka.exercise2.message.Start;

public class Main {

	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("system");
		// Create actor sender of class Sender using ActorSystem
		ActorRef sender = system.actorOf(Props.create(Sender.class), "sender");
		// Send Start message to sender actor
		sender.tell(new Start(), ActorRef.noSender());
	}
}
