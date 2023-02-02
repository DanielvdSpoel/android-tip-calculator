package nl.danielvdspoel.tipcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    EditText inputBox;
    SeekBar tipPercentageSeekbar;
    TextView percentageTextView;
    RadioGroup splittingCostRadioGroup;
    RadioButton splittingCostYes;
    RadioButton splittingCostNo;
    TextView splittingCostLabel;
    EditText splittingCostInput;

    TextView tipAmountLabel;

    TextView totalBeforeTipTextView;
    TextView totalTipTextView;
    TextView totalAfterTipTextView;
    TextView amountPerPersonTextView;
    TextView amountPerPersonLabelTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputBox = findViewById(R.id.inputBox);
        tipPercentageSeekbar = findViewById(R.id.tipPercentageSeekbar);
        percentageTextView = findViewById(R.id.percentageTextView);
        splittingCostRadioGroup = findViewById(R.id.splittingCostRadioGroup);
        splittingCostYes = findViewById(R.id.splittingCostYes);
        splittingCostNo = findViewById(R.id.splittingCostNo);
        splittingCostLabel = findViewById(R.id.splittingCostCountLabel);
        splittingCostInput = findViewById(R.id.splittingCostInput);
        tipAmountLabel = findViewById(R.id.tipAmountLabel);

        totalBeforeTipTextView = findViewById(R.id.totalBeforeTipTextView);
        totalTipTextView = findViewById(R.id.totalTipTextView);
        totalAfterTipTextView = findViewById(R.id.totalAfterTipTextView);
        amountPerPersonTextView = findViewById(R.id.amountPerPersonTextView);
        amountPerPersonLabelTextView = findViewById(R.id.amountPerPersonLabelTextView);

        tipPercentageSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                percentageTextView.setText(progress + "%");
                tipAmountLabel.setText("Tip amount (" + progress + "%): ");
                calculateTip();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        splittingCostRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.splittingCostYes) {
                splittingCostLabel.setVisibility(TextView.VISIBLE);
                splittingCostInput.setVisibility(TextView.VISIBLE);
                amountPerPersonTextView.setVisibility(TextView.VISIBLE);
                amountPerPersonLabelTextView.setVisibility(TextView.VISIBLE);

            } else if (checkedId == R.id.splittingCostNo) {
                splittingCostLabel.setVisibility(TextView.INVISIBLE);
                splittingCostInput.setVisibility(TextView.INVISIBLE);
                amountPerPersonTextView.setVisibility(TextView.INVISIBLE);
                amountPerPersonLabelTextView.setVisibility(TextView.INVISIBLE);
            }
            calculateTip();
        });

        inputBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                calculateTip();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        splittingCostInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                calculateTip();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }


    public void calculateTip() {
        DecimalFormat df = new DecimalFormat("#.##");

        if (inputBox.getText().toString().equals("")) {
            return;
        }
        double input = Double.parseDouble(inputBox.getText().toString());
        double tipPercentage = tipPercentageSeekbar.getProgress();
        double tipAmount = (double) input / 100 * tipPercentage;

        totalBeforeTipTextView.setText(String.format("$%.2f", input));
        totalTipTextView.setText(String.format("$%.2f", tipAmount));
        totalAfterTipTextView.setText(String.format("$%.2f", input + tipAmount));

        if (splittingCostYes.isChecked()) {
            if (splittingCostInput.getText().toString().equals("")) {
                return;
            }
            int splittingCost = Integer.parseInt(splittingCostInput.getText().toString());
            double amountPerPerson = (input + tipAmount) / splittingCost;
            amountPerPersonTextView.setText(String.format("$%.2f", amountPerPerson));
        }


    }
}