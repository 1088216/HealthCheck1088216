package mazen.saad.adu.ac.ae.healthcheck1088216;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etHeight, etWeight, etSugar;
    private TextView tvBmiVal, tvBmiStatus, tvHa1cVal, tvHa1cStatus, tvRecWeight, tvRecSugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etHeight = findViewById(R.id.et_height);
        etWeight = findViewById(R.id.et_weight);
        etSugar = findViewById(R.id.et_sugar);

        tvBmiVal = findViewById(R.id.tv_bmi_val);
        tvBmiStatus = findViewById(R.id.tv_bmi_status);
        tvHa1cVal = findViewById(R.id.tv_ha1c_val);
        tvHa1cStatus = findViewById(R.id.tv_ha1c_status);
        tvRecWeight = findViewById(R.id.tv_rec_weight);
        tvRecSugar = findViewById(R.id.tv_rec_sugar);

        TextWatcher bmiWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculateBMI();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        etHeight.addTextChangedListener(bmiWatcher);
        etWeight.addTextChangedListener(bmiWatcher);

        TextWatcher sugarWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculateHA1C();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        etSugar.addTextChangedListener(sugarWatcher);
    }

    private void calculateBMI() {
        String heightStr = etHeight.getText().toString();
        String weightStr = etWeight.getText().toString();

        if (!heightStr.isEmpty() && !weightStr.isEmpty()) {
            try {
                double heightCm = Double.parseDouble(heightStr);
                double weight = Double.parseDouble(weightStr);

                if (heightCm > 0) {
                    double heightM = heightCm / 100.0;
                    double bmi = weight / (heightM * heightM);
                    tvBmiVal.setText(String.format("%.2f", bmi));

                    if (bmi < 18) {
                        tvBmiStatus.setText("Underweight");
                    } else if (bmi <= 24.9) {
                        tvBmiStatus.setText("Normal");
                    } else if (bmi <= 29.9) {
                        tvBmiStatus.setText("Overweight");
                    } else {
                        tvBmiStatus.setText("Obese");
                    }

                    double idealWeight = 22.5 * (heightM * heightM);
                    double weightDiff = Math.abs(weight - idealWeight);

                    if (weight > idealWeight) {
                        tvRecWeight.setText(String.format("You should loss %.1f Kg", weightDiff));
                    } else {
                        tvRecWeight.setText(String.format("You should gain %.1f Kg", weightDiff));
                    }
                }
            } catch (NumberFormatException e) {

            }
        } else {
            tvBmiVal.setText("BMI Results");
            tvBmiStatus.setText("BMI Status");
            tvRecWeight.setText("Loss / Gain");
        }
    }

    private void calculateHA1C() {
        String sugarStr = etSugar.getText().toString();

        if (!sugarStr.isEmpty()) {
            try {
                double sugar = Double.parseDouble(sugarStr);
                double ha1c = (sugar + 46.7) / 28.7;
                tvHa1cVal.setText(String.format("%.2f", ha1c));

                if (ha1c < 4) {
                    tvHa1cStatus.setText("Low Sugar Level");
                } else if (ha1c <= 6.5) {
                    tvHa1cStatus.setText("Normal");
                } else if (ha1c <= 8.5) {
                    tvHa1cStatus.setText("High Sugar Level");
                } else {
                    tvHa1cStatus.setText("High reduce glucose");
                }

                double targetSugar = 111.15;
                double sugarDiff = Math.abs(sugar - targetSugar);

                if (sugar > targetSugar) {
                    tvRecSugar.setText(String.format("Reduce Sugar by %.2f mg/dL", sugarDiff));
                } else {
                    tvRecSugar.setText(String.format("Increase Sugar by %.2f mg/dL", sugarDiff));
                }
            } catch (NumberFormatException e) {

            }
        } else {
            tvHa1cVal.setText("(sugar + 46.7) / 28.7");
            tvHa1cStatus.setText("A1C Status");
            tvRecSugar.setText("Reduce / Increase");
        }
    }
}