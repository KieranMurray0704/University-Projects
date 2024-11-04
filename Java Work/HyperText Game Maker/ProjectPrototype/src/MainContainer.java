import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;


public class MainContainer extends JFrame implements ActionListener, MouseListener, KeyListener{
    NodeManager node = new NodeManager();
    ArrayManage nodeList = new ArrayManage();
    InputFrame inpFr = new InputFrame();
    ActionFrame actFr = new ActionFrame();
    FileHandler fileHandle = new FileHandler();
    DrawPanel drPan = new DrawPanel(this);

    JMenuBar mainMB = new JMenuBar();

    boolean boolNew = false;
    boolean boolConEdit = false;
    boolean boolMove = false;
    boolean boolDEL = false;
    boolean boolAc = false;
    int nodeManipStage = 0;
    int[] tempNodeStore = {0,0};

    int[] viewPosAdj = {0,0};

    JButton resetViewButt = new JButton("Reset View");
    JButton leftButt = new JButton("LEFT");
    JButton upButt = new JButton("UP");
    JButton downButt = new JButton("DOWN");
    JButton rightButt = new JButton("RIGHT");

    JButton newButt = new JButton("Add New Nodes");
    JButton moveButt = new JButton("Move Nodes");
    JButton connButt = new JButton("Edit Connections");
    JButton actionButt = new JButton("Add Action/Item");
    JButton delButt = new JButton("DELETE Node");
    JButton refButt = new JButton("REFRESH");

    JMenuItem mOpen = new JMenuItem("Open");
    JMenuItem mSave = new JMenuItem("Save");
    JMenuItem mSaveAs = new JMenuItem("Save As");
    JMenuItem mExport = new JMenuItem("Export Project");

    JMenuItem mHelpNodes = new JMenuItem("Nodes & Connections");

    JOptionPane errorPop = new JOptionPane("ERROR MESSAGE", JOptionPane.INFORMATION_MESSAGE);


    public void actionPerformed(ActionEvent e){
        if(e.getSource() == mSaveAs){
            fileHandle.toFileWrite(nodeList);
        }else if(e.getSource() == mOpen){
            nodeList = fileHandle.fromFileRead();
            nodeList.updateActionList();
            redrawArr();
        }else if(e.getSource() == resetViewButt){
            viewPosAdj[0] = 0;
            viewPosAdj[1] = 0;
            nodeList.resetAdjust();
            redrawArr();
        }else if(e.getSource()==leftButt){
            viewPosAdj[0] -= 50;
            nodeList.updateAdjust(-50,0);
            redrawArr();
        }else if(e.getSource()==upButt){
            viewPosAdj[1] -= 50;
            nodeList.updateAdjust(0,-50);
            redrawArr();
        }else if(e.getSource()==downButt){
            viewPosAdj[1] += 50;
            nodeList.updateAdjust(0,50);
            redrawArr();
        }else if(e.getSource()==rightButt){
            viewPosAdj[0] += 50;
            nodeList.updateAdjust(50,0);
            redrawArr();
        }else if(e.getSource() == newButt){
            if(!boolNew){
                newButt.setText("CREATING NEW NODE");
                boolMove = false; boolDEL = false; boolConEdit = false;
                boolNew = true;
            }else{
                newButt.setText("Add New Nodes");
                boolNew = false;
            }
        }else if(e.getSource() == connButt){
            if(!boolConEdit){
                connButt.setText("ADDING NEW CONNECTION");
                newButt.setText("Add New Nodes");
                boolMove = false; boolDEL = false; boolNew = false;
                boolConEdit = true;
                nodeManipStage = 0;
            }else{
                connButt.setText("Edit Connections");
                boolConEdit = false;
                nodeManipStage = 0;
            }
        }
        else if(e.getSource() == moveButt){
            if(!boolMove){
                moveButt.setText("MOVING NODE");
                newButt.setText("Add New Nodes");
                boolConEdit = false; boolDEL = false; boolNew = false; boolAc = false;
                boolMove = true;
            }else{
                moveButt.setText("Move Nodes");
                boolMove = false;
            }
        }
        else if(e.getSource() == actionButt){
            if(!boolAc){
                boolConEdit = false; boolDEL = false; boolNew = false;
                boolAc = true;
                actionButt.setText("ADDING ACTION/ITEM");
            }else{
                actionButt.setText("Add Action/Item");
                boolAc = false;
            }
        }
        else if(e.getSource() == delButt){
            if(!boolDEL){
                delButt.setText("DELETING NODE");
                newButt.setText("Add New Nodes");
                boolConEdit = false; boolMove = false; boolNew = false; boolAc = false;
                boolDEL = true;
            }else{
                delButt.setText("DELETE Node");
                boolDEL = false;
            }
        }
        else if(e.getSource() == refButt){
            redrawArr();
        }
        else if(e.getSource() == mExport){
            fileHandle.writeExport(nodeList);
        }
    }

    public void redrawArr(){
        drPan.drawArray(nodeList);
    }

    //Overall Mouse Click check
    public void mouseClicked(MouseEvent e){
        int xCheck, yCheck, xMin, xMax, yMin, yMax;
        xCheck = e.getX(); //Click X,Y position, directly where user clicks
        yCheck = e.getY();
        xMin = xCheck - 50; //Min max of the 100px area around where the user clicks
        xMax = xCheck + 50;
        yMin = yCheck - 50;
        yMax = yCheck + 50;
        while(true){
            int[] posCoords = {xMin,xMax,yMin ,yMax};
            if(nodeList.canvasSpace(posCoords) && nodeList.nodeRetrieve(xCheck,yCheck) == null && boolNew){
                System.out.println("Area clear and in-Range");
                NodeManager newNode = new NodeManager();
                ArrayList<NodeManager> listCount = nodeList.getArray();
                ArrayList<int[]> newCons = new ArrayList<>();
                ArrayList<String[]> newConTex = new ArrayList<>();
                int nodeNum = listCount.size();
                int[] adjustPos = {xMin - viewPosAdj[0], xMax - viewPosAdj[0], yMin - viewPosAdj[1], yMax - viewPosAdj[1]};
                newNode.NodeObj(nodeNum,"NEW NODE", newCons, newConTex, "CONTENT", Color.RED, adjustPos);
                nodeList.objStore(newNode);
                redrawArr();
                break;
            }
            else{
                node = nodeList.nodeRetrieve(xCheck-viewPosAdj[0],yCheck-viewPosAdj[1]);
                if(boolDEL && node != null){
                    int delInp = JOptionPane.showConfirmDialog(null, "Do you wish to delete " + node.getTitle() + "?", "Delete " + node.getTitle() +"?", JOptionPane.YES_NO_OPTION);
                    if(delInp == 0){
                        nodeList.nodeRemove(node);
                    }
                    boolDEL = false;
                    delButt.setText("DELETE Node");
                    redrawArr();
                    break;
                }else if(!boolConEdit && !boolMove && node !=null){
                    if(boolAc){
                        actionButt.setText("Add Action/Item");
                        actFr.open(node,nodeList);
                        boolAc = false;
                        break;
                    }else{
                        System.out.println("Area already filled");
                        System.out.println(node.getNum());
                        inpFr.clickEvent(node, nodeList);
                        break;
                    }

                }else{
                    if(nodeManipStage == 0 && node != null) {
                        tempNodeStore[0] = node.getNum();
                        nodeManipStage = 1;
                        System.out.println("FIRST NODE");
                        break;
                    }else{
                        if(boolConEdit && node.getNum() != tempNodeStore[0]){
                            //System.out.println("First Node: " + tempNodeStore[0]);
                            tempNodeStore[1] = node.getNum();
                            //System.out.println("Second Node: " + node.getNum());
                            ArrayList<int[]> tempLi = node.getCons();
                            boolean delCon = false;
                            for (int[] tempCheck : tempLi) {
                                if (tempCheck[0] == tempNodeStore[0]) {
                                    int delConInp = JOptionPane.showConfirmDialog(null, "Do you wish to delete this node connection?", "Delete Connection?", JOptionPane.YES_NO_OPTION);
                                    if(delConInp == 0){
                                        delCon = true;
                                    }
                                }
                            }
                            if(!delCon) {
                                int[] revCon = {tempNodeStore[0], 1};
                                //System.out.println("Node: " + revCon[0] + ", State: " + revCon[1]);
                                tempLi.add(revCon);
                                node.setCons(tempLi);
                                ArrayList<String[]> tempConTL = node.getConsTList();
                                String[] tempTex = {String.valueOf(tempNodeStore[0]), "New Connection"};
                                tempConTL.add(tempTex);
                                node.setConsText(tempConTL);
                                node = nodeList.nodeRetrieve(tempNodeStore[0]);
                                int[] forCon = {tempNodeStore[1], 0};
                                tempLi = node.getCons();
                                //System.out.println("Node: " + forCon[0] + ", State: " + forCon[1]);
                                tempLi.add(forCon);
                                node.setCons(tempLi);
                                ArrayList<String[]> newtempConTL = node.getConsTList();
                                String[] otempTex = {String.valueOf(tempNodeStore[1]), "New Connection"};
                                newtempConTL.add(otempTex);
                                node.setConsText(newtempConTL);
                                nodeManipStage = 0;
                                System.out.println("SECOND NODE");
                                connButt.setText("Edit Connections");
                            }
                            else{
                                node.removeCon(tempNodeStore[0]);
                                node = nodeList.nodeRetrieve(tempNodeStore[0]);
                                node.removeCon(tempNodeStore[1]);
                            }
                            redrawArr();
                            boolConEdit = false;
                            break;
                        }else if(boolMove && nodeList.canvasSpace(posCoords) && nodeManipStage == 1){
                            int[] moveCoords = {xMin + viewPosAdj[0],xMax + viewPosAdj[0],yMin + viewPosAdj[1],yMax + viewPosAdj[1]};
                            node = nodeList.nodeRetrieve(tempNodeStore[0]);
                            node.setPos(moveCoords);
                            tempNodeStore[0] = 0;
                            redrawArr();
                            moveButt.setText("Move Nodes");
                            nodeManipStage = 0;
                            boolMove = false;
                            break;
                        }else if(boolMove){
                            System.out.println("Node cannot be moved, area already filled.");
                            errorPop.setVisible(true);
                            break;
                        }

                    }
                }
            }
            if(boolNew){
                errorPop.setVisible(true);
                System.out.println("Cannot create here");
            }
            break; //No result from clicking
        }
        System.out.println(xCheck + " " + yCheck);
    }

    public void mouseExited(MouseEvent e){}

    public void mousePressed(MouseEvent e){}

    public void mouseReleased(MouseEvent e){}

    public void mouseEntered(MouseEvent e){}

    public void keyTyped(KeyEvent e) {
        System.out.println("yippee");
    }

    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {

    }


    public void createComps(){
        //Screen Setup
        setTitle("HyperText Creator Prototype");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500, 800);
        setBackground(Color.DARK_GRAY);
        addMouseListener(this);
        addKeyListener(this);

        //MenuBar
        JMenu mbFile = new JMenu("File");
        JMenu mbHelp = new JMenu("Help");

        mainMB.add(mbFile);
        mainMB.add(mbHelp);

        mOpen.addActionListener(this);
        mSaveAs.addActionListener(this);
        mSaveAs.addActionListener(this);
        mExport.addActionListener(this);

        mbFile.add(mOpen);
        mbFile.add(mSave);
        mbFile.add(mSaveAs);
        mbFile.add(mExport);

        mbHelp.add(mHelpNodes);

        setJMenuBar(mainMB);

        //North Buttons
        JPanel northButs = new JPanel();
        newButt.addActionListener(this);
        moveButt.addActionListener(this);
        connButt.addActionListener(this);
        actionButt.addActionListener(this);
        delButt.addActionListener(this);
        refButt.addActionListener(this);
        northButs.add(newButt, BorderLayout.WEST);
        northButs.add(moveButt, BorderLayout.WEST);
        northButs.add(connButt, BorderLayout.CENTER);
        northButs.add(actionButt, BorderLayout.CENTER);
        northButs.add(delButt, BorderLayout.EAST);
        northButs.add(refButt,BorderLayout.EAST);

        JPanel arrowButs = new JPanel();
        resetViewButt.addActionListener(this);
        leftButt.addActionListener(this);
        upButt.addActionListener(this);
        downButt.addActionListener(this);
        rightButt.addActionListener(this);
        arrowButs.add(resetViewButt, BorderLayout.WEST);
        arrowButs.add(leftButt, BorderLayout.CENTER);
        arrowButs.add(upButt, BorderLayout.CENTER);
        arrowButs.add(downButt, BorderLayout.CENTER);
        arrowButs.add(rightButt, BorderLayout.CENTER);


        //drPan.setSize(400,400);
        //Assemble Panels
        add(northButs, BorderLayout.NORTH);
        add(arrowButs, BorderLayout.SOUTH);

        drPan.setSize(3000,3000);

        add(drPan, BorderLayout.CENTER);

        inpFr.iniFrame();
        actFr.iniFrame();

        nodeList.addAction("No Action");

        setVisible(true);
    }

    public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                MainContainer genFrame = new MainContainer();
                genFrame.createComps();
            }
        });
    }
}
