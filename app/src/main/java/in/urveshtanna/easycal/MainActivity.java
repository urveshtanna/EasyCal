package in.urveshtanna.easycal;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import java.util.ArrayList;
import java.util.List;

import in.urveshtanna.easycal.adapters.MainCalculationAdapter;
import in.urveshtanna.easycal.databinding.ActivityMainBinding;
import in.urveshtanna.easycal.model.Calculation;
import in.urveshtanna.easycal.tools.ViewUtils;
import in.urveshtanna.easycal.ui.base.ParentActivity;

public class MainActivity extends ParentActivity implements View.OnClickListener {

    ActivityMainBinding binding;
    MainCalculationAdapter adapter;
    BottomSheetBehavior mBottomSheetBehavior;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mContext = this;
        clearAdapter();

        binding.buttonAdd.setOnClickListener(this);
        binding.buttonClear.setOnClickListener(this);
        binding.buttonDivide.setOnClickListener(this);
        binding.buttonSubtract.setOnClickListener(this);
        binding.buttonDot.setOnClickListener(this);
        binding.buttonMultiply.setOnClickListener(this);
        binding.buttonEqual.setOnClickListener(this);
        binding.buttonNext.setOnClickListener(this);

        binding.buttonNine.setOnClickListener(this);
        binding.buttonEight.setOnClickListener(this);
        binding.buttonSeven.setOnClickListener(this);
        binding.buttonSix.setOnClickListener(this);
        binding.buttonFive.setOnClickListener(this);
        binding.buttonFour.setOnClickListener(this);
        binding.buttonThree.setOnClickListener(this);
        binding.buttonTwo.setOnClickListener(this);
        binding.buttonOne.setOnClickListener(this);
        binding.buttonZero.setOnClickListener(this);

        binding.recyclerView.setPadding(ViewUtils.dpToPx(mContext, 16), ViewUtils.dpToPx(mContext, 16), ViewUtils.dpToPx(mContext, 16), ViewUtils.dpToPx(mContext, 300));

        mBottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet);
        mBottomSheetBehavior.setPeekHeight(ViewUtils.dpToPx(this, 68));
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    binding.recyclerView.setPadding(ViewUtils.dpToPx(mContext, 16), ViewUtils.dpToPx(mContext, 16), ViewUtils.dpToPx(mContext, 16), ViewUtils.dpToPx(mContext, 16));
                } else if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    binding.recyclerView.setPadding(ViewUtils.dpToPx(mContext, 16), ViewUtils.dpToPx(mContext, 16), ViewUtils.dpToPx(mContext, 16), ViewUtils.dpToPx(mContext, 300));
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    public void updateTotal(boolean showTotal) {
        if (showTotal) {
            List<Object> objectList = adapter.getAllCalculations();
            Double total = 0.0;
            for (Object o : objectList) {
                if (o instanceof Calculation) {
                    Calculation cal = ((Calculation) o);
                    if (cal.getSymbol() != null) {
                        switch (cal.getSymbol()) {
                            case "+":
                                total += cal.getValue() == null ? 0.0 : cal.getValue();
                                break;
                            case "-":
                                total -= cal.getValue() == null ? 0.0 : cal.getValue();
                                break;
                            case "/":
                                total /= cal.getValue() == null ? 0.0 : cal.getValue();
                                break;
                            case "*":
                                total *= cal.getValue() == null ? 0.0 : cal.getValue();
                                break;
                        }
                    } else {
                        total = cal.getValue() == null ? 0.0 : cal.getValue();
                    }
                }
            }

            binding.tvTotal.setText(String.valueOf(total));
        } else {


            final Animation in = new AlphaAnimation(0.0f, 1.0f);
            in.setDuration(500);

            final Animation out = new AlphaAnimation(1.0f, 0.0f);
            out.setDuration(500);
            out.setAnimationListener(new Animation.AnimationListener() {

                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    binding.tvTotal.setText(null);

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            binding.tvTotal.startAnimation(out);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonAdd:
                if (adapter.getEdtSymbolText() != null)
                    adapter.getEdtSymbolText().setText("+");
                break;
            case R.id.buttonClear:
                adapter.removeElement();
                break;
            case R.id.buttonSubtract:
                if (adapter.getEdtSymbolText() != null)
                    adapter.getEdtSymbolText().setText("-");
                break;
            case R.id.buttonMultiply:
                if (adapter.getEdtSymbolText() != null)
                    adapter.getEdtSymbolText().setText("*");
                break;
            case R.id.buttonDivide:
                if (adapter.getEdtSymbolText() != null)
                    adapter.getEdtSymbolText().setText("/");
                break;
            case R.id.buttonDot:
                if (adapter.getEdtFocusedNumberText() != null) {
                    String text = adapter.getEdtFocusedNumberText().getText().toString();
                    if (!text.contains(".")) {
                        text += ".";
                        adapter.getEdtFocusedNumberText().setText(text);
                    }
                }
                break;
            case R.id.buttonZero:
                if (adapter.getEdtFocusedNumberText() != null) {
                    String text = adapter.getEdtFocusedNumberText().getText().toString();
                    text += "0";
                    adapter.getEdtFocusedNumberText().setText(text);
                }
                break;
            case R.id.buttonOne:
                if (adapter.getEdtFocusedNumberText() != null){
                    String text = adapter.getEdtFocusedNumberText().getText().toString();
                    text += "1";
                    adapter.getEdtFocusedNumberText().setText(text);
                }
                break;
            case R.id.buttonTwo:
                if (adapter.getEdtFocusedNumberText() != null){
                    String text = adapter.getEdtFocusedNumberText().getText().toString();
                    text += "2";
                    adapter.getEdtFocusedNumberText().setText(text);
                }
                break;
            case R.id.buttonThree:
                if (adapter.getEdtFocusedNumberText() != null){
                    String text = adapter.getEdtFocusedNumberText().getText().toString();
                    text += "3";
                    adapter.getEdtFocusedNumberText().setText(text);
                }
                break;
            case R.id.buttonFour:
                if (adapter.getEdtFocusedNumberText() != null){
                    String text = adapter.getEdtFocusedNumberText().getText().toString();
                    text += "4";
                    adapter.getEdtFocusedNumberText().setText(text);
                }
                break;
            case R.id.buttonFive:
                if (adapter.getEdtFocusedNumberText() != null){
                    String text = adapter.getEdtFocusedNumberText().getText().toString();
                    text += "5";
                    adapter.getEdtFocusedNumberText().setText(text);
                }
                break;
            case R.id.buttonSix:
                if (adapter.getEdtFocusedNumberText() != null){
                    String text = adapter.getEdtFocusedNumberText().getText().toString();
                    text += "6";
                    adapter.getEdtFocusedNumberText().setText(text);
                }
                break;
            case R.id.buttonSeven:
                if (adapter.getEdtFocusedNumberText() != null){
                    String text = adapter.getEdtFocusedNumberText().getText().toString();
                    text += "7";
                    adapter.getEdtFocusedNumberText().setText(text);
                }
                break;
            case R.id.buttonEight:
                if (adapter.getEdtFocusedNumberText() != null){
                    String text = adapter.getEdtFocusedNumberText().getText().toString();
                    text += "8";
                    adapter.getEdtFocusedNumberText().setText(text);
                }
                break;
            case R.id.buttonNine:
                if (adapter.getEdtFocusedNumberText() != null){
                    String text = adapter.getEdtFocusedNumberText().getText().toString();
                    text += "9";
                    adapter.getEdtFocusedNumberText().setText(text);
                }
                break;
            case R.id.buttonNext:
                adapter.addNewElement();
                binding.recyclerView.smoothScrollToPosition(adapter.getObjectList().size());
                break;
            case R.id.buttonEqual:
                break;

        }
    }

    public void clearAdapter() {
        List<Object> objects = new ArrayList<>();
        Calculation calculation = new Calculation();
        objects.add(calculation);
        adapter = new MainCalculationAdapter(objects, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);
    }
}
