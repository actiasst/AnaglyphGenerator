package com.company;

import java.awt.*;

public class Main {

    public static void main(String[] args) {
//        CanvasExample canvasExample = new CanvasExample();
        MinimumSpanningTree minimumSpanningTree = new MinimumSpanningTree();
        minimumSpanningTree.example2();
    }
}

class CanvasExample
{
    public CanvasExample()
    {
        Frame f= new Frame("Canvas Example");
        f.add(new MyCanvas());
        f.setLayout(null);
        f.setSize(400, 400);
        f.setVisible(true);
    }
    public static void main(String args[])
    {
        new CanvasExample();
    }
}
class MyCanvas extends Canvas
{
    public MyCanvas() {
        setBackground (Color.GRAY);
        setSize(300, 200);
    }
    public void paint(Graphics g)
    {
        g.setColor(Color.red);
        g.fillOval(75, 75, 150, 75);
    }
}