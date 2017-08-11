package activitytest.example.com.uibestpractice;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static activitytest.example.com.uibestpractice.Msg.TYPE_RECIVED;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private List<Msg> randomMsgList = new ArrayList<>();
    private List<Msg> msgList = new ArrayList<>();
    private EditText inputText;
    private Button send;
    private ImageView buttonAvatarLeft;
    private ImageView buttonAvatarRight;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;
    private Handler handler=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取控件
        inputText = (EditText) findViewById(R.id.input_text);
        msgRecyclerView = (RecyclerView) findViewById(R.id.msg_recycler_view);

        //初始化消息列表
        initMsgs();

        //消息处理
        msgHandle();

        //头像预览
        //avatarPreView();
    }

    public void avatarPreView() {
        buttonAvatarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Resources resources = buttonAvatarLeft.getResources();
                //startActivity();
                //Log.d(TAG, "onClick: "+buttonAvatarLeft.getDrawingCache());
            }
        });
    }

    private void msgHandle() {
        //创建属于主线程的handler
        handler=new Handler();
        msgRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);
        send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String content = inputText.getText().toString();

                if (!"".equals(content)) {
                    //显示我发送的消息
                    Msg msg = new Msg(content, Msg.TYPE_SEND);
                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size()-1);
                    msgRecyclerView.scrollToPosition(msgList.size()-1);
                    inputText.setText("");

                    //随机回应消息
                    //getMsgTuLing(content);
                    new Thread(){
                        public void run(){
                            //图灵机器人接口
                            getMsgTuLing(content,new TuLingMsg.Callback(){

                                @Override
                                public void run(String msg) {
                                    //更新界面
                                    msgList.add(new Msg(msg,Msg.TYPE_RECIVED));
                                    adapter.notifyItemInserted(msgList.size()-1);
                                    msgRecyclerView.scrollToPosition(msgList.size()-1);
                                }
                            });

                        }
                    }.start();
                }
            }
        });
    }

    private void initMsgs(){
        msgList.add(new Msg("你好.", TYPE_RECIVED));
        msgList.add(new Msg("我很好.",Msg.TYPE_SEND));
        msgList.add(new Msg("那就好.", TYPE_RECIVED));
    }

    /**
     * post请求 提交数据到服务器
     * @param
     */
    public void getMsgTuLing(String msg, final TuLingMsg.Callback callback){
        String json="{\n" +
                    "    \"key\":\"b596bc014659412aaa2f7defb14b2bb8\",\n" +
                    "    \"info\":\""+msg+"\",\n" +
                    "    \"userid\":\"ewew223\"\n" +
                    "}";

            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

            //图灵机器人接口
            String url = "http://www.tuling123.com/openapi/api";
            OkHttpClient client = new OkHttpClient();//创建okhttp实例
            RequestBody body=RequestBody.create(mediaType,json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Call call = client.newCall(request);
            OkHttpClient mOkHttpClient = new OkHttpClient();
            mOkHttpClient.newCall(request).enqueue(new Callback(){
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, "onFailure: "+e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String result = response.body().string();
                        Log.i(TAG, "onResponse: " + result);
                        TuLingMsg.Text text = JSON.parseObject(result, TuLingMsg.Text.class);
                        final String str = text.getText();

                        //回应消息，必须这样写，请参考 http://blog.csdn.net/djx123456/article/details/6325983
                        handler.post(new  Runnable(){
                            @Override
                            public void run() {
                                callback.run(str);
                            }
                        });
                    }
                }
        });
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("兄台不要走，在玩玩！");
        dialog.setCancelable(true);
        dialog.setPositiveButton("我要走了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                dialogInterface.dismiss();
            }
        });

        dialog.setNegativeButton("战斗到天亮", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
       dialog.show();
    }
}
