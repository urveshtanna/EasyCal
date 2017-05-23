package in.urveshtanna.easycal.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import in.urveshtanna.easycal.MainActivity;
import in.urveshtanna.easycal.R;
import in.urveshtanna.easycal.adapters.viewHolders.Holder;
import in.urveshtanna.easycal.databinding.ItemMainCalculationBinding;
import in.urveshtanna.easycal.model.Calculation;

/**
 * Adapter used to search product with pricing and navigate to product details
 *
 * @author urveshtanna
 * @version <Current-Version>
 * @see <Usage>
 * @since 1.2.0
 */

public class MainCalculationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<Object> objectList = new ArrayList<>();
    private Context mContext;
    private EditText edtFocusedNumberText, edtSymbolText;
    private Holder holder;

    public MainCalculationAdapter(List<Object> objectList, Context mContext) {
        this.objectList = objectList;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMainCalculationBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_main_calculation, parent, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof Holder && objectList.get(viewHolder.getAdapterPosition()) instanceof Calculation) {
            final Holder holder = (Holder) viewHolder;
            setEdtFocusedNumberText(holder.getBinding().edtNumber);
            setEdtSymbolText(holder.getBinding().edtSymbol);
            setHolder(holder);
            holder.getBinding().edtSymbol.setInputType(InputType.TYPE_NULL);
            holder.getBinding().edtNumber.setInputType(InputType.TYPE_NULL);
            holder.getBinding().setModel((Calculation) objectList.get(holder.getAdapterPosition()));
            holder.getBinding().edtNumber.setOnEditorActionListener(
                    new EditText.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            if (event != null) {
                                // if shift key is down, then we want to insert the '\n' char in the TextView;
                                // otherwise, the default action is to send the message.
                                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                                    addNewElement();
                                    return true;
                                }
                                return false;
                            }
                            addNewElement();
                            return true;
                        }
                    });

            holder.getBinding().edtSymbol.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() == 1) {
                        holder.getBinding().edtNumber.requestFocus();
                        holder.getBinding().edtNumber.setFocusable(true);
                        holder.getBinding().edtNumber.setFocusableInTouchMode(true);
                        if (!holder.getBinding().edtNumber.getText().toString().isEmpty()) {
                            doPreviousCalculation(holder);
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            holder.getBinding().edtNumber.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    doPreviousCalculation(holder);
                }
            });
        }
    }

    public void addNewElement() {
        if (holder != null)
            if (holder.getAdapterPosition() >= 0) {
                if (holder.getAdapterPosition() != 0) {
                    Calculation cal = (Calculation) objectList.get(objectList.size() - 1);
                    if (cal.getSymbol() != null && cal.getValue() > 0.0) {
                        Calculation calculation = new Calculation();
                        objectList.add(calculation);
                        notifyItemInserted(objectList.size() - 1);
                    }
                } else {
                    Calculation cal = (Calculation) objectList.get(holder.getAdapterPosition());
                    if (cal.getValue() != null && cal.getValue() > 0.0) {
                        Calculation calculation = new Calculation();
                        objectList.add(calculation);
                        notifyItemInserted(objectList.size() - 1);
                    } else {
                        objectList.remove(holder.getAdapterPosition());
                        Calculation calculation = new Calculation();
                        objectList.add(calculation);
                        notifyDataSetChanged();
                    }
                }
            } else {
                Calculation calculation = new Calculation();
                objectList.add(calculation);
                notifyDataSetChanged();
            }

    }

    public void removeElement() {
        if (holder != null) {
            if (holder.getBinding().edtNumber.isFocusable() || holder.getBinding().edtNumber.isFocused() || holder.getBinding().edtNumber.isFocusableInTouchMode()) {
                if (holder.getBinding().edtNumber.getText().toString().isEmpty()) {
                    if (holder.getBinding().edtSymbol.getText().toString().isEmpty()) {
                        int size = objectList.size();
                        if (size > 0) {
                            objectList.remove(size - 1);
                            notifyItemRangeRemoved(size - 1, size);
                            if (objectList.size() == 0) {
                                ((MainActivity) mContext).clearAdapter();
                            }
                        } else {
                            ((MainActivity) mContext).clearAdapter();
                        }
                    } else {
                        holder.getBinding().edtSymbol.setText("");
                    }
                } else {
                    holder.getBinding().edtNumber.setText(holder.getBinding().edtNumber.getText().toString().substring(0, holder.getBinding().edtNumber.length() - 1));
                }
            }
            ((MainActivity) mContext).updateTotal(true);
        }
    }

    public Holder getHolder() {
        if (objectList.size() > 0) {
            return holder;
        } else {
            return null;
        }
    }

    public void setHolder(Holder holder) {
        this.holder = holder;
    }

    public EditText getEdtFocusedNumberText() {
        if (objectList.size() > 0) {
            return edtFocusedNumberText;
        } else {
            return null;
        }
    }

    public void setEdtFocusedNumberText(EditText edtFocusedNumberText) {
        this.edtFocusedNumberText = edtFocusedNumberText;
    }

    public EditText getEdtSymbolText() {
        if (objectList.size() > 0) {
            return edtSymbolText;
        } else {
            return null;
        }
    }

    public void setEdtSymbolText(EditText edtSymbolText) {
        this.edtSymbolText = edtSymbolText;
    }

    public List<Object> getObjectList() {
        return objectList;
    }

    private void doPreviousCalculation(Holder holder) {
        try {
            if (holder.getAdapterPosition() != 0 && objectList.get(holder.getAdapterPosition() - 1) instanceof Calculation) {
                Calculation calculation = (Calculation) objectList.get(holder.getAdapterPosition() - 1);
                Double previousValue = 0.0;
                if (holder.getAdapterPosition() - 1 == 0)
                    previousValue = calculation.getValue() == null ? 0.0 : calculation.getValue();
                else
                    previousValue = calculation.getValue() == null ? 0.0 : calculation.getAnswer();

                Double currentValue = holder.getBinding().edtNumber.getText().toString().isEmpty() ? 0.0 : Double.parseDouble(holder.getBinding().edtNumber.getText().toString());
                String symbol = holder.getBinding().edtSymbol.getText().toString().isEmpty() ? null : holder.getBinding().edtSymbol.getText().toString();
                if (symbol != null) {
                    Double newValue = 0.0;
                    switch (symbol) {
                        case "+":
                            newValue = previousValue + currentValue;
                            break;
                        case "-":
                            newValue = previousValue - currentValue;
                            break;
                        case "/":
                            newValue = previousValue / currentValue;
                            break;
                        case "*":
                            newValue = previousValue * currentValue;
                            break;
                    }

                    ((Calculation) objectList.get(holder.getAdapterPosition())).setAnswer(newValue);
                    ((Calculation) objectList.get(holder.getAdapterPosition())).setValue(currentValue);
                    ((Calculation) objectList.get(holder.getAdapterPosition())).setSymbol(symbol);
                    holder.getBinding().tvAnswers.setText(NumberFormat.getNumberInstance(Locale.US).format(newValue));
                    ((MainActivity) mContext).updateTotal(true);
                }
            } else {
                if (!holder.getBinding().edtNumber.getText().toString().isEmpty()) {
                    ((Calculation) objectList.get(holder.getAdapterPosition())).setAnswer(0.0);
                    ((Calculation) objectList.get(holder.getAdapterPosition())).setValue(Double.valueOf(holder.getBinding().edtNumber.getText().toString()));
                    ((Calculation) objectList.get(holder.getAdapterPosition())).setSymbol(null);
                } else {
                    ((Calculation) objectList.get(holder.getAdapterPosition())).setAnswer(0.0);
                    ((Calculation) objectList.get(holder.getAdapterPosition())).setValue(0.0);
                    ((Calculation) objectList.get(holder.getAdapterPosition())).setSymbol(null);
                    objectList.clear();
                }
                ((MainActivity) mContext).updateTotal(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    public List<Object> getAllCalculations() {
        return objectList;
    }
}
