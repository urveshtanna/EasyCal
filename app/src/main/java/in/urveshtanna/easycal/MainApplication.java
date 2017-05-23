package in.urveshtanna.easycal;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Adapter used to search product with pricing and navigate to product details
 *
 * @author urveshtanna
 * @version <Current-Version>
 * @see <Usage>
 * @since 1.2.0
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
