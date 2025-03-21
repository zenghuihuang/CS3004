import java.io.*;
import java.net.*;

public class ExA {
    public static void main(String[] args) throws IOException {

        // Set up the socket, in and out variables

        Socket ActionClientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        int ActionSocketNumber = 4546;
        String ActionServerName = "localhost";
        String ActionClientID = "ExA"; //Exit A

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
                if(fromUser.equalsIgnoreCase("exit")){
                    String message = "Remove_car";
                    System.out.println(ActionClientID + " sending : " + message + " to ActionServer");
                    out.println(message);
                }else{
                    System.out.println(ActionClientID + " :  exit is the only action allowed.");
                }

            }
            fromServer = in.readLine();
            System.out.println(ActionClientID + " received : " + fromServer + " from ActionServer");
        }
            
        
       // Tidy up - not really needed due to true condition in while loop
      //  out.close();
       // in.close();
       // stdIn.close();
       // ActionClientSocket.close();
    }
}
