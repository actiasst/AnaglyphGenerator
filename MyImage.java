package com.company;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MyImage {
    private int width;
    private int height;
    private Color colorTable[][];
    public int grayScale[][];

    public MyImage(){};
    public MyImage(BufferedImage image){
        this.height = image.getHeight();
        this.width = image.getWidth();
        this.colorTable = new Color[height][width];
        for(int i = 0; i < height; i++)
            for(int j = 0; j < width; j++) {
                colorTable[j][i] = new Color(image.getRGB(i,j));
            }
        if(this.height % 4 != 0)
            this.height += 4 - (image.getHeight() % 4);
        if(this.width % 4 != 0)
            this.width += 4 - (image.getWidth() % 4);
        for(int i = image.getHeight() - 1; i < this.height; i++ )
            for(int j =  0; j < this.width; j++)
                colorTable[i][j] = colorTable[image.getHeight()][j];
        for(int i = image.getWidth() - 1; i < this.width; i++ )
            for(int j =  0; j < this.height; j++)
                colorTable[i][j] = colorTable[image.getWidth()][j];
    }

    void printMyImage(){
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++)
                System.out.print("R" + colorTable[i][j].getRed() + " G" + colorTable[i][j].getGreen() + " B" + colorTable[i][j].getBlue() + " ");
            System.out.println("");
        }
    }

    void createGrayScale(){
        this.grayScale = new int[height][width];
        for(int i = 0; i < width; i++)
            for(int j = 0; j < height; j++)
                grayScale[i][j] = (int)(0.2989 * colorTable[i][j].getRed() + 0.5870 * colorTable[i][j].getGreen() + 0.1140 * colorTable[i][j].getBlue());
    }

    void printGrayScale(){
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++)
                System.out.print(grayScale[i][j] + " ");
            System.out.println("");
        }
    }
}
