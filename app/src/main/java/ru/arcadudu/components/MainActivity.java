package ru.arcadudu.components;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_text;

    private AutoCompleteTextView autoCompleteTV;
    String[] months;

    private BottomAppBar appBar;
    private ConstraintLayout bottomSheetConstraint;
    private BottomSheetBehavior bottomSheetBehavior;
    private FloatingActionButton fab;

    // bottom sheet
    private EditText et_setText;
    private TextView bs_increaseSizeTV, bs_decreaseSizeTV, bs_changeColorTV;
    private Button btnDefaultSettings;

    List<Float> dimensList = new ArrayList<>();
    int defaultSizeIndex = 4;
    int currentSizeIndex = defaultSizeIndex;

    List<Integer> colorList = new ArrayList<>();
    int colorIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        packSizeList();
        packColorList();

        // autocomplete tv
        months = getResources().getStringArray(R.array.months);
        autoCompleteTV = findViewById(R.id.autoComplete);
        autoCompleteTV.setHintTextColor(getColor(R.color.grey));
        ArrayAdapter<String> autoCompAdapter = new ArrayAdapter<>
                (this, R.layout.autocomplete_item_layout, R.id.tv_auto_complete_item, months);
        autoCompleteTV.setAdapter(autoCompAdapter);
        autoCompleteTV.setThreshold(1);


        // default text tv
        tv_text = findViewById(R.id.tv_test);
        tv_text.setTextSize(getResources().getDimension(R.dimen.twenty_eight));


        appBar = findViewById(R.id.bottom_app_bar);
        bottomSheetConstraint = findViewById(R.id.bottom_sheet_constraint);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetConstraint);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);


        // bottom sheet components
        et_setText = findViewById(R.id.et_set_text);
        et_setText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tv_text.setText(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        bs_increaseSizeTV = findViewById(R.id.tv_increase_size);
        bs_decreaseSizeTV = findViewById(R.id.tv_decrease_size);
        bs_changeColorTV = findViewById(R.id.tv_change_color);

        bs_increaseSizeTV.setOnClickListener(this);
        bs_decreaseSizeTV.setOnClickListener(this);
        bs_changeColorTV.setOnClickListener(this);

        btnDefaultSettings = findViewById(R.id.bs_btnDefault);
        btnDefaultSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_text.setTextSize(dimensList.get(defaultSizeIndex));
                tv_text.setTextColor(getColor(R.color.black));
                currentSizeIndex = defaultSizeIndex;
                et_setText.setText("");
                tv_text.setText("Default text");
                Toast toast1 = Toast.makeText(MainActivity.this, "Настройки сброшены", Toast.LENGTH_SHORT);
                toast1.setGravity(Gravity.BOTTOM, 0, 16);
                toast1.show();
            }
        });


        appBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
    }


    private void packColorList() {
        colorList.add(getColor(R.color.black));
        colorList.add(getColor(R.color.purple_200));
        colorList.add(getColor(R.color.purple_500));
        colorList.add(getColor(R.color.teal_200));
        colorList.add(getColor(R.color.teal_700));
        colorList.add(getColor(R.color.grey));
    }

    private void packSizeList() {
        dimensList.add(getResources().getDimension(R.dimen.twenty)); // min size
        dimensList.add(getResources().getDimension(R.dimen.twenty_two));
        dimensList.add(getResources().getDimension(R.dimen.twenty_four));
        dimensList.add(getResources().getDimension(R.dimen.twenty_six));
        dimensList.add(getResources().getDimension(R.dimen.twenty_eight)); // default size
        dimensList.add(getResources().getDimension(R.dimen.thirty));
        dimensList.add(getResources().getDimension(R.dimen.thirty_two));
        dimensList.add(getResources().getDimension(R.dimen.thirty_four));
        dimensList.add(getResources().getDimension(R.dimen.thirty_six)); // max size
    }

    public void increaseSize() {
        if (currentSizeIndex < dimensList.size() - 1) {
            currentSizeIndex += 1;
            tv_text.setTextSize(dimensList.get(currentSizeIndex));
        } else {
            Toast.makeText(this, "Достигнут предел увеличения", Toast.LENGTH_SHORT).show();
        }
    }

    public void decreaseSize() {
        if (currentSizeIndex > 0) {
            currentSizeIndex -= 1;
            tv_text.setTextSize(dimensList.get(currentSizeIndex));
        } else {
            Toast.makeText(this, "Достигнут предел уменьшения", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_increase_size:
                increaseSize();
                break;
            case R.id.tv_decrease_size:
                decreaseSize();
                break;
            case R.id.tv_change_color:
                changeColor();
                break;
            case R.id.fab:
                Toast toast2 = Toast.makeText(this, "Окей, ты нажал на кнопку", Toast.LENGTH_SHORT);
                toast2.setGravity(Gravity.CENTER, 0, 0);
                toast2.show();
        }
    }

    private void changeColor() {
        if (colorIndex < colorList.size() - 1) {
            colorIndex += 1;

        } else {
            colorIndex = 0;
        }
        tv_text.setTextColor(colorList.get(colorIndex));

    }
}