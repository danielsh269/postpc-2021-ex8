package com.example.postpc_2021_ex8;

import java.io.Serializable;

public class RootCalc implements Serializable {
    private long number;
    private boolean status; //false = IN-PROGRESS, true = DONE
    private String roots;

    public RootCalc(long number)
    {
        this.number = number;
        this.status = false;
        this.roots = "prime number";
    }

    public boolean isDone()
    {
        return this.status;
    }

    public void setDone(boolean status)
    {
        this.status = status;
    }

    public void calculateRoots()
    {
        for(long i = 2; i <= this.number / 2; i++)
        {
            if (this.number % i == 0)
            {
                this.roots = i + "x" + (this.number / i);
                break;
            }
        }
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
}
