package Helpers;


import java.net.Socket;

public class Order {
    private final String customerName;
    private int[] waiting = {0,0};
    private int[] brewing = {0,0};
    private int[] tray = {0,0};
    private final Socket customerSock;
    private boolean cusState;

    public Order(String cusN, Socket orSock) {
        this.customerName = cusN;
        this.customerSock = orSock;
        this.cusState = true;
    }

    public String getName(){
        return customerName;
    }

    public Socket getSock(){
        return customerSock;
    }

    public int[] getWait(){
        return waiting;
    }
    public int[] getBrew(){return brewing;}
    public int[] getTray(){return tray;}

    public void setWait(String[] newOr) {
        waiting[0] += Integer.parseInt(newOr[1]);
        waiting[1] += Integer.parseInt(newOr[2]);
    }

    public void upWait(int prod){
        if(prod ==1){
            waiting[0] += -1;
        }else{
            waiting[1] += -1;
        }

    }

    public void upBrew(int prod){ //Function to move products from waiting to Brewing
        if(prod == 0) {
            brewing[0] += 1;
            waiting[0] -= 1;
        }else{
            brewing[1] += 1;
            waiting[1] -= 1;
        }

    }

    public void upTray(int prod){ //Function to move products from Brewing state to Tray state
        if(prod == 0){
            brewing[0] -= 1;
            tray[0] += 1;
        }else{
            brewing[1] -= 1;
            tray[1] += 1;
        }
    }

    public void clearTray(){ //Array clear function
        tray[0] = 0;
        tray[1] = 0;
    }

    public boolean getState(){
        int test1 = waiting[0];
        int test2 = waiting[1];
        int test3 = brewing[0];
        int test4 = brewing[1];
        if(test1 == 0 && test2 == 0 && test3 == 0 && test4 == 0){
            cusState = false;
        }
        return cusState;
    }

    public boolean teaCoff(String wordInp){ //Basic input check to ensure product is the correct variant of the accepted products
        if(wordInp.equalsIgnoreCase("tea") || wordInp.equalsIgnoreCase("teas") || wordInp.equalsIgnoreCase("coffee") || wordInp.equalsIgnoreCase("coffees")){
            return true;
        }else{
            return false;
        }
    }
}
