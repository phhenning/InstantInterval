package com.henning.pieter.instantinterval;

import android.Manifest;
import android.app.Notification;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import static android.bluetooth.le.ScanSettings.MATCH_MODE_AGGRESSIVE;

public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeScanner mBluetoothLeScanner;
    private ScanFilter mScanFilter;
    private ScanSettings mScanSettings;
    private static final Logger logger = Logger.getLogger("II");
    private static boolean btFilter;
    private SharedPreferences sharedPreferences;
    private boolean sendNoticer;
    private HashMap<String, PodHistory> points = new HashMap<String, PodHistory>();
    private ArrayList<Tag> triggers  = new ArrayList<Tag>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        btFilter = sharedPreferences.getBoolean("pref_btFilter", false);
        sendNoticer = sharedPreferences.getBoolean("pref_notice", true);

        SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                btFilter = sharedPreferences.getBoolean("pref_btFilter", false);
                sendNoticer = sharedPreferences.getBoolean("pref_notice", true);
                Toast.makeText(getApplicationContext(), "isChecked : " + btFilter, Toast.LENGTH_LONG).show();
            }
        };

        sharedPreferences.registerOnSharedPreferenceChangeListener(listener);

        checkBt();

        final Button button = (Button) findViewById(R.id.buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Simple", Toast.LENGTH_LONG).show();//display the text of button1
                sendNotification(v);
                //sendMessage();
                System.out.printf("print button ");
                //logger.log( Level.INFO,"log button");
                Random rand = new Random();
            }
        });

        Thread myThread = null;
        Runnable runnable = new CountDownRunner();
        myThread = new Thread(runnable);
        myThread.start();

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
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


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    protected ScanCallback mScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
//            ScanRecord mScanRecord = result.getScanRecord();
            // byte[] manufacturerData = mScanRecord.getManufacturerSpecificData(224);
//            ScanRecord sr = result.getScanRecord();
//            byte[] mr = sr.getBytes();
//            StringBuilder data = new StringBuilder();
//            for(byte b : mr){
//                data.append(String.format("%02X ", b));
//            }
//            logger.log( Level.INFO, data.toString());
//            I/II: 02 01 1A 1A FF 4C 00 02 15 FD A5 06 93 A4 E2 4F B1 AF CF C6 EB 07 64 78 25 DE AD BE EF C5 0D 09 65 62 65 6F 6F 2D 31 32 42 41 42 43 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00
//            I/II: ebeoo-12BABC : 20:91:48:12:BA:BC - 3    7936
//            I/II: 02 01 1A 1A FF 4C 00 02 15 FD A5 06 93 A4 E2 4F B1 AF CF C6 EB 07 64 78 25 00 01 00 01 C5 0D 09 65 62 65 6F 6F 2D 30 36 42 30 30 41 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00
//            I/II: ebeoo-06B00A : 20:91:48:06:B0:0A - 3    7936

            long time = System.currentTimeMillis();
            // RSSI  the received signal strength in dBm. The valid range is [-127, 126].
            int rssi = result.getRssi();

            BluetoothDevice unit = result.getDevice();
            String name = unit.getName();

            String mgs = unit.getName() + " : " + String.valueOf(rssi) + " # " + Long.toString(time);
            TextView editText = (TextView) findViewById(R.id.textViewPower);
            editText.setText(mgs);
            mgs = unit.getName() + " : " + unit.getAddress() + " - " + unit.getType();
            logger.log(Level.INFO, mgs);


            if (points.containsKey(name)) {
                PodHistory ph = points.get(name);
                ph.update(time, rssi);
                //check for trigger ( power peak detekted )
                Tag ts = ph.getMax();
                if (ts != null ){
                    triggers.add(ts);
                }
            } else {
                PodHistory ph = new PodHistory(time, rssi);
                points.put(name, ph);
            }
        }
    };


    private void checkBt() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            System.out.printf("No Bluetooth");
            Toast.makeText(getApplicationContext(), "App Needs Bluetooth", Toast.LENGTH_LONG).show();//display the text of button1
            this.finish();
            System.exit(0);
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
            mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
            enableBtScan();
        }
    }

    private void enableBtScan() {
        setScanSettings();
        setScanFilter();
        mBluetoothLeScanner.startScan(Arrays.asList(mScanFilter), mScanSettings, mScanCallback);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            // launch settings activity
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setScanFilter() {
        //        System.out.printf("Filter Bluetooth " + btFilter);
        if (btFilter) {
            ScanFilter.Builder mBuilder = new ScanFilter.Builder();
            // the manufacturer data byte is the filter!
            final byte[] manufacturerData = new byte[]{
                    0x4c, 0,
                    // uuid
                    0, 0, 0, 0,
                    0, 0,
                    0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0,
                    // major
                    0, 0,
                    // minor
                    0, 0, 0
            };
            //Set filter on partial manufacture data. For any bit in the mask, set it the 1 if it needs to match the one in manufacturer data, otherwise set it to 0.
            final byte[] manufacturerDataMask = new byte[]{
                    0, 0,
                    // uuid
                    0, 0, 0, 0,
                    0, 0,
                    0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0,
                    // major
                    0, 0,
                    // minor
                    0, 0, 0
            };
            mBuilder.setManufacturerData(76, manufacturerData, manufacturerDataMask);
            mScanFilter = mBuilder.build();
        } else {
            //        try empty filter
            ScanFilter.Builder builder = new ScanFilter.Builder();
            mScanFilter = builder.build();
        }
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
        mBuilder.setMatchMode(MATCH_MODE_AGGRESSIVE);
        mBuilder.setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY);
        mScanSettings = mBuilder.build();
    }

    private void logToDisplay(final String line) {
        runOnUiThread(new Runnable() {
            public void run() {
                TextView editText = (TextView) findViewById(R.id.textViewPower);
                editText.setText(line);
            }
        });
    }

    public void sendNotification(View view) {
        if (sendNoticer) {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, BuildConfig.APPLICATION_ID)
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
    }

    public void sendMessage() {
        if (sendNoticer) {
            ContentValues values = new ContentValues();
            values.put("address", "0832281222");
            values.put("body", "foo bar");
//      getContentResolver().insert(Uri.parse("content://sms/sent"), values);
            getContentResolver().insert(Uri.parse("content://sms/inbox"), values);
        }
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
                try {
                    TextView txtCurrentTime = (TextView) findViewById(R.id.textViewTime);
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    String strDate = sdf.format(c.getTime());
                    txtCurrentTime.setText(strDate);
                } catch (Exception e) {
                }
            }
        });
    }


    class CountDownRunner implements Runnable {
        // @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    doWork();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                }
            }
        }
    }
}


class Tag {
        public long ts;  // timestamp in ms
        public int pwr;
        public int avr; //

        Tag(long timestamp , int power) {
            this.ts = timestamp;
            this.pwr = power;
            this.avr = power;
        }

        Tag(long timestamp , int power , int avr) {
            this.ts = timestamp;
            this.pwr = power;
            this.avr = avr;
        }
    }


class PodHistory {
    public Tag[] tags = new Tag[5];
    int last = 1;

    PodHistory(long time, int rssi) {
        tags[0] = new Tag(time, rssi);
        last = 1;
    }

    // newest valye always at 0
    public void update(long time, int rssi) {
        // shift tags
        for (int i = last; i >=  1; i--) {
            tags[i] = tags[i - 1];
        }
        int avr = (tags[1].avr + rssi) / 2;
        tags[0] = new Tag(time, rssi, avr);
        if (last < 4) {
            last++;
        }
        System.out.println(tags[0].ts + " " + tags[0].avr);
        //isMax();
    }

    // newest tag always at 0
    public Tag getMax() {
        if (last > 2) {
            if ((tags[2].avr < tags[1].avr) && (tags[0].avr < tags[1].avr)) {
                System.out.println("max : " + tags[1].pwr  + " at time " + tags[1].ts);
                return tags[1];
            }
        }
        return null;
    }
}
            //        private E[] array;
//
//        public PeakFinder(E[] array){
//            if(array == null || array.length == 0)
//                throw new IllegalArgumentException("array cannot be null or 0");
//            this.array = array;
//        }
//
//        public E find(){
//            return findUtil(0, array.length - 1);
//        }
//
//        private E findUtil(int left, int right){
//            int mid = (right - left)/2 + left;
//            int newLeft = mid + 1;
//            int newRight = mid - 1;
//
//            if(newLeft <= right && array[newLeft].compareTo(array[mid]) > 0)
//                return findUtil(newLeft, right);
//            else if(newRight >= left && array[newRight].compareTo(array[mid]) > 0)
//                return findUtil(left, newRight);
//            else
//                return array[mid];
//        }
//    }

//import java.util.*;
//
//    public class HelloWorld{
//
//        public static Map m1 = new HashMap();
//        public static void main(String []args){
//            System.out.println("Hello World");
//            makemap();
//            System.out.println();
//            System.out.print("\t" + m1.get("Ayan"));
//            System.out.print("\t" + m1.containsKey("Ayan"));
//        }
//
//        private static void makemap(){
//
//            m1.put("Zara", "8");
//            m1.put("Mahnaz", "31");
//            m1.put("Ayan", "12");
//            m1.put("Daisy", "14");
//
//            System.out.println();
//            System.out.println(" Map Elements");
//            System.out.print("\t" + m1);
//        }
//    }


