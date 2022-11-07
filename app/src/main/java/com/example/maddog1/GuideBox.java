package com.example.maddog1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.graphics.BlurMaskFilter.Blur;
import android.util.Log;
import android.view.View;

public class GuideBox extends View {
	
	static int pixX;
	static int pixY;
	private float[] points;
	private Bitmap bitmap;
	private Bitmap scratch;
    private Canvas offscreen;
    Paint paint = new Paint();
    Paint paint2 = new Paint();
	
	public GuideBox(Context context) {
	super(context);
	setOffscreenBitmap();
	
	paint.setAntiAlias(true);
	//paint.setMaskFilter(new EmbossMaskFilter(new float[] { 1, 1, 1 },0.4f, 10, 8.2f));
	
	paint.setStyle(Paint.Style.FILL_AND_STROKE);
	paint.setStrokeCap(Paint.Cap.BUTT);
	paint.setStrokeWidth(5);
	paint.setColor(Color.BLACK);
	paint.setShadowLayer(50, -30, 30, Color.DKGRAY);
	// TODO Auto-generated constructor stub
	}

	protected void onDraw(Canvas canvas) {
	// TODO Auto-generated method stub
	//center
	int x0=canvas.getWidth()/2;
	int y0=canvas.getHeight()/2;
	int dx=canvas.getHeight()/3;
	int dy=canvas.getHeight()/3;
	
	//draw guide box
	//canvas.drawRect(x0-dx, y0-dy, x0+dx, y0+dy, paint);
//this.get
	//canvas.drawPoint(pixX, pixY, paint);
	if(points!=null)
	{
		offscreen.drawPoints(points, paint);
		//canvas.drawPoints(points, paint);
	}
	
	if(bitmap!=null)
	{
		//bitmap = bitmap.extractAlpha();
		//paint2.setMaskFilter(new BlurMaskFilter(100, Blur.NORMAL));
		//canvas.drawBitmap(bitmap, 0, 0, paint);
		canvas.drawBitmap(scratch, 0, 0, paint);
		offscreen.drawColor(Color.WHITE);
	}
	
	super.onDraw(canvas);
	}
	
	public void paintPoints(float[] points)
	{
		this.points = points;
		invalidate();
	}
	
	public void paintPix(int x,int y)
	{
		pixX = x;
		pixY = y;
		invalidate();
	}
	
	public void paintBmp(Bitmap bmp)
	{
		this.bitmap = bmp;
		invalidate();
	}
	
	private void setOffscreenBitmap()
	 
    {
         scratch = Bitmap.createBitmap(640,480,Bitmap.Config.ARGB_8888);
         offscreen = new Canvas();
         offscreen.setBitmap(scratch);
    }
	
	}
