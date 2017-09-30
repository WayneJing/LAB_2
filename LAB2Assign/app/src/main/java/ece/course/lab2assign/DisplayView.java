package ece.course.lab2assign;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * Created by chenjingwen on 2017/9/23.
 */

public class DisplayView extends SurfaceView {


    private float mCenterX = 0.0f;
    private float mCenterY = 0.0f;
    private float mCenterHeight=0.0f;
    private float mCenterWidth=0.0f;




    private float mPtrCenterX = 0.0f;
    private float mPtrCenterY = 0.0f;
    private float mPtrRadius = 0.0f;

    private int mPtrColor = Color.LTGRAY;

    public DisplayView(Context context, AttributeSet attrs){
        super(context, attrs);
        setWillNotDraw(false);
    }
    public void setPtr( float posY) {

        mPtrCenterY = -posY  * 2*mCenterHeight + mCenterY;
        invalidate();
    }
    public void onDraw(Canvas canvas){
        if (canvas == null)
            return;

        canvas.drawColor(Color.BLACK);
        Paint paint = new Paint();

        paint.setColor(Color.GREEN);
        canvas.drawRect(mCenterX-mCenterWidth,mCenterY-mCenterHeight,mCenterX+mCenterWidth,mCenterY+mCenterHeight,paint);

        paint.setColor(Color.YELLOW);
        canvas.drawRect(mCenterX-mCenterWidth,mCenterY+mCenterHeight,mCenterX+mCenterWidth,mCenterY+3*mCenterHeight,paint);
        canvas.drawRect(mCenterX-mCenterWidth,mCenterY-3*mCenterHeight,mCenterX+mCenterWidth,mCenterY-mCenterHeight,paint);

        paint.setColor(Color.RED);
        canvas.drawRect(mCenterX-mCenterWidth,mCenterY+3*mCenterHeight,mCenterX+mCenterWidth,mCenterY+4.5f*mCenterHeight,paint);
        canvas.drawRect(mCenterX-mCenterWidth,mCenterY-4.5f*mCenterHeight,mCenterX+mCenterWidth,mCenterY-3*mCenterHeight,paint);

        paint.setColor(Color.LTGRAY);

        canvas.drawCircle(mPtrCenterX,mPtrCenterY,mCenterWidth/1.4f,paint);

    }
    public void onSizeChanged(int width, int height, int oldWidth, int oldHeight){
        mCenterX = width/2;
        mCenterY = height/2;
        mPtrCenterX=mCenterX;
        mPtrCenterY=mCenterY;
        mCenterHeight=height*1.0f/11.0f;
        mCenterWidth=width*1.0f/14.0f;
        invalidate();
    }

}