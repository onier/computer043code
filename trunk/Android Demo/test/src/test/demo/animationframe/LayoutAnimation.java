/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test.demo.animationframe;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LayoutAnimation extends ListActivity {

    private String[] mStrings = {"Bordeaux", "Lyon", "Marseille", "Nancy", "Paris", "Toulouse", "Strasbourg"};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setListAdapter(new ArrayAdapter(this, 17367043, this.mStrings));

        AnimationSet set = new AnimationSet(true);

        Animation animation = new AlphaAnimation(0.0F, 1.0F);
        animation.setDuration(1000L);
        set.addAnimation(animation);

        animation = new TranslateAnimation(1, 0.0F, 1, 0.0F, 1, -1.0F, 1, 0.0F);
        animation.setDuration(1000L);
        set.addAnimation(animation);

        LayoutAnimationController controller = new LayoutAnimationController(set, 0.5F);
        ListView listView = getListView();
        listView.setLayoutAnimation(controller);
    }
}
