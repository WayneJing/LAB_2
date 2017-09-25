package ece.course.lab_2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceView;

/**
 * Created by chenjingwen on 2017/9/23.
 */

public class DisplayView extends SurfaceView {
    private float mCenterX = 0.0f;
    private float mCenterY = 0.0f;
    private float mRadius = 0.0f;


    public DisplayView(Context context){
        super(context);
        setWillNotDraw(false);
    }

    public void onDraw(Canvas canvas){
        if (canvas == null)
            return;

        canvas.drawColor(Color.BLACK);
        Paint paint = new Paint();
        paint.setColor(Color.LTGRAY);
        canvas.drawCircle(mCenterX, mCenterY, mRadius, paint);

        paint.setColor(Color.RED);
        canvas.drawCircle(40.0f, 40.0f, 10.0f, paint);

        paint.setColor(Color.BLUE);
        canvas.drawRect(70.0f, 30.0f, 90.0f, 50.0f, paint);

        paint.setColor(Color.GREEN);
        Path path = new Path();
        path.moveTo(40.0f, 70.0f);
        path.lineTo(30.0f, 80.0f);
        path.lineTo(40.0f, 90.0f);
        path.lineTo(50.0f, 80.0f);
        path.close();
        canvas.drawPath(path, paint);

        paint.setColor(Color.WHITE);
        canvas.drawArc(new RectF(70.0f, 70.0f, 90.0f, 90.0f),
                -45.0f, -90.0f, true, paint);
    }
    public void onSizeChanged(int width, int height, int oldWidth, int oldHeight){
        mCenterX = width/2;
        mCenterY = height/2;
        mRadius = ((width < height)?width:height)*3.0f/8.0f;
        invalidate();
    }
}
