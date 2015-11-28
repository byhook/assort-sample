package sample.byhook.adapter;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sample.byhook.assort.R;
import sample.byhook.bean.AppBean;

/**
 * 所有APP
 * 宫格
 */
public class BaseGridAdapter extends BaseAdapter {

    private Context ctx;

    private LayoutInflater inflater;

    private List<AppBean> bean;

    private PackageManager pm;

    public BaseGridAdapter(Context ctx, List<AppBean> bean) {
        this.ctx = ctx;
        this.inflater = LayoutInflater.from(ctx);
        this.bean = bean;

        this.pm = ctx.getPackageManager();
    }

    @Override
    public int getCount() {
        return bean.size();
    }

    @Override
    public Object getItem(int position) {
        return bean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.allapp_grid_item, null);
            holder.init(convertView);
            holder.setIndex(position);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setIndex(position);
        return convertView;
    }

    private class ViewHolder {
        private ImageView iv_logo;
        private TextView tv_title;

        private void init(View content) {
            tv_title = (TextView) content.findViewById(R.id.tv_title);
            iv_logo = (ImageView) content.findViewById(R.id.iv_logo);
        }

        private void setIndex(int index) {
            tv_title.setText(bean.get(index).getTitle());
            iv_logo.setImageDrawable(bean.get(index).getDrawable());
        }

    }
}
