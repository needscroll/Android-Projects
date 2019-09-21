package com.example.fingerpainting;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

/*
name: james wei
class: cs 4301.002 android develoipement
netid: jaw160230
program description: this is a fingerpainting app. It will draw pretty lines on the screen based on where you touch it with your finger
 */
public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    //variables for line color and thickness
    float oldx = -1;
    float oldy = -1;
    int radius = 10;
    int color = Color.BLACK;

    //stores the lines in a stack
    ArrayList<Line> lines = new ArrayList<Line>();
    Line topLine = new Line(Color.BLACK, radius);
    private CircleView paintArea;

    //paint stuff
    ImageView imageview;
    Bitmap bitmap;
    Canvas canvas;
    Paint paint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        //sets up all the drawing stuff
        imageview = (ImageView) this.findViewById(R.id.canvas);
        Display currentDisplay = getWindowManager().getDefaultDisplay();
        float dw = currentDisplay.getWidth();
        float dh = currentDisplay.getHeight();
        bitmap = Bitmap.createBitmap((int) dw, (int) dh, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint = new Paint();
        paint.setStrokeWidth(radius);
        paint.setColor(Color.BLACK);
        imageview.setImageBitmap(bitmap);

        //float[] a = {200, 200, 400,200};
        //canvas.drawCircle(200, 200, 50, paint);
        //canvas.drawLines(a, paint);
        //sets the touch listener
        View mView = findViewById(R.id.main);
        mView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int count = event.getPointerCount();
                float x = event.getX();
                float y = event.getY();

                TextView text = findViewById(R.id.text);
                text.setText("x is: " + Float.toString(x) + "\n y is: " + Float.toString(y) + "\n" + Float.toString(event.getPressure()));

                int action = event.getAction();

                switch (action)
                {
                    case MotionEvent.ACTION_DOWN:
                        oldx = x;
                        oldy = y;
                        topLine = new Line(color, radius);
                        break;
                    case MotionEvent.ACTION_UP:
                        topLine.setColor(color);
                        topLine.setWidth(radius);
                        lines.add(topLine);
                        break;
                }
                drawLine(oldx, oldy, x, y);

                //debug(getColors());
                return true; //return true to consume event and get continuous touch events
            }
        });
        debug("debug works");

    }

    //draws a line beteween registered points
    private void drawLine(float x1, float y1, float x2, float y2)
    {
        //canvas.drawCircle(x, y, radius, paint);
        canvas.drawLine(x1, y1, x2, y2, paint);
        topLine.addPoint(x2, y2);
        oldx = x2;
        oldy = y2;
    }

    //erases the most recently drawn line
    public void reverseLine(View view)
    {
        int oldRadius = radius;
        int oldColor = color;

        if (lines.size() > 0)
        {
            lines.remove(lines.size() - 1);
        }
        canvas.drawColor(Color.WHITE);

        for (int i = 0; i < lines.size(); i++)
        {
            Line line = lines.get(i);
            Point[] points = line.getPoints();
            paint.setColor(line.color);
            paint.setStrokeWidth(line.width);

            for (int j = 0; j < points.length - 1; j++)
            {
                drawLine(points[j].x, points[j].y, points[j+1].x, points[j+1].y);
            }
        }

        radius = oldRadius;
        color = oldColor;
        paint.setStrokeWidth(radius);
        paint.setColor(color);
    }

    //gets colors for debugging, not actually used now
    public String getColors()
    {
        String result = "";

        for (Line l: lines)
        {
            result += l.color + "W";
        }

        return result;
    }

    //changes line color
    public void changeColor(View view)
    {
        TextView text = ((TextView) view);
        String buttonAction = text.getText().toString();

        switch (buttonAction)
        {
            case "Red":
                color = Color.RED;
                paint.setColor(color);
                break;
            case "Blue":
                color = Color.BLUE;
                paint.setColor(color);
                break;
            case "Green":
                color = Color.GREEN;
                paint.setColor(color);
                break;
            case "Black":
                color = Color.BLACK;
                paint.setColor(color);
                break;
        }
    }

    //code to change line size based on seekbar
    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            radius = progress;
            paint.setStrokeWidth(radius);
            debug(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    //the next four functions print stuff to the screen to debug
    public void debug(String custom_string)
    {
        TextView a = findViewById(R.id.debug);
        a.setText(custom_string);
    }

    public void debug(int num)
    {
        TextView a = findViewById(R.id.debug);
        a.setText(Integer.toString(num));
    }

    public void debug2(String custom_string)
    {
        TextView a = findViewById(R.id.debug2);
        a.setText(custom_string);
    }

    public void debug2(int num)
    {
        TextView a = findViewById(R.id.debug2);
        a.setText(Integer.toString(num));
    }

    //default onTouch override
    @Override
    public boolean onTouch(View view, MotionEvent event)
    {
        return true;
    }
}
