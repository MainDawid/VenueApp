package pl.edu.pwr.s249268.venueapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import static android.content.pm.PackageInstaller.EXTRA_SESSION_ID;

public class LoginActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioButton;
    EditText username, password;
    Button loginButton;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        radioGroup = findViewById(R.id.login_radio_group);
        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_buttonSignIn);
        dataBaseHelper = new DataBaseHelper(this);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioID = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioID);

                String typed_username = username.getText().toString();
                String typed_password = password.getText().toString();

                if (typed_username.equals("") || typed_password.equals("")) {
                    Toast.makeText(LoginActivity.this, "Fill all the fields.", Toast.LENGTH_SHORT).show();
                } else {
                    boolean checkUserPassword;

                    if (radioButton.getText().equals("Band")) {
                        checkUserPassword = dataBaseHelper.checkPassword(typed_username, typed_password, false);    //type 1 == customer, 0 == band
                        if (checkUserPassword) {
                            Toast.makeText(LoginActivity.this, "Band Sign In successful!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), BandHomeActivity.class);
                            intent.putExtra("BandName", typed_username);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Invalid Band data.", Toast.LENGTH_SHORT).show();
                        }
                    }


                    if (radioButton.getText().equals("Customer")) {
                        checkUserPassword = dataBaseHelper.checkPassword(typed_username, typed_password, true);     //type 1 == customer, 0 == band

                        if (checkUserPassword) {
                            Toast.makeText(LoginActivity.this, "Customer Sign In successful!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), CustomerHomeActivity.class);
                            intent.putExtra("UserName", typed_username);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Invalid Customer data.", Toast.LENGTH_SHORT).show();
                        }
                    } else if (radioButton.getText().equals("Admin")) {
                        if (typed_username.equals("admin") && typed_password.equals("admin")) {
                            Toast.makeText(LoginActivity.this, "Admin Sign In successful!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), AdminHomeActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Invalid Admin data.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        });
    }

    public void checkButton(View v) {
        int radioID = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioID);
        Toast.makeText(this, "Selected Account Type is: " + radioButton.getText(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed()
    {
        //super.onBackPressed(); // EXCLUDE this line
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}