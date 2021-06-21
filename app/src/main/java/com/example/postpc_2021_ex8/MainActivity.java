package com.example.postpc_2021_ex8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RootsApplication app = RootsApplication.getInstance();
    RootsHolder holder = app.getDataHolder();
    WorkManager workManager = WorkManager.getInstance(this);
    Context c = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //define of the add calculation fragment
        FragmentContainerView calcFrag = findViewById(R.id.calcFrag);
        AddCalculationFragment acFrag = new AddCalculationFragment();
        getSupportFragmentManager().beginTransaction().replace(calcFrag.getId(), acFrag).commit();





        RecyclerView recyclerView = findViewById(R.id.recycler);
        RootAdapter adapter = new RootAdapter(this, holder);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL, false));

        acFrag.listener = new AddCalculationFragment.OnMyButtonClickedListener() {
            @Override
            public void onButtonClicked() {
                long n = Long.parseLong(acFrag.getText(acFrag.getView()));
                RootCalc num = holder.getRoot(n);
                if (num != null)
                {
                    //TODO - make a toast that number already in the list and leave listener
                }
                else
                {


                    OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(RootWorker.class)
                            .addTag("calculate_roots")
                            .setInputData(new Data.Builder().putLong("number",n).build()).build();

                    workManager.enqueue(request);
                    holder.addNewItem(new RootCalc(request.getId(), n));
                }

                acFrag.setText(acFrag.getView(), "");
            }
        };



        holder.rootsLiveDataPublic.observe(this, new Observer<RootsHolder>() {
            @Override
            public void onChanged(RootsHolder rootsHolder) {
                adapter.notifyDataSetChanged();
            }
        });

        workManager.getWorkInfosByTagLiveData("calculate_roots").observe(this, new Observer<List<WorkInfo>>() {
            @Override
            public void onChanged(List<WorkInfo> workInfos) {
                for (WorkInfo workInfo: workInfos)
                {

                    if (workInfo.getState() == WorkInfo.State.SUCCEEDED)
                    {
                        RootCalc r = holder.getRoot(workInfo.getOutputData().getLong("number", -1));
                        if (r != null)
                        {
                            holder.setRoots(r, workInfo.getOutputData().getString("roots"));
                            holder.markRootDone(r);
                            adapter.notifyDataSetChanged();
                        }

                    }
                }
            }
        });



    }
}