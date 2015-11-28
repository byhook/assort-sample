package sample.byhook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sample.byhook.assort.R;
import sample.byhook.bean.AppBean;
import sample.byhook.bean.AppItem;
import sample.byhook.view.ExGridView;

/**
 * 所有APP列表适配器
 */
public class BaseListAdapter extends BaseAdapter {
	
	private Context ctx;
	
	private LayoutInflater inflater;
	
	private List<AppItem> items;
	
	public BaseListAdapter(Context ctx, List<AppItem> items){
		this.ctx = ctx;
		this.inflater = LayoutInflater.from(ctx);
		this.items = items;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(null==convertView){
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.layout_grid, null);
			holder.init(convertView);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.setIndex(position);
		return convertView;
	}
	
	private class ViewHolder{
		private ExGridView exGridView;
		private TextView logo;
		
		private void init(View content){
			exGridView = (ExGridView) content.findViewById(R.id.grid_home);
			logo = (TextView) content.findViewById(R.id.logo);
		}
		
		private void setIndex(int index){
			logo.setText(items.get(index).getLetterString());
			BaseGridAdapter adapter = new BaseGridAdapter(ctx,items.get(index).getAppBeans());
			exGridView.setAdapter(adapter);
		}
	}

}
