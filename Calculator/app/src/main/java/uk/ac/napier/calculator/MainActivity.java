package uk.ac.napier.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // Initialising variables for UI inputs/outputs
    private EditText result;
    private EditText newNumber;
    private TextView displayOperation;

    // Variables to hold the operands and type of calculations
    private Double operand = null;
    private String pendingOperation = "=";

    // Variables Strings for the operand and the state of the operation
    private static final String STATE_OPERAND = "Operand";
    private static final String STATE_PENDING_OPERATION = "PendingOperation";

    // Opening app
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assigning variables for UI inputs/outputs
        result = findViewById(R.id.result);
        newNumber = findViewById(R.id.newNumber);
        displayOperation = findViewById(R.id.operation);

        // Number buttons
        Button btn0 = findViewById(R.id.button0);
        Button btn1 = findViewById(R.id.button1);
        Button btn2 = findViewById(R.id.button2);
        Button btn3 = findViewById(R.id.button3);
        Button btn4 = findViewById(R.id.button4);
        Button btn5 = findViewById(R.id.button5);
        Button btn6 = findViewById(R.id.button6);
        Button btn7 = findViewById(R.id.button7);
        Button btn8 = findViewById(R.id.button8);
        Button btn9 = findViewById(R.id.button9);
        Button btnDot = findViewById(R.id.buttonDot);

        // Operation buttons
        Button btnEquals = findViewById(R.id.buttonEquals);
        Button btnDivide = findViewById(R.id.buttonDivide);
        Button btnMultiply = findViewById(R.id.buttonMultiply);
        Button btnMinus = findViewById(R.id.buttonMinus);
        Button btnPlus = findViewById(R.id.buttonPlus);

        // Negative button
        Button btnNeg = findViewById(R.id.buttonNeg);

        // Clear button
        Button btnClear = findViewById(R.id.buttonClear);

        // Numbers listener
        View.OnClickListener numListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button) v;
                newNumber.append(btn.getText().toString());
            }
        };
        btn0.setOnClickListener(numListener);
        btn1.setOnClickListener(numListener);
        btn2.setOnClickListener(numListener);
        btn3.setOnClickListener(numListener);
        btn4.setOnClickListener(numListener);
        btn5.setOnClickListener(numListener);
        btn6.setOnClickListener(numListener);
        btn7.setOnClickListener(numListener);
        btn8.setOnClickListener(numListener);
        btn9.setOnClickListener(numListener);
        btnDot.setOnClickListener(numListener);

        // Operation listener and call performOperation method
        View.OnClickListener opListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button) v;

                // Get operation
                String operation = btn.getText().toString();

                // Get number value
                String numValue = newNumber.getText().toString();

                try {
                    Double doubleValue = Double.valueOf(numValue);
                    performOperation(doubleValue, operation);
                } catch (NumberFormatException e) {
                    newNumber.setText("");
                }

                // Display operation selected
                pendingOperation = operation;
                displayOperation.setText(pendingOperation);
            }
        };
        btnEquals.setOnClickListener(opListener);
        btnDivide.setOnClickListener(opListener);
        btnMultiply.setOnClickListener(opListener);
        btnMinus.setOnClickListener(opListener);
        btnPlus.setOnClickListener(opListener);

        // Negative listener
        btnNeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = newNumber.getText().toString();
                if (value.length() == 0) {
                    newNumber.setText("-");
                }
                else {
                    try {
                        // Reverse value to negative
                        Double doubleValue = Double.valueOf(value);
                        doubleValue *= -1;
                        newNumber.setText(doubleValue.toString());
                    }
                    catch (NumberFormatException e) {
                        // if newNumber was "-" or ".", clear it
                        newNumber.setText("");
                    }
                }
            }
        });

        // Clear listener
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.setText("");
                newNumber.setText("");
                operand = null;
                pendingOperation = "=";
                displayOperation.setText("");
            }
        });
    }

    // Saving instance
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_PENDING_OPERATION, pendingOperation);
        if (operand != null) {
            outState.putDouble(STATE_OPERAND, operand);
        }
        super.onSaveInstanceState(outState);
    }

    // Restoring instance
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION);
        operand = savedInstanceState.getDouble(STATE_OPERAND);
        displayOperation.setText(pendingOperation);
    }

    // Method to calculate operation
    private void performOperation(Double numValue, String operation) {
        if (null == operand) {
            operand = numValue;
        }
        else {
            if (pendingOperation.equals("=")) {
                // Change operation sign displayed
                pendingOperation = operation;
            }
            // Operand1 gets the results value between Operand1 and Operand2
            switch (pendingOperation) {
                case "=":
                    operand = numValue;
                    break;
                case "/":
                    if (numValue == 0) {
                        operand = 0.0;
                    }
                    else {
                        operand /= numValue;
                    }
                    break;
                case "*":
                    operand *= numValue;
                    break;
                case "-":
                    operand -= numValue;
                    break;
                case "+":
                    operand += numValue;
                    break;
            }
        }

        // Display result and clean bottom input
        result.setText(operand.toString());
        newNumber.setText("");
    }
}
