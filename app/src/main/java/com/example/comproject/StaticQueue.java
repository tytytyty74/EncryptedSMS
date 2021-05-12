package com.example.comproject;

import java.util.LinkedList;

public class StaticQueue {
    public static LinkedList<String[]> mainQueue = new LinkedList<>();
    public static synchronized void queue(String[] Item)
    {
        mainQueue.add(Item);
    }
    public static synchronized void queue(String p1, String p2)
    {
        queue(new String[]{p1, p2});
    }
    public static synchronized String[] dequeue()
    {
        return mainQueue.pop();
    }
    public static boolean hasData()
    {
        return !mainQueue.isEmpty();
    }
}
