package sample.byhook.assort;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import sample.byhook.adapter.BaseListAdapter;
import sample.byhook.bean.AppBean;
import sample.byhook.bean.AppItem;
import sample.byhook.parser.CharacterParser;
import sample.byhook.view.SideBar;

public class MainActivity extends Activity implements SideBar.OnTouchingLetterChangedListener, AbsListView.OnScrollListener {

	/**
	 * 拼音解析器
	 */
	private CharacterParser characterParser;

	/**
	 * 列表控件
	 */
	private ListView lv_home;

	private SideBar sideBar;

	/**
	 * 一级列表集合
	 */
	private List<AppItem> appItems = new ArrayList<AppItem>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.allapp_home);

		initView();
		init();
	}

	private void initView(){
		lv_home = (ListView) findViewById(R.id.lv_home);
		sideBar = (SideBar) findViewById(R.id.sideBar);

		TextView dialog = (TextView) findViewById(R.id.dialog);
		sideBar.setTextView(dialog);

		lv_home.setOnScrollListener(this);
		sideBar.setOnTouchingLetterChangedListener(this);
	}

	/**
	 * 分离分类
	 */
	private void init(){
		characterParser = new CharacterParser();

		AppBean[] appBeans = getAllApp();
        //排序
		Arrays.sort(appBeans, new AppComparator());

		LinkedHashMap<String,ArrayList<AppBean>> maps = new LinkedHashMap<String, ArrayList<AppBean>>();

		String letterString = "";
		String temp;
		ArrayList<AppBean> item;


		for(AppBean bean : appBeans){
			temp = bean.getLetter();
			if(temp.equals(letterString)){
				//同一组
				item = maps.get(temp);
				item.add(bean);
				maps.put(temp,item);
			}else{
				//不同组
				item = new ArrayList<AppBean>();
				item.add(bean);
				maps.put(temp,item);
				letterString = temp;

				final AppItem appItem = new AppItem();
				appItem.setLetterString(temp);
				appItem.setAppBeans(item);
				appItems.add(appItem);
			}
		}

		sideBar.setBasetext(maps.keySet().toArray(new String[]{}));
		BaseListAdapter baseListAdapter = new BaseListAdapter(this,appItems);
		lv_home.setAdapter(baseListAdapter);

		System.out.println("#Z=" + appItems.toString());
	}

	@Override
	public void onTouchingLetterChanged(String str) {
		int index = 0;
        for(AppItem item:appItems){
              if(str.equals(item.getLetterString())){
                  lv_home.setSelection(index);
				  break;
			  }
			index++;
		}
	}

	/**
	 * APP名称比较器
	 */
	private class AppComparator implements Comparator<AppBean>{
		@Override
		public int compare(AppBean left, AppBean right) {
			return left.getLetter().compareTo(right.getLetter());
		}
	}

	/**
	 * 获取所有APP
	 * @return
	 */
	private AppBean[] getAllApp(){
        PackageManager pm = getPackageManager();
		//获取安装的APP
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);

		AppBean[] appBeans = new AppBean[resolveInfos.size()];

		int index = 0;
		String title;
		for(ResolveInfo info : resolveInfos){
			appBeans[index] = new AppBean();

			title = info.loadLabel(pm).toString();

			//获取拼音
			final String py = characterParser.getSelling(title);
			//截取第一个字母 大写
            final String letterString = py.substring(0,1).toUpperCase();
			if(letterString.matches("[A-Z]")){
				appBeans[index].setLetter(letterString);
			}else{
				appBeans[index].setLetter("#");
			}

			appBeans[index].setTitle(title);
			appBeans[index].setDrawable(info.loadIcon(pm));

			index++;
		}
		return appBeans;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		sideBar.setItem(firstVisibleItem,visibleItemCount);
	}

}
