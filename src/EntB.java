import java.io.*;
import java.net.*;

public class EntB {
    public static void main(String[] args) throws IOException {

        // Set up the socket, in and out variables

        Socket ActionClientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        int ActionSocketNumber = 4546;
        String ActionServerName = "localhost";
        String ActionClientID = "EntB"; //Entrance B
        int EntB_queue = 0;

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
                switch (fromUser.toLowerCase()){
                    case "new":
                        System.out.println("New car arriving");
                        EntB_queue+=1;
                        message = "Check_space";
                        System.out.println(ActionClientID + " sending:  " + message + " to ActionServer");
                        out.println(message);
                        break;
                    case "enter":
                        message="Add_car";
                        System.out.println(ActionClientID + " sending:  " + message + " to ActionServer");
                        out.println(message);
                        break;
                    default:
                        System.out.println(ActionClientID + " sending:  " + fromUser + " to ActionServer");
                        out.println("Error: something wrong happened!");
                        break;

                }
            }
            fromServer = in.readLine();
            System.out.println(ActionClientID + " received " + fromServer + " from ActionServer");
            if(fromServer!=null){
                if(fromServer.substring(0,31).equalsIgnoreCase("The car entered in the car park")){
                    EntB_queue-=1;
                }

            }
            System.out.println("queue: "+EntB_queue );

        }
            
        
       // Tidy up - not really needed due to true condition in while loop
      //  out.close();
       // in.close();
       // stdIn.close();
       // ActionClientSocket.close();
    }
}
