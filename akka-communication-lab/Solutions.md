## Akka communication Lab ##


### Goal ###

- Learn different ways of creating actors in Akka
- Learn how to exchange messages between actors
- Get familiar with common *master/workers* pattern

### Realization ###


**Exercise1: Solution**
Based on the problem description and steps explained following implementation was done to complete the exercise.
The essence of this exercise was to understand how two actors can be created using Akka which operate independently and are able to communicate with each other using Akka's Messaging Interface for a Akka ActorSystem.The reference heirarchy in this case was:

ActorSystem ---(child)--> Sender
            ---(child)--> Receiver


This kind of an approach where the two Akka Actors play independent roles and still communicate for message & data exchanges can be used in simple

**Exercise2: Solution**
This exercise was created to learn and understand how one can create hierarchy of Akka Actors using the Akka ActorSystem. In this particular example, we had two Actors Sender and Receiver. Initially we create the Akka ActorSystem, this was in turn used to create the Sender Actor. In the Sender Actor, we had a constructor with no-argument which would initialize a Receiver Actor inside it. This lead to the following reference heirarchy:

ActorSystem ---(child)--> Sender ---(child)--> Receiver

This is a kind of Master-Slave relationship, where Sender is the Master and Receiver is its Slave. This kind of
pattern can be used to do parallel computations where the Master spawns the Slaves which are used to do the desired computation. Exercise 3 below is the perfect example of this design.

**Exercise3: Solution**
The purpose of this exercise is to demonstrate how Akka can be used to distribute large set of data among multiple actors to perform computation simultaneously.

This exercise creates an ActorSystem which is used to instantiate a Master Actor. The Master actor has one-argument constructor which takes number of workers as input and instantiates Worker actors in the constructor. This initialization of Master Actor leads to the following Hierarchy.

ActorSystem ---(child)--> Master ---(child)--> Worker1
                                 ---(child)--> Worker2
                                 ---(child)--> Worker3
                                 ---(child)--> WorkerN

***Evaluation***
To evaluate the performance of this application we created two set of tests:

1. Basic Evaluation(evaluation/basic_evaluation.py):
In this evaluation the range in which prime numbers are computed is kept constant (1000000L, 9999999L) and the number of workers is variable.

workers=[1, 2, 4, 10, 100, 1000, 10000, 100000]
![ScreenShot](https://raw.github.com/apawar2/actors-in-action/akka-communication-lab/solution/evaluation/actors_eval.eps)
