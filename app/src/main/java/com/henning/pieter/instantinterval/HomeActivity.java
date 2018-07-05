package com.henning.pieter.instantinterval;

import android.Manifest;
import android.app.Notification;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

//import org.altbeacon.beacon.*;   //https://altbeacon.github.io/android-beacon-library/samples.html
//https://www.pubnub.com/blog/2015-04-14-building-android-beacon-android-ibeacon-tutorial-overview/
//https://www.pubnub.com/blog/2015-04-15-build-android-beacon-ibeacon-detector/
//https://www.pubnub.com/blog/2015-04-16-build-android-ibeacon-beacon-emitter/
public class HomeActivity extends AppCompatActivity {
//public class HomeActivity extends Activity {

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeScanner mBluetoothLeScanner;
    private ScanFilter mScanFilter;
    private ScanSettings mScanSettings;
    private static final Logger logger =  Logger.getLogger( HomeActivity.class.getName() );

    protected ScanCallback mScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            ScanRecord mScanRecord = result.getScanRecord();
            // byte[] manufacturerData = mScanRecord.getManufacturerSpecificData(224);
            // RSSI  the received signal strength in dBm. The valid range is [-127, 126].
            int rssi = result.getRssi();
            BluetoothDevice unit = result.getDevice();

            String mgs = unit.getName() + " : " + String.valueOf(rssi);
            TextView editText = (TextView)findViewById(R.id.textViewPower);
            editText.setText(mgs);
//            logToDisplay(String.valueOf(rssi));
//            mgs = unit.getName() + " : " + unit.getAddress() + " - " + result.getScanRecord()    ;
            mgs = unit.getName() + " : " + unit.getAddress() + " - "   + String.valueOf(rssi)    ;
            logger.log( Level.INFO, mgs);
            //  int txPower = result.getTxPower();
        }
    };



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment ())
                .commit();

//        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
////        verifyBluetooth();
//        mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
//        setScanFilter();
//        setScanSettings();
//
////        mBluetoothLeScanner.startScan(Arrays.asList(mScanFilter), mScanSettings, mScanCallback);
//        mBluetoothLeScanner.startScan( mScanCallback);

        final Button button = (Button)findViewById(R.id.buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Toast.makeText(getApplicationContext(), "Simple", Toast.LENGTH_LONG).show();//display the text of button1
                //sendNotification(v);
                //sendMessage();
                System.out.printf("print button ");
                logger.log( Level.INFO,"log button");
                Random rand = new Random();
            }
        });

        Thread myThread = null;
        Runnable runnable = new CountDownRunner();
        myThread= new Thread(runnable);
        myThread.start();

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1);
        //        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);
//        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { 
//            // Android M Permission check 
//            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) { 
//                final AlertDialog.Builder builder = new AlertDialog.Builder(this); 
//                builder.setTitle("This app needs location access");
//                builder.setMessage("Please grant location access so this app can detect beacons.");
//                builder.setPositiveButton(android.R.string.ok, null); 
//                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {  
//                    @Override 
//                    public void onDismiss(DialogInterface dialog) {
//                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION); 
//                    }  
//                }); 
//                builder.show(); 
//            }
//         }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case PERMISSION_REQUEST_COARSE_LOCATION: {
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Log.d(TAG, "coarse location permission granted");
//                } else {
//                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                    builder.setTitle("Functionality limited");
//                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons when in the background.");
//                    builder.setPositiveButton(android.R.string.ok, null);
//                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
//
//                        @Override
//                        public void onDismiss(DialogInterface dialog) {
//                        }
//
//                    });
//                    builder.show();
//                }
//                return;
//            }
//        }
//    }



//    public double calculateDistance(int txPower, double rssi) {
//        if (rssi == 0) {
//            return -1.0; // if we cannot determine accuracy, return -1.
//        }
//        double ratio = rssi*1.0/txPower;
//        if (ratio < 1.0) {
//            return Math.pow(ratio,10);
//        }
//        else {
//            double accuracy =  (0.89976)*Math.pow(ratio,7.7095) + 0.111;
//            return accuracy;
//        }
//    }

    private void checckBt() {
        BluetoothAdapter bluetoothAdapter;
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
//            textInfo.setText("BlueTooth not supported in this device");
        } else {
            if (!bluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
            ;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle app bar item clicks here. The app bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
           case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                // Do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    private void setScanFilter() {
        ScanFilter.Builder mBuilder = new ScanFilter.Builder();
        ByteBuffer mManufacturerData = ByteBuffer.allocate(23);
        ByteBuffer mManufacturerDataMask = ByteBuffer.allocate(24);
//        UUID uid = UUID.fromString("0CF052C297CA407C84F8B62AAC4E9020");
//        byte[] uuid = getIdAsByte(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"));
        byte[] uuid = getIdAsByte(UUID.fromString("0CF052C2-97CA-407C-84F8-B62AAC4E9020"));
        mManufacturerData.put(0, (byte)0xBE);
        mManufacturerData.put(1, (byte)0xAC);
        for (int i=2; i<=17; i++) {
            mManufacturerData.put(i, uuid[i-2]);
        }
        for (int i=0; i<=17; i++) {
            mManufacturerDataMask.put((byte)0x01);
        }
        mBuilder.setManufacturerData(224, mManufacturerData.array(), mManufacturerDataMask.array());
        mScanFilter = mBuilder.build();
    }

    public static byte[] getIdAsByte(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }

    private void setScanSettings() {
        ScanSettings.Builder mBuilder = new ScanSettings.Builder();
        mBuilder.setReportDelay(0);
        mBuilder.setScanMode(ScanSettings.SCAN_MODE_LOW_POWER);
        mScanSettings = mBuilder.build();
    }

    private void logToDisplay(final String line) {
        runOnUiThread(new Runnable() {
            public void run() {
                TextView editText = (TextView)findViewById(R.id.textViewPower);
                editText.setText(line);
            }
        });
    }

    public void sendNotification(View view) {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, BuildConfig.APPLICATION_ID  )
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("interval")
                .setContentText("my time")
                .setDefaults(Notification.DEFAULT_ALL)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Much longer text that cannot fit one line..."))
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1234, mBuilder.build());

    }

    public void sendMessage() {
        ContentValues values = new ContentValues();
        values.put("address", "0832281222");
        values.put("body", "foo bar");
//      getContentResolver().insert(Uri.parse("content://sms/sent"), values);
//        getContentResolver().insert(Uri.parse("content://sms/inbox"), values);
        getContentResolver().insert(Uri.parse("content://sms/inbox"), values);

    }

//    // normal sms
//    public void sendMessage() {
//        try {
//            SmsManager smsManager = SmsManager.getDefault();
//            smsManager.sendTextMessage("Interval", null, "my time", null, null);
//            Toast.makeText(getApplicationContext(), "Message Sent",
//                    Toast.LENGTH_LONG).show();
//        } catch (Exception ex) {
//            Toast.makeText(getApplicationContext(),ex.getMessage().toString(),
//                    Toast.LENGTH_LONG).show();
//            ex.printStackTrace();
//        }
//    }

    public void doWork() {
        runOnUiThread(new Runnable() {
            public void run() {
                try{
                    TextView txtCurrentTime = (TextView)findViewById(R.id.textViewTime);
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    String strDate = sdf.format(c.getTime());
                    txtCurrentTime.setText(strDate);
                }catch (Exception e) {}
            }
        });
    }


    class CountDownRunner implements Runnable{
        // @Override
        public void run() {
            while(!Thread.currentThread().isInterrupted()){
                try {
                    doWork();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }catch(Exception e){
                }
            }
        }
    }




}
