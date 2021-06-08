package com.example.postpc_2021_ex8;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class RootsHolder implements Serializable {
    List<RootCalc> rootList;
    private final Context context;
    private final SharedPreferences sp;

    private final MutableLiveData<RootsHolder> rootsLiveDataMutable = new MutableLiveData<>();
    public final LiveData<RootsHolder> rootsLiveDataPublic = rootsLiveDataMutable;

    public RootsHolder(Context context) {
        this.context = context;
        this.rootList = new ArrayList<>();
        this.sp = context.getSharedPreferences("local_db_roots", Context.MODE_PRIVATE);
        initializeFromSp();

    }

    private void initializeFromSp()
    {
        Set<String> keys = sp.getAll().keySet();
        for (String key: keys)
        {
            String itemSavedAsString = sp.getString(key, null);
            RootCalc item = stringToItem(itemSavedAsString);
            if (item != null)
            {
                this.addNewItem(item);
            }
        }
        this.rootsLiveDataMutable.setValue(this);
    }

    public RootCalc stringToItem(String s)
    {
        try
        {
            String[] split = s.split("#");
            long number = Long.parseLong(split[0]);
            boolean status = Boolean.parseBoolean(split[1]);
            String roots = split[2];
            RootCalc item = new RootCalc(number);
            item.setDone(status);
            item.setRoots(roots);
            return item;
        }
        catch (Exception e)
        {
            System.out.println("Exception in stringToItem");
            return null;
        }
    }
    public void addNewItem(RootCalc item) {

        this.rootList.add(item);
        Collections.sort(this.rootList, new RootComparator());

        SharedPreferences.Editor editor = sp.edit();
        editor.putString(itemToString(item), itemToString(item));
        editor.apply();

        this.rootsLiveDataMutable.setValue(this);
    }

    public List<RootCalc> getCurrentList() {
        return this.rootList;
    }

    public void markRootDone(RootCalc number) {

        for (RootCalc i : this.rootList) {
            if (i.getNumber() == number.getNumber())
            {
                SharedPreferences.Editor editor = sp.edit();
                editor.remove(itemToString(i));
                editor.apply();

                i.setDone(true);

                editor.putString(itemToString(i), itemToString(i));
                editor.apply();

            }
        }

        Collections.sort(this.rootList, new RootComparator());

        this.rootsLiveDataMutable.setValue(this);
    }

    public void deleteRoot(RootCalc number) {

        this.rootList.remove(number);

        SharedPreferences.Editor editor = sp.edit();
        editor.remove(itemToString(number));
        editor.apply();

        this.rootsLiveDataMutable.setValue(this);
    }

    public String itemToString(RootCalc item)
    {
        return item.getNumber() + "#" + item.isDone() + '#' + item.getRoots();
    }
    private static class RootComparator implements Comparator<RootCalc> {
        @Override
        public int compare(RootCalc o1, RootCalc o2) {

            if (o1.isDone() == o2.isDone()) {
                return 0;
            }

            if (o1.isDone())
                return 1;
            return -1;
        }
    }
}
