package in.urveshtanna.easycal.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

import in.urveshtanna.easycal.R;

/**
 * Created by urveshtanna on 21/01/17.
 */

public class ParentFragment extends Fragment {

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ParentFragment.
     */
    public static ParentFragment newInstance() {
        return new ParentFragment();
    }

    public ParentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void onBackPressed() {
        FragmentManager fragmentManager = getFragmentManager();
        try {
            if (fragmentManager.getBackStackEntryCount() > 0) {
                fragmentManager.popBackStack();
                fragmentManager.executePendingTransactions();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Write all Fragment Transaction methord here
     */

    public void doFragmentTransaction(int containerViewId, ParentFragment destinationFragment, boolean animate) {
        try {
            if (getActivity() != null && !getActivity().isFinishing()) {
                String backStateName = destinationFragment.getClass().getName();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

                if (!fragmentPopped) { //fragment not in back stack, create it.
                    FragmentTransaction ft = manager.beginTransaction();
                    if (animate)
                        ft.setCustomAnimations(R.anim.slide_from_right, R.anim.exit_to_left, R.anim.slide_from_left, R.anim.exit_to_right);
                    ft.replace(containerViewId, destinationFragment);
                    ft.addToBackStack(backStateName);
                    ft.commit();
                }
            }
        } catch (Exception ignoreThisException) {
            ignoreThisException.printStackTrace();
        }
    }

    public void doFragmentTransactionWithoutBackStack(int containerViewId, ParentFragment destinationFragment) {
        if (getActivity() != null && !getActivity().isFinishing()) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.alpha, R.anim.alpha);
            fragmentTransaction.replace(containerViewId, destinationFragment);
            fragmentTransaction.commit();
        }
    }

    public void removeFragment(ParentFragment fragment) {
        if (getActivity() != null && !getActivity().isFinishing()) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.alpha, R.anim.fade_out);
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
        }

    }

    public void doChildFragmentTransaction(int containerViewId, ParentFragment destinationFragment, String tag, boolean animate) {
        if (getActivity() != null && !getActivity().isFinishing()) {
            /*FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if(animate)
                fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.exit_to_left, R.anim.slide_from_left, R.anim.exit_to_right);

            fragmentTransaction.add(containerViewId, destinationFragment, tag);

            fragmentTransaction.commit();*/

            String backStateName = destinationFragment.getClass().getName();

            FragmentManager manager = getChildFragmentManager();
            boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

            if (!fragmentPopped) { //fragment not in back stack, create it.
                FragmentTransaction ft = manager.beginTransaction();
                if (animate)
                    ft.setCustomAnimations(R.anim.slide_from_right, R.anim.exit_to_left, R.anim.slide_from_left, R.anim.exit_to_right);
                ft.replace(containerViewId, destinationFragment);
                ft.commit();
            }
        }
    }

}
