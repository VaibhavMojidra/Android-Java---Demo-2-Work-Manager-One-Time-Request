package com.vaibhavmojidra.androidjavademo2workmanageronetimerequest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.vaibhavmojidra.androidjavademo2workmanageronetimerequest.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);

        Constraints constraints=new Constraints.Builder().setRequiresCharging(true).build();

        Data inputData=new Data.Builder().putInt("UPPER_LIMIT",60000).build();

        OneTimeWorkRequest workRequest= new OneTimeWorkRequest.Builder(MyWorker.class)
                .setConstraints(constraints)
                .setInputData(inputData)
                .build();

        WorkManager workManager=WorkManager.getInstance(this);

        workManager.getWorkInfoByIdLiveData(workRequest.getId()).observe(this, workInfo -> {
            binding.workStatusTextView.setText(workInfo.getState().name());
            if(workInfo.getState().isFinished()){
                int oddNumbers=workInfo.getOutputData().getInt("ODD_NUMBERS",0);
                Toast.makeText(this, "Total Odd Numbers: "+oddNumbers, Toast.LENGTH_SHORT).show();
            }
        });

        binding.startWorkButton.setOnClickListener(view -> {
            workManager.enqueue(workRequest);
        });

    }
}