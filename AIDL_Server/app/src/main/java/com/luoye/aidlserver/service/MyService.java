package com.luoye.aidlserver.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.luoye.aidlserver.aidl.IMyAidlInterface;

import java.util.Random;

/**
 * Created by zyw on 18-7-23.
 */

public class MyService extends Service {
    private  String TAG="MyService";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    class MyBinder extends IMyAidlInterface.Stub {

        @Override
        public int getRandomValue() throws RemoteException {
            return new Random().nextInt(10000);
        }
    }
}
