package pl.edu.pwr.s249268.venueapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterCustomerActivity extends AppCompatActivity {

    EditText username, name, surname, password, repassword;
    Button signUp, signIn;
    CustomerModel customerModel;
    DataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_customer);

        username = findViewById(R.id.customer_username);
        name = findViewById(R.id.customer_name);
        surname = findViewById(R.id.customer_surname);
        password = findViewById(R.id.customer_password);
        repassword = findViewById(R.id.customer_repassword);
        signUp = findViewById(R.id.customer_register);
        signIn = findViewById(R.id.customer_login);
        dbHelper = new DataBaseHelper(this);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                RegisterCustomerActivity.this.finish();
            }
        });


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String typed_username = username.getText().toString();
                String typed_name = name.getText().toString();
                String typed_surname = surname.getText().toString();
                String typed_password = password.getText().toString();
                String typed_repassword = repassword.getText().toString();

                if (typed_username.equals("") || typed_name.equals("") || typed_surname.equals("") || typed_password.equals("") || typed_repassword.equals("")) {
                    Toast.makeText(RegisterCustomerActivity.this, "Error creating customer.", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(typed_password.equals(typed_repassword))
                    {
                        boolean checkuser = dbHelper.checkUserName(typed_username, true);
                        if(!checkuser)
                        {
                            customerModel = new CustomerModel(-1,typed_username, typed_name, typed_surname, typed_password);
                            dbHelper = new DataBaseHelper(RegisterCustomerActivity.this);

                            boolean success = dbHelper.addCustomer(customerModel);

                            if(success)
                            {
                                Toast.makeText(RegisterCustomerActivity.this, "Successfully added new Customer", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), CustomerHomeActivity.class);
                                intent.putExtra("UserName", typed_username);
                                startActivity(intent);
                                RegisterCustomerActivity.this.finish();
                            }
                            else
                            {
                            Toast.makeText(RegisterCustomerActivity.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(RegisterCustomerActivity.this, "User already exists, sign in.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(RegisterCustomerActivity.this, "You have a typo in your password.", Toast.LENGTH_SHORT).show();
                    }
                }
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