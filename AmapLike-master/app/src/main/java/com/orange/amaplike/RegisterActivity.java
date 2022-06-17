package com.orange.amaplike;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.orange.amaplike.po.User;
import com.orange.amaplike.utils.Constants;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class RegisterActivity extends AppCompatActivity implements  View.OnClickListener{

    private Button btnRegister;
    private EditText etAccount;
    private EditText etPassword;
    private EditText etPassword2;
    private User user = new User();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("注册");
        initView();
    }

    private void initView() {
        btnRegister = (Button) findViewById(R.id.btn_register);
        etAccount = (EditText) findViewById(R.id.et_account);
        etPassword = (EditText) findViewById(R.id.et_password);
        etPassword2 = (EditText) findViewById(R.id.et_password2);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                String account = etAccount.getText().toString();
                String password = etPassword.getText().toString();
                String password2 = etPassword2.getText().toString();
                if(TextUtils.isEmpty(account) || TextUtils.isEmpty(password)){
                    Toast.makeText(this,"用户名或密码不可为空",Toast.LENGTH_LONG).show();
                }else{
                    if(!password.equals(password2)){
                        Toast.makeText(this,"两次密码不一致，请重试",Toast.LENGTH_LONG).show();
                    }else {
                        user.setAccount(account);
                        user.setPassword(password);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                //发送可能会失败
                                try{
                                    String json = JSON.toJSONString(user); //使用阿里的fastJson库
                                    OkHttpClient client = new OkHttpClient(); //创建http客户端
                                    Request request = new Request.Builder().url("http://"+ Constants.SEARCH_IP  +":9090/register")
                                            .post(RequestBody.create(MediaType.parse("application/json"),json)).build(); //创造http请求
                                    Response response = client.newCall(request).execute(); //执行发送的指令，并接收返回
                                    final String isSuccee = response.body().string();
                                    //操作成功，弹窗提示
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if("0".equals(isSuccee)){
                                                Toast.makeText(RegisterActivity.this,"该用户名已被注册，请重试",Toast.LENGTH_SHORT).show();
                                            }else if("1".equals(isSuccee)){
                                                Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                            }else{
                                                Toast.makeText(RegisterActivity.this,"注册失败，请重试",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }catch (Exception e){
                                    e.printStackTrace();
                                    runOnUiThread(new Runnable() { //UI操作必须要在主线程中，所以弹出操作要在runOnUiThread()中进行
                                        @Override
                                        public void run() {
                                            Toast.makeText(RegisterActivity.this,"注册失败，请重试",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        }).start();
                    }
                }
        }
    }
}
