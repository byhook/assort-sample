package sample.byhook.assort;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import sample.byhook.bean.AppBean;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


	}

	private class AppComparator implements Comparator<ApplicationInfo>{

		@Override
		public int compare(ApplicationInfo left, ApplicationInfo right) {
			CharSequence leftChar = left.loadLabel(getPackageManager());
			CharSequence rightChar = right.loadLabel(getPackageManager());

			return 1;
		}
	}

	private void getAllApp(){
        PackageManager pm = getPackageManager();
		List<ApplicationInfo> apps = pm.getInstalledApplications(0);

		AppBean[] appBeans = new AppBean[apps.size()];

		int index = 0;
		CharSequence title;
		for(ApplicationInfo info : apps){
			title = info.loadLabel(pm);

			appBeans[index] = new AppBean();

			index++;
		}

	}

}
