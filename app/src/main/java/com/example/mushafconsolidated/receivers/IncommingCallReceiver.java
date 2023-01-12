package com.example.mushafconsolidated.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class IncommingCallReceiver extends BroadcastReceiver {
    AppAudioManager audi=new AppAudioManager();
    @Override
    public void onReceive(Context context, Intent intent) {

        try {
            System.out.println("Receiver start");
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            //receive_incoming_call
            if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)){

                LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent("receive_incoming_call").putExtra("state",1));
            }
            //EXTRA_STATE_OFFHOOK
            if ((state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))){
                LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent("EXTRA_STATE_OFFHOOK").putExtra("state",3));
            }
            //rejected_incoming_call
            if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent("rejected_incoming_call").putExtra("state",2));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
