package com.njuse.xiaotidu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.njuse.utils.EmailSender;
import com.njuse.utils.PasswordStyle;

/**
 * Created by Administrator on 2018/2/5.
 */

public class ForgetPasswordActivity extends AppCompatActivity{
    private LinearLayout backToLogin;
    private EditText emailAddress;
    private Button findBackPassword;

    private boolean isSendSuccess;
    private String email;
    private String password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.njuse.xiaotidu.R.layout.activity_forget_password);

        //初始化控件
        initWidget();

        //响应事件
        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgetPasswordActivity.this,LoginActivity.class));
                finish();
            }
        });

        findBackPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置一个正则表达式
                String emailPattern = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";
                if (!emailAddress.getText().toString().matches(emailPattern)){
                    Toast.makeText(ForgetPasswordActivity.this,"邮箱格式不正确，请修改！",Toast.LENGTH_LONG).show();
                }else {
                    email = emailAddress.getText().toString();
                    password = getPasswordByEmail(email);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            isSendSuccess = EmailSender.sendEmail(email, PasswordStyle.getPasswordStyle(password));

                            Looper.prepare();
                            if (isSendSuccess){
                                Toast.makeText(ForgetPasswordActivity.this,"发送成功！",Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(ForgetPasswordActivity.this,"发送失败，请重试！",Toast.LENGTH_LONG).show();
                            }
                            Looper.loop();
                        }
                    }).start();
                }
            }
        });
    }

    private String getPasswordByEmail(String email) {
        //To do --- 从服务器端获取密码
        String password = "123456789";
        return password;
    }

    //初始化控件
    private void initWidget() {
        backToLogin = findViewById(R.id.back_to_log_in);
        findBackPassword = findViewById(R.id.find_password_back_button);
        emailAddress = findViewById(R.id.emailaddress_edittext);

        isSendSuccess = false;
    }
}
