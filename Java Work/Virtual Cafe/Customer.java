import Helpers.Order;

import java.io.IOException;
import java.io.*;
import java.net.*;
import java.util.Objects;
import java.util.Scanner;

public class Customer {


    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException{
        Scanner userInp = new Scanner(System.in);
        String message;
        while(true){
            //Initial User inputs to establish Object streams and the Customer's name for Order setting
            Socket socket = new Socket("localhost", 0704);
            ObjectOutputStream toServer = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Welcome to the cafe. Please input your Name:");
            String inp = userInp.nextLine();
            toServer.writeObject(inp);
            ObjectInputStream toClient = new ObjectInputStream(socket.getInputStream());
            message = (String) toClient.readObject();
            System.out.println(message);
            while(true){
                System.out.println("Please input your order. Inputs are accepted in the following format;");
                System.out.println("order x tea(s) x coffee(s)");
                System.out.println("Order: ");
                inp = userInp.nextLine();
                if(Objects.equals(inp, "exit")){
                    break;
                }else{
                    toServer.writeObject(inp);
                    message = (String) toClient.readObject();
                    System.out.println(message);
                }
            }
            toServer.close();
            toClient.close();
            Thread.sleep(100);
        }
    }
}
