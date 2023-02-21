package nl.danielvdspoel.tipcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Map;
import java.util.Objects;

public class DefaultSettingsActivity extends AppCompatActivity {

    SeekBar defaultTipPercentageSeekbar;
    TextView defaultPercentageTextView;

    RadioGroup DefaultSplittingCostRadioGroup;
    RadioButton defaultSplittingCostYes;
    RadioButton defaultSplittingCostNo;

    EditText defaultSplittingCostCount;
    ConstraintLayout defaultSplittingCostLayout;


    Button saveButton;
    Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_settings);
        Objects.requireNonNull(this.getSupportActionBar()).hide();

        defaultTipPercentageSeekbar = findViewById(R.id.defaultTipPercentageSeekbar);
        defaultPercentageTextView = findViewById(R.id.defaultPercentageTextView);
        DefaultSplittingCostRadioGroup = findViewById(R.id.DefaultSplittingCostRadioGroup);

        defaultSplittingCostYes = findViewById(R.id.defaultSplittingCostYes);
        defaultSplittingCostNo = findViewById(R.id.defaultSplittingCostNo);
        defaultSplittingCostLayout = findViewById(R.id.defaultSplittingCostLayout);

        defaultSplittingCostCount = findViewById(R.id.defaultSplittingCostCount);

        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);

        SharedPreferences file = getSharedPreferences("defaultSettings", Context.MODE_PRIVATE);

        defaultTipPercentageSeekbar.setProgress(file.getInt("tipPercentage", 15));
        defaultPercentageTextView.setText(file.getInt("tipPercentage", 15) + "%");

        if (file.getBoolean("splittingCost", false)) {
            defaultSplittingCostLayout.setVisibility(TextView.VISIBLE);
            defaultSplittingCostYes.setChecked(true);
            defaultSplittingCostCount.setText(String.valueOf(file.getInt("splittingCostCount", 2)));
        } else {
            defaultSplittingCostNo.setChecked(true);
        }

        cancelButton.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        });

        saveButton.setOnClickListener(v -> {
            SharedPreferences.Editor editor = file.edit();

            editor.putInt("tipPercentage", defaultTipPercentageSeekbar.getProgress());
            editor.putBoolean("splittingCost", DefaultSplittingCostRadioGroup.getCheckedRadioButtonId() == defaultSplittingCostYes.getId());
            editor.putInt("splittingCostCount", Integer.parseInt(defaultSplittingCostCount.getText().toString()));
            editor.commit();

            Map<String, ?> something = file.getAll();

            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        });



        defaultTipPercentageSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                defaultPercentageTextView.setText(progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        DefaultSplittingCostRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.defaultSplittingCostYes) {
                defaultSplittingCostLayout.setVisibility(View.VISIBLE);

            } else if (checkedId == R.id.defaultSplittingCostNo) {
                defaultSplittingCostLayout.setVisibility(View.INVISIBLE);
            }
        });

    }
}