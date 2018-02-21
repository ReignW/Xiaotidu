package com.njuse.xiaotidu;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.njuse.utils.EmailSender;
import com.njuse.utils.IdentifyingCodeStyle;
import com.njuse.utils.MailUtil;

import java.util.Properties;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

/**
 * Created by Administrator on 2018/2/5.
 */

public class RegisterActivity extends AppCompatActivity {
    private LinearLayout backToLogin;
    private TextView userAgreement;
    private EditText emailAddress;
    private Button identifyingCode;
    boolean isSendSuccess;
    private String code;
    private ProgressDialog waiting;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //初始化控件
        initWidget();

        //点击事件
        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });


        identifyingCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEmailLegal(emailAddress.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "邮箱格式不正确，请修改！", Toast.LENGTH_LONG).show();
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this)
                        .setTitle("发送验证码")
                        .setMessage("请确认你的邮箱地址：\n" + emailAddress.getText().toString() + "\n如果填写无误，稍后你将收到一封验证码邮件");

                setNegativeButton(builder);
                setPositiveButton(builder);

                builder.create().show();
            }

            private void setNegativeButton(AlertDialog.Builder builder) {
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //To do something
                    }
                });
            }

            private void setPositiveButton(AlertDialog.Builder builder) {
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int rawCode = new Random().nextInt(999999);     //生成验证码
                        code = IdentifyingCodeStyle.getCodeStyle(rawCode + "");
                        waiting = ProgressDialog.show(RegisterActivity.this,"正在发送","正在发送验证码，请稍候...");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    isSendSuccess = EmailSender.sendEmail(emailAddress.getText().toString(), code,"小题督用户注册验证码");

                                    Looper.prepare();
                                    waiting.dismiss();
                                    if (isSendSuccess) {
                                        startActivity(new Intent(RegisterActivity.this, IdentifyingCodeActivity.class)
                                                .putExtra("email", emailAddress.getText().toString())
                                                .putExtra("identifyingCode", code));
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "验证码发送失败，请重试！", Toast.LENGTH_LONG).show();
                                    }
                                    Looper.loop();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();

                    }
                });
            }
        });
    }

    private boolean isEmailLegal(String email) {
        /*
           设定邮箱地址的合法规则，合法邮箱地址要求如下：
                   （1）字符必须是英文或数字开始
                   （2）必须包含一个@
                   （3）@符号在. 符号前面
                   （4）以英文或数字结尾
		 */
        //设置一个正则表达式
        String reg = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";
        //告知此字符串是否匹配给定的正则表达式。
        if (email.matches(reg))
            return true;
        else
            return false;
    }

    private void initWidget() {
        backToLogin = findViewById(R.id.back_to_login);
        userAgreement = findViewById(R.id.user_agreement_textview);
        emailAddress = findViewById(R.id.email_edittext);
        identifyingCode = findViewById(R.id.identifiying_code_button);
    }

}
