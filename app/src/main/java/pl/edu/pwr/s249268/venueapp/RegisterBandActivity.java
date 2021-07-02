package pl.edu.pwr.s249268.venueapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterBandActivity extends AppCompatActivity {

    EditText username, name, password, repassword;
    Button signUp, signIn;
    DataBaseHelper dataBaseHelper;
    BandModel bandModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_band);

        username = findViewById(R.id.band_username);
        name = findViewById(R.id.band_name);
        password = findViewById(R.id.band_password);
        repassword = findViewById(R.id.band_repassword);
        signUp = findViewById(R.id.band_register);
        signIn = findViewById(R.id.band_login);
        dataBaseHelper = new DataBaseHelper(this);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String typed_username = username.getText().toString();
                String typed_name = name.getText().toString();
                String typed_password = password.getText().toString();
                String typed_repassword = repassword.getText().toString();

                if (typed_username.equals("") || typed_name.equals("") || typed_password.equals("") || typed_repassword.equals(""))
                {
                    Toast.makeText(RegisterBandActivity.this, "Error creating band account.", Toast.LENGTH_SHORT).show();
                }
                else
                    {
                    if (typed_password.equals(typed_repassword)) {
                        boolean checkuser = dataBaseHelper.checkUserName(typed_username, false);

                        if (!checkuser) {
                            bandModel = new BandModel(-1, typed_username, typed_name, typed_password);
                            dataBaseHelper = new DataBaseHelper(RegisterBandActivity.this);

                            boolean success = dataBaseHelper.addBand(bandModel);

                            if (success) {
                                Toast.makeText(RegisterBandActivity.this, "Successfully added new Band Account", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), BandHomeActivity.class);
                                 intent.putExtra("BandName", typed_username);
                                startActivity(intent);
                                RegisterBandActivity.this.finish();
                            } else {
                                Toast.makeText(RegisterBandActivity.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RegisterBandActivity.this, "There is a band with this username, sign in.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterBandActivity.this, "You have a typo in your password.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                RegisterBandActivity.this.finish();
            }
        });
    }
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}