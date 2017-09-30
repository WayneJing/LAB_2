package ece.course.lab2assign;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private final static float MAX_GRAVITY = 9.82f;
    private final static float THRESHOLD = 3.0f;
    private float mX = -100.0f;
    private float mY = -100.0f;
    private float mZ = -100.0f;
    private float mForce=0.0f;
    private float mForceOld=0.0f;
    private int mCount=0;
    private int flag=0;
    private float time=0.0f;
    private DisplayView mDisplayView;
    private AccelerometerSensor mAccelerometerSensor;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDisplayView = (DisplayView)findViewById(R.id.mDisplayView);

        mAccelerometerSensor=new AccelerometerSensor(this,new Handler() {
            @Override
            public void handleMessage(Message msg) {
                float tmpX = msg.getData().getFloat(AccelerometerSensor.TAG_VALUE_DX);
                float tmpY = -msg.getData().getFloat(AccelerometerSensor.TAG_VALUE_DY);
                float tmpZ = msg.getData().getFloat(AccelerometerSensor.TAG_VALUE_DZ);
                float tmptime = (float) (msg.getData().getLong(AccelerometerSensor.TAG_VALUE_TIME)*1.0f/1000000000.0f);
                //TextView tvValueTime = (TextView)findViewById(R.id.tvValueTime);
                TextView tvValueForce=(TextView)findViewById(R.id.tvValueForce);
                float tmpForce=(float) Math.sqrt(tmpX*tmpX+tmpY*tmpY+tmpZ*tmpZ);
                if (tmpX - mX > THRESHOLD || tmpX - mX < -THRESHOLD ||
                        tmpY - mY > THRESHOLD || tmpY - mY < -THRESHOLD ||
                        tmpZ - mZ > THRESHOLD || tmpZ - mZ < -THRESHOLD) {
                    mX = tmpX;
                    mY = tmpY;
                    mZ = tmpZ;

                    if (tmpForce-mForce>3.0f&& (tmptime-time>0.25f)){

                        mForceOld=mForce;
                        mCount=mCount+1;
                        time=tmptime;
                    }
                    mForce= (float) Math.sqrt(mX*mX+mY*mY+mZ*mZ);

                    TextView tvValueX = (TextView) findViewById(R.id.tvValueX);
                    TextView tvValueY = (TextView) findViewById(R.id.tvValueY);
                    TextView tvValueZ = (TextView) findViewById(R.id.tvValueZ);

                    TextView tvValueCount=(TextView)findViewById(R.id.tvValueCount);
                    tvValueX.setText("" + mX);
                    tvValueY.setText("" + mY);
                    tvValueZ.setText("" + mZ);

                    tvValueCount.setText(""+mCount);

                }
                tvValueForce.setText(""+mForce);
                //tvValueTime.setText(""+time);
                mDisplayView.setPtr( (tmpForce-mForceOld) / MAX_GRAVITY);
            }
        });
        Button btnClear=(Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCount=0;
                TextView tvValueCount=(TextView)findViewById(R.id.tvValueCount);
                tvValueCount.setText(""+mCount);

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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
