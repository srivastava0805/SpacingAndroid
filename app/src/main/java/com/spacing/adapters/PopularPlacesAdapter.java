package com.spacing.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.spacing.R;
import com.spacing.activity.MainActivity;
import com.spacing.models.PopularCitiesDataStructure;
import com.spacing.models.PopularLocalityDataStructure;

import java.util.List;

public class PopularPlacesAdapter extends RecyclerView.Adapter<PopularPlacesAdapter.ViewHolder> {
    private final MainActivity.GotUpdateLocationInterface gotUpdateLocationInterface;
    private List<?> values;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtName;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtName = (TextView) v.findViewById(R.id.textview_rowpouplarity_name);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PopularPlacesAdapter(List<?> myDataset, MainActivity.GotUpdateLocationInterface gotUpdateLocationInterface) {
        values = myDataset;
        this.gotUpdateLocationInterface = gotUpdateLocationInterface;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PopularPlacesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.row_layout_pouplarity, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String name;
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if (values.get(position).getClass() == PopularCitiesDataStructure.class) {
            name = ((PopularCitiesDataStructure) values.get(position)).getCitynName();
        } else {
            name = ((PopularLocalityDataStructure) values.get(position)).getLocalityName();
        }
        holder.txtName.setText(name);
        holder.txtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotUpdateLocationInterface.onUpdated(name);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }

}