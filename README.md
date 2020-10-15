# 接入方式
### app界面截图

![image](/pic/apppic.png)

### Step 1. Add the JitPack repository to your build file

Add it in your root **build.gradle** at the end of repositories:

```groovy
allprojects {
	repositories {
		...
		maven { url 'https://www.jitpack.io' }
	}
}
```

### Step 2. Add the dependency

#### 以下存在两个library 请按需选择

#### base_iotutils
```groovy
dependencies {
         //以宽高进行屏幕适配,shell,网络判断等多种工具类以及后台存活串口封装等
         implementation 'com.face_chtj.base_iotutils:base_iotutils:1.4.8'
}
```

#### base_socket
```groovy
dependencies {
         //socket通信 tcp/udp工具类 使用方式请参考app module中的代码
         implementation 'com.chtj.base_socket:base_socket:1.0.2'
}
```
##  base_iotutils Module 说明

### 自定义Application
```java

//注意：别忘了在 Manifest 中通过 android:name 使用这个自定义的 Application.

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //如果(不)需要开启适配  请用此方式
        BaseIotUtils.instance().create(getApplication());

        //如果需要开启适配 请用此方式
        //1080(宽),1920(高)是为了适配而去设置相关的值
        //设置宽度|高度布局尺寸 layout 布局文件以pt为单位 setBaseScreenParam(1080,1920,true)
        BaseIotUtils.instance().
              setBaseScreenParam(1080,1920,true).
              setCreenType(SCREEN_TYPE.WIDTH).//按照宽度适配
              create(getApplication());
    }
}
```


##  base_socket Module使用说明

```java
    //BaseUdpSocket | BaseTcpSocket tcp|udp 使用方式类似
    BaseTcpSocket baseTcpSocket = new BaseTcpSocket(192.168.1.100,8080, 5000);
    //监听回调
    baseTcpSocket.setSocketListener(new ISocketListener()...);
    //开启连接
    baseTcpSocket.connect(this);
    //发送数据
     baseTcpSocket.send("hello world!".getBytes());
    //关闭连接
    baseTcpSocket.close();
```

## 屏幕适配

### 创建pt模拟器设备

![image](/pic/create_step.png)

![image](/pic/unit_pt.png)

### 1080*1920 px 效果

![image](/pic/big_screen.png)

-可在app Model中找到使用示例

## base_iotutils 常用工具类

- 进制转换类 | DataConvertUtils

- 设备相关 | DeviceUtils

- 音频播放 | PlayUtils

- 键盘相关 | KeyBoardUtils

- 网络判断 | NetUtils

- adb命令工具类 | ShellUtils

- ShareProfrence工具类 | SPUtils

- Toast工具类 | ToastUtils

- 后台服务类 保活 | AbsWorkService

- PackagesName相关信息工具类 | PackagesUtils

- 屏幕适配相关 | AdaptScreenUtils

- 串口工具 | SerialPort | SerialPortFinder

- 日志管理(使用时开启日志) | KLog

- 文件操作 | FileUtils

- 事件管理 | RxBus

- 断点下载 | DownloadSupport

- Notification通知 | NotifyUtils

- 权限管理 | PermissionsUtils

- Service状态获取(是否正在运行) | ServiceUtils

- 时间工具类(返回各种时间格式) | TimeUtils

- 应用上方对话框(全局对话框) | SurfaceLoadDialog

- 压缩相关工具类 | ZipUtils

- 字符串判断 | StringUtils

- 网络侦听者 | NetListenerUtils 网络是否正常，类型，连接状态


# FileUtils文件操作 读写,删除,文件大小等
```java
        //param1 文件路径 例如/sdcard/config.txt
        //param2 写入内容
        //param3 是否覆盖这个文件里的内容
        boolean writeResult = FileUtils.writeFileData(filePath, content, true);
        //读取filePath文件中的内容
        String readResult = FileUtils.readFileData(filePath);
        //更多文件操作方法请查询FileUtils中的内容
```

# KeyBoardUtils软键盘管理
```java
       //打卡软键盘
       KeyBoardUtils.openKeybord(editeTextView);

       //关闭软键盘
       KeyBoardUtils.closeKeybord(editeTextView);
```


# NetUtils网络工具类
```java
        //得到网络类型 NETWORK_NO,NETWORK_WI,NETWORK_2G,NETWORK_3G,NETWORK_4G,NETWORK_UN,NETWORK_ETH
        NetUtils.getNetWorkType();
        //得到网络类型字符串
        NetUtils.getNetWorkTypeName();
        //判断网络连接是否可用
        NetUtils.isNetworkAvailable();
        //判断网络是否可用
        NetUtils.isAvailable();
        //判断网络是否连接
        NetUtils.isConnected();
        //判断是否有外网连接（普通方法不能判断外网的网络是否连接，比如连接上局域网）不要在主线程使用，会阻塞线程
        NetUtils.ping();  NetUtils.ping(int count,int time); NetUtils.ping(String ip);
        //判断WIFI是否打开
        NetUtils.isWifiEnabled();
        //判断网络连接方式是否为WIFI
        NetUtils.isWifi();
        //判断网络连接方式是否为ETH
        NetUtils.isEth();
        //判断wifi是否连接状态
        NetUtils.isWifiConnected();
        //判断是否为3G网络
        NetUtils.is3rd();
        //判断网络是否是4G
        NetUtils.is4G();
        //GPS是否打开
        NetUtils.isGpsEnabled();
        //打开网络设置界面
        NetUtils.openWirelessSettings();
        //获取活动网络信息
        NetUtils.getActiveNetworkInfo();
        //获取移动网络运营商名称 如中国联通、中国移动、中国电信
        NetUtils.getNetworkOperatorName();
        //获取移动终端类型 0 手机制式未知 1 手机制式为GSM，移动和联通 2 手机制式为CDMA，电信 3
        NetUtils.getPhoneType();
```

# DownloadSupport多任务下载  任务相互独立

![image](/pic/download.png)

```java
        DownloadSupport downloadSupport=new DownloadSupport();

        //开启任务下载----------------------这里可执行多个任务 重复执行即可---------
        FileCacheData fileCacheData = new FileCacheData();
        fileCacheData.setUrl(downloadUrl);
        fileCacheData.setFileName(fileName1);
        fileCacheData.setRequestTag(downloadUrl);
        fileCacheData.setFilePath("/sdcard/" + fileName1);
        addDownloadTask(fileCacheData);
        //-----------------------------------------------------------
        FileCacheData fileCacheData2 = new FileCacheData();
        fileCacheData2.setUrl(downloadUrl2);
        fileCacheData2.setFileName(fileName2);
        fileCacheData2.setRequestTag(downloadUrl2);
        fileCacheData2.setFilePath("/sdcard/" + fileName2);
        addDownloadTask(fileCacheData2);
        //-----------------------------------------------------------

        downloadSupport.addStartTask(fileCacheData, downloadCallBack);


       //下载进度  可根据设置的requestTag来区分属于哪个下载进度 fileCacheData.getRequestTag()
       DownloadSupport.DownloadCallBack downloadCallBack = new DownloadSupport.DownloadCallBack() {
           @Override
           public void download(FileCacheData fileCacheData, int percent, boolean isComplete) {
               Message message1 = handler.obtainMessage();
               message1.obj = fileCacheData;
               message1.arg1 = percent;
               handler.sendMessage(message1);
           }

           @Override
           public void error(Exception e) {
               KLog.d(TAG, "error:>errMeg=" + e.getMessage());
           }

           @Override
           public void downloadStatus(String requestTag, DownloadStatus downloadStatus) {
               KLog.d(TAG, "downloadStatus:>requestTag =" + requestTag + ",status=" + downloadStatus.name());
           }
       };
       //全部关闭
       downloadSupport.cancel();

       //关闭某个任务
       downloadSupport.cancel(fileCacheData.getRequestTag());
       downloadSupport.cancel(fileCacheData2.getRequestTag());



```

# NotificationUtils 使用
```java
     //获取系统中是否已经通过 允许通知的权限
     if (NotifyUtils.notifyIsEnable()) {
         NotifyUtils.getInstance("xxid")
                 .setEnableCloseButton(false)//设置是否显示关闭按钮
                 .setOnNotifyLinstener(new OnNotifyLinstener() {
                     @Override
                     public void enableStatus(boolean isEnable) {
                         KLog.e(TAG, "isEnable=" + isEnable);
                     }
                 })
                 .setNotifyParam(R.drawable.ic_launcher, R.drawable.app_img
                         , "BaseIotUtils"
                         , "工具类"
                         , "文件压缩，文件下载，日志管理，时间管理，网络判断。。。"
                         , "this is a library ..."
                         , "2020-3-18"
                         , false
                         , true)
                 .exeuNotify();
     } else {
         //去开启通知
         NotifyUtils.toOpenNotify();
     }
     //更改部分内容
     NotifyUtils.getInstance("xxid").setAppName("");
     NotifyUtils.getInstance("xxid").setAppAbout("");
     NotifyUtils.getInstance("xxid").setRemarks("");
     NotifyUtils.getInstance("xxid").setPrompt("");
     NotifyUtils.getInstance("xxid").setDataTime("");
     //关闭此notification
     NotifyUtils.closeNotify();
 ```

# PlayUtils 使用
```java
      //开始播放
      PlayUtils.getInstance().
         setPlayStateChangeListener(new PlayUtils.PlayStateChangeListener() {

             @Override
             public void onPlayStateChange(PlayUtils.PLAY_STATUS play_status) {
                 //获取当前状态PLAY, RESUME, PAUSE, STOP, NONE
                 KLog.d(TAG," play_status= "+play_status.name());
             }

             @Override
             public void getProgress(int sumProgress, int nowProgress) {
                 //sumProgress 总时长  nowProgress 当前时长
                 KLog.d(TAG, " sumProgress= " + sumProgress + ",nowProgress= " + nowProgress);
             }

         }).
         startPlaying("/sdcard/ding.wav");//文件地址

      //暂停播放
      PlayUtils.getInstance().pausePlay();

      //继续播放
      PlayUtils.getInstance().resumePlay();

      //停止播放
      PlayUtils.getInstance().stopPlaying();
 ```

# NetListenerUtils 网络监听者
```java
     //注册广播
     NetListenerUtils.getInstance().registerReceiver();
     //设置监听 NetTypeInfo (NETWORK_2G,NETWORK_3G,NETWORK_4G,NETWORK_WIFI,NETWORK_ETH,NETWORK_NO,NETWORK_UNKNOWN)
     NetListenerUtils.getInstance().setOnNetChangeLinstener(new OnNetChangeLinstener() {
         @Override
         public void changed(NetTypeInfo type, boolean isNormal) {
             //isNormal 网络经过ping后 true为网络正常 false为网络异常
             KLog.e(TAG, "network type=" + type.name() + ",isNormal=" + isNormal);
             tvType.setText("" + type.name());
             tvStatus.setText("" + isNormal);
         }
     });
     .......
     //注销广播
     NetListenerUtils.getInstance().unRegisterReceiver();
 ```
 
## 串口封装类
```java
        //获得串口地址
        SerialPortFinder mSerialPortFinder = new SerialPortFinder();
        String[] entryValues = mSerialPortFinder.getAllDevicesPath();
        //根据串口地址和波特率开启串口
        SerialPort port =  null;
        try{ 
            port=new SerialPort(new File(entryValues[xxx]), xxx,0);
            Log.e(TAG,"开启成功");
        }catch(Exception e){
            e.printStackTrace();
            Log.e(TAG,"errMeg:"+e.getMessage());
        }
        
        //写命令
        port.write(command);
        //读命令
        //可根据SerialPortHelper中的readInputStreamData(int flag)方法读取数据，这里是一个一个字节读取
        //亦或用port中的read方法一次读取，如果数据量大可能存在粘包
        port.read(byte[] buff,int lenght);
        //关闭串口
        port.close();
        
```

## 后台保活
使用方式
```java

        创建Service继承 AbsWorkService重写方法
        public class TraceServiceImpl extend AbsWorkService{
             //是否 任务完成, 不再需要服务运行?
                public static boolean sShouldStopService;
                public static Disposable sDisposable;

                public static void stopService() {
                    //我们现在不再需要服务运行了, 将标志位置为 true
                    sShouldStopService = true;
                    //取消对任务的订阅
                    if (sDisposable != null) sDisposable.dispose();
                    //取消 Job / Alarm / Subscription
                    cancelJobAlarmSub();
                }

                /**
                 * 是否 任务完成, 不再需要服务运行?
                 * @return 应当停止服务, true; 应当启动服务, false; 无法判断, 什么也不做, null.
                 */
                @Override
                public Boolean shouldStopService(Intent intent, int flags, int startId) {
                    return sShouldStopService;
                }

                @Override
                public void startWork(Intent intent, int flags, int startId) {
                    //在这里操作。。。。
                }

                @Override
                public void stopWork(Intent intent, int flags, int startId) {
                    stopService();
                }

                /**
                 * 任务是否正在运行?
                 * @return 任务正在运行, true; 任务当前不在运行, false; 无法判断, 什么也不做, null.
                 */
                @Override
                public Boolean isWorkRunning(Intent intent, int flags, int startId) {
                    //若还没有取消订阅, 就说明任务仍在运行.
                    return sDisposable != null && !sDisposable.isDisposed();
                }

                @Override
                public IBinder onBind(Intent intent, Void v) {
                    return null;
                }

                @Override
                public void onServiceKilled(Intent rootIntent) {
                    System.out.println("保存数据到磁盘。");
                }
        }

        //初始化后台保活Service
        BaseIotUtils.initSerice(TraceServiceImpl.class, BaseIotUtils.DEFAULT_WAKE_UP_INTERVAL);

        //开启service
        TraceServiceImpl.sShouldStopService = false;
        BaseIotUtils.startServiceMayBind(TraceServiceImpl.class);
        
        //关闭service
        TraceServiceImpl.stopService();
        
```

## adb操作工具类
使用方式
```java
        //单条命令执行
        ShellUtils.CommandResult commResult=ShellUtils.execCommand("reboot",true);
        //多条命令执行
        //ShellUtils.CommandResult commResult2=ShellUtils.execCommand(new String[]{"comm1","comm2","comm3","commN..."},true);
        if(commResult.result==0){
            Log.e(TAG, "commResult2 exeu successful");
        }else{
            Log.e(TAG, "commResult exeu faild errMeg="+commResult.errorMsg);
        }
```

## PermissionsUtils操作工具类
使用方式
```java
        PermissionsUtils.with(mContext).
            addPermission(Manifest.permission.ACCESS_FINE_LOCATION).
            addPermission(Manifest.permission.ACCESS_COARSE_LOCATION).
            addPermission(Manifest.permission......).
            initPermission();
```

## Version Code
 ### v1.0.3
> 优化各个工具类
> 新增部分工具类
 ### v1.0.2
> 新增crash控制界面
> 修改NotifyUtils支持6.0以上系统显示，并新增获取通知是否允许的状态NotifyUtils.notifyIsEnable();跳转应用设置界面NotifyUtils.toOpenNotify();
