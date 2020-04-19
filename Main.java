package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
//        CanvasExample canvasExample = new CanvasExample();
        MinimumSpanningTree minimumSpanningTree = new MinimumSpanningTree();
        BufferedImage image = ImageIO.read(new File("E://testImageAnaglyph.png"));
        MyImage myImage = new MyImage(image);
        myImage.createGrayScale();
        myImage.printGrayScale();
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