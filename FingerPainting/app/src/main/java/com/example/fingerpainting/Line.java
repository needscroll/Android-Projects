package com.example.fingerpainting;

import java.util.ArrayList;

/*
line object to store drawn lines
 */
public class Line {

    int color;
    int width;

    public ArrayList<Point> linepoints = new ArrayList<Point>();

    public Line(int color, int width)
    {
        this.color = color;
        this.width = width;
    }

    public void addPoint(float x, float y)
    {
        linepoints.add(new Point(x, y));
    }

    //returns all the points in the line in a string
    public String toString()
    {
        String result = "";

        for(Point p : linepoints)
        {
            result += p.toString();
        }
        return result;
    }

    public void setColor(int color)
    {
        this.color = color;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public Point[] getPoints()
    {
        return linepoints.toArray(new Point[1]);
    }
}
