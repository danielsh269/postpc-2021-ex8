package com.example.postpc_2021_ex8;

import java.io.Serializable;
import java.util.UUID;

public class RootCalc implements Serializable {
    public UUID id;
    private final long number;
    private boolean status; //false = IN-PROGRESS, true = DONE
    private String roots;
    long progress;


    public RootCalc(UUID id, long number)
    {
        this.id = id;
        this.number = number;
        this.status = false;
        this.roots = "prime number";
        this.progress = 0;
    }

    public boolean isDone()
    {
        return this.status;
    }

    public void setDone(boolean status)
    {
        this.status = status;
    }

    public void setRoots(String roots)
    {
        this.roots = roots;
    }

    public String getRoots()
    {
        if (!this.status)
        {
            return "";
        }

        return this.roots;
    }

    public long getNumber()
    {
        return this.number;
    }

    public long getProgress()
    {
        return this.progress;
    }

    public void setProgress(long progress)
    {
        this.progress = progress;
    }
}
