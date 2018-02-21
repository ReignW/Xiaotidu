package com.njuse.xiaotidu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/2/5.
 */

public class OtherInfoActivity extends AppCompatActivity {
    private LinearLayout backToEmailInput;
    private EditText phoneNum;
    private EditText password;
    private EditText passwordConfirm;
    private Button register;
    private CheckBox showPassword;

    String emailAddress;    //邮箱地址

    boolean isPhoneNumberMatch;
    boolean isPasswordMatch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.njuse.xiaotidu.R.layout.activity_other_infomation);

        //初始化控件
        initWidget();

        //响应事件
        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //设置EditText的密码为可见的
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordConfirm.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    //设置密码为隐藏的
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordConfirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                //防止光标移到前面
                password.setSelection(password.getText().length());
                passwordConfirm.setSelection(passwordConfirm.getText().length());
            }
        });

        backToEmailInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OtherInfoActivity.this, RegisterActivity.class));
                finish();
            }
        });

        //手机号文本变化监听器
        phoneNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String phoneNumPattern = "(13|14|15|18)[0-9]{9}";     //手机号正则表达式
                if (phoneNum.getText().toString().matches(phoneNumPattern)) {
                    isPhoneNumberMatch = true;
                } else {
                    isPhoneNumberMatch = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //密码输入框文本变化监听器
        TextWatcher passwordChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String passwordPattern = "[A-Za-z0-9_]{8,16}";      //密码匹配的正则表达式(还未完善)
                if (password.getText().toString().equals("") || password.getText().toString().equals("")){
                    return;
                }
                if (password.getText().toString().matches(passwordPattern) && passwordConfirm.getText().toString().equals(password.getText().toString())){
                    isPasswordMatch = true;
                }else {
                    isPasswordMatch = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        password.addTextChangedListener(passwordChangedListener);
        passwordConfirm.addTextChangedListener(passwordChangedListener);

        //注册按钮点击事件
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPhoneNumberMatch){
                    Toast.makeText(OtherInfoActivity.this,"手机号码的格式不正确，请修改！",Toast.LENGTH_LONG).show();
                    return;
                }
                if (!isPasswordMatch){
                    Toast.makeText(OtherInfoActivity.this,"密码格式不正确或者两次密码输入不匹配，请修改！",Toast.LENGTH_LONG).show();
                    return;
                }

                //如果信息正确，则提交后台注册用户信息

            }
        });
    }

    //初始化控件
    private void initWidget() {
        backToEmailInput = findViewById(R.id.back_to_email_input);
        phoneNum = findViewById(R.id.phone_number_edittext);
        password = findViewById(R.id.password_edittext);
        passwordConfirm = findViewById(R.id.password_confirm_edittext);
        register = findViewById(R.id.register_button);
        showPassword = findViewById(R.id.show_password_checkbox);

        emailAddress = getIntent().getStringExtra("email");     //获取邮箱地址

        isPasswordMatch = false;
        isPhoneNumberMatch = false;
    }
}
