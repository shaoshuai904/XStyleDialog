package com.maple.iosdialog;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.maple.msdialog.ActionSheetDialog;
import com.maple.msdialog.AlertDialog;
import com.maple.msdialog.AlertEditDialog;

/**
 * Custom Dialog Demo
 *
 * @author maple
 * @time 17/3/28
 */
public class MainActivity extends Activity {
    public static final String DEF_BLUE = "#037BFF";
    public static final String DEF_RED = "#FD4A2E";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // -------------------------------- Action Sheet Dialog ----------------------------------------

    public void asMessage(View view) {
        new ActionSheetDialog(MainActivity.this)
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .setTitle("清空消息列表后，聊天记录依然保留，确定要清空消息列表？")
                .addSheetItem("清空消息列表", Color.parseColor(DEF_RED),
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                showToast("clear msg list");
                            }
                        })
                .setCancelText("取 消", Color.BLUE, 18, false)
                .show();
    }

    public void asImage(View view) {
        new ActionSheetDialog(MainActivity.this)
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .addSheetItem("发送给好友", Color.parseColor(DEF_BLUE),
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {

                            }
                        })
                .addSheetItem("转载到空间相册", Color.parseColor(DEF_BLUE),
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {

                            }
                        })
                .addSheetItem("上传到群相册",
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {

                            }
                        })
                .addSheetItem("保存到手机", Color.parseColor(DEF_BLUE),
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {

                            }
                        })
                .addSheetItem("收藏",
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {

                            }
                        })
                .addSheetItem("查看聊天图片", Color.parseColor(DEF_BLUE),
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {

                            }
                        })
                .show();
    }

    public void asList(View view) {
        new ActionSheetDialog(MainActivity.this)
                .setTitle("请选择操作")
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .addSheetItem("条目一", Color.parseColor(DEF_RED), new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        showToast("item " + which);
                    }
                })
                .addSheetItem("条目二", new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        showToast("item " + which);
                    }
                })
                .addSheetItem("条目三", Color.BLUE, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        showToast("item " + which);
                    }
                })
                .addSheetItem("条目四", Color.CYAN, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        showToast("item " + which);
                    }
                })
                .addSheetItem("条目五", Color.GRAY, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        showToast("item " + which);
                    }
                })
                .addSheetItem("条目六", Color.LTGRAY, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        showToast("item " + which);
                    }
                })
                .addSheetItem("条目七", Color.RED, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        showToast("item " + which);
                    }
                })
                .addSheetItem("条目八", new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        showToast("item " + which);
                    }
                })
                .addSheetItem("条目九", new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        showToast("item " + which);
                    }
                })
                .addSheetItem("条目十", new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        showToast("item " + which);
                    }
                }).show();
    }

    // ------------------------------------ Alert Dialog -------------------------------------------

    public void adOne(View view) {
        new AlertDialog(MainActivity.this)
                .setTitle("退出当前账号")
                .setMsg("再连续登陆15天，就可变身为QQ达人。退出QQ可能会使你现有记录归零，确定退出？")
                .setLeftButton("取消", null)
                .setRightButton("确认退出", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("exit");
                    }
                })
                .show();
    }

    public void adTwo(View view) {
        new AlertDialog(MainActivity.this)
                .setScaleWidth(0.7)
                .setMsg("你现在无法接收到新消息提醒。请到系统-设置-通知中开启消息提醒", Color.BLUE)
                .setRightButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("OK");
                    }
                })
                .show();
    }

    // --------------------------------- Alert Edit Dialog -----------------------------------------

    String name;

    public void aeOne(View view) {
        new AlertEditDialog(MainActivity.this)
                .setTitle("姓名")
                .setMsg("请输入您的真实姓名。")
                .setEditCallListener(new AlertEditDialog.EditTextCallListener() {
                    @Override
                    public void callBack(String str) {
                        name = str;
                    }
                })
                .setLeftButton("取消", null)
                .setRightButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast(name);
                    }
                })
                .show();
    }

    public void aeTwo(View view) {
        new AlertEditDialog(MainActivity.this)
                .setMsg("给自己起一个好听的名字吧")
                .setEditCallListener(new AlertEditDialog.EditTextCallListener() {
                    @Override
                    public void callBack(String str) {
                        name = str;
                    }
                })
                .setRightButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(name))
                            showToast(name);
                    }
                })
                .show();
    }

    // ----------------------------------- other methods -------------------------------------------

    private void showToast(String msg) {
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

}
