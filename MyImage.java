package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Stack;

public class MyImage {
    private int width;
    private int height;
    private Color colorTable[][];
    private int grayScale[][];
    private int blockWidth;
    private int blockHeight;
    private int blocks[][];
    private LinkedList<LinkedList<Integer>> groupedBlocks = new LinkedList<LinkedList<Integer>>();
    private int depthBlocks[][];
    private int depthImage[][];

    public MyImage(){};
    public MyImage(BufferedImage image){
        this.height = image.getHeight();
        this.width = image.getWidth();
        if(this.height % 4 != 0)
            this.height += (4 - image.getHeight() % 4);
        if(this.width % 4 != 0)
            this.width += (4 - image.getWidth() % 4);
        this.colorTable = new Color[height][width];
        for(int i = 0; i < image.getHeight(); i++)
            for(int j = 0; j < image.getWidth(); j++)
                colorTable[i][j] = new Color(image.getRGB(j,i));

        for(int i = image.getHeight() - 1; i < this.height; i++)
            for(int j = 0; j < this.width - 1; j++)
                colorTable[i][j] = colorTable[image.getHeight() - 2][j];

        for(int i = image.getWidth() - 1; i < this.width; i++)
            for(int j = 0; j < this.height - 1; j++)
                colorTable[j][i] = colorTable[j][image.getWidth() - 2];

            colorTable[this.height - 1][this.width - 1] = new Color(
                (colorTable[this.height - 1][this.width - 2].getRed()
                        + colorTable[this.height - 2][this.width - 1].getRed()
                        + colorTable[this.height - 2][this.width - 2].getRed()) /3,
                (colorTable[this.height - 1][this.width - 2].getGreen()
                        + colorTable[this.height - 2][this.width - 1].getGreen()
                        + colorTable[this.height - 2][this.width - 2].getGreen()) /3,
                (colorTable[this.height - 1][this.width - 2].getRed()
                        + colorTable[this.height - 2][this.width - 1].getRed()
                        + colorTable[this.height - 2][this.width - 2].getRed()) /3);
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
        for(int i = 0; i < height; i++)
            for(int j = 0; j < width; j++)
                grayScale[i][j] = (int)(0.2989 * colorTable[i][j].getRed() + 0.5870 * colorTable[i][j].getGreen() + 0.1140 * colorTable[i][j].getBlue());
    }

    void printGrayScale(){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++)
                System.out.print(grayScale[i][j] + " ");
            System.out.println("");
        }
    }

    void createBlocks(){
        int tmp = 0;
        blockHeight = height / 4;
        blockWidth = width / 4;
        blocks = new int[blockHeight][blockWidth];
        for(int i = 0; i < blockHeight; i++)
            for(int j = 0; j < blockWidth; j++){
                for(int k = 0; k < 4; k++)
                    for(int l = 0; l < 4; l++)
                        tmp += grayScale[i*4+k][j*4+l];
                tmp /= 16.;
                blocks[i][j] = tmp;
                tmp = 0;
            }
    }

    void printBlocks(){
        for(int i = 0; i < blockHeight; i++){
            for(int j = 0; j < blockWidth; j++)
                System.out.print(blocks[i][j] + " ");
            System.out.println("");
        }
    }

    double differenceBetweenNodes(double value1, double value2){
        if(value1 - value2 >= 0)
            return value1 - value2;
        else
            return (value1 - value2) * (-1);
    }

    double getValueOfBlock(int y, int x){
        return (double)blocks[y][x];
    }

    void createMST(double value) {
        MinimumSpanningTree minimumSpanningTree = new MinimumSpanningTree();
        for (int i = 0; i < blockWidth * blockHeight; i++)
            minimumSpanningTree.addNode();
        LinkedList<Integer> tmp = new LinkedList<Integer>();
        //CORNERS
        tmp.add(1);
        tmp.add(blockWidth);
        minimumSpanningTree.addNeighbors(0, tmp);
        tmp.clear();
        tmp.add(blockWidth - 2);
        tmp.add(blockWidth * 2 - 1);
        minimumSpanningTree.addNeighbors(blockWidth - 1, tmp);
        tmp.clear();
        tmp.add((blockHeight - 2) * blockWidth);
        tmp.add((blockHeight - 1) * blockWidth + 1);
        minimumSpanningTree.addNeighbors(blockWidth * (blockHeight - 1), tmp);
        tmp.clear();
        tmp.add((blockHeight - 1) * blockWidth - 1);
        tmp.add(blockHeight * blockWidth - 2);
        minimumSpanningTree.addNeighbors(blockHeight * blockWidth - 1, tmp);
        tmp.clear();

        //EDGES OF IMAGE
        for (int i = 1; i < blockWidth - 1; i++) {
            tmp.add(i - 1);
            tmp.add(i + blockWidth);
            tmp.add(i + 1);
            minimumSpanningTree.addNeighbors(i, tmp);
            tmp.clear();
            tmp.add(blockWidth * (blockHeight - 1) + i - 1);
            tmp.add(blockWidth * (blockHeight - 1) + i - blockWidth);
            tmp.add(blockWidth * (blockHeight - 1) + i + 1);
            minimumSpanningTree.addNeighbors(blockWidth * (blockHeight - 1) + i, tmp);
            tmp.clear();
        }
        for (int i = 1; i < blockHeight - 1; i++) {
            tmp.add((blockWidth * i) - blockWidth);
            tmp.add(blockWidth * i + 1);
            tmp.add((blockWidth * i) + blockWidth);
            minimumSpanningTree.addNeighbors(blockWidth * i, tmp);
            tmp.clear();
            tmp.add(blockWidth * (i + 1) - 1 - blockWidth);
            tmp.add(blockWidth * (i + 1) - 1 - 1);
            tmp.add(blockWidth * (i + 1) - 1 + blockWidth);
            minimumSpanningTree.addNeighbors(blockWidth * (i + 1) - 1, tmp);
            tmp.clear();
        }
        // CENTER
        for (int i = 1; i < blockHeight - 1; i++)
            for (int j = 1; j < blockWidth - 1; j++) {
                tmp.add(blockWidth * i + j - blockWidth);
                tmp.add(blockWidth * i + j + 1);
                tmp.add(blockWidth * i + j + blockWidth);
                tmp.add(blockWidth * i + j - 1);
                minimumSpanningTree.addNeighbors(blockWidth * i + j, tmp);
                tmp.clear();
            }

        for (int i = 0; i < blockWidth * blockHeight; i++)
            minimumSpanningTree.addEdge();
        LinkedList<Double> tmp2 = new LinkedList<Double>();
        //CORNERS
        double tmpValueOfNode;
        tmpValueOfNode = getValueOfBlock(0, 0);
        tmp2.add(differenceBetweenNodes(tmpValueOfNode, getValueOfBlock(0, 1)));
        tmp2.add(differenceBetweenNodes(tmpValueOfNode, getValueOfBlock(1, 0)));
        minimumSpanningTree.addEdgeValues(0, tmp2);
        tmp2.clear();
        tmpValueOfNode = getValueOfBlock(0, blockWidth - 1);
        tmp2.add(differenceBetweenNodes(tmpValueOfNode, getValueOfBlock(0, blockWidth - 2)));
        tmp2.add(differenceBetweenNodes(tmpValueOfNode, getValueOfBlock(1, blockWidth - 1)));
        minimumSpanningTree.addEdgeValues(blockWidth - 1, tmp2);
        tmp2.clear();
        tmpValueOfNode = getValueOfBlock(blockHeight - 1, 0);
        tmp2.add(differenceBetweenNodes(tmpValueOfNode, getValueOfBlock(blockHeight - 2, 0)));
        tmp2.add(differenceBetweenNodes(tmpValueOfNode, getValueOfBlock(blockHeight - 1, 1)));
        minimumSpanningTree.addEdgeValues(blockWidth * (blockHeight - 1), tmp2);
        tmp2.clear();
        tmpValueOfNode = getValueOfBlock(blockHeight - 1, blockHeight - 1);
        tmp2.add(differenceBetweenNodes(tmpValueOfNode, getValueOfBlock(blockHeight - 2, blockWidth - 1)));
        tmp2.add(differenceBetweenNodes(tmpValueOfNode, getValueOfBlock(blockHeight - 1, blockWidth - 2)));
        minimumSpanningTree.addEdgeValues(blockHeight * blockWidth - 1, tmp2);
        tmp2.clear();

        //EDGES
        for (int i = 1; i < blockWidth - 1; i++) {
            tmpValueOfNode = getValueOfBlock(0, i);
            tmp2.add(differenceBetweenNodes(tmpValueOfNode, getValueOfBlock(0, i - 1)));
            tmp2.add(differenceBetweenNodes(tmpValueOfNode, getValueOfBlock(1, i)));
            tmp2.add(differenceBetweenNodes(tmpValueOfNode, getValueOfBlock(0, i + 1)));
            minimumSpanningTree.addEdgeValues(i, tmp2);
            tmp2.clear();
            tmpValueOfNode = getValueOfBlock(blockHeight - 1, i);
            tmp2.add(differenceBetweenNodes(tmpValueOfNode, getValueOfBlock(blockHeight - 1, i - 1)));
            tmp2.add(differenceBetweenNodes(tmpValueOfNode, getValueOfBlock(blockHeight - 2, i)));
            tmp2.add(differenceBetweenNodes(tmpValueOfNode, getValueOfBlock(blockHeight - 1, i + 1)));
            minimumSpanningTree.addEdgeValues(blockWidth * (blockHeight - 1) + i, tmp2);
            tmp2.clear();
        }
        for (int i = 1; i < blockHeight - 1; i++) {
            tmpValueOfNode = getValueOfBlock(i, 0);
            tmp2.add(differenceBetweenNodes(tmpValueOfNode, getValueOfBlock(i - 1, 0)));
            tmp2.add(differenceBetweenNodes(tmpValueOfNode, getValueOfBlock(i, 1)));
            tmp2.add(differenceBetweenNodes(tmpValueOfNode, getValueOfBlock(i + 1, 0)));
            minimumSpanningTree.addEdgeValues(blockWidth * i, tmp2);
            tmp2.clear();
            tmpValueOfNode = getValueOfBlock(i, blockWidth - 1);
            tmp2.add(differenceBetweenNodes(tmpValueOfNode, getValueOfBlock(i - 1, blockWidth - 1)));
            tmp2.add(differenceBetweenNodes(tmpValueOfNode, getValueOfBlock(i, blockWidth - 2)));
            tmp2.add(differenceBetweenNodes(tmpValueOfNode, getValueOfBlock(i + 1, blockWidth - 1)));
            minimumSpanningTree.addEdgeValues(blockWidth * (i + 1) - 1, tmp2);
            tmp2.clear();
        }
        //CENTER
        for (int i = 1; i < blockHeight - 1; i++)
            for (int j = 1; j < blockWidth - 1; j++) {
                tmpValueOfNode = getValueOfBlock(i, j);
                tmp2.add(differenceBetweenNodes(tmpValueOfNode, getValueOfBlock(i - 1, j)));
                tmp2.add(differenceBetweenNodes(tmpValueOfNode, getValueOfBlock(i, j + 1)));
                tmp2.add(differenceBetweenNodes(tmpValueOfNode, getValueOfBlock(i + 1, j)));
                tmp2.add(differenceBetweenNodes(tmpValueOfNode, getValueOfBlock(i, j - 1)));
                minimumSpanningTree.addEdgeValues(blockWidth * i + j, tmp2);
                tmp2.clear();
            }

        LinkedList<Edge> edges = minimumSpanningTree.MST();
        LinkedList<Integer> nodeEntries = new LinkedList<Integer>();
        int groupedBlocksCounter = 1;
        for (int i = 0; i < edges.size(); i++) {
            if (minimumSpanningTree.edgesValues.get(edges.get(i).edgePoint1).get(minimumSpanningTree.nodesNeighbors.get(edges.get(i).edgePoint1).indexOf(edges.get(i).edgePoint2)) > value) {
                nodeEntries.add(edges.get(i).edgePoint2);
                edges.remove(i);
                i--;
                groupedBlocksCounter++;
            }
        }

        LinkedList<LinkedList<Edge>> edgesByNode = new LinkedList<LinkedList<Edge>>();
        for(int i = 0; i < minimumSpanningTree.nodesNeighbors.size(); i++){
            edgesByNode.add(new LinkedList<Edge>());
        }
        for(int i = 0; i < edges.size(); i++){
            edgesByNode.get(edges.get(i).edgePoint1).add(edges.get(i));
        }

        LinkedList<Integer> availableNodes = new LinkedList<Integer>();
        for (int i = 0; i < blockWidth * blockHeight; i++)
            availableNodes.add(i);
        for (int i = 0; i < groupedBlocksCounter; i++)
            groupedBlocks.add(new LinkedList<Integer>());

        groupedBlocks.get(0).add(0);
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(0);
        availableNodes.remove(availableNodes.indexOf(0));
        int node = stack.peek();
        boolean flag = false;
        int group = 0;
        int index = 0;

        while (availableNodes.size() != 0) {
            flag = false;
            for (int i = 0; i < edgesByNode.get(node).size(); i++) {
                if(availableNodes.indexOf(edgesByNode.get(node).get(i).edgePoint2) != -1){
                    index = edgesByNode.get(node).get(i).edgePoint2;
                    groupedBlocks.get(group).add(index);
                    stack.add(index);
                    availableNodes.remove(availableNodes.indexOf(index));
                    node = stack.peek();
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                stack.pop();
                if (!stack.isEmpty())
                    node = stack.peek();
            }
            if (stack.isEmpty()) {
                group++;
                stack.add(nodeEntries.get(0));
                groupedBlocks.get(group).add(stack.peek());
                availableNodes.remove(availableNodes.indexOf(nodeEntries.get(0)));
                node = stack.peek();
                nodeEntries.remove(0);
            }
        }
    }

    void printGroupedBlocks(){
        for(int i = 0; i < groupedBlocks.size(); i++) {
            for (int j = 0; j < groupedBlocks.get(i).size(); j++)
                System.out.print(groupedBlocks.get(i).get(j) + " ");
            System.out.println("");
        }
    }

    int getYofNode(int node){
        return node / blockWidth;
    }
    int getXofNode(int node) {
        return node % blockWidth;
    }

    void createDepthImage() throws IOException {
        depthBlocks = new int[blockHeight][blockWidth];
        double tmp = 0;
        for(int i = 0; i < groupedBlocks.size(); i++){
            for(int j = 0; j < groupedBlocks.get(i).size(); j++){
                tmp += ((getYofNode(groupedBlocks.get(i).get(j)) - ((double)blockHeight/2.)) / (double)blockHeight);
            }
            tmp *= 255;
            tmp /= groupedBlocks.get(i).size();
            tmp += 128;
            for(int j = 0; j < groupedBlocks.get(i).size(); j++)
                depthBlocks[getYofNode(groupedBlocks.get(i).get(j))][getXofNode(groupedBlocks.get(i).get(j))] = (int)tmp;
            tmp = 0;
        }

        depthImage = new int[height][width];
        for(int i = 0; i < blockHeight; i++)
            for (int j = 0; j < blockWidth; j++)
                for(int k = 0; k < 4; k++)
                    for(int l = 0; l < 4; l++)
                        depthImage[i * 4 + k][j * 4 + l] = depthBlocks[i][j];

        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_BYTE_GRAY);
        int R,G, B;
        String RGB;
        for(int i = 0; i < height; i++)
            for(int j = 0; j < width; j++) {
                R = depthImage[i][j];
                G = depthImage[i][j];
                B = depthImage[i][j];
                RGB = "0x" + Integer.toHexString(R)+Integer.toHexString(G)+Integer.toHexString(B);
                image.setRGB(j,i, Integer.decode(RGB));
            }
        ImageIO.write(image,"png",new File("D://test.png"));
    }

    void printDepthImage(){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++)
                System.out.print(depthImage[i][j] + " ");
        System.out.println("");
        }
    }
}
