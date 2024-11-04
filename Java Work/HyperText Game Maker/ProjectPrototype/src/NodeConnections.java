import java.util.*;


public class NodeConnections {
    private ArrayList<int[]> connData;
    private ArrayList<String[]> connText;

    public void connObj(ArrayList<int[]> inpData, ArrayList<String[]> inpText){
        this.connData = inpData;
        this.connText = inpText;
        System.out.println("Generated");
    }

    public ArrayList<int[]> getConnects(){
        return this.connData;
    }

    public void addConn(int[] newCon){
        this.connData.add(newCon);
        String[] temp = {"2", "Temp"};
        this.connText.add(temp);
    }

    public void removeConn(String nodeID){
        for(int i = 0; i < this.connText.size(); i++){
            int[] temp = this.connData.get(i);
            if (temp[1] == Integer.parseInt(nodeID)){
                this.connData.remove(i);
                this.connText.remove(i);
                break;
            }
        }
    }

    public String getConnText(String nodeID){
        for(int i = 0; i < connText.size(); i++){
            int[] temp = connData.get(i);
            if (temp[1] == Integer.parseInt(nodeID)){
                String[] tempInfo = connText.get(i);
                return tempInfo[1];
            }
        }
        return "No Connection Text Found";
    }
}
