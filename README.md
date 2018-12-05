# OCRPackage
对百度OCR通用文字识别的封装，简化使用
### 使用方式：
#### Step 1.在项目的根build.gradle中添加jitpack代码托管库:
```
allprojects {
  repositories {
   ...
   maven { url 'https://jitpack.io' }
  }
 }
```
#### Step 2.在Module的build.gradle中添加依赖：
```
implementation 'com.github.Dragonfly09:OCRPackage:v1.1'
```
#### Step 3.在需要文字识别的地方初始化
```
OCRPackage.getInstance(context).init(context, "您的应用AK", "您的应用SK");
```
#### Step 4.使你的Activity实现CallBack接口，以便接收识别结果
```
public class TestActivity extends AppCompatActivity implements CallBack {
    /**
     * 识别结果回调
     * @param s
     */
    @Override
    public void onResult(String s) {
        
    }

    /**
     * 识别错误回调
     * @param s
     */
    @Override
    public void onError(String s) {

    }
}
```
#### Step 5.在你的Activity的onActivityResult方法中设置回调
```
 @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        OCRPackage.getCameraRoute().onActivityResult(requestCode,resultCode,data,this);
    }
```
### 使用示例：
```
public class TestActivity extends AppCompatActivity implements CallBack {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvOCR = findViewById(R.id.tvOCR);
        tvOCR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //初始化OCR
                OCRPackage.getInstance(TestActivity.this).init(TestActivity.this, Constants.BaiDu_AK, Constants.BaiDu_SK);
            }
        });
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //注册回调以便接收识别结果
        OCRPackage.getCameraRoute().onActivityResult(requestCode,resultCode,data,this);
    }
    /**
     * 识别结果回调
     * @param s
     */
    @Override
    public void onResult(String s) {

    }

    /**
     * 识别错误回调
     * @param s
     */
    @Override
    public void onError(String s) {

    }
}
```
