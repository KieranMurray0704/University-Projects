import java.util.*;
import java.awt.*;

public class NodeManager {
    private int nodeNum;
    private String nodeTitle;
    private ArrayList<int[]> nodeConnections;
    private String nodeText;
    private Color nodeColour;
    private int[] nodePos;
    private ArrayList<String[]> nodeConnTexts;
    private ArrayList<String[]> nodeActions;

    public void NodeObj(int num, String title, ArrayList<int[]> connects, ArrayList<String[]> connectsTex, String textCont, Color col, int[] pos){
        this.nodeNum = num;
        this.nodeTitle = title;
        this.nodeConnections = connects;
        this.nodeConnTexts = connectsTex;
        this.nodeText = textCont;
        this.nodeColour = col;
        this.nodePos = pos;
        this.nodeActions = new ArrayList<>();
    }

    public int getNum(){return nodeNum;}

    public String getTitle(){return nodeTitle;}

    public void setTitle(String newTitle){
        nodeTitle = newTitle;
    }

    public int[] getPos(){
        return nodePos;
    }

    public void setPos(int[] newPos){
        this.nodePos = newPos;
    }

    public void setCons(ArrayList<int[]> newCons){
        this.nodeConnections = newCons;
    }

    public void setConsText(ArrayList<String[]> consText){
        this.nodeConnTexts = consText;
    }

    public void removeCon(int nodeID){
        ArrayList<int[]> tempIDList = this.getCons();
        ArrayList<String[]> tempTexList = this.getConsTList();
        int[] found = {0,0,0};
        //System.out.println(this.nodeNum + " - IDs = " + tempIDList.size() + " texts = " + tempTexList.size());
        System.out.println("Node: " + this.nodeNum + ". ID Connections: " + tempIDList.size() + " Text Connections: " + tempTexList.size());
        for(int i=0;i<tempIDList.size();i++){
            int[] idTemp = tempIDList.get(i);
            String[] texTemp =  tempTexList.get(i);
            if(idTemp[0] ==  nodeID){
                found[0] = 1;
                found[1] = i;
            }
            if(Integer.parseInt(texTemp[0]) == nodeID){
                found[0] = 1;
                found[2] = i;
            }
        }
        tempIDList.remove(found[1]);
        tempTexList.remove(found[2]);
        this.setCons(tempIDList);
        this.setConsText(tempTexList);
    }

    public ArrayList<String[]> getConsTList(){
        return nodeConnTexts;
    }

    public void addCons(int[] newCon){
        nodeConnections.add(newCon);
    }

    public ArrayList<int[]> getCons(){
        return nodeConnections;
    }

    public String getConText(String nodeID){
        if(this.nodeConnTexts != null && nodeConnTexts.size() > 0){
            for (String[] temp : nodeConnTexts) {
                if (Objects.equals(temp[0], nodeID)) {
                    return temp[1];
                }
            }
            return "No Text Found";
        }
        return "New Connection";
    }

    public String getConAction(String nodeID){
        if(this.nodeConnTexts != null && nodeConnTexts.size() > 0){
            for (String[] temp : nodeConnTexts) {
                System.out.println("length: " + temp.length);
                if (temp.length > 2) {
                    System.out.println(temp[0] + " = " + nodeID);
                    if (Objects.equals(temp[0], nodeID)) {
                        return temp[2];
                    }
                }
            }
            return "No Action";
        }
        return "New Connection";
    }

    public String printCons(){
        String conOut = "Connections: " + "\r\n";
        for(int i = 0; i < nodeConnections.size(); i++){
            int[] tempCon = nodeConnections.get(i);
            System.out.println("Node Connection: " + tempCon[0] + ", state: " + tempCon[1]);
            if(tempCon[1] == 0){
                conOut += this.getNum() + " -> " + tempCon[0] + "\r\n";
            }else{
                conOut += tempCon[0] + " -> " + this.getNum() + "\r\n";
            }
        }
        return conOut;
    }

    public void setContent(String newContent){
        this.nodeText = newContent;
    }

    public String getContent(){
        return nodeText;
    }

    public void setCol(String inpCol){
        Color newCol;
        if(Objects.equals(inpCol, "New Node (RED)") || Objects.equals(inpCol, "red")){
            newCol = Color.RED;
        }
        else if(Objects.equals(inpCol, "Incomplete (ORANGE)") || Objects.equals(inpCol, "orange")){
            newCol = Color.ORANGE;
        }
        else if(Objects.equals(inpCol, "Complete (GREEN)") || Objects.equals(inpCol, "green")){
            newCol = Color.GREEN;
        }else{
            newCol = Color.BLACK;
        }
        this.nodeColour = newCol;
    }

    public Color getCol(){
        return nodeColour;
    }

    public void addAction(String acType, String acName, String oneTime, String actionTex){
        String[] newAction = {acType, acName, oneTime, actionTex};
        //System.out.println("New " + acType + ": " + acName + ". One time use: " + oneTime);
        this.nodeActions.add(newAction);
    }

    public void updateActions(ArrayList<String[]> newList){
        this.nodeActions = newList;
    }

    public ArrayList<String[]> getActions(){
        return this.nodeActions;
    }
}
