package Helpers;

import java.io.*;
import java.net.*;
import java.util.*;

public class CustomerHandler implements Runnable{
    private final Socket customerSocket;
    private Order order;
    public OrderHandler orderHand;
    final ObjectInputStream toServer;
    final ObjectOutputStream toClient;
    String userInp;
    outHandler outHand;
    public CustomerHandler(Socket sock, OrderHandler orderHand, ObjectInputStream OIS, ObjectOutputStream OOS){
        this.customerSocket = sock;
        this.orderHand = orderHand;
        this.toServer = OIS;
        this.toClient = OOS;
        outHand = new outHandler();
    }

    @Override
    public void run(){
        try{
            while(true) {
                userInp = (String) toServer.readObject(); //Recieves the user's first input as their name
                System.out.println("New Customer: " + userInp); //Updates the Server of a new Customer
                toClient.writeObject("Welcome to the Cafe, " + userInp);
                order = new Order(userInp, customerSocket); //Creates a new Order object that will handle all functions in terms of brewing
                while(true){
                    userInp = (String) toServer.readObject();
                    String[] inpCheck = userInp.trim().split("\\s+"); //User input is split by spaces and stored in a temporary String array
                    String[] inpSplit = orderVeri(inpCheck); //Input is passed to function to verify and returns a readable String array for pathing
                    //System.out.println(inpSplit[0] + " " + inpSplit[1]);
                    if(Objects.equals(inpSplit[0], "order")) { //Should the input be correct, the customer is passed down into the options available
                        if(Objects.equals(inpSplit[1], "status")) { //Status functinality pulls the Order's three int arrays and verify's it's current positon
                            if(order.getState()){ //Function that checks if the Waiting Area and Brewing Area of an order is empty
                                toClient.writeObject(outHand.orderStat(order)); //If there are still products in the Brew and Wait area, the customer is informed of their status
                            }else{
                                toClient.writeObject(outHand.orderComp(order)); //Function that summarises the customer's complete order and exits them from the cafe
                                userInp = "exit";
                                break;
                            }
                        } else {
                            System.out.println("ORDER RECEIVED");
                            order.setWait(inpSplit); //Assigning the Customer's order to the new number of Products (Either new set or update)
                            toClient.writeObject(outHand.orderAck(order)); //Basic to customer output that summarises their order and verifies it's been accepted
                            orderHand.addOrder(customerSocket, order); //Adds the customer's order to the OrderHandler for the brewing process
                        }
                    }else{
                        toClient.writeObject("INVALID INPUT: " + inpSplit[0]);
                    }
                    if(userInp.equalsIgnoreCase("exit")) break;
                }
                if(userInp.equalsIgnoreCase("exit")) break;
            }
        }
        catch(Exception e){
            System.out.println("ERROR - CustomerHandler Run");
        }
    }

    //Function to split the Customer's input. The function checks if the input is valid for new/updated orders and order status.
    //Should the input be incorrect, the issue with the input is printed back to the customer to correct.
    public String[] orderVeri(String[] orderTest){
        System.out.println("VERIFYING ORDER");
        String[] checked = {"","0","0"};
        while(true) {
            if (!orderTest[0].equalsIgnoreCase("order")){
                checked[0] += "ORDER ERROR " ;
                break;
            }
            if(orderTest[0].equalsIgnoreCase("order") && orderTest[1].equalsIgnoreCase("status")){
                checked[0] = "order";
                checked[1] = "status";
            }else if(!order.teaCoff(orderTest[2])){
                checked[0] += " PRODUCT ERROR ";
                break;
            }else{
                checked[0] = "order";
                try{
                    int test = Integer.parseInt(orderTest[1]);
                    if(Objects.equals(orderTest[2], "tea") || Objects.equals(orderTest[2], "teas")){
                        checked[1] = String.valueOf(test);
                    }else{
                        checked[2] = String.valueOf(test);
                    }
                }catch(NumberFormatException e){
                    if(Objects.equals(orderTest[1], "status")){
                        checked[0] = "status";
                        break;
                    }else {
                        checked[0] += " FIRST AMOUNT ERROR ";
                        break;
                    }
                }
            }
            if(orderTest.length > 3){
                if(!order.teaCoff(orderTest[4])){
                    checked[0] += " 2ND PRODUCT ERROR ";
                    break;
                }else{
                    try{
                        int test = Integer.parseInt(orderTest[3]);
                        if(Objects.equals(orderTest[4], "tea") || Objects.equals(orderTest[4], "teas")){
                            checked[1] = String.valueOf(test);
                        }else{
                            checked[2] = String.valueOf(test);
                        }
                    }catch (NumberFormatException nfe){
                        checked[0] += " 2ND AMOUNT ERROR";
                        break;
                    }
                }
            }
            break;
        }
        return checked;
    }
}
