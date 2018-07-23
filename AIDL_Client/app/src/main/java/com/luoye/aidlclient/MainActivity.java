package com.luoye.aidlclient;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.luoye.aidlserver.aidl.IMyAidlInterface;

public class MainActivity extends AppCompatActivity {

    private IMyAidlInterface myAidlInterface;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=(Button)findViewById(R.id.btn_update);
        button.setOnClickListener(new MyOnClick());
        bindService();

    }

    private  void bindService(){
        Intent intent=new Intent();
        intent.setAction("myAction");
        intent.setPackage("com.luoye.aidlserver");
        bindService(intent, myServiceConnection,BIND_AUTO_CREATE);
    }

    private ServiceConnection myServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myAidlInterface=IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bindService();

        }
    };

    private  class MyOnClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.btn_update){
                int val=-1;
                try {
                   val=myAidlInterface.getRandomValue();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                toast("从其他进程得到的随机数："+val);
            }
        }
    }

    private Toast toast;
    private  void toast(CharSequence msg){
        if(toast==null){
            toast=Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT);
        }
        else{
            toast.setText(msg);
        }
        toast.show();
    }
}
