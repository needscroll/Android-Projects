package com.example.fingerpainting;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/*
this class doesn't actually do anything but I don't want to remove it because I am afraid that things might break if I do....
just ignore this class
 */
public class CircleView extends View {

    private Paint paint;
    public static int BRUSH_SIZE = 10;

    public CircleView(Context context) {
        super(context);
        paint = new Paint();
        paint.setColor(Color.GRAY);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        canvas.drawCircle(200, 200, 50, paint);
        canvas.drawCircle(300, 200, 50, paint);
    }

}
