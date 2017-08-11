package activitytest.example.com.uibestpractice;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/8/5 0005.
 */

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {

    private static final String TAG = "MsgAdapter";

    private List<Msg> mMsgList;

    public MsgAdapter(List<Msg> mMsgList){
        this.mMsgList = mMsgList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout leftLayout;
        LinearLayout rightLayout;
        TextView leftMsg;
        TextView rightMsg;
        ImageView avatarLeft;
        ImageView avatarRight;

        public ViewHolder(View view) {
            super(view);
            leftLayout = view.findViewById(R.id.left_layout);
            rightLayout = view.findViewById(R.id.right_layout);
            leftMsg = view.findViewById(R.id.left_msg);
            rightMsg = view.findViewById(R.id.right_msg);
            avatarLeft = view.findViewById(R.id.avatar_left);
            avatarRight = view.findViewById(R.id.avatar_right);
        }
    }


    @Override
    public MsgAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.msg_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        View.OnClickListener avatarClickListen = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int src = (int) view.getTag();
                Intent intent = new Intent(context, AvatarPreview.class);
                intent.putExtra("src",src);
                context.startActivity(intent);
            }
        };
        viewHolder.avatarLeft.setOnClickListener(avatarClickListen);
        viewHolder.avatarRight.setOnClickListener(avatarClickListen);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MsgAdapter.ViewHolder holder, int position) {
        Msg msg = mMsgList.get(position);
        if (msg.getType() == Msg.TYPE_RECIVED) {
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftMsg.setText(msg.getContent());
            holder.avatarLeft.setImageResource(R.drawable.avatar_left);
            //将图片的src属性值放到tag里面之后从tag里面获取
            holder.avatarLeft.setTag(R.drawable.avatar_left);
        } else if (msg.getType() == Msg.TYPE_SEND) {
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.rightMsg.setText(msg.getContent());
            holder.avatarRight.setImageResource(R.drawable.avatar_right);
            //将图片的src属性值放到tag里面之后从tag里面获取
            holder.avatarRight.setTag(R.drawable.avatar_right);
        }
    }

    @Override
    public int getItemCount() {
        return mMsgList.size();
    }
}
