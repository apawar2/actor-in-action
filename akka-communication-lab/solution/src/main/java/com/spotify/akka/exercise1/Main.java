package com.spotify.akka.exercise1;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.ActorRef;

import com.spotify.akka.exercise1.actor.Sender;
import com.spotify.akka.exercise1.actor.Receiver;
import com.spotify.akka.exercise1.message.Start;

public class Main {

	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("system");
		//create receiver actor and get the reference in return 'receiver' variable
		ActorRef receiver = system.actorOf(Props.create(Receiver.class), "receiver");
		//create sender actor along with reference
		//to receiver actor passed in constructor arguments
		ActorRef sender = system.actorOf(Props.create(Sender.class, receiver), "sender");
		//start the communication by sending Start message to Sender
		sender.tell(new Start(), ActorRef.noSender());
	}
}
