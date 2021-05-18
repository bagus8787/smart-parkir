package com.example.loginfirebase.jarak;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Jarak implements Comparable<Jarak> {
    public final String name;
    public Edge[] adjacencies;
    public double minDistance = Double.POSITIVE_INFINITY;
    public Jarak previous;
    public Jarak(String argName) { name = argName; }
    public String toString() { return name; }
    public int compareTo(Jarak other)
    {
        return Double.compare(minDistance, other.minDistance);
    }
}

class Edge {
    public final Jarak target;
    public final double weight;
    public Edge(Jarak argTarget, double argWeight)
    { target = argTarget; weight = argWeight; }
}

class Dijkstra {
    public static void computePaths(Jarak source)
    {
        source.minDistance = 0.;
        PriorityQueue<Jarak> vertexQueue = new PriorityQueue<Jarak>();
        vertexQueue.add(source);

        while (!vertexQueue.isEmpty()) {
            Jarak u = vertexQueue.poll();

            // Visit each edge exiting u
            for (Edge e : u.adjacencies)
            {
                Jarak v = e.target;
                double weight = e.weight;
                double distanceThroughU = u.minDistance + weight;
                if (distanceThroughU < v.minDistance) {
                    vertexQueue.remove(v);
                    v.minDistance = distanceThroughU ;
                    v.previous = u;
                    vertexQueue.add(v);
                }
            }
        }
    }

    public static List<Jarak> getShortestPathTo(Jarak target)
    {
        List<Jarak> path = new ArrayList<Jarak>();
        for (Jarak vertex = target; vertex != null; vertex = vertex.previous)
            path.add(vertex);
        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        Jarak v0 = new Jarak("Redvile");
        Jarak v1 = new Jarak("Blueville");
        Jarak v2 = new Jarak("Greenville");
        Jarak v3 = new Jarak("Orangeville");
        Jarak v4 = new Jarak("Purpleville");

        v0.adjacencies = new Edge[]{ new Edge(v1, 5),
                new Edge(v2, 10),
                new Edge(v3, 8) };
        v1.adjacencies = new Edge[]{ new Edge(v0, 5),
                new Edge(v2, 3),
                new Edge(v4, 7) };
        v2.adjacencies = new Edge[]{ new Edge(v0, 10),
                new Edge(v1, 3) };
        v3.adjacencies = new Edge[]{ new Edge(v0, 8),
                new Edge(v4, 2) };
        v4.adjacencies = new Edge[]{ new Edge(v1, 7),
                new Edge(v3, 2) };
        Jarak[] vertices = { v0, v1, v2, v3, v4 };
        computePaths(v0);
        for (Jarak v : vertices)
        {
            System.out.println("Distance to " + v + ": " + v.minDistance);
            List<Jarak> path = getShortestPathTo(v);
            System.out.println("Path: " + path);
        }
    }
}
