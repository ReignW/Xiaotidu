package com.njuse.xiaotidu;

import android.app.ProgressDialog;
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
import com.njuse.utils.Format;
import com.njuse.utils.PasswordStyle;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

/**
 * Created by Administrator on 2018/2/5.
 */

public class ForgetPasswordActivity extends AppCompatActivity {
    private LinearLayout backToLogin;
    private EditText emailAddress;
    private Button findBackPassword;

    private boolean isSendSuccess;
    private String email;
    private String password;

    ProgressDialog waiting;

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
                startActivity(new Intent(ForgetPasswordActivity.this, LoginActivity.class));
                finish();
            }
        });

        findBackPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Format.isEmailLegal(emailAddress.getText().toString())) {
                    Toast.makeText(ForgetPasswordActivity.this, "邮箱格式不正确，请修改！", Toast.LENGTH_LONG).show();
                } else {
                    email = emailAddress.getText().toString();
                    password = getPasswordByEmail(email);

                    waiting = ProgressDialog.show(ForgetPasswordActivity.this, "正在发送", "正在为你找回密码，请稍候...");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            isSendSuccess = EmailSender.sendEmail(email, PasswordStyle.getPasswordStyle(password),"小题督用户找回密码");

                            Looper.prepare();
                            waiting.dismiss();//万万不可少这句，否则会程序会卡死。
                            if (isSendSuccess) {
                                Toast.makeText(ForgetPasswordActivity.this, "发送成功！", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(ForgetPasswordActivity.this, LoginActivity.class));
                                finish();
                            } else {
                                Toast.makeText(ForgetPasswordActivity.this, "发送失败，请重试！", Toast.LENGTH_LONG).show();
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
