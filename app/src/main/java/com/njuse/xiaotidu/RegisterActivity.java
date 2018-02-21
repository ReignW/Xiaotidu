package com.njuse.xiaotidu;

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
    String code;

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
//                        code = rawCode + "";
                        code = "111111";

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {

//                                    isSendSuccess = sendIdentifyingCode(emailAddress.getText().toString(), code);
                                    isSendSuccess = true;
                                    if (isSendSuccess) {
                                        startActivity(new Intent(RegisterActivity.this, IdentifyingCodeActivity.class)
                                                .putExtra("email", emailAddress.getText().toString())
                                                .putExtra("identifyingCode", code));
                                    }else {
                                        Looper.prepare();
                                        Toast.makeText(RegisterActivity.this, "验证码发送失败，请重试！", Toast.LENGTH_LONG).show();
                                        Looper.loop();
                                    }
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

    //发送验证码到用户邮箱
    public static boolean sendIdentifyingCode(final String receiver, final String identifyingCode) {
        // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();          // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");  // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", "smtp.163.com");   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");      // 需要请求认证
        props.setProperty("mail.smtp.port", "25");
        //如果遇到ssl类错误，请打开一下代码
//        final String smtpPort = "587";
//        props.setProperty("mail.smtp.port", smtpPort);
//        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        props.setProperty("mail.smtp.socketFactory.fallback", "false");
//        props.setProperty("mail.smtp.socketFactory.port", smtpPort);
        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getDefaultInstance(props);
        // 设置为debug模式, 可以查看详细的发送 log
        session.setDebug(true);
        // 3. 创建一封邮件
        MimeMessage message = null;//我这里是以163邮箱为发信邮箱测试通过
        try {
            String codeStyle = IdentifyingCodeStyle.getCodeStyle(identifyingCode);
//            message = MailUtil.createMimeMessage(session, "13420146901@163.com", receiver, "验证码："+identifyingCode+"\n(注意！请不要泄露给他人！)\n\n如果不是你本人操作，请忽略该邮件！请勿回复！");
            message = MailUtil.createMimeMessage(session, "13420146901@163.com", receiver, codeStyle);
            // 4. 根据 Session 获取邮件传输对象
            Transport transport = session.getTransport();
            transport.connect("13420146901@163.com", "njuse2017");
            // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
            transport.sendMessage(message, message.getAllRecipients());
            // 7. 关闭连接
            transport.close();

        } catch (Exception e) {
            return false;
//            e.printStackTrace();
        }

        return true;
    }

    private void initWidget() {
        backToLogin = findViewById(R.id.back_to_login);
        userAgreement = findViewById(R.id.user_agreement_textview);
        emailAddress = findViewById(R.id.email_edittext);
        identifyingCode = findViewById(R.id.identifiying_code_button);
    }

}
