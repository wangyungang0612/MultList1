package com.ljfbest.temp;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MultActivity extends Activity {
	private ListView lv_data;
	private Button bt;
	private boolean state = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mult);
		lv_data = (ListView) findViewById(R.id.lv_data);
		bt = (Button) findViewById(R.id.bt);

		ArrayList<HashMap<String, String>> mult_data = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < 30; i++) {
			HashMap<String, String> temp = new HashMap<>();
			temp.put("item", "mult_" + i);
			mult_data.add(temp);
		}

		lv_data.getCheckedItemIds();
		SimpleAdapter adapter = new SimpleAdapter(this, mult_data,
				R.layout.item_data, new String[] { "item" },
				new int[] { R.id.tv }) {
			@Override
			public boolean hasStableIds() {
				return true;
			}

			@Override
			public long getItemId(int position) {
				return position+100;
			}
		};
		lv_data.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		lv_data.setAdapter(adapter);

		bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				for (int i = 0; i < lv_data.getAdapter().getCount(); i++) {
					lv_data.setItemChecked(i, state);
				}
				state = !state;
			}
		});

		lv_data.addFooterView(new View(this));
		System.out.println(((HeaderViewListAdapter)lv_data.getAdapter()).getWrappedAdapter().getCount());
		lv_data.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (ListView.CHOICE_MODE_MULTIPLE == lv_data.getChoiceMode()) {
					HashMap<String, String> item = (HashMap<String, String>) lv_data
							.getItemAtPosition(position);
					Toast.makeText(
							MultActivity.this,
							"当前为多选模式\n选中的条数：" + lv_data.getCheckedItemCount()
									+ "\n" + "点击的数据：" + item.get("item"), 2)
							.show();
					long[] ids = lv_data.getCheckedItemIds();
					for (int i = 0; i < ids.length; i++) {
						Log.i("tag", ids[i]+"");//如果选中了前四条，打印：100，101，102，103;由前面的adapter getItemId()返回的
					}
				}
			}
		});
	}

}
