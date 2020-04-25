package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Main {
    public static long start;
    public static long stop;
    public static void main(String[] args) throws IOException {
//        CanvasExample canvasExample = new CanvasExample();
        MinimumSpanningTree minimumSpanningTree = new MinimumSpanningTree();
        BufferedImage image = ImageIO.read(new File("D://testImageAnaglyph3.png"));
        MyImage myImage = new MyImage(image);

        timeStart();
        myImage.createGrayScale();
        timeStop();
        showTime();
        myImage.createBlocks();
        timeStop();
        showTime();
        myImage.createMST(10);
        timeStop();
        showTime();
        myImage.createDepthImage();
        timeStop();
        showTime();
    }

    static long time(){
        return System.currentTimeMillis();
    }

    static void timeStart(){
        start = time();
    }

    static void timeStop(){
        stop = time();
    }

    static void showTime(){
        System.out.println("Time elapsed: " + (stop - start)/1000. + "s");
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