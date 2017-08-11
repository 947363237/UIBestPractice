package activitytest.example.com.uibestpractice;

/**
 * Created by Administrator on 2017/8/5 0005.
 */

public class TuLingMsg {

    public interface Callback{
        void run(String msg);
    }

    /**
     * 文本类型
     */
    static class Text{
        private int code;
        private String text;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }


}
