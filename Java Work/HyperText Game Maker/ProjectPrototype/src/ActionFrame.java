import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ActionFrame extends JFrame{
    NodeManager inpNode = new NodeManager();
    ArrayManage nodeArr = new ArrayManage();
    GridBagConstraints actCons = new GridBagConstraints();
    JTextField titleInp = new JTextField("");
    String[] typeDropLi = {"Item","Action"};
    JComboBox<String> typeDrop = new JComboBox<>(typeDropLi);
    JCheckBox oneTimeCheck = new JCheckBox();
    JButton saveAction =  new JButton("Save Action/Item");

    public void iniFrame(){
        setSize(400,200);
        setLocationRelativeTo(null);

        saveAction.addActionListener(e-> actionAdd());

        JLabel titleLabel = new JLabel("Name: ");
        JLabel typeLab = new JLabel("Type of Interactable: ");
        JLabel oneTimeLab = new JLabel("Toggleable Action?");

        GridBagLayout actLay = new GridBagLayout();
        setLayout(actLay);

        actCons.fill = GridBagConstraints.HORIZONTAL;
        actCons.gridx = 0; actCons.gridy = 0;
        actCons.gridwidth = 1;
        actCons.anchor = GridBagConstraints.PAGE_START;
        actCons.ipady = 10;
        actCons.ipadx = 10;
        add(titleLabel, actCons);
        actCons.gridx = 1; actCons.gridy = 0;
        actCons.gridwidth = 2;
        add(titleInp, actCons);
        actCons.gridx = 0; actCons.gridy = 1;
        actCons.gridwidth = 2;
        actCons.anchor = GridBagConstraints.CENTER;
        add(typeLab, actCons);
        actCons.gridx = 2; actCons.gridy = 1;
        actCons.gridwidth = 1;
        add(typeDrop, actCons);
        actCons.gridx = 0; actCons.gridy = 2;
        actCons.gridwidth = 2;
        add(oneTimeLab,actCons);
        actCons.gridx = 2; actCons.gridy = 2;
        actCons.gridwidth = 1;
        add(oneTimeCheck,actCons);
        actCons.gridx = 0; actCons.gridy = 3;
        actCons.gridwidth = 3;
        actCons.anchor = GridBagConstraints.PAGE_END;
        add(saveAction, actCons);
    }

    public void actionAdd(){
        String oneTime = "ONE TIME";
        if(oneTimeCheck.isSelected()){
            oneTime =  "TOGGLE";
        }
        inpNode.addAction(typeDrop.getSelectedItem().toString(), titleInp.getText(), oneTime, "NEW ITEM/ACTION");
        nodeArr.addAction(titleInp.getText());
        setVisible(false);
    }


    public void open(NodeManager activeNode, ArrayManage nodeList){
        inpNode = activeNode;
        nodeArr = nodeList;
        setTitle("Add new Action/Item to " + activeNode.getTitle());
        titleInp.setText("");
        typeDrop.setSelectedIndex(0);
        oneTimeCheck.setSelected(false);
        setVisible(true);
    }

}
