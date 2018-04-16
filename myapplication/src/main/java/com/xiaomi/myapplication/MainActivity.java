package com.xiaomi.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
    private TextView text1;
    private Button button;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text1=(TextView)findViewById(R.id.text1);
        button=(Button)findViewById(R.id.button);
        editText=(EditText)findViewById(R.id.editText);
        //如何来获取一个activity传给我的数据
         Intent i=getIntent();
         final Bundle data = i.getExtras();
         User user = i.getParcelableExtra("user");
         text1.setText(String.format("User info(name=%s,age=%d)",user.getName(),user.getAge()));

         //text1.setText(i.getStringExtra("nba"));
    button.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
          Intent i = new Intent();
         i.putExtra("data",editText.getText().toString());
            setResult(1,i);
            finish();
          }
      });




    }
}
