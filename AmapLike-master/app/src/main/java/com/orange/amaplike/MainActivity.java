package com.orange.amaplike;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.amap.api.location.AMapLocationClient;
import com.orange.amaplike.po.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener{

    private static String TAG = "MainActivity";
    private Button btnLogin;
    private EditText etAccount;
    private EditText etPassword;
    private TextView tvRegister;
    private User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AMapLocationClient.updatePrivacyAgree(this, true);
        AMapLocationClient.updatePrivacyShow(this, true, true);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("登录");
        initView();
    }

    private void initView() {
        btnLogin = (Button) findViewById(R.id.btn_login);
        etAccount = (EditText) findViewById(R.id.et_account);
        etPassword = (EditText) findViewById(R.id.et_password);
        tvRegister = (TextView) findViewById(R.id.tv_register);
        btnLogin.setOnClickListener(this);
        tvRegister.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); // 设置下划线
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //登录验证
            case R.id.btn_login:
                String account = etAccount.getText().toString();
                String password = etPassword.getText().toString();
                if(TextUtils.isEmpty(account) || TextUtils.isEmpty(password)){
                    Toast.makeText(this,"用户名或密码不可为空",Toast.LENGTH_LONG).show();
                }else{
                    user.setAccount(account);
                    user.setPassword(password);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //发送可能会失败
                            try{
                                String json = JSON.toJSONString(user); //使用阿里的fastJson库
                                OkHttpClient client = new OkHttpClient(); //创建http客户端
                                Request request = new Request.Builder().url("http://10.116.88.215:9090/login")
                                        .post(RequestBody.create(MediaType.parse("application/json"),json)).build(); //创造http请求
                                Response response = client.newCall(request).execute(); //执行发送的指令，并接收返回
                                final String isSuccee = response.body().string();
                                Log.d(TAG,isSuccee);
                                //操作成功，弹窗提示
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if("1".equals(isSuccee)){
                                            Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(MainActivity.this, RoutePlanActivity.class));
                                        }else{
                                            Toast.makeText(MainActivity.this,"密码错误，请重试",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }catch (Exception e){
                                e.printStackTrace();
                                runOnUiThread(new Runnable() { //UI操作必须要在主线程中，所以弹出操作要在runOnUiThread()中进行
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainActivity.this,"登录异常，请重试",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }).start();
                }
                break;

            case R.id.tv_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }

//    @OnClick({R.id.goin_text})
//    public void onViewclick(View view){
//        if (view.getId()==R.id.goin_text){
//            startActivity(new Intent(MainActivity.this,RoutePlanActivity.class));
//        }
//    }
}
