package com.njuse.xiaotidu;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.njuse.utils.Format;

/**
 * Created by Administrator on 2018/2/4.
 */

public class LoginActivity extends AppCompatActivity {
    private TextView skipLogin;
    private TextView forgetPassword;
    private TextView register;
    private TextView userAgreement;
    private EditText account;
    private EditText password;
    private Button login;
    private CheckBox showPassword;
    private CheckBox savePassword;

    private ProgressDialog waiting;

    private String userInfoDB;
    private String userInfoTable;
    private SQLiteDatabase db;
    private String path;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //初始化控件
        initWidget();

        //点击事件
        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //设置EditText的密码为可见的
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    //设置密码为隐藏的
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                //防止光标移到前面
                password.setSelection(password.getText().length());
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waiting = ProgressDialog.show(LoginActivity.this, "登录", "正在登录，请稍候！");

                if (!Format.isEmailLegal(account.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "邮箱账号格式不正确！", Toast.LENGTH_SHORT).show();
                    waiting.dismiss();
                    return;
                }
                if (!Format.isPasswordLegal(password.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "密码错误！", Toast.LENGTH_SHORT).show();
                    waiting.dismiss();
                    return;
                }
                //向服务器查询账号与密码是否匹配
                boolean isLoginSuccess = login(account.getText().toString(), password.getText().toString());
                if (isLoginSuccess) {
                    saveUserInfo(account.getText().toString(), password.getText().toString(), savePassword.isChecked());
                    waiting.dismiss();
                    Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("account", account.getText().toString());
                    startActivity(intent);
                    LoginActivity.this.finish();
                } else {
                    Toast.makeText(LoginActivity.this, "登录失败，请重试！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        skipLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();   //销毁当前Activity
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            //跳转至注册界面
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
            }
        });
    }

    //将用户信息保存在本地数据库SQLite
    private boolean saveUserInfo(String account, String password, boolean isSavePassword) {
        if (!isSavePassword) password = "";
        String querySql = "SELECT * FROM " + userInfoTable + " WHERE account='" + account + "'";
        Cursor cursor = db.rawQuery(querySql, null);

//        Toast.makeText(LoginActivity.this, cursor.getCount() + "", Toast.LENGTH_LONG).show();     //用作测试

        if (cursor.getCount() == 0) {
            insertData(db, userInfoTable, account, password);
        } else if (cursor.getCount() == 1) {
            updateData(db, userInfoTable, account, password);
        }

        return true;
    }

    private boolean updateData(SQLiteDatabase db, String userInfoTable, String account, String password) {
        String updateSql = "UPDATE " + userInfoTable + " SET password = '" + password + "',islogin = '1' WHERE account = '" + account + "' ";
        db.execSQL(updateSql);
        return true;
    }

    private boolean insertData(SQLiteDatabase db, String userInfoTable, String account, String password) {
        String insertSql = "insert into " + userInfoTable + "(account,password,islogin) values('" + account + "','" + password + "','1')";
        db.execSQL(insertSql);
        return true;
    }

    //用户登录
    private boolean login(String account, String password) {
        //需要与服务器交互
        return true;
    }

    //初始化控件
    @SuppressLint("SdCardPath")
    private void initWidget() {
        skipLogin = findViewById(R.id.skip_login_button);
        forgetPassword = findViewById(R.id.forget_password_textview);
        register = findViewById(R.id.register_textview);
        userAgreement = findViewById(R.id.user_agreement_textview);
        account = findViewById(R.id.account_edittext);
        password = findViewById(R.id.password_edittext);
        login = findViewById(R.id.login_button);
        showPassword = findViewById(R.id.showpassword_checkbox);
        savePassword = findViewById(R.id.save_password_checkbox);

        userInfoDB = "userinfo.db";
        userInfoTable = "userinfo";
        path = this.getFilesDir() + "/"; //数据库路径
        //打开或者创建数据库，此处要用绝对路径
        db = SQLiteDatabase.openOrCreateDatabase(path + userInfoDB, null);
    }
}
