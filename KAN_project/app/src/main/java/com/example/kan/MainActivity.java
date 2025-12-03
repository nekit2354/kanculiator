package com.example.kan;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    TextView resultField;
    EditText numberField;
    TextView operatoinField;
    Double operand = null;
    String lastOperation = "=";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridLayout gridLayout = new GridLayout(this);
        gridLayout.setRowCount(4);

        resultField = findViewById(R.id.resultField);
        numberField = findViewById(R.id.numberField);
        operatoinField = findViewById(R.id.operatoinField);

        findViewById(R.id.add).setOnClickListener((view)->onOperationClick("+"));
        findViewById(R.id.sub).setOnClickListener((view)->onOperationClick("-"));
        findViewById(R.id.mul).setOnClickListener((view)->onOperationClick("*"));
        findViewById(R.id.div).setOnClickListener((view)->onOperationClick("/"));
        findViewById(R.id.eq).setOnClickListener((view)->onOperationClick("="));
        findViewById(R.id.pr).setOnClickListener((view)->onOperationClick("%"));
        findViewById(R.id.AC).setOnClickListener((view)->onOperationClick("AC"));

        findViewById(R.id.button).setOnClickListener((view)->onNumberClick("1"));
        findViewById(R.id.button2).setOnClickListener((view)->onNumberClick("2"));
        findViewById(R.id.button9).setOnClickListener((view)->onNumberClick("3"));
        findViewById(R.id.button8).setOnClickListener((view)->onNumberClick("4"));
        findViewById(R.id.button7).setOnClickListener((view)->onNumberClick("5"));
        findViewById(R.id.button6).setOnClickListener((view)->onNumberClick("6"));
        findViewById(R.id.button5).setOnClickListener((view)->onNumberClick("7"));
        findViewById(R.id.button4).setOnClickListener((view)->onNumberClick("8"));
        findViewById(R.id.button3).setOnClickListener((view)->onNumberClick("9"));
        findViewById(R.id.button10).setOnClickListener((view)->onNumberClick("0"));
        findViewById(R.id.button19).setOnClickListener((view)->onNumberClick(","));

    }
    @Override
    protected void onSaveInstanceState(Bundle outState){
        outState.putString("OPERATION", lastOperation);
        if (operand!=null)
            outState.putDouble("OPERAND", operand);
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        lastOperation = savedInstanceState.getString("OPERATION");
        operand = savedInstanceState.getDouble("OPERAND");
        resultField.setText(operand.toString());
        operatoinField.setText(lastOperation);
    }
    private void onNumberClick(String number) {
        numberField.append(number);
        if (lastOperation.equals("=") && operand != null){
            operand = null;
        }
    }
    private void onOperationClick(String op){
        String number = numberField.getText().toString();
        if(number.length() > 0){
            number = number.replace(",", ".");
            try {
                performOperation(Double.valueOf(number), op);
            }catch (NumberFormatException ex){
                numberField.setText("");
            }
        }
        lastOperation = op;
        operatoinField.setText(lastOperation);

    }
    private void performOperation(Double number, String op){
        if(operand == null){
            operand = number;
        }
        else{
            if(lastOperation.equals("=")){
                lastOperation = op;
            }
            switch (lastOperation){
                case "=":
                    operand = number;
                    break;
                case "/":
                    if(number == 0){
                        operand = 0.0;
                    }
                    else{
                        operand /= number;
                    }
                    break;
                case "+":
                    operand += number;
                    break;
                case "*":
                    operand *= number;
                    break;
                case "-":
                    operand -= number;
                    break;
                case "%":
                    operand += number / 100;
                    break;
                case "AC":
                    operand = null;
                    resultField.setText("");
                    operatoinField.setText("");
                    numberField.setText("");
                    lastOperation = "=";
                    break;
            }
        }
        resultField.setText(
                operand == null ? "":
                operand.toString().replace('.', ','));
        numberField.setText("");
    }
}