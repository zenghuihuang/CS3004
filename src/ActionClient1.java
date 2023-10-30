import java.io.*;
import java.net.*;

public class ActionClient1 {
    public static void main(String[] args) throws IOException {

        // Set up the socket, in and out variables

        Socket ActionClientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        int ActionSocketNumber = 4545;
        String ActionServerName = "localhost";
        String ActionClientID = "ActionClient1";
        int EntA_queue = 0; // number of vehicles in queue at the entrance A

        try {
            ActionClientSocket = new Socket(ActionServerName, ActionSocketNumber);
            out = new PrintWriter(ActionClientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(ActionClientSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: localhost ");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: "+ ActionSocketNumber);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;

        System.out.println("Initialised " + ActionClientID + " client and IO connections");
        
        // This is modified as it's the client that speaks first

        while (true) {
            
            fromUser = stdIn.readLine();
            if (fromUser != null) {
                String message ="";
                switch (fromUser){
                    case "New":
                        System.out.println("New car arriving");
                        EntA_queue+=1;
                        System.out.println("queue: "+EntA_queue );
                        message = "Is there a space?";
                        System.out.println(ActionClientID + " sending " + message + " to ActionServer");
                        out.println(message);
                        break;
                    case "Enter":
                        EntA_queue-=1;
                        System.out.println(ActionClientID + " sending " + fromUser + " to ActionServer");
                        System.out.println("queue: "+EntA_queue );
                        out.println(fromUser);
                    default:
                        out.println("Error: something wrong happened!");

                }


            }
            fromServer = in.readLine();
            System.out.println(ActionClientID + " received " + fromServer + " from ActionServer");
        }
            
        
       // Tidy up - not really needed due to true condition in while loop
      //  out.close();
       // in.close();
       // stdIn.close();
       // ActionClientSocket.close();
    }
}
