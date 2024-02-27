package com.example.remotetv;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.connectsdk.*;
import com.connectsdk.device.ConnectableDevice;
import com.connectsdk.device.ConnectableDeviceListener;
import com.connectsdk.device.DevicePicker;
import com.connectsdk.discovery.DiscoveryManager;
import com.connectsdk.service.DeviceService;
import com.connectsdk.service.capability.listeners.ResponseListener;
import com.connectsdk.service.command.ServiceCommandError;

import java.util.List;

public class DeviceController extends Activity{

    ConnectableDeviceListener mDeviceListener = new ConnectableDeviceListener() {
        @Override
        public void onDeviceReady(ConnectableDevice device) {

        }

        @Override
        public void onDeviceDisconnected(ConnectableDevice device) {

        }

        @Override
        public void onPairingRequired(ConnectableDevice device, DeviceService service, DeviceService.PairingType pairingType) {

        }

        @Override
        public void onCapabilityUpdated(ConnectableDevice device, List<String> added, List<String> removed) {

        }

        @Override
        public void onConnectionFailed(ConnectableDevice device, ServiceCommandError error) {

        }
    };
    private DiscoveryManager mDiscoveryManager;
    private ConnectableDevice mDevice;

    private Activity mainActivity;
    DevicePicker devicePicker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This step could even happen in your app's delegate
        mDiscoveryManager = DiscoveryManager.getInstance();
        mDiscoveryManager.setPairingLevel(DiscoveryManager.PairingLevel.ON);
        mDiscoveryManager.registerDefaultDeviceTypes();
        mDiscoveryManager.start();
    }

    public void FindTV(Activity mainActivity) {
        AdapterView.OnItemClickListener selectDevice = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View parent, int position, long id) {
                mDevice = (ConnectableDevice) adapter.getItemAtPosition(position);
                mDevice.addListener(mDeviceListener);
                mDevice.connect();
            }
        };

        devicePicker = new DevicePicker(mainActivity);
        AlertDialog dialog = devicePicker.getPickerDialog("Select TV", selectDevice);
        dialog.show();
    }

    public void TurnOnTV(Activity mainActivity) {
        ResponseListener<Object> listener = new ResponseListener<Object>() {
            @Override
            public void onSuccess(Object object) {
                String text = "Turn On TV!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(mainActivity, text, duration);
                toast.show();
            }

            @Override
            public void onError(ServiceCommandError error) {
                String text = "Error when turn on TV!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(mainActivity, text, duration);
                toast.show();
                Log.d("App Tag", "Turn on TV failure: " + error);
            }
        };
        if (mDevice != null){
            mDevice.getPowerControl().powerOn(listener);
        }
        else {
            String text = "No TV device to turn on!";
            devicePicker = new DevicePicker(mainActivity);
            AlertDialog dialog = devicePicker.getPickerDialog(text, null);
            dialog.show();

            // TODO: Find TV device
            //FindTV(mainActivity);
        }

    }

    public void TurnOffTV(Activity mainActivity) {
        ResponseListener<Object> listener = new ResponseListener<Object>() {
            @Override
            public void onSuccess(Object object) {
                String text = "Turn Off TV!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(mainActivity, text, duration);
                toast.show();
            }

            @Override
            public void onError(ServiceCommandError error) {
                Log.d("App Tag", "Turn off TV failure: " + error);
            }
        };
        if (mDevice != null){
            mDevice.getPowerControl().powerOn(listener);
        }
        else {
            String text = "No TV device to turn off!";
            int duration = Toast.LENGTH_SHORT;
            devicePicker = new DevicePicker(mainActivity);
            AlertDialog dialog = devicePicker.getPickerDialog(text, null);
            dialog.show();

            // TODO: Find TV device
            //FindTV(mainActivity);
        }
    }

    public void SetMainActivity(Activity mainActivity){
        this.mainActivity = mainActivity;
    }

    public void SetDevicePicker(Activity mainActivity){
        devicePicker = new DevicePicker(mainActivity);
    }

}
