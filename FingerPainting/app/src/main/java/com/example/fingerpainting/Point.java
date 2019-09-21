package com.example.fingerpainting;

/*
a point, the most basic unit of a line
 */
public class Point {
    float x;
    float y;

    public Point(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public String toString()
    {
        return Float.toString(x) + ":" + Float.toString(y);
    }
}
