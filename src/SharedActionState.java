import java.net.*;
import java.io.*;

public class SharedActionState {


	private SharedActionState mySharedObj;
	private String myThreadName;
	private int mySharedVariable;
	private boolean accessing = false; // true a thread has a lock, false otherwise
	private int threadsWaiting = 0; // number of waiting writers


// Constructor	

	SharedActionState(int SharedVariable) {
		mySharedVariable = SharedVariable;
	}

//Attempt to aquire a lock

	public synchronized void acquireLock() throws InterruptedException {
		Thread me = Thread.currentThread(); // get a ref to the current thread
		System.out.println(me.getName() + " is attempting to acquire a lock!");
		++threadsWaiting;
		while (accessing) {  // while someone else is accessing or threadsWaiting > 0
			System.out.println(me.getName() + " waiting to get a lock as someone else is accessing...");
			//wait for the lock to be released - see releaseLock() below
			wait();
		}
		// nobody has got a lock so get one
		--threadsWaiting;
		accessing = true;
		System.out.println(me.getName() + " got a lock!");
	}

	// Releases a lock to when a thread is finished

	public synchronized void releaseLock() {
		//release the lock and tell everyone
		accessing = false;
		notifyAll();
		Thread me = Thread.currentThread(); // get a ref to the current thread
		System.out.println(me.getName() + " released a lock!");
	}


	/* The processInput method */

	public synchronized String processInput(String myThreadName, String theInput) {
		System.out.println(myThreadName + " received " + theInput);
		String theOutput = null;
		// Check what the client said
		switch (theInput.toLowerCase()) {
			case "check_space":
					//Correct request
					if (myThreadName.equals("ActionServerThread1") || myThreadName.equals("ActionServerThread2") ) {
						if(mySharedVariable>0)
						   theOutput = "Check space completed. Yes, there is a space. Shared Variable now = " + mySharedVariable;
						else{
							theOutput = "Check space completed. Sorry, there is " + mySharedVariable+" space available";
						}

					break;
				}
			case  "add_car" :
						if (myThreadName.equals("ActionServerThread1") || myThreadName.equals("ActionServerThread2")) {
							if (mySharedVariable >0) {
								mySharedVariable -= 1;
								System.out.println(myThreadName + " made the SharedVariable " + mySharedVariable);
								theOutput = "The car entered in the car park.   Car spaces available now = " + mySharedVariable;
								}else{
								System.out.println(myThreadName + ": Impossible to enter the car park.");
								theOutput = "Sorry, the car cannot be parked here. Car spaces available now = " + mySharedVariable;
							}
							}else{
							theOutput=myThreadName+" not allowed.";
						}
						break;
			case "remove_car":
					if (myThreadName.equals("ActionServerThread3") || myThreadName.equals("ActionServerThread4")) {
						mySharedVariable += 1;
						System.out.println(myThreadName + " made the SharedVariable " + mySharedVariable);
						theOutput = "The car has successfully been removed from the car park.   Car spaces available now = " + mySharedVariable;
						}
					break;
				}

		//Return the output message to the ActionServer
		System.out.println(theOutput);
		return theOutput;
	}
}

