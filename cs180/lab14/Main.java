package edu.purdue.YL.lab14;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class Main extends Activity {
	/** Called when the activity is first created. */
	DrawView drawView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Set full screen view
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//add DrawView to screen
		drawView = new DrawView(this);
		setContentView(drawView);
		drawView.requestFocus();
	}
}

class DrawView extends View {
	private static final String USERNAME = "your teams user names";
	Paint paint = new Paint();
    
	public DrawView(Context context) {
		super(context);
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
	}

	//This is called everytime screen is refreshed
	//Screen can be refreshed by orientation
	@Override
	public void onDraw(Canvas canvas) {
		
		//Find if triangles side should be the length of the
		//width or height of the kindle device. This ensures that 
		//the triangle can be seen in both orientations.
		int w = getWidth();
		int h = getHeight();
		int size = (w>h)? h:w;
		
		//Creates three points each being a vertex of the triangle
        int triangleHeight = (int) Math.round(size * Math.sqrt(3.0) / 2.0);
        Point p1 = new Point(0, triangleHeight);	//bottom right vertex
        Point p2 = new Point(size / 2, 0); 			//middle top vertex
        Point p3 = new Point(size, triangleHeight);	//bottom left vertex
        
        drawFigure(5, canvas, p1, p2, p3);			//first and only call to drawFigure outside of drawFigure
        
        canvas.drawText(USERNAME, 5,100, paint);	//draw usernames to screen
	}

	public void drawFigure(int level, Canvas g, Point p1, Point p2,Point p3) {


	}

	// returns the midpoint of p1 and p2
	public static Point midpoint(Point p1, Point p2) {
		return new Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
	}

}

class Point {
	float x, y;
	
	public Point(float f,float g){
		this.x=f;
		this.y=g;
	}
	
	/**
	 * @return the x
	 */
	public float getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public float getY() {
		return y;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(float y) {
		this.y = y;
	}
}