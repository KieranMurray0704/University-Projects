import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class InputFrame extends JFrame {
    NodeManager inpNode = new NodeManager();
    ArrayManage nodeList = new ArrayManage();
    String nodeTitle = "";
    JLabel nodeName = new JLabel("Story Node Name: ");
    JTextField nodeNameInp = new JTextField("");
    JLabel nodeContent = new JLabel("Node Content:");
    JTextArea nodeContentInp = new JTextArea(50,50);
    JButton nodeInput = new JButton("SAVE");
    JLabel conLabel = new JLabel("Connections:");
    JLabel actLabel = new JLabel("Actions:");
    JLabel colLabel = new JLabel("Set Node State:");
    JPanel connectionsPan = new JPanel();
    JPanel actionsPan = new JPanel();
    String[] nodeStateList = {"New Node (RED)", "Incomplete (ORANGE)", "Complete (GREEN)"};
    JComboBox colourDrop = new JComboBox(nodeStateList);
    ArrayList<int[]> connList = new ArrayList<>();
    ArrayList<JTextField> conTexInput = new ArrayList<>();
    ArrayList<JComboBox<String>> conActLi = new ArrayList<>();
    ArrayList<JComboBox<String>> actionTypeLi =  new ArrayList<>();
    ArrayList<JTextField> actionNameLi = new ArrayList<>();
    ArrayList<JTextField> actionTexInpLi = new ArrayList<>();
    ArrayList<JCheckBox> actionTogLi = new ArrayList<>();
    GridBagConstraints inpC = new GridBagConstraints();

    public void iniFrame(){

        setSize(800,400);
        setLocationRelativeTo(null);
        nodeInput.addActionListener(e -> nodeUp(nodeNameInp.getText(), nodeContentInp.getText()));
        addWindowListener(new CustomWindowListener());

        colourDrop.setSelectedIndex(0);
        colourDrop.addActionListener(e -> {
            JComboBox test = (JComboBox)e.getSource();
            String colChoice = (String)test.getSelectedItem();
            inpNode.setCol(colChoice);
        });

        JScrollPane inputScroll = new JScrollPane(nodeContentInp);

        GridBagLayout inpLay = new GridBagLayout();
        setLayout(inpLay);
        inpC.fill = GridBagConstraints.HORIZONTAL;
        inpC.gridx = 0; inpC.gridy = 0;
        inpC.weightx = 1.0;
        inpC.gridwidth = 3;
        inpC.anchor = GridBagConstraints.PAGE_START;
        add(nodeInput, inpC);
        inpC.weightx = 1.0;
        inpC.gridwidth = 1;
        inpC.gridx = 0; inpC.gridy = 1;
        add(nodeName, inpC);
        inpC.gridx = 1; inpC.gridy = 1;
        add(nodeNameInp, inpC);
        inpC.gridx = 0; inpC.gridy = 2;
        inpC.anchor = GridBagConstraints.CENTER;
        add(nodeContent, inpC);
        inpC.gridx = 0; inpC.gridy = 3;
        inpC.weightx = 0.0;
        inpC.ipady = 80;
        inpC.gridwidth = 3;
        inpC.gridheight = 2;
        add(inputScroll, inpC);
        inpC.gridheight = 1;
        inpC.gridwidth = 1;
        inpC.gridx = 0; inpC.gridy = 5;
        inpC.ipady = 10;
        add(colLabel, inpC);
        inpC.gridx = 1; inpC.gridy = 5;
        add(colourDrop, inpC);
        inpC.gridx = 0; inpC.gridy = 6;
        inpC.weightx = 1.0;
        inpC.gridwidth = 1;
        add(conLabel, inpC);
        inpC.gridx = 0; inpC.gridy = 7;
        inpC.ipady = 50;
        inpC.gridwidth = 3;
        inpC.gridheight = 2;
        JScrollPane connScroll = new JScrollPane(connectionsPan);
        add(connScroll, inpC);
        inpC.gridx = 0; inpC.gridy = 8;
        inpC.weightx = 1.0;
        inpC.gridwidth = 1;
        add(actLabel, inpC);
        inpC.gridx = 0; inpC.gridy = 9;
        inpC.ipady = 50;
        inpC.gridwidth = 3;
        inpC.gridheight= 2;
        JScrollPane actionScroll = new JScrollPane(actionsPan);
        add(actionScroll, inpC);
    }

    public void nodeUp(String title, String content){
        inpNode.setTitle(title);
        String newContent = "";
        Scanner tempScan = new Scanner(content);
        while(tempScan.hasNextLine()){
            String tempLine = tempScan.nextLine();
            newContent += tempLine + "\n";
        }
        tempScan.close();
        inpNode.setContent(newContent);
        ArrayList<String[]> connText = new ArrayList<>();
        //System.out.println(conTexInput.size());
        ArrayList<int[]> tempConns = inpNode.getCons();
        //System.out.println("Number of written Connects: " + conTexInput.size());
        int texFieldLoop = 0;
        if(conTexInput.size() > 0){
            for(int i = 0; i < tempConns.size(); i++){
                if(texFieldLoop < conTexInput.size()){
                    JTextField tempTexField = conTexInput.get(texFieldLoop);
                    int[] tempType = tempConns.get(i);
                    //System.out.println("Connection type: " + tempType[1]);
                    if(tempType[1] == 0){
                        System.out.println(tempTexField.getText() + " -> " + tempType[0]);
                        if(inpNode.getCons().size() > 0){
                            String[] test = {String.valueOf(tempType[0]),tempTexField.getText(),conActLi.get(i).getSelectedItem().toString()};
                            System.out.println(test[0] + test[1] + test[2]);
                            connText.add(test);
                            texFieldLoop += 1;
                        }
                    }
                }
            }
        }
        if(inpNode.getActions().size() > 0){
            ArrayList<String[]> newActList = new ArrayList<>();
            for(int j = 0; j < inpNode.getActions().size(); j++){
                JComboBox<String> temp = actionTypeLi.get(j);
                String type = "Action";
                if(temp.getSelectedItem() == "Item"){
                    type= "Action";
                }
                String tog = "ONE TIME";
                if(actionTogLi.get(j).isSelected()){
                    tog = "TOGGLE";
                }
                String[] tempActBuild = {type,actionNameLi.get(j).getText(), tog, actionTexInpLi.get(j).getText()};
                newActList.add(tempActBuild);
            }
            inpNode.updateActions(newActList);
        }
        inpNode.setConsText(connText);
        setTitle(title);
    }

    public int iniConnections(){
        connList = inpNode.getCons();
        if(connList !=null){
            //System.out.println(connList.size());
            return connList.size();
        }else{
            return 0;
        }
    }

    class CustomWindowListener implements WindowListener{
        public void windowOpened(WindowEvent e) {
        }

        public void windowClosing(WindowEvent e) {
            connectionsPan.removeAll();
        }

        public void windowClosed(WindowEvent e) {
        }

        public void windowIconified(WindowEvent e) {
        }

        public void windowDeiconified(WindowEvent e) {
        }

        public void windowActivated(WindowEvent e) {
        }

        public void windowDeactivated(WindowEvent e) {
        }
    }


    public void clickEvent(NodeManager activeNode, ArrayManage listInput){
        inpNode = activeNode;
        nodeList = listInput;
        conTexInput.clear();
        actionTypeLi.clear();
        actionTexInpLi.clear();
        actionNameLi.clear();
        actionTogLi.clear();
        conActLi.clear();
        connectionsPan.removeAll();
        actionsPan.removeAll();
        int conLoop = iniConnections();
        if(conLoop != 0){
            //System.out.println(conLoop);
            GridBagLayout inpLay = new GridBagLayout();
            connectionsPan.setLayout(inpLay);
            for(int i = 0; i < conLoop; i++ ){
                JComboBox<String> actionDrop =  new JComboBox<>();
                for(int a = 0; a < nodeList.getActionList().size(); a++){
                    actionDrop.addItem(nodeList.getActionList().get(a));
                }
                conActLi.add(actionDrop);
                int[] tempConnInfo = connList.get(i);
                //System.out.println(tempConnInfo[0]);
                NodeManager temp = nodeList.nodeRetrieve(tempConnInfo[0]);
                JLabel tempLab = new JLabel("");
                if(tempConnInfo[1] == 0){
                    tempLab.setText("CONNECTION TO " + temp.getTitle());
                }else{
                    tempLab.setText("CONNECTION FROM " + temp.getTitle());
                }
                JTextField tempTex = new JTextField(activeNode.getConText(String.valueOf(tempConnInfo[0])));
                NodeManager tempNode = nodeList.nodeRetrieve(tempConnInfo[0]);
                JLabel tempConLab = new JLabel(tempNode.getConText(String.valueOf(activeNode.getNum())));
                String tempAct = inpNode.getConAction(String.valueOf(tempConnInfo[0]));
                System.out.println(tempAct);
                for(int b = 0; b < nodeList.getActionList().size(); b++){
                    if(Objects.equals(nodeList.getActionList().get(b), tempAct)){
                        actionDrop.setSelectedIndex(b);
                    }
                }
                JLabel actLab = new JLabel(temp.getConAction(String.valueOf(inpNode.getNum())));
                inpC.gridheight = 1;
                inpC.gridwidth = 1;
                inpC.gridx = 0; inpC.gridy =  i;
                inpC.ipady = 10;
                connectionsPan.add(tempLab, inpC);
                inpC.gridx = 1; inpC.gridy =  i;
                inpC.gridwidth = 1;
                if(tempConnInfo[1] == 0){
                    connectionsPan.add(actionDrop, inpC);
                    inpC.gridx = 2; inpC.gridy = i;
                    connectionsPan.add(tempTex, inpC);
                    conTexInput.add(tempTex);
                }else{
                    connectionsPan.add(actLab, inpC);
                    inpC.gridx = 2; inpC.gridy = i;
                    connectionsPan.add(tempConLab, inpC);
                }
            }
        }
        String[] typeDrop = {"Item", "Action"};
        GridBagLayout actLay = new GridBagLayout();
        actionsPan.setLayout(actLay);
        for(int j = 0; j < inpNode.getActions().size(); j++){
            String[] tempAction = inpNode.getActions().get(j);
            JComboBox<String> actionType = new JComboBox<>(typeDrop);
            if(Objects.equals(tempAction[0], "Item")){
                actionType.setSelectedIndex(0);
            }else{
                actionType.setSelectedIndex(1);
            }
            actionTypeLi.add(actionType);
            JTextField actionName = new JTextField(tempAction[1]);
            actionNameLi.add(actionName);
            JLabel togLab = new JLabel("Toggleable?");
            JCheckBox actionTog = new JCheckBox();
            if(Objects.equals(tempAction[2], "TOGGLE")){
                actionTog.setSelected(true);
            }
            actionTogLi.add(actionTog);
            JTextField actionTex = new JTextField(tempAction[3]);
            actionTexInpLi.add(actionTex);

            inpC.gridheight = 1;
            inpC.gridwidth = 1;
            inpC.gridx = 0; inpC.gridy = j;
            inpC.ipady = 10;
            inpC.ipadx = 5;
            actionsPan.add(actionType,inpC);
            inpC.gridx = 1; inpC.gridy =  j;
            inpC.ipadx = 10;
            actionsPan.add(actionName,inpC);
            inpC.gridx = 2; inpC.gridy = j;
            actionsPan.add(togLab, inpC);
            inpC.gridx = 3; inpC.gridy = j;
            actionsPan.add(actionTog, inpC);
            inpC.gridx = 4; inpC.gridy = j;
            inpC.gridwidth = 2;
            actionsPan.add(actionTex, inpC);
        }
        setTitle(activeNode.getTitle());
        nodeNameInp.setText(activeNode.getTitle());
        nodeContentInp.setText(activeNode.getContent());
        if(inpNode.getCol() == Color.RED){
            colourDrop.setSelectedIndex(0);
        }else if(inpNode.getCol() == Color.ORANGE){
            colourDrop.setSelectedIndex(1);
        }else{
            colourDrop.setSelectedIndex(2);
        }
        actionsPan.invalidate();
        actionsPan.validate();
        actionsPan.repaint();
        connectionsPan.invalidate();
        connectionsPan.validate();
        connectionsPan.repaint();
        setVisible(true);
    }
}
