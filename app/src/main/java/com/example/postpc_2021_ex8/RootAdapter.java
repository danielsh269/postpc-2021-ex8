package com.example.postpc_2021_ex8;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.WorkManager;

public class RootAdapter extends RecyclerView.Adapter<ViewRootHolder> {

    RootsHolder holder;
    Context context;

    public RootAdapter(Context context, RootsHolder holder)
    {
        this.context = context;
        this.holder = holder;
    }


    @NonNull
    @Override
    public ViewRootHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.row_root_item, parent, false);
        return new ViewRootHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewRootHolder holder, int position) {

        RootCalc item = this.holder.rootList.get(position);
        if (item.isDone())
        {
            String str = "Roots for " + item.getNumber() + ": ";
            holder.numberView.setText(str);
            holder.rootsView.setText(item.getRoots());
            holder.progressBar.setVisibility(View.GONE);
        }
        else
        {
            String str = "Calculation roots for " + item.getNumber();
            holder.numberView.setText(str);
            holder.rootsView.setText("");
            holder.progressBar.setVisibility(View.VISIBLE);
            int percent = (int) (100 * ((double) item.getProgress() / (item.getNumber() / 2)));
            holder.progressBar.setProgress(percent);
        }

        holder.deleteButton.setOnClickListener(view->{
            if (!item.isDone())
            {
                WorkManager workManager = WorkManager.getInstance(context);
                workManager.cancelWorkById(item.id);
            }
            this.holder.deleteRoot(item);
        });


    }

    @Override
    public int getItemCount() {
        return holder.rootList.size();
    }
}

class ViewRootHolder extends RecyclerView.ViewHolder
{

    TextView numberView, rootsView;
    ImageButton deleteButton;
    ProgressBar progressBar;

    public ViewRootHolder(@NonNull View itemView) {
        super(itemView);
        this.numberView = itemView.findViewById(R.id.rootsForView);
        this.rootsView = itemView.findViewById(R.id.theRoots);
        this.deleteButton = itemView.findViewById(R.id.deleteButton);
        this.progressBar = itemView.findViewById(R.id.progressBar);
    }
}
