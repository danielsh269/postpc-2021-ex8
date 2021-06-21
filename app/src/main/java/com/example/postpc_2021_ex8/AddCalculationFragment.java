package com.example.postpc_2021_ex8;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AddCalculationFragment extends Fragment{

    public static interface OnMyButtonClickedListener {
        public void onButtonClicked();
    }

    public OnMyButtonClickedListener listener = null;

    public AddCalculationFragment()
    {
        super(R.layout.fragment_add_calc);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.calcButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                {
                    listener.onButtonClicked();
                }
            }
        });
    }

    public void setText(View v, String text)
    {
        EditText editText = v.findViewById(R.id.numberView);
        editText.setText(text);
    }

    public String getText(View v)
    {
        EditText editText = v.findViewById(R.id.numberView);
        return editText.getText().toString();
    }
}
