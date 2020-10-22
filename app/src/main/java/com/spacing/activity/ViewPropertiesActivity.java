package com.spacing.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.spacing.R;
import com.spacing.SpacingApp;
import com.spacing.adapters.PropertiesAdapter;
import com.spacing.models.AllDetail;
import com.spacing.models.Img;
import com.spacing.models.SearchForPropertyResultsDataStruct;

import java.util.ArrayList;
import java.util.List;

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
        findViews();
    }

    private void findViews() {
        mSpinnnerViewpropertiesBhk = findViewById(R.id.spinnner_viewproperties_bhk);
        mSpinnnerViewpropertiesPrice = findViewById(R.id.spinnner_viewproperties_price);
        mRecylerviewViewPropertiesProperties = findViewById(R.id.recylerview_viewProperties_properties);
        getData();
    }

    private void getData() {
        Intent intent = getIntent();
        SearchForPropertyResultsDataStruct propertiesData = (SearchForPropertyResultsDataStruct) intent.getSerializableExtra(SpacingApp.PROPERTIES_DATA);
        if (propertiesData != null && propertiesData.getAllDetails() != null && propertiesData.getAllDetails().size() > 0) {
            filterAllDataForImage(propertiesData);
        }

    }

    private void filterAllDataForImage(SearchForPropertyResultsDataStruct propertiesData) {
        for (int i = 0; i < propertiesData.getAllDetails().size(); i++) {
            List<Img> filteredImages = new ArrayList<>();
            for (int j = 0; j < propertiesData.getImgs().size(); j++) {
                if (propertiesData.getImgs().get(j).getUniqueID() == propertiesData.getAllDetails().get(i).getUniqueID()) {
                    filteredImages.add(propertiesData.getImgs().get(j));
                }
            }
            propertiesData.getAllDetails().get(i).setPropertyImages(filteredImages);
        }
        List<AllDetail> data = propertiesData.getAllDetails();
        data.add(propertiesData.getAllDetails().get(0));
        setAdapter(data);
    }

    private void setAdapter(List<AllDetail> properties) {
        mRecylerviewViewPropertiesProperties.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        mRecylerviewViewPropertiesProperties.setLayoutManager(layoutManager);
        mAdapter = new PropertiesAdapter(this, properties);
        mRecylerviewViewPropertiesProperties.setAdapter(mAdapter);
    }
}
