import java.awt.*;
import java.util.*;

public class ArrayManage extends NodeManager{
    ArrayList<NodeManager> nodeList = new ArrayList<NodeManager>();
    ArrayList<String> actionList = new ArrayList<>();
    ArrayList<String[]> fileReadConTex = new ArrayList<>();
    ArrayList<ArrayList<int[]>> fileReadCons = new ArrayList<>();
    ArrayList<String[]> fileReadConAct = new ArrayList<>();
    int[] posAdjust = {0,0};
    String filePath =  "new file";

    public void updateList(ArrayList<NodeManager> inpList){
        this.nodeList = inpList;
    }

    public void objStore(NodeManager newNode){
        nodeList.add(newNode);
    }

    public ArrayList<NodeManager> getArray(){
        return nodeList;
    }

    public void addAction(String actionName){
        actionList.add(actionName);
    }

    public ArrayList<String> getActionList(){
        return actionList;
    }

    public void updateActionList(){
        ArrayList<String> newActions = new ArrayList<>();
        for(int i = 0; i < nodeList.size(); i++){
            ArrayList<String[]> tempList = nodeList.get(i).getActions();
            for(int j = 0; j < tempList.size(); j++){
                newActions.add(tempList.get(j)[1]);
            }
        }
        this.actionList = newActions;
    }

    public String getFilePath(){
        return filePath;
    }

    public void fileReadCons(ArrayList<ArrayList<int[]>> inpLi, ArrayList<String[]> inpTex, ArrayList<String[]> inpAct){
        for(int i = 0; i < inpLi.size(); i++){
            ArrayList<int[]> tempNodeCons = inpLi.get(i);
            NodeManager tempN = new NodeManager();
            int nodeNum = tempNodeCons.get(tempNodeCons.size() - 1)[0];
            tempNodeCons.remove(tempNodeCons.size() - 1);
            tempN = nodeRetrieve(nodeNum);
            tempN.setCons(tempNodeCons);
            System.out.println(nodeNum);
            String numTex = String.valueOf(nodeNum);
            ArrayList<String[]> conTex = new ArrayList<>();
            for(int j = 0; j < inpTex.size(); j++){
                if(numTex.equals(inpTex.get(j)[0])){
                    String[] tempBuild = {inpTex.get(j)[0], inpTex.get(j)[1],inpAct.get(j)[1]};
                    conTex.add(tempBuild);
                }
            }
            tempN.setConsText(conTex);
        }
    }

    public void updateAdjust(int x,  int y){
        posAdjust[0] += x;
        posAdjust[1] += y;
    }

    public int[] getAdjust(){
        return posAdjust;
    }

    public void resetAdjust(){
        posAdjust[0] = 0;
        posAdjust[1] = 0;
    }

    public void setFilePath(String newPath){
        this.filePath = newPath;
    }

    public boolean canvasSpace(int[] checkCoord){
        NodeManager testNode;
        for(int i = 0; i < nodeList.size(); i++){
            testNode = nodeList.get(i);
            int[] nodeCoords = testNode.getPos();
            for(int j = 0; j < 2; j++){
                if(checkCoord[j] > nodeCoords[0] && checkCoord[j] < nodeCoords[1] && checkCoord[j+2] > nodeCoords[2] && checkCoord[j+2] < nodeCoords[3]){
                    return false;
                }
            }

        }
        return true;
    }

    public NodeManager nodeRetrieve(int x, int y){
        NodeManager retNode;
        for(int i = 0; i < nodeList.size(); i++){
            retNode = nodeList.get(i);
            int[] nodeCoords = retNode.getPos();
            if(nodeCoords[0] < x && nodeCoords[1] > x && nodeCoords[2] < y && nodeCoords[3] > y){
                return retNode;
            }
        }
        return null;
    }

    public NodeManager nodeRetrieve(int nodeNum){
        NodeManager retNode;
        for(int i = 0; i < nodeList.size(); i++){
            retNode = nodeList.get(i);
            int searchNum = retNode.getNum();
            if(searchNum == nodeNum){
                return retNode;
            }
        }
        return null;
    }

    public void nodeRemove(NodeManager inpNode){
        for(int i=0; i < nodeList.size(); i++){
            NodeManager tempNode = nodeList.get(i);
            tempNode.removeCon(inpNode.getNum());
        }
        System.out.println(inpNode.getTitle() + " deleted.");
        nodeList.remove(inpNode);
    }

    public ArrayList<String> dataToString(int nodeNum){
        ArrayList<String> dataOut = new ArrayList<>();
        NodeManager node = nodeList.get(nodeNum);
        int tempNum = node.getNum();
        dataOut.add(Integer.toString(tempNum));
        String tempTitle = node.getTitle();
        dataOut.add(tempTitle);
        String tempConns = "";
        String tempConTex = "";
        for(int i = 0; i < node.getCons().size(); i++){
            int[] arrConns = node.getCons().get(i);
            int firstCoord = arrConns[0];
            int secondCoord = arrConns[1];
            tempConns += firstCoord + "," + secondCoord;
            if(arrConns[1] == 0){
                tempConTex += node.getConText(String.valueOf(arrConns[0]));
            }else{
                tempConTex += "n/a";
            }
            if(node.getCons().size() > 1 && i != node.getCons().size()){
                tempConns += "''";
                tempConTex += "''";
            }
        }
        dataOut.add(tempConns);
        dataOut.add(tempConTex);
        String tempAcName = "";
        String tempAcBool = "";
        String tempAcTex = "";
        for(int i = 0; i < node.getActions().size(); i++){
            String tempAction[] = node.getActions().get(i);
            tempAcName += tempAction[1];
            tempAcBool += tempAction[2];
            tempAcTex += tempAction[3];
            if(node.getActions().size() > 1 && i != node.getActions().size()){
                tempAcName += "''";
                tempAcBool += "''";
                tempAcTex += "''";
            }
        }
        dataOut.add(tempAcName);
        dataOut.add(tempAcBool);
        dataOut.add(tempAcTex);
        String tempContent = node.getContent();
        String newCont = "";
        Scanner tempScan = new Scanner(tempContent);
        while(tempScan.hasNextLine()){
            String tempLine = tempScan.nextLine();
            newCont += tempLine + "''";
        }
        tempScan.close();
        dataOut.add(newCont);
        Color col = node.getCol();
        String colToText = "red";
        if(col != Color.RED){
            if(col != Color.ORANGE){
                colToText = "green";
            }else{
                colToText = "orange";
            }
        }
        dataOut.add(colToText);
        int[] pos = node.getPos();
        String tempPos = pos[0] + "," + pos[1] + "," + pos[2] + "," + pos[3];
        dataOut.add(tempPos);
        String conActs = "";
        for(int i = 0; i < node.getCons().size(); i++){
            int[] tempNodeID = node.getCons().get(i);
            if(tempNodeID[1] == 0){
                conActs += node.getConAction(String.valueOf(tempNodeID[0])) + "''";
            }else{
                conActs += "N/A''";
            }
        }
        dataOut.add(conActs);
        return dataOut;
    }
}
