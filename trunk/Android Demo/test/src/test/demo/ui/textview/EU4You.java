package test.demo.ui.textview;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

import test.demo.R;

public class EU4You extends ListActivity {
  static private ArrayList<Country> EU=new ArrayList<Country>();
  
  static {
    EU.add(new Country(R.string.austria, R.drawable.logo,
                       R.string.austria_url));
    EU.add(new Country(R.string.belgium, R.drawable.logo,
                       R.string.belgium_url));
    EU.add(new Country(R.string.bulgaria, R.drawable.logo,
                       R.string.bulgaria_url));
    EU.add(new Country(R.string.cyprus, R.drawable.logo,
                       R.string.cyprus_url));
    EU.add(new Country(R.string.czech_republic,
                       R.drawable.logo,
                       R.string.czech_republic_url));
    EU.add(new Country(R.string.denmark, R.drawable.logo,
                       R.string.denmark_url));
    EU.add(new Country(R.string.estonia, R.drawable.logo,
                       R.string.estonia_url));
    EU.add(new Country(R.string.finland, R.drawable.logo,
                       R.string.finland_url));
    EU.add(new Country(R.string.france, R.drawable.logo,
                       R.string.france_url));
    EU.add(new Country(R.string.germany, R.drawable.logo,
                       R.string.germany_url));
    EU.add(new Country(R.string.greece, R.drawable.logo,
                       R.string.greece_url));
    EU.add(new Country(R.string.hungary, R.drawable.logo,
                       R.string.hungary_url));
    EU.add(new Country(R.string.ireland, R.drawable.logo,
                       R.string.ireland_url));
    EU.add(new Country(R.string.italy, R.drawable.logo,
                       R.string.italy_url));
    EU.add(new Country(R.string.latvia, R.drawable.logo,
                       R.string.latvia_url));
    EU.add(new Country(R.string.lithuania, R.drawable.logo,
                       R.string.lithuania_url));
    EU.add(new Country(R.string.luxembourg, R.drawable.logo,
                       R.string.luxembourg_url));
    EU.add(new Country(R.string.malta, R.drawable.logo,
                       R.string.malta_url));
    EU.add(new Country(R.string.netherlands, R.drawable.logo,
                       R.string.netherlands_url));
    EU.add(new Country(R.string.poland, R.drawable.logo,
                       R.string.poland_url));
    EU.add(new Country(R.string.portugal, R.drawable.logo,
                       R.string.portugal_url));
    EU.add(new Country(R.string.romania, R.drawable.logo,
                       R.string.romania_url));
    EU.add(new Country(R.string.slovakia, R.drawable.logo,
                       R.string.slovakia_url));
    EU.add(new Country(R.string.slovenia, R.drawable.logo,
                       R.string.slovenia_url));
    EU.add(new Country(R.string.spain, R.drawable.logo,
                       R.string.spain_url));
    EU.add(new Country(R.string.sweden, R.drawable.logo,
                       R.string.sweden_url));
    EU.add(new Country(R.string.united_kingdom,
                       R.drawable.logo,
                       R.string.united_kingdom_url));
  }
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.en4you);
    setListAdapter(new CountryAdapter());
  }
  
  @Override
  protected void onListItemClick(ListView l, View v,
                                 int position, long id) {
    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(EU.get(position).url))));
  }
  
  static class Country {
    int name;
    int flag;
    int url;
    
    Country(int name, int flag, int url) {
      this.name=name;
      this.flag=flag;
      this.url=url;
    }
  }
  
  class CountryAdapter extends ArrayAdapter<Country> {
    CountryAdapter() {
      super(EU4You.this, R.layout.row, R.id.name, EU);
    }
    
    @Override
    public View getView(int position, View convertView,
                        ViewGroup parent) {
      CountryWrapper wrapper=null;
      
      if (convertView==null) {
        convertView=getLayoutInflater().inflate(R.layout.row, null);
        wrapper=new CountryWrapper(convertView);
        convertView.setTag(wrapper);
      }
      else {
        wrapper=(CountryWrapper)convertView.getTag();
      }
      
      wrapper.populateFrom(getItem(position));
      
      return(convertView);
    }
  }

  class CountryWrapper {
    private TextView name=null;
    private ImageView flag=null;
    private View row=null;
    
    CountryWrapper(View row) {
      this.row=row;
    }
    
    TextView getName() {
      if (name==null) {
        name=(TextView)row.findViewById(R.id.name);
      }
      
      return(name);
    }
    
    ImageView getFlag() {
      if (flag==null) {
        flag=(ImageView)row.findViewById(R.id.flag);
      }
      
      return(flag);
    }
    
    void populateFrom(Country nation) {
      getName().setText(nation.name);
      getFlag().setImageResource(nation.flag);
    }
  }
}