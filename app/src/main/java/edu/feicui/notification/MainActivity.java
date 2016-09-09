package edu.feicui.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RemoteViews;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private NotificationManager mManager;
    private Notification mNotification;
    private PendingIntent mIntent;
    private int index = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        init();
    }

    private void init() {
        //初始化通知栏管理者
        mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //意图数组
        Intent[] intents = {new Intent(this, NotificationAcitivity.class)};
        //待处理意图对象
        mIntent = PendingIntent.getActivities(this, 0, intents, 0);
        //消息栏通知对象
        mNotification = new Notification();
    }

    @OnClick(R.id.btn_create)
    public void create() {
        //设置在通知栏的消息图标
        mNotification.icon = R.mipmap.ic_launcher;
        //设置在通知栏的信息内容
        mNotification.tickerText = "this is a notification";
        //设置默认的声音,此外还可以设置震动（需加入权限） 灯光 等
        mNotification.defaults = Notification.DEFAULT_SOUND;
        //不能删除
        mNotification.flags = Notification.FLAG_NO_CLEAR;
        //设置下拉时的显示布局
        RemoteViews convertView = new RemoteViews(getPackageName(), R.layout.layout_content);
        convertView.setImageViewResource(R.id.img, R.mipmap.ic_launcher);
        convertView.setTextViewText(R.id.txt, "下拉后的内容");
        mNotification.contentView = convertView;
        mNotification.contentIntent = mIntent;
        //发送通知
        // 第一个参数唯一的标识该Notification，第二个参数就是Notification对象
        mManager.notify(1, mNotification);
    }

    @OnClick(R.id.btn_flush)
    public void flush() {
        mNotification.tickerText = "this is a flush message";
        mNotification.icon = R.mipmap.icon;
        RemoteViews convertView = new RemoteViews(getPackageName(), R.layout.layout_content);
        convertView.setImageViewResource(R.id.img, R.mipmap.icon);
        convertView.setTextViewText(R.id.txt, "更新后下拉内容");
        mNotification.contentView = convertView;
        mNotification.contentIntent = mIntent;
        mManager.notify(1, mNotification);
    }

    @OnClick(R.id.btn_add)
    public void add() {
        Notification notification = new Notification();
        mNotification.tickerText = "this is an added notification" + index;
        mNotification.icon = R.mipmap.icon;
        RemoteViews convertView = new RemoteViews(getPackageName(), R.layout.layout_content);
        convertView.setImageViewResource(R.id.img, R.mipmap.icon);
        convertView.setTextViewText(R.id.txt, "添加的下拉内容");
        mNotification.contentView = convertView;
        mNotification.contentIntent = mIntent;
        mManager.notify(index, mNotification);
        index++;
    }

    @OnClick(R.id.btn_cancle)
    public void cancel() {
        mManager.cancel(1);
    }

    @OnClick(R.id.btn_cancleAll)
    public void cancelAll() {
        index = 2;
        mManager.cancelAll();
    }
}
