package com.spacing.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.spacing.R;
import com.spacing.models.AllDetail;

import java.util.List;

public class PropertiesAdapter extends RecyclerView.Adapter<PropertiesAdapter.ViewHolder> {
    private Context mCtx;

    public PropertiesAdapter(Context ctx, List<AllDetail> properties) {
        this.mCtx = ctx;
    }

    @NonNull
    @Override
    public PropertiesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.row_layout_propertyview, parent, false);
        // set the view's size, margins, paddings and layout parameters
        PropertiesAdapter.ViewHolder vh = new PropertiesAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        for (int i = 0; i < 10; i++) {
            //create imageview here and setbg
            ImageView imageView = new ImageView(mCtx);
            imageView.setImageResource(R.drawable.ic_launcher_foreground);
            holder.mLinearLayoutRowpropertyImagecontainer.addView(imageView,i);

        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private HorizontalScrollView mHorizontalscrollviewRowpropertyImages;
        private TextView mTextviewRowpropertyPrice;
        private TextView mTextviewRowpropertyCongiguration;
        private TextView mTextviewRowpropertyAddress;
        private TextView mTextviewRowpropertyPropertysize;
        private TextView mTextviewRowpropertyPropertyaccessory;
        private LinearLayout mLinearLayoutRowpropertyImagecontainer;

        public ViewHolder(View v) {
            super(v);
            mHorizontalscrollviewRowpropertyImages = v.findViewById(R.id.horizontalscrollview_rowproperty_images);
            mTextviewRowpropertyPrice = v.findViewById(R.id.textview_rowproperty_price);
            mTextviewRowpropertyCongiguration = v.findViewById(R.id.textview_rowproperty_congiguration);
            mTextviewRowpropertyAddress = v.findViewById(R.id.textview_rowproperty_address);
            mTextviewRowpropertyPropertysize = v.findViewById(R.id.textview_rowproperty_propertysize);
            mTextviewRowpropertyPropertyaccessory = v.findViewById(R.id.textview_rowproperty_propertyaccessory);
            mLinearLayoutRowpropertyImagecontainer = v.findViewById(R.id.linearLayout_rowproperty_imagecontainer);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
