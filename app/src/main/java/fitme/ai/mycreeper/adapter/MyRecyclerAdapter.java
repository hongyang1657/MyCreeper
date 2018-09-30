package fitme.ai.mycreeper.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import fitme.ai.mycreeper.view.DetailActivity;
import fitme.ai.mycreeper.R;
import fitme.ai.mycreeper.bean.DailyHomeBean;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder>{

    private List<DailyHomeBean> mList;
    private Context mContext;
    private Intent intent;
    public MyRecyclerAdapter(List<DailyHomeBean> mList){
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        intent = new Intent(mContext, DetailActivity.class);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvTitle.setText(mList.get(position).getTitle());
        Picasso.get().load(mList.get(position).getImgUrl()).into(holder.ivImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //item click
                intent.putExtra("href",mList.get(position).getHref());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public final TextView tvTitle;
        public final ImageView ivImage;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            ivImage = itemView.findViewById(R.id.iv_img);
        }
    }
}
