package com.khiancode.wm.washingmachine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.khiancode.wm.washingmachine.R;
import com.khiancode.wm.washingmachine.helper.PrefUtils;

import java.util.ArrayList;


public class AdapterSettingNotification extends RecyclerView.Adapter<AdapterSettingNotification.VersionViewHolder> {
    ArrayList<String> model;
    Context context;
    OnItemClickListener clickListener;

    public AdapterSettingNotification(Context applicationContext, ArrayList<String> model) {
        this.context = applicationContext;
        this.model = model;
    }

    @Override
    public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_setting_notification, viewGroup, false);
        return new VersionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final VersionViewHolder versionViewHolder, final int i) {
        versionViewHolder.title.setText(model.get(i));
        final PrefUtils prefUtils = new PrefUtils(context);
        if (prefUtils.getNotification(i) == 1) {
            versionViewHolder.swit.setChecked(true);
        } else {
            versionViewHolder.swit.setChecked(false);
        }

        versionViewHolder.swit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    versionViewHolder.swit.setChecked(true);
                    prefUtils.setNotification(i, 1);
                } else {
                    versionViewHolder.swit.setChecked(false);
                    prefUtils.setNotification(i, 0);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return model == null ? 0 : model.size();
    }

    class VersionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        Switch swit;
        public VersionViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            swit = itemView.findViewById(R.id.swit);
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
