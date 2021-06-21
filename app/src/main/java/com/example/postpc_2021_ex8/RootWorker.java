package com.example.postpc_2021_ex8;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class RootWorker extends Worker{

    RootsApplication app = RootsApplication.getInstance();
    RootsHolder holder = app.getDataHolder();
    RootCalc r;
    public RootWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        long n = getInputData().getLong("number", -1);
        this.r = holder.getRoot(n);
        long[] roots = calculateRoots(n);
        if (roots[0] == n)
            return Result.success(new Data.Builder().putString("roots", "number is prime").putLong("number", n).build());
        if (roots[0] != -1)
            return Result.success(new Data.Builder().putString("roots", roots[0] + "x" + roots[1]).putLong("number", n).build());

        return Result.retry();

    }

    private long[] calculateRoots(long n)
    {
        long start = System.currentTimeMillis();
        long curr = System.currentTimeMillis();
        long i = Math.max(2, this.r.getProgress());

        while(curr - start < 600_000L && !isStopped())
        {
            if (n % i == 0)
                return new long[]{n / i, i};

            if (i == n / 2)
                return new long[]{n, 1};

            //this.holder.setProgress(this.r, i);
            i++;
            curr = System.currentTimeMillis();
        }

        return new long[]{-1, -1};
    }
}
