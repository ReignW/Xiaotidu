package com.njuse.xiaotidu;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.njuse.selfdefview.AutoFixTextureView;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CameraActivity extends Activity implements View.OnClickListener {
    //    首先，创建OrientationEventListener对象：
    private OrientationEventListener mOrientationListener;
    // screen orientation listener
    private boolean mScreenProtrait = true;
    private boolean mCurrentOrient = false;

    private FrameLayout.LayoutParams params;
    private View shotLandView;
    private View shotPortView;

    private String fileName;    //照片保存的名字
    private String tempName = "temp.jpg";
    private Intent i;
    private boolean isFlashlightOpen;   //记录闪光灯是否打开

    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    private static final String TAG = "CameraActivity";

    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    private int FLASH_MODE;
    private AutoFixTextureView textureView;
    private Camera camera;
    private Camera.Parameters parameters;
    private CameraManager manager;
    private String mCameraId = "0"; // 摄像头ID（通常0代表后置摄像头，1代表前置摄像头）
    private CameraDevice cameraDevice;// 定义代表摄像头的成员变量
    private Size previewSize;    // 预览尺寸
    private CaptureRequest.Builder previewRequestBuilder;
    private CaptureRequest previewRequest;// 定义用于预览照片的捕获请求
    private CameraCaptureSession captureSession;// 定义CameraCaptureSession成员变量
    private ImageReader imageReader;
    private final TextureView.SurfaceTextureListener mSurfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture texture
                , int width, int height) {
            // 当TextureView可用时，打开摄像头
            openCamera(width, height);
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    };
    private final CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        //  摄像头被打开时激发该方法
        @Override
        public void onOpened(CameraDevice cameraDevice) {
            CameraActivity.this.cameraDevice = cameraDevice;
            // 开始预览
            createCameraPreviewSession();  // ②
        }

        // 摄像头断开连接时激发该方法
        @Override
        public void onDisconnected(CameraDevice cameraDevice) {
            cameraDevice.close();
            CameraActivity.this.cameraDevice = null;
        }

        // 打开摄像头出现错误时激发该方法
        @Override
        public void onError(CameraDevice cameraDevice, int error) {
            cameraDevice.close();
            CameraActivity.this.cameraDevice = null;
            CameraActivity.this.finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startOrientationChangeListener();
        setContentView(R.layout.activity_shot);

        //初始化控件
        intiWidget();

        //响应事件
        // 为textureView组件设置监听器
        textureView.setSurfaceTextureListener(mSurfaceTextureListener);

        //按下拍照键，实现拍照和跳转到Confirm界面
        findViewById(R.id.shot_button).setOnClickListener(this);
        findViewById(R.id.shot_button_land).setOnClickListener(this);

        //返回主界面
        findViewById(R.id.cancel_textview).setOnClickListener(this);
        findViewById(R.id.cancel_textview_land).setOnClickListener(this);

        //调用相册
        findViewById(R.id.album_textview).setOnClickListener(this);
        findViewById(R.id.album_textview_land).setOnClickListener(this);

        //打开或者关闭闪光灯
        findViewById(R.id.light_textview).setOnClickListener(this);
        findViewById(R.id.light_textview_land).setOnClickListener(this);
    }

    //点击拍照
    private void shot() {
        //进行拍照
        captureStillPicture();
        i = new Intent(CameraActivity.this, CameraConfirmActivity.class);
        final Handler handler = new Handler();
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                                 @Override
                                 public void run() {
                                     if (tempName.equals(fileName)) {
                                         i.putExtra("fileName", getExternalFilesDir(null) + "/" + fileName);    //将照片传递到另一个页面
                                         fileName = null;
                                         startActivity(i);
                                         timer.cancel();
                                         finish();
                                     }
                                 }
                             }
                );
            }
        }, 0, 500);
    }

    //打开或者关闭闪光灯
    private void openOrCloseFlashlight() {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            Toast.makeText(this, "你的设备不支持闪光灯!", Toast.LENGTH_LONG).show();
            return;
        }
        if (isFlashlightOpen) {
            isFlashlightOpen = false;
        } else {
            isFlashlightOpen = true;
        }
        //控制闪光灯
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //android6.0调用的手电筒接口
            //我实在是搞不定。。。。

        }else{
//            低于6.0系统的手电筒
            camera = Camera.open();
            parameters = camera.getParameters();
            if ( isFlashlightOpen){
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);// 开启
                camera.setParameters(parameters);
                camera.startPreview();
            }else{
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);// 关闭
                camera.setParameters(parameters);
                camera.stopPreview();
                camera.release();
            }

        }
    }

    private void intiWidget() {
        textureView = findViewById(R.id.preview_textureview);
        shotPortView = findViewById(R.id.shot_port_include);
        shotLandView = findViewById(R.id.shot_land_include);

        isFlashlightOpen = false;
    }


    //自定义重力传感器监听类
    private final void startOrientationChangeListener() {
        mOrientationListener = new OrientationEventListener(this) {
            @Override
            public void onOrientationChanged(int rotation) {
                if (((rotation >= 0) && (rotation <= 45)) || (rotation >= 315) || ((rotation >= 135) && (rotation <= 225))) {//portrait
                    mCurrentOrient = true;
                    if (mCurrentOrient != mScreenProtrait) {
                        mScreenProtrait = mCurrentOrient;
                        OrientationChanged(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        Log.d(TAG, "Screen orientation changed from Landscape to Portrait!");
                    }
                } else if (((rotation > 45) && (rotation < 135)) || ((rotation > 225) && (rotation < 315))) {//landscape
                    mCurrentOrient = false;
                    if (mCurrentOrient != mScreenProtrait) {
                        mScreenProtrait = mCurrentOrient;
                        OrientationChanged(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                        Log.d(TAG, "Screen orientation changed from Portrait to Landscape!");
                    }
                }
            }
        };
        mOrientationListener.enable();
    }

    //自定义屏幕变化回调接口
    private void OrientationChanged(int screenOrientationPortrait) {
        switch (screenOrientationPortrait) {
            case ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE:
                //横屏监听事件
//                CameraActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                shotPortView.setVisibility(View.GONE);
                shotLandView.setVisibility(View.VISIBLE);
                break;
            case ActivityInfo.SCREEN_ORIENTATION_PORTRAIT:
                //竖屏监听事件
//                CameraActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                shotLandView.setVisibility(View.GONE);
                shotPortView.setVisibility(View.VISIBLE);
                break;
        }
    }

    //拍照
    private void captureStillPicture() {
        try {
            if (cameraDevice == null) {
                return;
            }
            // 创建作为拍照的CaptureRequest.Builder
            CaptureRequest.Builder captureRequestBuilder = null;
            try {
                captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
            // 将imageReader的surface作为CaptureRequest.Builder的目标
            captureRequestBuilder.addTarget(imageReader.getSurface());
            // 设置自动对焦模式
            captureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE,
                    CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            // 设置自动曝光模式
            captureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE,
                    CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
            // 获取设备方向
            int rotation = getWindowManager().getDefaultDisplay().getRotation();
            // 根据设备方向计算设置照片的方向
            captureRequestBuilder.set(CaptureRequest.JPEG_ORIENTATION, ORIENTATIONS.get(rotation));
            // 停止连续取景
            captureSession.stopRepeating();
            // 捕获静态图像
            captureSession.capture(captureRequestBuilder.build()
                    , new CameraCaptureSession.CaptureCallback()  // ⑤
                    {
                        // 拍照完成时激发该方法
                        public void onCaptureCompleted(CameraCaptureSession session
                                , CaptureRequest request, TotalCaptureResult result) {
                            try {
                                // 重设自动对焦模式
                                previewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER,
                                        CameraMetadata.CONTROL_AF_TRIGGER_CANCEL);
                                // 设置自动曝光模式
                                previewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE,
                                        CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
                                // 打开连续取景模式
                                captureSession.setRepeatingRequest(previewRequest, null,
                                        null);
                            } catch (CameraAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    // 打开摄像头
    private void openCamera(int width, int height) {
        setUpCameraOutputs(width, height);
        manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            // 打开摄像头
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                /* 相机请求码 */
                final int REQUEST_CAMERA = 0;
                ActivityCompat.requestPermissions(CameraActivity.this,
                        new String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA);
                return;
            }
            manager.openCamera(mCameraId, stateCallback, null); // ①
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void createCameraPreviewSession() {
        try {
            SurfaceTexture texture = textureView.getSurfaceTexture();
            texture.setDefaultBufferSize(previewSize.getWidth(), previewSize.getHeight());
            Surface surface = new Surface(texture);
            // 创建作为预览的CaptureRequest.Builder
            previewRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            // 将textureView的surface作为CaptureRequest.Builder的目标
            previewRequestBuilder.addTarget(new Surface(texture));
            // 创建CameraCaptureSession，该对象负责管理处理预览请求和拍照请求
            cameraDevice.createCaptureSession(Arrays.asList(surface
                    , imageReader.getSurface()), new CameraCaptureSession.StateCallback() // ③
                    {
                        @Override
                        public void onConfigured(CameraCaptureSession cameraCaptureSession) {
                            // 如果摄像头为null，直接结束方法
                            if (null == cameraDevice) {
                                return;
                            }

                            // 当摄像头已经准备好时，开始显示预览
                            captureSession = cameraCaptureSession;
                            try {
                                // 设置自动对焦模式
                                previewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE,
                                        CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                                // 设置自动曝光模式
                                previewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE,
                                        CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH_REDEYE);
                                // 开始显示相机预览
                                previewRequest = previewRequestBuilder.build();
                                // 设置预览时连续捕获图像数据
                                captureSession.setRepeatingRequest(previewRequest,
                                        null, null);  // ④
                            } catch (CameraAccessException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
                            Toast.makeText(CameraActivity.this, "配置失败！"
                                    , Toast.LENGTH_SHORT).show();
                        }
                    }, null
            );
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void setUpCameraOutputs(int width, int height) {
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            // 获取指定摄像头的特性
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(mCameraId);
            // 获取摄像头支持的配置属性
            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            // 获取摄像头支持的最大尺寸
            Size largest = Collections.max(Arrays.asList(map.getOutputSizes(ImageFormat.JPEG)), new CompareSizesByArea());
            // 创建一个ImageReader对象，用于获取摄像头的图像数据
//            imageReader = ImageReader.newInstance(largest.getWidth(), largest.getHeight(), ImageFormat.JPEG, 2);
            imageReader = ImageReader.newInstance(textureView.getHeight(), textureView.getWidth(), ImageFormat.JPEG, 1);
            imageReader.setOnImageAvailableListener(
                    new ImageReader.OnImageAvailableListener() {
                        // 当照片数据可用时激发该方法
                        @Override
                        public void onImageAvailable(ImageReader reader) {
                            // 获取捕获的照片数据
                            Image image = reader.acquireNextImage();
                            ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                            byte[] bytes = new byte[buffer.remaining()];
                            // 使用IO流将照片写入指定文件
                            tempName = new Date().getTime() + ".jpg";
                            File file = new File(getExternalFilesDir(null), tempName);
                            buffer.get(bytes);
                            try (FileOutputStream output = new FileOutputStream(file)) {
                                output.write(bytes);
                                fileName = tempName;
//                                Toast.makeText(CameraActivity.this, "保存: " + file, Toast.LENGTH_LONG).show();     //测试之用
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                image.close();
                            }
                        }
                    }, null);

            // 获取最佳的预览尺寸
            previewSize = chooseOptimalSize(map.getOutputSizes(
                    SurfaceTexture.class), width, height, largest);
            // 根据选中的预览尺寸来调整预览组件（TextureView的）的长宽比
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                textureView.setAspectRatio(previewSize.getWidth(), previewSize.getHeight());
            } else {
                textureView.setAspectRatio(previewSize.getHeight(), previewSize.getWidth());
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            Log.d(TAG, "出现错误");
        }
    }

    private static Size chooseOptimalSize(Size[] choices, int width, int height, Size aspectRatio) {
        // 收集摄像头支持的大过预览Surface的分辨率
        List<Size> bigEnough = new ArrayList<>();
        int w = aspectRatio.getWidth();
        int h = aspectRatio.getHeight();
        for (Size option : choices) {
            if (option.getHeight() == option.getWidth() * h / w &&
                    option.getWidth() >= width && option.getHeight() >= height) {
                bigEnough.add(option);
            }
        }
        // 如果找到多个预览尺寸，获取其中面积最小的。
        if (bigEnough.size() > 0) {
            return Collections.min(bigEnough, new CompareSizesByArea());
        } else {
            System.out.println("找不到合适的预览尺寸！！！");
            return choices[1];
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shot_button:
            case R.id.shot_button_land:
                shot();
                break;
            case R.id.album_textview:
            case R.id.album_textview_land:
                break;
            case R.id.cancel_textview:
            case R.id.cancel_textview_land:
                CameraActivity.this.finish();
                break;
            case R.id.light_textview:
            case R.id.light_textview_land:
                openOrCloseFlashlight();
                break;
        }
    }

    // 为Size定义一个比较器Comparator
    static class CompareSizesByArea implements Comparator<Size> {
        @Override
        public int compare(Size lhs, Size rhs) {
            // 强转为long保证不会发生溢出
            return Long.signum((long) lhs.getWidth() * lhs.getHeight() -
                    (long) rhs.getWidth() * rhs.getHeight());
        }
    }

}


