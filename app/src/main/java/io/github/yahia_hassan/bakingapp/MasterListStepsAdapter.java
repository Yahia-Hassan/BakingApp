package io.github.yahia_hassan.bakingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import io.github.yahia_hassan.bakingapp.POJO.Step;

public class MasterListStepsAdapter extends RecyclerView.Adapter<MasterListStepsAdapter.StepsViewHolder> {

    private Context mContext;
    private ArrayList<Step> mStepArrayList;

    public MasterListStepsAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.steps_list_item, parent, false);
        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, int position) {
        String shortDescription = mStepArrayList.get(position).getShortDescription();
        holder.shortDescriptionTextView.setText(shortDescription);
    }

    @Override
    public int getItemCount() {
        if (mStepArrayList == null) return 0;
        return mStepArrayList.size();
    }

    public void setStepArrayList(ArrayList<Step> stepArrayList) {
        mStepArrayList = stepArrayList;
        notifyDataSetChanged();
    }

    public class StepsViewHolder extends RecyclerView.ViewHolder {
        TextView shortDescriptionTextView;

        public StepsViewHolder(View itemView) {
            super(itemView);
            shortDescriptionTextView = itemView.findViewById(R.id.steps_list_item_short_description_text_view);
        }
    }
}
