package com.maple.iosdialog;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.maple.iosdialog.dialog.ActionSheetDialog;
import com.maple.iosdialog.dialog.AlertDialog;
import com.maple.iosdialog.dialog.AlertEditDialog;


public class MainActivity extends Activity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                new ActionSheetDialog(MainActivity.this)
                        .builder()
                        .setCancelable(false)
                        .setCanceledOnTouchOutside(false)
                        .setTitle("清空消息列表后，聊天记录依然保留，确定要清空消息列表？")
                        .addSheetItem("清空消息列表", ActionSheetDialog.SheetItemColor.Red,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        showToast("clear msg list");
                                    }
                                })
                        .show();
                break;
            case R.id.btn2:
                new ActionSheetDialog(MainActivity.this)
                        .builder()
                        .setCancelable(false)
                        .setCanceledOnTouchOutside(false)
                        .addSheetItem("发送给好友", ActionSheetDialog.SheetItemColor.Red,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {

                                    }
                                })
                        .addSheetItem("转载到空间相册", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {

                                    }
                                })
                        .addSheetItem("上传到群相册", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {

                                    }
                                })
                        .addSheetItem("保存到手机", ActionSheetDialog.SheetItemColor.Red,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {

                                    }
                                })
                        .addSheetItem("收藏", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {

                                    }
                                })
                        .addSheetItem("查看聊天图片", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {

                                    }
                                })
                        .show();
                break;
            case R.id.btn3:
                new ActionSheetDialog(MainActivity.this)
                        .builder()
                        .setTitle("请选择操作")
                        .setCancelable(false)
                        .setCanceledOnTouchOutside(false)
                        .addSheetItem("条目一", ActionSheetDialog.SheetItemColor.Red, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                showToast("item " + which);
                            }
                        })
                        .addSheetItem("条目二", null, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                showToast("item " + which);
                            }
                        })
                        .addSheetItem("条目三", ActionSheetDialog.SheetItemColor.Red, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                showToast("item " + which);
                            }
                        })
                        .addSheetItem("条目四", null, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                showToast("item " + which);
                            }
                        })
                        .addSheetItem("条目五", null, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                showToast("item " + which);
                            }
                        })
                        .addSheetItem("条目六", null, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                showToast("item " + which);
                            }
                        })
                        .addSheetItem("条目七", null, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                showToast("item " + which);
                            }
                        })
                        .addSheetItem("条目八", null, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                showToast("item " + which);
                            }
                        })
                        .addSheetItem("条目九", null, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                showToast("item " + which);
                            }
                        })
                        .addSheetItem("条目十", null, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                showToast("item " + which);
                            }
                        }).show();
                break;
            case R.id.btn4:
                new AlertDialog(MainActivity.this).builder().setTitle("退出当前账号")
                        .setMsg("再连续登陆15天，就可变身为QQ达人。退出QQ可能会使你现有记录归零，确定退出？")
                        .setPositiveButton("确认退出", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showToast("exit");
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
            case R.id.btn5:
                new AlertDialog(MainActivity.this).builder()
                        .setMsg("你现在无法接收到新消息提醒。请到系统-设置-通知中开启消息提醒")
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showToast("OK");
                            }
                        }).show();
                break;
            case R.id.btn6:
                final String[] name = {""};
                new AlertEditDialog(MainActivity.this).builder()
                        .setTitle("姓名")
                        .setMsg("请输入您的真实姓名。")
                        .setEditCallListener(new AlertEditDialog.EditTextCallListener() {
                            @Override
                            public void callBack(String str) {
                                if (!TextUtils.isEmpty(str)) {
                                    name[0] = str;
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showToast(name[0]);
                            }
                        }).show();
                break;
            case R.id.btn7:
                new AlertEditDialog(MainActivity.this).builder()
                        .setMsg("给自己起一个好听的名字吧")
                        .setEditCallListener(new AlertEditDialog.EditTextCallListener() {
                            @Override
                            public void callBack(String str) {
                                if (!TextUtils.isEmpty(str)) {
                                    showToast(str);
                                }
                            }
                        })
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showToast("OK");
                            }
                        }).show();
                break;
            default:
                break;
        }
    }

    private void showToast(String msg) {
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }


}
