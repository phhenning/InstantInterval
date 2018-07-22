package com.henning.pieter.instantinterval;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogToStorage {
    private static final Logger logger = Logger.getLogger("LS");
        static File file;
        static File dir;


        public LogToStorage(String logFile) {
//            stageLog = logFile;
            boolean storeExternal = isExternalStorageWritable();
            logger.log(Level.INFO, " external writable " + storeExternal);
            if (storeExternal) {
                dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);

                logger.log(Level.INFO, " dir  " + dir.getAbsolutePath());

                if (!dir.exists()) {
                    if (!dir.mkdirs()) {
                        System.out.println("Directory not created");
                    }
                }
                file = new File(dir, "interval.csv");
//                file = new File (Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_DOCUMENTS), "interval.csv");
//                if (!file.mkdirs()) {
//                    logger.log(Level.SEVERE, "Directory not created");
//                }

            }

        }





        /* Checks if external storage is available for read and write */
        public boolean isExternalStorageWritable() {
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                return true;
            }
            return false;
        }

        public void addToFile(String logEntry) throws IOException {
            BufferedWriter bufferedWriter = null;
            FileWriter fileWriter = null;
            logger.log(Level.INFO, "!!! " + logEntry);
            logger.log(Level.INFO, "$$$ " + file.getAbsolutePath());
            try {
                fileWriter = new FileWriter(file, true);
                bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(logEntry);
                bufferedWriter.close();
                fileWriter.close();

            }finally {
                if (bufferedWriter != null ) {
                    bufferedWriter.close();
                }
                if (fileWriter != null ) {
                    fileWriter.close();
                }
            }
        }
    }

