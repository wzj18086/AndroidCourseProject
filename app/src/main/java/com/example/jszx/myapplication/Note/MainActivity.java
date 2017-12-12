package com.example.jszx.myapplication.Note;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.jszx.myapplication.R;
import com.github.clans.fab.FloatingActionButton;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private EditText titleText;
    private Uri imageUri;
    private int position;
    private int actual_position;
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    private int state=0;
    private int camera_location=1;
    private int local_location=1;
    private List<Integer> id_list;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_note);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar_note);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        FloatingActionButton floatingActionButton=(FloatingActionButton)findViewById(R.id.test1);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File outputImage = new File(getExternalCacheDir(), "out_image.jpg");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT >= 24) {
                    imageUri = FileProvider.getUriForFile(MainActivity.this, "com.example.edittestpractise.fileprovider", outputImage);
                } else {
                    imageUri = Uri.fromFile(outputImage);
                }
                //启动相机程序
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO);
            }
        });
        FloatingActionButton floatingActionButton1=(FloatingActionButton) findViewById(R.id.test2);
        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new
                            String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();
                }
            }
        });


        Connector.getDatabase();
        List<Content> text= DataSupport.findAll(Content.class);
        final List<Integer> positions=new ArrayList<>();
        List<String> content_list=new ArrayList<>();
        List<String> title_list=new ArrayList<>();
        id_list=new ArrayList<>();
        for(Content content:text)
        {
            String edit_text=content.getContent();
            content_list.add(edit_text);
            String title_text=content.getTitleText();
            title_list.add(title_text);
            positions.add(content.getPosition());
            id_list.add(content.getId());
        }
        Intent intent=getIntent();
        actual_position=intent.getIntExtra("actual_position",0);
        state=intent.getIntExtra("state",0);
        if(state==1)
        {
            position=intent.getIntExtra("note_position",0);
        }else
        {
            if(!positions.isEmpty())
                position=positions.get(positions.size()-1)+1;
            else
                position=0;
        }

        Log.d("MainActivity",""+position);
        editText=(EditText) findViewById(R.id.myEdit);
        titleText=(EditText) findViewById(R.id.title_edit);

        String edit="";
        String title="";
        if(!title_list.isEmpty() & title_list.size()>actual_position & state==1)
        {
            title=title_list.get(actual_position);
        }
        titleText.setText(title);

        if(content_list.size()!=0 & content_list.size()>actual_position &state==1)
        {
            edit=content_list.get(actual_position);
        }
        if(!edit.equals(""))
        {
            Pattern pattern=Pattern.compile("/data/data/com.example.jszx.myapplication/files/\\w{9}-\\d{1}");
            Matcher matcher=pattern.matcher(edit);
            SpannableString spannableString=new SpannableString(edit);
            while (matcher.find())
            {
                Bitmap bitmap=BitmapFactory.decodeFile(matcher.group());
                Log.d("MainActivity",matcher.group());
                bitmap=resizeBitmap(bitmap,1000);
                ImageSpan imageSpan=new ImageSpan(MainActivity.this,bitmap);
                spannableString.setSpan(imageSpan,matcher.start(),matcher.end(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            }
            editText.setText(spannableString);

        }else
        {
            editText.setText("");
        }
        editText.setSelection(edit.length());
    }

    public void save_picture(Bitmap bitmap)
    {
        FileOutputStream outputStream=null;
        try {
            String picture_name="picturec"+camera_location+"-"+position;
            //File file=new File(picture_name);
            //outputStream=new FileOutputStream(picture_name);
            outputStream=openFileOutput(picture_name, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.JPEG,60,outputStream);
            outputStream.flush();
            outputStream.close();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }//contains a path separator

    public void save_picture2(Bitmap bitmap)
    {
        FileOutputStream outputStream=null;
        try {
            String picture_name="picturel"+local_location+"-"+position;
            outputStream=openFileOutput(picture_name, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.JPEG,60,outputStream);
            outputStream.flush();
            outputStream.close();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public SpannableString decodeImage(String imagePath)
    {
        Bitmap bitmap;
        bitmap=BitmapFactory.decodeFile(imagePath);
        SpannableString spannableString=new SpannableString(imagePath);
        ImageSpan imageSpan=new ImageSpan(MainActivity.this,bitmap);
        spannableString.setSpan(imageSpan,0,imagePath.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
    private void insertIntoEditText(SpannableString ss) {
        Editable et=editText.getText();// 先获取Edittext中的内容
        int start = editText.getSelectionStart();
        et.insert(start, ss);// 设置ss要添加的位置
        editText.setText(et);// 把et添加到Edittext中
        editText.setSelection(start + ss.length());// 设置Edittext中光标在最后面显示
    }
    private void openAlbum(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO); //打开相册
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int [] grantResults){
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else{
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        //图片部分操作
        switch (requestCode){
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK){
                    try{
                        //将拍摄的照片显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
//                        Log.d("MainActivity",""+bitmap.getByteCount());
                        save_picture(bitmap);
                        bitmap=resizeBitmap(bitmap,1000);
                        String image_path="/data/data/com.example.jszx.myapplication/files/"+"picturec"+camera_location+"-"+position;
                        camera_location++;
                        SpannableString spannableString=new SpannableString(image_path);
                        ImageSpan imageSpan=new ImageSpan(MainActivity.this,bitmap);
                        spannableString.setSpan(imageSpan,0,image_path.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        insertIntoEditText(spannableString);
                        editText.append("\n");
                        Log.d("MainActivity","camera:"+editText.getText().toString());
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK){
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19){
                        //4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    }else{
                        //4.4以下系统使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }

                }
            default:
                break;
        }
    }
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data){
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)){
            //如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1]; //解析出数字格式的id
                String selection = MediaStore.Images.Media._ID+"="+id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){
            //如果是content型的Uri，则用普通方式处理
            imagePath = getImagePath(uri, null);
        }else if ("file".equalsIgnoreCase(uri.getScheme())){
            //直接获取图片路径
            imagePath = uri.getPath();
        }
        displayImage(imagePath);//根据图片路径显示图片
    }
    private void handleImageBeforeKitKat(Intent data){
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);//根据图片路径显示图片
    }
    private String getImagePath(Uri uri, String selection){
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null){
            if (cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
    private void displayImage(String imagePath){
        if (imagePath != null){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            save_picture2(bitmap);
            bitmap=resizeBitmap(bitmap,1000);
            String image_path="/data/data/com.example.jszx.myapplication/files/"+"picturel"+local_location+"-"+position;
            local_location++;
            SpannableString spannableString=new SpannableString(image_path);
            ImageSpan imageSpan=new ImageSpan(MainActivity.this,bitmap);
            spannableString.setSpan(imageSpan,0,image_path.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            insertIntoEditText(spannableString);
            editText.append("\n");
            Log.d("MainActivity","local:"+editText.getText().toString());
            //picture.setImageBitmap(bitmap);
        }else{
            Toast.makeText(this, "无法获取图片", Toast.LENGTH_SHORT).show();
        }
    }
    public Bitmap resizeBitmap(Bitmap bitmap,int S)
    {
        int imgWidth = bitmap.getWidth();
        int imgHeight = bitmap.getHeight();
        double partion = imgWidth*1.0/imgHeight;
        double sqrtLength = Math.sqrt(partion*partion + 1);
        //新的缩略图大小
        double newImgW = S*(partion / sqrtLength);
        double newImgH = S*(1 / sqrtLength);
        float scaleW = (float) (newImgW/imgWidth);
        float scaleH = (float) (newImgH/imgHeight);

        Matrix mx = new Matrix();
        //对原图片进行缩放
        mx.postScale(scaleW, scaleH);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, imgWidth, imgHeight, mx, true);
        return bitmap;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                this.finish();
                break;
            case R.id.save_data:
                Content content=new Content();
                content.setContent(editText.getText().toString());
                content.setTitleText(titleText.getText().toString());
                content.setPosition(position);
                if(state==0)
                {
                    content.save();
                }else
                    content.update(id_list.get(actual_position));
                Intent intent1=new Intent("com.example.edittextpractise.MY_BROADCAST");
                sendBroadcast(intent1);
                Intent intent2=new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent2);
                break;
        }
        return true;
    }
}
