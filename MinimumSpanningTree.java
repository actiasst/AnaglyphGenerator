package com.company;

import java.util.LinkedList;

public class MinimumSpanningTree {
    LinkedList<LinkedList<Integer>> nodesNeighbors = new LinkedList<LinkedList<Integer>>();
    LinkedList<LinkedList<Double>> edgesValues = new LinkedList<LinkedList<Double>>();
    LinkedList<Integer> nodesUsed = new LinkedList<Integer>();
    LinkedList<Edge> edgesChoose = new LinkedList<Edge>();
    LinkedList<Edge> edgesAvailable = new LinkedList<Edge>();

    void addNode() {
        nodesNeighbors.add(new LinkedList<Integer>());
    }

    void addNeighbors(int index, LinkedList<Integer> neighbors){
        for(int i = 0; i < neighbors.size(); i++)
            nodesNeighbors.get(index).add(neighbors.get(i));
    }

    void printNeighbors(){
        System.out.println("Nodes:");
        for(int i = 0; i < nodesNeighbors.size();i++) {
            for (int j = 0; j < nodesNeighbors.get(i).size(); j++)
                System.out.print(nodesNeighbors.get(i).get(j) + " ");
            System.out.println("");
        }
    }

    void addEdge() {
        edgesValues.add(new LinkedList<Double>());
    }

    void addEdgeValues(int index, LinkedList<Double> values){
        for(int i = 0; i < values.size(); i++)
            edgesValues.get(index).add(values.get(i));
    }

    void printEdgesValues(){
        System.out.println("Node edge values:");
        for(int i = 0; i < edgesValues.size();i++) {
            for (int j = 0; j < edgesValues.get(i).size(); j++)
                System.out.print(edgesValues.get(i).get(j) + " ");
            System.out.println("");
        }
    }

    LinkedList<Edge> MST(){
        double value = 0;
        int index = 0;
        boolean flag = false;
        boolean addingFlag = false;

        nodesUsed.add(0);
        edgesAvailable.add(new Edge(0,nodesNeighbors.get(0).get(0), edgesValues.get(0).get(0)));
        for(int i = 1; i < nodesNeighbors.get(0).size(); i++) {
            addingFlag = false;
            for (int j = 0; j < edgesAvailable.size(); j++) {
                if (edgesAvailable.get(j).value > edgesValues.get(0).get(i)) {
                    edgesAvailable.add(j, new Edge(0, nodesNeighbors.get(0).get(i), edgesValues.get(0).get(i)));
                    addingFlag = true;
                    break;
                }
            }
            if(!addingFlag){
                edgesAvailable.addLast(new Edge(0, nodesNeighbors.get(0).get(i), edgesValues.get(0).get(i)));
            }
        }

        while(nodesUsed.size() != nodesNeighbors.size()) {
            index = 0;

            edgesChoose.add(new Edge(edgesAvailable.get(index).edgePoint1, edgesAvailable.get(index).edgePoint2,
                    edgesValues.get(edgesAvailable.get(index).edgePoint1).get(nodesNeighbors.get(edgesAvailable.get(index).edgePoint1).indexOf(edgesAvailable.get(index).edgePoint2))));
            nodesUsed.add(edgesAvailable.get(index).edgePoint2);

            for (int i = 0; i < nodesNeighbors.get(edgesAvailable.get(index).edgePoint2).size(); i++) {
                if (nodesUsed.indexOf(nodesNeighbors.get(edgesAvailable.get(index).edgePoint2).get(i)) == -1) {
                    addingFlag = false;
                    value = edgesValues.get(edgesAvailable.get(index).edgePoint2).get(i);
                    for(int j = 1; j < edgesAvailable.size(); j++) {
                        if (edgesAvailable.get(j).value > value) {
                            edgesAvailable.add(j, new Edge(edgesAvailable.get(index).edgePoint2, nodesNeighbors.get(edgesAvailable.get(index).edgePoint2).get(i), value));
                            addingFlag = true;
                            break;
                        }
                    }
                    if(!addingFlag){
                        edgesAvailable.addLast(new Edge(edgesAvailable.get(index).edgePoint2, nodesNeighbors.get(edgesAvailable.get(index).edgePoint2).get(i), value));
                    }
                }
            }
            edgesAvailable.remove(index);

            for (int i = 0; i < edgesAvailable.size(); i++) {
                flag = false;
                if (edgesAvailable.get(i).checkIfThereIsNode(nodesUsed.getLast())) {
                    for (int j = 0; j < nodesNeighbors.get(nodesUsed.getLast()).size(); j++) {
                        if(nodesUsed.indexOf(nodesNeighbors.get(nodesUsed.getLast()).get(j)) != -1) {
                            if (edgesAvailable.get(i).checkIfThereIsNode(nodesNeighbors.get(nodesUsed.getLast()).get(j))) {
                                edgesAvailable.remove(i);
                                flag = true;
                                break;
                            }
                        }
                    }
                }
                if(flag)
                    i--;
            }
        }
        return edgesChoose;
    }

    void example1() {
        for (int i = 0; i < 8; i++)
            addNode();
        LinkedList<Integer> tmp = new LinkedList<Integer>();
        tmp.add(1);
        tmp.add(5);
        tmp.add(7);
        addNeighbors(0, tmp);
        tmp.clear();
        tmp.add(0);
        tmp.add(2);
        addNeighbors(1, tmp);
        tmp.clear();
        tmp.add(1);
        tmp.add(5);
        addNeighbors(2, tmp);
        tmp.clear();
        tmp.add(4);
        addNeighbors(3, tmp);
        tmp.clear();
        tmp.add(3);
        tmp.add(5);
        addNeighbors(4, tmp);
        tmp.clear();
        tmp.add(0);
        tmp.add(2);
        tmp.add(4);
        tmp.add(6);
        addNeighbors(5, tmp);
        tmp.clear();
        tmp.add(5);
        tmp.add(7);
        addNeighbors(6, tmp);
        tmp.clear();
        tmp.add(0);
        tmp.add(6);
        addNeighbors(7, tmp);
        tmp.clear();

        for (int i = 0; i < 8; i++)
            addEdge();
        LinkedList<Double> tmp2 = new LinkedList<Double>();
        tmp2.add(4.);
        tmp2.add(3.);
        tmp2.add(5.);
        addEdgeValues(0, tmp2);
        tmp2.clear();
        tmp2.add(4.);
        tmp2.add(7.);
        addEdgeValues(1, tmp2);
        tmp2.clear();
        tmp2.add(7.);
        tmp2.add(1.);
        addEdgeValues(2, tmp2);
        tmp2.clear();
        tmp2.add(5.);
        addEdgeValues(3, tmp2);
        tmp2.clear();
        tmp2.add(5.);
        tmp2.add(8.);
        addEdgeValues(4, tmp2);
        tmp2.clear();
        tmp2.add(3.);
        tmp2.add(1.);
        tmp2.add(8.);
        tmp2.add(4.);
        addEdgeValues(5, tmp2);
        tmp2.clear();
        tmp2.add(4.);
        tmp2.add(2.);
        addEdgeValues(6, tmp2);
        tmp2.clear();
        tmp2.add(5.);
        tmp2.add(2.);
        addEdgeValues(7, tmp2);
        tmp2.clear();

        printNeighbors();
        printEdgesValues();

        MST();
    }

    void example2(){
        for (int i = 0; i < 21; i++)
            addNode();
        LinkedList<Integer> tmp = new LinkedList<Integer>();
        tmp.add(3);
        addNeighbors(0, tmp);
        tmp.clear();
        tmp.add(2);
        tmp.add(9);
        addNeighbors(1, tmp);
        tmp.clear();
        tmp.add(1);
        tmp.add(4);
        tmp.add(6);
        addNeighbors(2, tmp);
        tmp.clear();
        tmp.add(13);
        tmp.add(14);
        addNeighbors(3, tmp);
        tmp.clear();
        tmp.add(2);
        tmp.add(5);
        tmp.add(9);
        tmp.add(14);
        addNeighbors(4, tmp);
        tmp.clear();
        tmp.add(4);
        tmp.add(7);
        tmp.add(8);
        tmp.add(15);
        addNeighbors(5, tmp);
        tmp.clear();
        tmp.add(2);
        tmp.add(7);
        tmp.add(16);
        addNeighbors(6, tmp);
        tmp.clear();
        tmp.add(6);
        tmp.add(16);
        addNeighbors(7, tmp);
        tmp.clear();
        tmp.add(5);
        tmp.add(15);
        addNeighbors(8, tmp);
        tmp.clear();
        tmp.add(1);
        tmp.add(4);
        tmp.add(10);
        addNeighbors(9, tmp);
        tmp.clear();
        tmp.add(9);
        tmp.add(11);
        tmp.add(12);
        tmp.add(13);
        addNeighbors(10, tmp);
        tmp.clear();
        tmp.add(10);
        addNeighbors(11, tmp);
        tmp.clear();
        tmp.add(10);
        addNeighbors(12, tmp);
        tmp.clear();
        tmp.add(3);
        tmp.add(10);
        addNeighbors(13, tmp);
        tmp.clear();
        tmp.add(3);
        tmp.add(4);
        addNeighbors(14, tmp);
        tmp.clear();
        tmp.add(5);
        tmp.add(8);
        addNeighbors(15, tmp);
        tmp.clear();
        tmp.add(6);
        tmp.add(7);
        tmp.add(17);
        addNeighbors(16, tmp);
        tmp.clear();
        tmp.add(16);
        tmp.add(18);
        addNeighbors(17, tmp);
        tmp.clear();
        tmp.add(17);
        tmp.add(19);
        tmp.add(20);
        addNeighbors(18, tmp);
        tmp.clear();
        tmp.add(18);
        addNeighbors(19, tmp);
        tmp.clear();
        tmp.add(18);
        addNeighbors(20, tmp);
        tmp.clear();

        for (int i = 0; i < 21; i++)
            addEdge();
        LinkedList<Double> tmp2 = new LinkedList<Double>();
        tmp2.add(2.);
        addEdgeValues(0, tmp2);
        tmp2.clear();
        tmp2.add(5.);
        tmp2.add(2.);
        addEdgeValues(1, tmp2);
        tmp2.clear();
        tmp2.add(5.);
        tmp2.add(19.);
        tmp2.add(7.);
        addEdgeValues(2, tmp2);
        tmp2.clear();
        tmp2.add(4.);
        tmp2.add(5.);
        addEdgeValues(3, tmp2);
        tmp2.clear();
        tmp2.add(19.);
        tmp2.add(9.);
        tmp2.add(1.);
        tmp2.add(5.);
        addEdgeValues(4, tmp2);
        tmp2.clear();
        tmp2.add(9.);
        tmp2.add(1.);
        tmp2.add(10.);
        tmp2.add(3.);
        addEdgeValues(5, tmp2);
        tmp2.clear();
        tmp2.add(7.);
        tmp2.add(12.);
        tmp2.add(14.);
        addEdgeValues(6, tmp2);
        tmp2.clear();
        tmp2.add(12.);
        tmp2.add(7.);
        addEdgeValues(7, tmp2);
        tmp2.clear();
        tmp2.add(10.);
        tmp2.add(7.);
        addEdgeValues(8, tmp2);
        tmp2.clear();
        tmp2.add(2.);
        tmp2.add(1.);
        tmp2.add(4.);
        addEdgeValues(9, tmp2);
        tmp2.clear();
        tmp2.add(4.);
        tmp2.add(15.);
        tmp2.add(16.);
        tmp2.add(8.);
        addEdgeValues(10, tmp2);
        tmp2.clear();
        tmp2.add(15.);
        addEdgeValues(11, tmp2);
        tmp2.clear();
        tmp2.add(16.);
        addEdgeValues(12, tmp2);
        tmp2.clear();
        tmp2.add(4.);
        tmp2.add(8.);
        addEdgeValues(13, tmp2);
        tmp2.clear();
        tmp2.add(5.);
        tmp2.add(5.);
        addEdgeValues(14, tmp2);
        tmp2.clear();
        tmp2.add(3.);
        tmp2.add(7.);
        addEdgeValues(15, tmp2);
        tmp2.clear();
        tmp2.add(14.);
        tmp2.add(7.);
        tmp2.add(8.);
        addEdgeValues(16, tmp2);
        tmp2.clear();
        tmp2.add(8.);
        tmp2.add(9.);
        addEdgeValues(17, tmp2);
        tmp2.clear();
        tmp2.add(9.);
        tmp2.add(4.);
        tmp2.add(2.);
        addEdgeValues(18, tmp2);
        tmp2.clear();
        tmp2.add(4.);
        addEdgeValues(19, tmp2);
        tmp2.clear();
        tmp2.add(2.);
        addEdgeValues(20, tmp2);
        tmp2.clear();
        printNeighbors();
        printEdgesValues();
        MST();
    }
}

class Edge{
    int edgePoint1;
    int edgePoint2;
    double value;

    Edge(){
        this.edgePoint1 = 0;
        this.edgePoint2 = 0;
    }

    Edge(int edgePoint1, int edgePoint2, double value){
        this.edgePoint1 = edgePoint1;
        this.edgePoint2 = edgePoint2;
        this.value = value;
    }

    boolean checkIfThereIsNode(int node){
        if(edgePoint1 == node)
            return true;
        if(edgePoint2 == node)
            return true;
        return false;
    }

    void printEdge(){
        System.out.println(edgePoint1 + " - " + edgePoint2);
    }

    void printEdgeValue(){
        System.out.println(edgePoint1 + " - " + edgePoint2 + " : " + value);
    }
}