package Helpers;


import java.util.Objects;

public class Brew extends Thread{
    String product;
    Order order;

    public Brew(String product, Order inpOr){
        this.product = product;
        this.order = inpOr;
    }


    @Override
    public void run() {
        if(Objects.equals(product, "tea")){
            try {
                System.out.println("TEA BEING BREWED FOR: " + order.getName());
                Thread.sleep(30000);
                order.upTray(0);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }else{
            try {
                System.out.println("COFFEE BEING BREWED FOR: " + order.getName());
                Thread.sleep(45000);
                order.upTray(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
