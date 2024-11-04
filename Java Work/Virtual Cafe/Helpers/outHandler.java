package Helpers;

public class outHandler {
    public String orderAck(Order inpOr){ //Output compilier that verifies the Customer's input and lists the accepted products
        String outStr = "Order recieved for ";
        outStr += inpOr.getName();
        int[] tempProd = inpOr.getWait();
        if(tempProd[0] != 0){
            outStr += " (" + tempProd[0] + " teas";
            if(tempProd[1] != 0){
                outStr += " and " + tempProd[1] + " coffees)";
            }else{
                outStr += ")";
            }
        }else{
            outStr += " (" + tempProd[1] + " coffees)";
        }
        return outStr;
    }

    public String orderStat(Order inpOr){ //Output compilier that runs through an Order's int arrays to inform the customer of their Order's status
        int[] tempWait = inpOr.getWait();
        int[] tempBrew = inpOr.getBrew();
        int[] tempTray = inpOr.getTray();
        String outStr = "Order Status for " + inpOr.getName() + System.lineSeparator();
        outStr += tempWait[0] + " teas and " + tempWait[1] + " coffees waiting to brew." + System.lineSeparator();
        outStr += tempBrew[0] + " teas and " + tempBrew[1] + " coffees on the brew." + System.lineSeparator();
        outStr += tempTray[0] + " teas and " + tempTray[1] + " coffees on the tray." + System.lineSeparator();
        return outStr;
    }

    public String orderComp(Order inpOr){ //Final output compilier that informs the customer of their finished order
        int[] tempTray = inpOr.getTray();
        String outStr = "Order delivered to " + inpOr.getName() + " (" + tempTray[0] + " teas and " + tempTray[1] + " coffees)";
        return outStr;
    }
}
