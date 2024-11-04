package Helpers;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.*;

public class OrderHandler implements Runnable{
    public Map<Order, Socket> orderMap;
    private Order NULLORDER = new Order("NULL", null);
    public Stack<Order> waitList;
    Thread teaOne;
    Thread teaTwo;
    Thread coffOne;
    Thread coffTwo;

    public OrderHandler(){
        orderMap = new HashMap<Order, Socket>();
        waitList = new Stack<Order>();
        teaOne = new Thread();
        teaTwo = new Thread();
        coffOne = new Thread();
        coffTwo = new Thread();
    }

    public void addOrder(Socket inpSock, Order inpOr){
        orderMap.put(inpOr, inpSock);
        waitList.add(inpOr);
    }


    @Override
    public void run() {
        while(true){
            while(!waitList.isEmpty()) { //Checks for any orders in the Wait Stack. If one is added the function begins
                Order curOrder = waitList.pop(); //Retrieves the most recent order from the stack
                int[] temp = curOrder.getWait(); //Retrieves the order's wait array
                while(temp[0] != 0 || temp[1] != 0){ //Checks if the wait array is empty, if there are contents, it proceeds
                    if (!teaOne.isAlive() && temp[0] != 0) { //Simple thread state check as well as array content check. Allows for a basic lock scenario
                        teaOne = new Thread(new Brew("tea",curOrder));
                        curOrder.upBrew(0); //Shifts product from wait array to brew array
                        teaOne.start();
                    } else if (!teaTwo.isAlive() && temp[0] != 0) {
                        teaTwo = new Thread(new Brew("tea", curOrder));
                        curOrder.upBrew(0);
                        teaTwo.start();
                    }
                    if (!coffOne.isAlive() && temp[1] != 0) {
                        coffOne = new Thread(new Brew("coffee",curOrder));
                        curOrder.upBrew(1);
                        coffOne.start();
                    } else if (!coffTwo.isAlive() && temp[1] != 0) {
                        coffTwo = new Thread(new Brew("coffee", curOrder));
                        curOrder.upBrew(1);
                        coffTwo.start();
                    }
                }
            }
        }
    }
}
