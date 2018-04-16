package com.xiaomi.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/3/19/019.
 */

public class LoginActivity extends Activity {
        private Button login;
        private TextView textView;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textView=(TextView)findViewById(R.id.textView);
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(LoginActivity.this,MainActivity.class);
                 //传递一些参数到另一个activity
                 //    i.putExtra("nba","杜兰特");
                /*        Bundle b = new Bundle();
                b.putString("name","库里");
                  b.putInt("age",20);
                 i.putExtras(b);*/

    i.putExtra("user",new User("jike",2));
              //  startActivity(i);
                startActivityForResult(i,0);
            }
        });





    }
   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        textView.setText("另一个Activity返回的数据："+data.getStringExtra("data"));
    }


}
