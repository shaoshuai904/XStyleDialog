package com.zf.iosdialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.ishare_lib.ui.dialog.ActionSheetDialog;
import com.ishare_lib.ui.dialog.ActionSheetDialog.OnSheetItemClickListener;
import com.ishare_lib.ui.dialog.ActionSheetDialog.SheetItemColor;
import com.ishare_lib.ui.dialog.AlertDialog;

public class MainActivity extends Activity implements OnClickListener {
	private Button btn1;
	private Button btn2;
	private Button btn3;
	private Button btn4;
	private Button btn5;
	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = MainActivity.this;
		initView();
	}

	private void initView() {
		btn1 = (Button) findViewById(R.id.btn1);
		btn1.setOnClickListener(this);
		btn2 = (Button) findViewById(R.id.btn2);
		btn2.setOnClickListener(this);
		btn3 = (Button) findViewById(R.id.btn3);
		btn3.setOnClickListener(this);
		btn4 = (Button) findViewById(R.id.btn4);
		btn4.setOnClickListener(this);
		btn5 = (Button) findViewById(R.id.btn5);
		btn5.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn1:
			new ActionSheetDialog(mContext).builder().setTitle("清空消息列表后，聊天记录依然保留，确定要清空消息列表？").setCancelable(false)
					.setCanceledOnTouchOutside(false)
					.addSheetItem("清空消息列表", SheetItemColor.Red, new OnSheetItemClickListener() {
						@Override
						public void onClick(int which) {

						}
					}).show();
			break;
		case R.id.btn2:
			new AlertDialog(mContext).builder().setTitle("提示").setMsg("已经是最后一页了")
					.setNegativeButton("取消", new OnClickListener() {
						@Override
						public void onClick(View v) {

						}
					}).show();
			break;
		case R.id.btn3:
			new ActionSheetDialog(mContext).builder().setTitle("请选择操作").setCancelable(false)
					.setCanceledOnTouchOutside(false)
					.addSheetItem("条目一", SheetItemColor.Blue, new OnSheetItemClickListener() {
						@Override
						public void onClick(int which) {
							Toast.makeText(MainActivity.this, "item" + which, Toast.LENGTH_SHORT).show();
						}
					}).addSheetItem("条目二", SheetItemColor.Blue, new OnSheetItemClickListener() {
						@Override
						public void onClick(int which) {
							Toast.makeText(MainActivity.this, "item" + which, Toast.LENGTH_SHORT).show();
						}
					}).addSheetItem("条目三", SheetItemColor.Blue, new OnSheetItemClickListener() {
						@Override
						public void onClick(int which) {
							Toast.makeText(MainActivity.this, "item" + which, Toast.LENGTH_SHORT).show();
						}
					}).addSheetItem("条目四", SheetItemColor.Blue, new OnSheetItemClickListener() {
						@Override
						public void onClick(int which) {
							Toast.makeText(MainActivity.this, "item" + which, Toast.LENGTH_SHORT).show();
						}
					}).addSheetItem("条目五", SheetItemColor.Blue, new OnSheetItemClickListener() {
						@Override
						public void onClick(int which) {
							Toast.makeText(MainActivity.this, "item" + which, Toast.LENGTH_SHORT).show();
						}
					}).addSheetItem("条目六", SheetItemColor.Blue, new OnSheetItemClickListener() {
						@Override
						public void onClick(int which) {
							Toast.makeText(MainActivity.this, "item" + which, Toast.LENGTH_SHORT).show();
						}
					}).addSheetItem("条目七", SheetItemColor.Blue, new OnSheetItemClickListener() {
						@Override
						public void onClick(int which) {
							Toast.makeText(MainActivity.this, "item" + which, Toast.LENGTH_SHORT).show();
						}
					}).addSheetItem("条目八", SheetItemColor.Blue, new OnSheetItemClickListener() {
						@Override
						public void onClick(int which) {
							Toast.makeText(MainActivity.this, "item" + which, Toast.LENGTH_SHORT).show();
						}
					}).addSheetItem("条目九", SheetItemColor.Blue, new OnSheetItemClickListener() {
						@Override
						public void onClick(int which) {
							Toast.makeText(MainActivity.this, "item" + which, Toast.LENGTH_SHORT).show();
						}
					}).addSheetItem("条目十", SheetItemColor.Blue, new OnSheetItemClickListener() {
						@Override
						public void onClick(int which) {
							Toast.makeText(MainActivity.this, "item" + which, Toast.LENGTH_SHORT).show();
						}
					}).show();
			break;
		case R.id.btn4:
			new AlertDialog(mContext).builder().setTitle("退出当前账号").setMsg("再连续登陆15天，就可变身为QQ达人。退出QQ可能会使你现有记录归零，确定退出？")
					.setPositiveButton("确认退出", new OnClickListener() {
						@Override
						public void onClick(View v) {

						}
					}).setNegativeButton("取消", new OnClickListener() {
						@Override
						public void onClick(View v) {

						}
					}).show();
			break;
		case R.id.btn5:
			new AlertDialog(mContext).builder().setMsg("你现在无法接收到新消息提醒。请到系统-设置-通知中开启消息提醒")
					.setNegativeButton("确定", new OnClickListener() {
						@Override
						public void onClick(View v) {

						}
					}).show();
			break;
		default:
			break;
		}
	}
}
