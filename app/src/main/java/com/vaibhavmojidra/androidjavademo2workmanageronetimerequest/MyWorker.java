package com.vaibhavmojidra.androidjavademo2workmanageronetimerequest;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorker extends Worker {


    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        int upperLimit=getInputData().getInt("UPPER_LIMIT",0);
        int noOfOddNumbers=0;
        try{


            for(int i=1;i<upperLimit;i++){
                if(i%2!=0){
                    noOfOddNumbers++;
                }
                Log.i("MyTag",i+"");
            }

            Data outputData=new Data.Builder().putInt("ODD_NUMBERS",noOfOddNumbers).build();
            //throw new Exception("Error");

            return Result.success(outputData);

        }catch (Exception e){
            return Result.failure();
        }
    }
}
