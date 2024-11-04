import java.io.IOException;
import java.io.*;
import java.net.*;

import Helpers.*;

public class Barista {
    private static int port = 0704;

    public static void main(String args[]) throws IOException, ClassNotFoundException{
        RunCafe();
    }

    private static void RunCafe(){
        ServerSocket cafeSocket = null;
        OrderHandler orderHand = new OrderHandler();
        Thread test = new Thread(orderHand);
        test.start();
        try{
            cafeSocket = new ServerSocket(port);
            System.out.println("--Waiting for New Customer--");
            while(true){
                System.out.println("New Connection...");
                Socket newCust = cafeSocket.accept();
                ObjectInputStream ois = new ObjectInputStream(newCust.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(newCust.getOutputStream());
                Thread newCusThread = new Thread(new CustomerHandler(newCust, orderHand, ois, oos));
                newCusThread.start();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
