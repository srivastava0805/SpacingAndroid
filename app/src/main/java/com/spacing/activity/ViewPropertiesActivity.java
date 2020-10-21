package com.spacing.activity;

import android.os.Bundle;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.spacing.R;
import com.spacing.adapters.PopularPlacesAdapter;
import com.spacing.adapters.PropertiesAdapter;
import com.spacing.models.AllDetail;
import com.spacing.models.PopularCitiesDataStructure;

import java.util.List;
import java.util.Properties;

public class ViewPropertiesActivity extends AppCompatActivity {

    private Spinner mSpinnnerViewpropertiesBhk;
    private Spinner mSpinnnerViewpropertiesPrice;
    private RecyclerView mRecylerviewViewPropertiesProperties;
    private LinearLayoutManager layoutManager;
    private PropertiesAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_properties);
        mSpinnnerViewpropertiesBhk = findViewById(R.id.spinnner_viewproperties_bhk);
        mSpinnnerViewpropertiesPrice = findViewById(R.id.spinnner_viewproperties_price);
        mRecylerviewViewPropertiesProperties = findViewById(R.id.recylerview_viewProperties_properties);
        setAdapter(null);
    }

    private void setAdapter(List<AllDetail> cities) {
        mRecylerviewViewPropertiesProperties.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        mRecylerviewViewPropertiesProperties.setLayoutManager(layoutManager);
        mAdapter = new PropertiesAdapter(this,cities);
        mRecylerviewViewPropertiesProperties.setAdapter(mAdapter);
    }
}
