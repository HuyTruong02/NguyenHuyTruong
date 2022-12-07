package truong.poly.asm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
Button button;
EditText name, pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        anhxa();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().trim().equals("truong")&& pass.getText().toString().trim().equals("truong")){
                    Toast.makeText(getApplicationContext(),"Dang nhap thanh cong",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(Login.this, MainActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(),"Dang nhap khong thanh cong",Toast.LENGTH_LONG).show();
                }

            }
        });
    }



  public void anhxa(){
        name = findViewById(R.id.edtname);
        pass = findViewById(R.id.edtpass);
        button = findViewById(R.id.btnLogin);
    }
}