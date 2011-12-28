package test.demo.listactivity;

import test.demo.R;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class DynamicDemo extends ListActivity {
	TextView selection;
	String[] items = { "lorem", "ipsum", "dolor", "sit", "amet", "consectetuer", "adipiscing", "elit", "morbi", "vel", "ligula", "vitae", "arcu", "aliquet", "mollis", "etiam", "vel", "erat", "placerat", "ante", "porttitor", "sodales", "pellentesque", "augue", "purus" };

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main);
		setListAdapter(new IconicAdapter());
		selection = (TextView) findViewById(R.id.selection);
	}

	public void onListItemClick(ListView parent, View v, int position, long id) {
		selection.setText(items[position]);
	}

	class IconicAdapter extends ArrayAdapter {
		IconicAdapter() {
			super(DynamicDemo.this, R.layout.listrow, items);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = getLayoutInflater();
			View row = inflater.inflate(R.layout.listrow, parent, false);
			TextView label = (TextView) row.findViewById(R.id.label);

			label.setText(items[position]);

			ImageView icon = (ImageView) row.findViewById(R.id.icon);

			if (items[position].length() > 4) {
				icon.setImageResource(R.drawable.logo);
			} else {
				icon.setImageResource(R.drawable.ic_launcher);
			}

			return (row);
		}
	}
}
