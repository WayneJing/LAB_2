package ece.course.lab_2_1;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private final static float MAX_GRAVITY  =9.82f;

    private DisplayView mDisplayView;
    private AccelerometerSensor mAccelerometerSensor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDisplayView = (DisplayView)findViewById(R.id.mDisplayView);

        mAccelerometerSensor=new AccelerometerSensor(this,new Handler(){
            @Override
            public void handleMessage(Message msg){
                float tmpX = msg.getData().getFloat(AccelerometerSensor.TAG_VALUE_DX);
                float tmpY = -msg.getData().getFloat(AccelerometerSensor.TAG_VALUE_DY);
                float tmpZ = msg.getData().getFloat(AccelerometerSensor.TAG_VALUE_DZ);

                TextView tvValueX = (TextView) findViewById(R.id.tvValueX);
                TextView tvValueY = (TextView) findViewById(R.id.tvValueY);
                TextView tvValueZ = (TextView) findViewById(R.id.tvValueZ);

                tvValueX.setText(""+tmpX);
                tvValueY.setText(""+tmpY);
                tvValueZ.setText(""+tmpZ);

                mDisplayView.setPtr(tmpX/ MAX_GRAVITY,tmpY/MAX_GRAVITY );

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.menuPtrBall :
                mDisplayView.setPtrType(DisplayView.TYPE_BALL);
                return true;
            case R.id.menuPtrSquare :
                mDisplayView.setPtrType(DisplayView.TYPE_SQUARE);
                return true;
            case R.id.menuPtrDiamond :
                mDisplayView.setPtrType(DisplayView.TYPE_DIAMOND);
                return true;
            case R.id.menuPtrArc :
                mDisplayView.setPtrType(DisplayView.TYPE_ARC);
                return true;
            case R.id.menuPtrRed :
                mDisplayView.setPtrColor(Color.RED);
                return true;
            case R.id.menuPtrBlue :
                mDisplayView.setPtrColor(Color.BLUE);
                return true;
            case R.id.menuPtrGreen :
                mDisplayView.setPtrColor(Color.GREEN);
                return true;
            case R.id.menuPtrWhite :
                mDisplayView.setPtrColor(Color.WHITE);
                return true;
        }
        return false;
    }

    public synchronized void onResume() {
        super.onResume();
        if (mAccelerometerSensor != null) {
            mAccelerometerSensor.startListening();
        }
    }

    public synchronized void onPause() {
        if (mAccelerometerSensor != null) {
            mAccelerometerSensor.stopListening();
        }
        super.onPause();
    }
}
