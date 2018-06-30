package com.mostafa.android.mqtt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    MQTHelper mqttHelper;
    @BindView(R.id.receiverd)
    TextView textViewReceived;
    @BindView(R.id.Edit_Client)
    EditText editTextReceived;
    public String client;
    boolean isOnline=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


    }

    public void send(String msg) {
        try {
            mqttHelper.mqttAndroidClient.publish("Mqtt/Example", msg.getBytes(), 0, false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void receivemsg(String client) {
        mqttHelper = new MQTHelper(getApplicationContext(),client);
        isOnline = true;
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {

            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.w("Debug", mqttMessage.toString());
                textViewReceived.setText(mqttMessage.toString());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }


    public void sendhello(View view) {
        if (isOnline) {
            send("hello");
        }else{
            Toast.makeText(this, "Enter the Client ID", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendbye(View view) {
        if (isOnline) {
            send("bye");
        }else {
            Toast.makeText(this, "Enter the Client ID", Toast.LENGTH_SHORT).show();
        }
    }

    public void startMqtt(View view) {
        client = editTextReceived.getText().toString();
        if (client.isEmpty()){
            editTextReceived.setError("Enter The client here");
        }
        receivemsg(client);
    }
}
