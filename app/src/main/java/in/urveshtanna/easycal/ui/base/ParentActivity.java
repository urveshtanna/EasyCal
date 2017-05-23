package in.urveshtanna.easycal.ui.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.WindowManager;

import in.urveshtanna.easycal.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ParentActivity extends AppCompatActivity {

    private boolean mIsFront = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIsFront = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mIsFront = false;
    }

    public boolean isFront() {
        return mIsFront;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(color);
        }
    }

    /**
     * do fragment transaction method
     */

    public void doFragmentTrasaction(ParentActivity activity, int containerViewId, Fragment destinationFragment, boolean animate) {
        //empty body, do what you need
        try {
            if (activity != null && !activity.isFinishing()) {
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                if (animate) {
                    fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.exit_to_left, R.anim.slide_from_left, R.anim.exit_to_right);
                }
                fragmentTransaction.replace(containerViewId, destinationFragment);

                fragmentTransaction.commit();
            }
        }catch (Exception ignoreThisException){
            //ignore this
            ignoreThisException.printStackTrace();
        }
    }
}
