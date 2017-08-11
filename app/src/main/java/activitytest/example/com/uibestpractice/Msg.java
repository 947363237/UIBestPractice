package activitytest.example.com.uibestpractice;

/**
 * Created by Administrator on 2017/8/5 0005.
 */

public class Msg {
    public static final int TYPE_RECIVED = 0;
    public static final int TYPE_SEND = 1;
    private String content;
    private int type;

    public Msg(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }
}
