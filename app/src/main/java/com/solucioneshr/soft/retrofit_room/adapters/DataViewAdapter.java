package com.solucioneshr.soft.retrofit_room.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.solucioneshr.soft.retrofit_room.R;
import com.solucioneshr.soft.retrofit_room.model.DataModel;
import java.util.List;

import com.amulyakhare.textdrawable.TextDrawable;

public class DataViewAdapter extends RecyclerView.Adapter<DataViewAdapter.ViewHolder> {
    List<DataModel> mItems;
    Context mContext;

    public DataViewAdapter(List<DataModel> items, Context context){
        mItems = items;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View theView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_view_data, parent, false);
        ViewHolder viewHolder = new ViewHolder(theView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataModel data = mItems.get(position);

        holder.textTitle.setText(data.getTitle());
        holder.textBody.setText(data.getBody());
        holder.imgText.setImageDrawable(TextDrawable.builder().buildRound(data.getId().toString(),
                ContextCompat.getColor(mContext, R.color.half_black)));

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textTitle;
        public TextView textBody;
        public ImageView imgText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitleView);
            textBody = itemView.findViewById(R.id.textBodyView);
            imgText = itemView.findViewById(R.id.imgTextDrawer);
        }
    }
}
