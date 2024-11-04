import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class FileHandler {
    File fileLoc = new File("src/files");

    public void toFileWrite(ArrayManage inpManage){
        String pathStr = "";
        String fileName = "";
        try {
            JFileChooser saveLoc = new JFileChooser();
            saveLoc.setCurrentDirectory(fileLoc);
            fileName = (String)JOptionPane.showInputDialog(null,"Input a file name:", "Save File Name", JOptionPane.PLAIN_MESSAGE,null,null,"newFile");
            saveLoc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int inpChoice = saveLoc.showOpenDialog(null);
            if(inpChoice == JFileChooser.APPROVE_OPTION){
              File saveFolder = saveLoc.getSelectedFile();
              pathStr = saveFolder.getAbsolutePath();
              System.out.println(pathStr);
            }
            ArrayList<NodeManager> inpList = inpManage.getArray();
            pathStr += "/" + fileName + ".txt";
            BufferedWriter outWrite = new BufferedWriter(new FileWriter(new File(pathStr),true));
            int test = inpList.size();
            outWrite.write(Integer.toString(test));
            for(int i = 0; i < inpList.size(); i++){
                outWrite.newLine();
                ArrayList<String> tempNode = inpManage.dataToString(i);
                outWrite.write("NodeNum: " + tempNode.get(0));
                outWrite.newLine();
                outWrite.write("NodeTitle: " + tempNode.get(1));
                outWrite.newLine();
                outWrite.write("NodeConns: " + tempNode.get(2));
                outWrite.newLine();
                outWrite.write("NodeTexts: " + tempNode.get(3));
                outWrite.newLine();
                outWrite.write("NodeActions: " + tempNode.get(4));
                outWrite.newLine();
                outWrite.write("NodeActionBool: " + tempNode.get(5));
                outWrite.newLine();
                outWrite.write("NodeActionTex: " + tempNode.get(6));
                outWrite.newLine();
                outWrite.write("NodeCont: " + tempNode.get(7));
                outWrite.newLine();
                outWrite.write("NodeCol: " + tempNode.get(8));
                outWrite.newLine();
                outWrite.write("NodePos: " + tempNode.get(9));
                outWrite.newLine();
                outWrite.write("NodeConActs: " + tempNode.get(10));
                outWrite.newLine();
            }
            outWrite.newLine();
            outWrite.write("END");
            outWrite.close();
            inpManage.setFilePath(pathStr);
        }catch (FileNotFoundException e){
            System.out.println("File not found.");
        }catch (IOException e){
            System.out.println("Stream Error");
        }
    }



    public ArrayManage fromFileRead(){
        ArrayManage newArr = new ArrayManage();
        ArrayList<ArrayList<int[]>> fullNodes = new ArrayList<>();
        ArrayList<String[]> fullConnInfo = new ArrayList<>();
        ArrayList<String[]> fullConActLi = new ArrayList<>();
        File inpFile = null;
        ArrayList<NodeManager> readList = new ArrayList<>();
        try{
            JFileChooser fileChoice = new JFileChooser();
            fileChoice.setCurrentDirectory(fileLoc);
            if(fileChoice.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                inpFile = fileChoice.getSelectedFile();
            }
            BufferedReader read = new BufferedReader( new FileReader(inpFile));
            String fileLine = read.readLine(); System.out.println("number of nodes:" + fileLine);
            int nodeAm = Integer.parseInt(fileLine);
            for(int i = 0; i < nodeAm; i++){
                fileLine = read.readLine(); System.out.println(fileLine);
                String nodeNum = fileLine.replaceFirst("NodeNum: ", "");
                fileLine = read.readLine();
                String nodeName = fileLine.replaceFirst("NodeTitle: ", "");
                fileLine = read.readLine();
                String nodeConns = fileLine.replaceFirst("NodeConns: ", "");
                ArrayList<int[]> nodeConnArr = consTrans(nodeConns);
                fileLine = read.readLine();
                String nodeConnTex = fileLine.replaceFirst("NodeTexts: ","");
                String tempReg = "''";
                String[] connTexSpli = nodeConnTex.split(tempReg);
                fileLine = read.readLine();
                String nodeActs = fileLine.replaceFirst("NodeActions: ", "");
                String[] actsSpli = nodeActs.split(tempReg);
                fileLine = read.readLine();
                String nodeActBool = fileLine.replaceFirst("NodeActionBool: ", "");
                String[] boolSpli = nodeActBool.split(tempReg);
                fileLine = read.readLine();
                String nodeActTex = fileLine.replaceFirst("NodeActionTex: ", "");
                String[] actTexSpli = nodeActTex.split(tempReg);
                fileLine = read.readLine();
                String nodeContent = fileLine.replaceFirst("NodeCont: ", "");
                nodeContent = nodeContent.replace("''", "\n");
                fileLine = read.readLine();
                String nodeColTex = fileLine.replaceFirst("NodeCol: ", "");
                fileLine = read.readLine();
                String nodePosTex = fileLine.replaceFirst("NodePos: ", "");
                String[] posSpli = nodePosTex.split(",");
                fileLine = read.readLine();
                String nodeConActs = fileLine.replaceFirst("NodeConActs: ", "");
                String[] conActsSpli = nodeConActs.split(tempReg);
                int[] nodePos = {0,0,0,0};
                for(int j = 0; j < 4; j++){
                    nodePos[j] = Integer.parseInt(posSpli[j]);
                }

                NodeManager tempNode = new NodeManager();
                for(int k = 0; k < nodeConnArr.size(); k++){
                    int[] tempCon = nodeConnArr.get(k);;
                    String[] cTex = {String.valueOf(tempCon[0]), connTexSpli[k]};
                    String[] conAct = {String.valueOf(tempCon[0]), conActsSpli[k]};
                    fullConnInfo.add(cTex);
                    fullConActLi.add(conAct);
                }
                int[] test = {Integer.parseInt(nodeNum)};
                nodeConnArr.add(test);
                ArrayList<String[]> connInfo = new ArrayList<>();
                ArrayList<int[]>  nodeConnTemp = new ArrayList<>();
                tempNode.NodeObj(Integer.parseInt(nodeNum) , nodeName, nodeConnTemp, connInfo, nodeContent, Color.RED, nodePos);
                tempNode.setCol(nodeColTex);
                for(int l = 0; l < actsSpli.length; l++){
                    tempNode.addAction("Item", actsSpli[l], boolSpli[l], actTexSpli[l]);
                }
                readList.add(tempNode);
                fileLine = read.readLine();
                fullNodes.add(nodeConnArr);
            }

        }catch (FileNotFoundException e){
            System.out.println("File not found.");
        }catch (IOException e){
            e.printStackTrace();
        }
        newArr.updateList(readList);
        newArr.fileReadCons(fullNodes, fullConnInfo, fullConActLi);
        return newArr;
    }

    public ArrayList<int[]> consTrans(String inpStr){
        ArrayList<int[]> tempCons = new ArrayList<>();
        if(!Objects.equals(inpStr, "")){
            int[] conCoord = {0,0};
            String[] consSplit = inpStr.split("''");
            for(int i = 0; i < consSplit.length; i++){
                String indiSplit = consSplit[i];
                if(!Objects.equals(indiSplit, "")){
                    String[] tempSpli = indiSplit.split(",");
                    conCoord[0] = Integer.parseInt(tempSpli[0]);
                    conCoord[1] = Integer.parseInt(tempSpli[1]);
                    System.out.println(conCoord[0] +  "," + conCoord[1]);
                    tempCons.add(conCoord);
                }
            }
        }
        return tempCons;
    }

    public void writeExport(ArrayManage nodeOut){
        ArrayList<NodeManager> allNodes = nodeOut.getArray();
        ArrayList<String> allActions = nodeOut.getActionList();
        //Folder Creation
        File newGameDir = new File("gameExport");
        if(!newGameDir.exists()){
            newGameDir.mkdirs();
        }
        //File Writing
        try{
            FileWriter styleWrite = new FileWriter("gameExport/gameStyle.css");
            styleWrite.write("");
            styleWrite.close();
            System.out.println("Style written.");
            FileWriter gameWrite = new FileWriter("gameExport/gameMain.htm");
            gameWrite.write("");
            gameWrite.close();
            System.out.println("Game written");
            FileWriter scriptWrite = new FileWriter("gameExport/gameScript.js");
            scriptWrite.write("");
            scriptWrite.close();
            File[] fileStore = {null,null,null,null,null,null};
            fileStore[0] = new File("generationFiles/styleFile.txt");
            fileStore[1] = new File("generationFiles/gameFile.txt");
            fileStore[2] = new File("generationFiles/scriptFile.txt");
            fileStore[3] = new File("gameExport/gameStyle.css");
            fileStore[4] = new File("gameExport/gameMain.htm");
            fileStore[5] = new File("gameExport/gameScript.js");
            for(int i  = 0; i < 3; i++){
                FileChannel src = new FileInputStream(fileStore[i]).getChannel();
                FileChannel dest = new FileOutputStream(fileStore[i+3]).getChannel();
                dest.transferFrom(src,0,src.size());
                src.close();
                dest.close();
            }
            String fileCap = infoArrBuilder(nodeOut);
            FileOutputStream fos = new FileOutputStream("gameExport/gameScript.js", true);
            fos.write(fileCap.getBytes());
            fos.close();
        }  catch (IOException e){
            System.out.println("Error Reading");
            e.printStackTrace();
        }
    }

    String infoArrBuilder(ArrayManage inpArr){
        String output = "";
        ArrayList<String[]> tempAcList = new ArrayList<>();
        for(int i = 0; i < inpArr.getArray().size();i++){
            NodeManager tempNode = inpArr.getArray().get(i);
            String newCont = "";
            Scanner tempScan = new Scanner(tempNode.getContent());
            while(tempScan.hasNextLine()){
                String tempLine = tempScan.nextLine();
                newCont += tempLine + "<br />";
            }
            tempScan.close();
            output += "\t\tvar tempObj = {nodeID: " + tempNode.getNum() + ", nodeTitle: \"" + tempNode.getTitle() + "\", nodeContent: \""+ newCont + "\", connIDs: ";
            String conIDs = "[";
            String conTex = "[";
            String conAct = "[";
            for(int j = 0; j < tempNode.getCons().size(); j++){
                int[] tempCon = tempNode.getCons().get(j);
                if(tempCon[1] == 0) {
                    conIDs += tempCon[0] + ", ";
                    conTex += "\"" + tempNode.getConText(String.valueOf(tempCon[0])) + "\", ";
                    conAct += "\"" + tempNode.getConAction(String.valueOf(tempCon[0])) + "\", ";
                }
            }
            System.out.println(conIDs + " " + conTex + " " + conAct);
            String tempId = "";
            String tempTex = "";
            String tempAct = "";
            if(tempNode.getCons().size() != 0 && !conIDs.equals("[")){
                tempId = conIDs.substring(0, conIDs.length()-2);
                tempTex = conTex.substring(0,conTex.length()-2);
                tempAct = conAct.substring(0,conAct.length()-2);
            }else{
                tempId = conIDs;
                tempTex = conTex;
                tempAct = conAct;
            }
            output += tempId + "], connTex: ";
            output += tempTex + "], connAct: ";
            output += tempAct + "], actNames: [";
            String actNames = "";
            String actTex = "";
            for(int k = 0; k < tempNode.getActions().size(); k++){
                String[] tempAction = tempNode.getActions().get(k);
                tempAcList.add(tempAction);
                actNames += "\"" + tempAction[1] + "\", ";
                actTex += "\"" + tempAction[3] + "\", ";
            }
            String tempName = "";
            String tempATex = "";
            if(tempNode.getActions().size() != 0){
                tempName = actNames.substring(0, actNames.length()-2);
                tempATex = actTex.substring(0,actTex.length()-2);
            }
            output += tempName + "], actTex: [";
            output += tempATex + "]};";
            output += "\n\t\tnodeInfoArr.push(tempObj);\n";
        }

        output += "\t},\n\tfeedActions: function(){\n";
        System.out.println();
        if(tempAcList.size() != 0){
            for(int u = 0; u < tempAcList.size(); u++){
                String[] tempAction = tempAcList.get(u);
                String tempTog = "true";
                if(tempAction[2] == "TOGGLE"){
                    tempTog = "false";
                }
                output += "\t\tvar acInp = {actionID: \"" + tempAction[1] +"\", actionBool: false, actionOneTime: " + tempTog +"}; \n\t\tactionArr.push(acInp);\n\t}\n}" ;
            }
        }else{
            output += "\t\tvar acInp = {actionID: \"\", actionBool: false, actionOneTime: false}; \n\t\tactionArr.push(acInp);\n\t}\n}";
        }
        return output;
    }
}
