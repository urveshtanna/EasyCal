
package in.urveshtanna.easycal.adapters.viewHolders;

import android.support.v7.widget.RecyclerView;

import in.urveshtanna.easycal.databinding.ItemMainCalculationBinding;

public class Holder extends RecyclerView.ViewHolder {


    private ItemMainCalculationBinding binding;

    public Holder(ItemMainCalculationBinding itemView) {
        super(itemView.getRoot());
        binding = itemView;
    }

    public ItemMainCalculationBinding getBinding() {
        return binding;
    }
}