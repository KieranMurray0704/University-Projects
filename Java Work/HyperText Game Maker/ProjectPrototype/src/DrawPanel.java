import javax.swing.JPanel;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.Graphics;
import java.util.*;

public class DrawPanel extends JPanel{
    MainContainer mFrame;
    NodeManager node = new NodeManager();
    ArrayManage nodeList = new ArrayManage();

    public DrawPanel(MainContainer mainCon){
        mFrame = mainCon;
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        this.setBackground(Color.DARK_GRAY);
        NodeManager tempNode;
        ArrayList<int[]> tempCons;
        ArrayList<NodeManager> curList = nodeList.getArray();
        int count = curList.size();
        int[] posAdj = nodeList.getAdjust();
        for(int i = 0; i < count; i++ ){
            node = curList.get(i);
            tempCons = node.getCons();
            int[] nodeXY = node.getPos();
            for(int j = 0; j < tempCons.size(); j++){
                int[] lineTest = tempCons.get(j);
                if(lineTest[1] == 0) {
                    tempNode = nodeList.nodeRetrieve(lineTest[0]);
                    int[] endLinePos = tempNode.getPos();
                    int[] arrowAdjust = arrowWork(node.getPos(), tempNode.getPos());
                    g.setColor(Color.WHITE);
                    double dx = (endLinePos[0] - nodeXY[0] + arrowAdjust[0] + posAdj[0]);
                    double dy = (endLinePos[2] - nodeXY[2] + arrowAdjust[1] + posAdj[1]);
                    double angle = Math.atan2(dy,dx);
                    int len = (int) Math.sqrt(dx*dx + dy*dy);
                    AffineTransform at = AffineTransform.getTranslateInstance(nodeXY[0], nodeXY[2]);
                    at.concatenate(AffineTransform.getRotateInstance(angle));
                    Graphics2D test = (Graphics2D) g.create();
                    test.transform(at);
                    g.drawLine(nodeXY[0] + 50 + posAdj[0] , nodeXY[2] - 50 + posAdj[1] , endLinePos[0] + arrowAdjust[0] + posAdj[0], endLinePos[2] + arrowAdjust[1] + posAdj[1]);
                    test.fillPolygon(new int[] {len, len-10, len-10,len},new int[]{0,-10,10,0},4);
                }
            }
            Color inpCol = node.getCol();
            g.setColor(Color.BLACK);
            g.fillRect(nodeXY[0] + posAdj[0],nodeXY[2] - 100 + posAdj[1],100,100);
            g.setColor(Color.WHITE);
            g.drawRect(nodeXY[0] + posAdj[0],nodeXY[2] - 100 + posAdj[1],100,100);
            g.setColor(inpCol);
            g.drawString(node.getTitle(), nodeXY[0] + 5 + posAdj[0], nodeXY[2] - 5 + posAdj[1]);
        }
    }

    public void drawArray(ArrayManage nList){
        nodeList = nList;
        Graphics g = this.getGraphics();
        super.paintComponent(g);
        repaint();
    }


    public int[] arrowWork(int[] endXY, int[] startXY){
        int[] xy = {0,0};
        if(endXY[3] < startXY[2]){
            if(startXY[0] >= endXY[1]){
                xy[1] = -100;
                return xy;
            }else if(endXY[1] <= startXY[1]){
                xy[0] = 50;
                xy[1] = -100;
                return xy;
            }else{
                xy[0] = 100;
                xy[1] = -100;
            }
        }else if(startXY[2] < endXY[3] && endXY[3] <= startXY[3]){
            if(startXY[0] > endXY[1]) {
                xy[1] = -50;
                return xy;
            }else if(startXY[1] < endXY[0]){
                xy[0] = 100;
                xy[1] = -50;
            }
        }else{
            if(startXY[0] > endXY[1]){
                return xy;
            }else if(startXY[0] < endXY[1] && endXY[1] <= startXY[1]){
                xy[0] = 50;
                return xy;
            }else{
                xy[0] = 100;
                return xy;
            }
        }


        return xy;
    }
}
