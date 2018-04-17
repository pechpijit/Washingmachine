package com.khiancode.wm.washingmachine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.khiancode.wm.washingmachine.R;
import com.khiancode.wm.washingmachine.helper.PrefUtils;

import java.util.ArrayList;


public class AdapterSettingMachine extends RecyclerView.Adapter<AdapterSettingMachine.VersionViewHolder> {
    ArrayList<String> model;
    Context context;
    OnItemClickListener clickListener;
    PrefUtils prefUtils;

    public AdapterSettingMachine(Context applicationContext, ArrayList<String> model) {
        this.context = applicationContext;
        this.model = model;
    }

    @Override
    public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_setting_machine, viewGroup, false);
        return new VersionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final VersionViewHolder versionViewHolder, final int i) {
        versionViewHolder.title.setText(model.get(i));
        prefUtils = new PrefUtils(context);
        if (prefUtils.getMachine() == i) {
            versionViewHolder.check.setImageResource(R.drawable.ic_check_black_48dp);
        }

    }

    @Override
    public int getItemCount() {
        return model == null ? 0 : model.size();
    }

    class VersionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView check;
        public VersionViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            check = itemView.findViewById(R.id.check);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(v, getPosition());
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

}
