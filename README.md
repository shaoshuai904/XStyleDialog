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
	compile 'com.github.shaoshuai904:iOS_Style_Dialog:1.2.1'
}
```

![show_01](https://github.com/shaoshuai904/IOSDialog/blob/master/screens/show_01.png)

![show_02](https://github.com/shaoshuai904/IOSDialog/blob/master/screens/show_02.png)

###  AlertDialog

	样式布局:[ 标题 + 消息 + 左按钮 + 右按钮]

```java                
        new AlertDialog(mContext)
                .setCancelable(false)
                .setTitle("退出当前账号")
                .setMessage("再连续登陆15天，就可变身为QQ达人。退出QQ可能会使你现有记录归零，确定退出？")
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

```java       
        new AlertEditDialog(mContext)
                .setTitle("姓名")
                .setMessage("请输入您的真实姓名。")
                .setLeftButton("取消", null)
                .setRightButton("确定", new AlertEditDialog.EditTextCallListener() {
                    @Override
                    public void callBack(String str) {
                        showToast(str);
                    }
                })
                .show();
```

### ActionSheetDialog

	样式布局:[ 标题 + 页签条目 + 取消按钮 ]

```java
        new ActionSheetDialog(mContext)
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
                // ……
                .addSheetItem("条目十", new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        showToast("item " + which);
                    }
                }).show();
```


[完整预览各类用法 -（简单使用类 传送门）](https://github.com/shaoshuai904/iOS_Style_Dialog/blob/master/app/src/main/java/com/maple/iosdialog/MainActivity.java)

----------


## v1.2 ##
 - 对各个Dialog的`Title`、`Message`、`Cancel/OK Button`，新增了`字体大小`，`字体颜色`，`是否加粗`等设置。
 - 改变了Dialog的默认色系。（QQ style --> iOS style）
 - 开放了ActionSheetDialog中 SheetItem `字体颜色`和 Cancel Button `显示文本`的自定义设置。
 

## v1.1 ##
 - 创建父类BaseDialog，对Dialog进行统一管理。
 - 新增AlertDialog和AlertEditDialog的`Cancelable`和`自适应宽度`设置。

## v1.0 ##
 - ActionSheetDialog
 - AlertEditDialog
 - AlertDialog

