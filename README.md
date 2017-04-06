# iOS style Dialog


### 项目说明
	仿iOS样式Dialog。 
	开发环境：Android Studio 2.3.

### 快速使用

**Step 1.** Add it in your root build.gradle at the end of repositories:
```groovy
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

**Step 2.** Add the dependency
```groovy
dependencies {
  compile 'com.github.shaoshuai904:iOS_Style_Dialog:1.0'
}
```

![demo_show1](https://github.com/shaoshuai904/IOSDialog/blob/master/screens/demo_show1.png)

![demo_show2](https://github.com/shaoshuai904/IOSDialog/blob/master/screens/demo_show2.png)

###  AlertDialog

	样式布局:[ 标题 + 消息 + 左按钮 + 右按钮]
```
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
```

### AlertEditDialog

	样式布局:[ 标题 + 消息 + 输入框 + 左按钮 + 右按钮 ]

```
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
```

### ActionSheetDialog

	样式布局:[ 标题 + 页签条目 + 取消按钮 ]

```
        new ActionSheetDialog(MainActivity.this)
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
```


 
