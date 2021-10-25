package android.jmichalek.jaymichalekwguscheduler.All.UI;

import android.content.Context;
import android.content.Intent;
import android.jmichalek.jaymichalekwguscheduler.All.Entities.Assessment;
import android.jmichalek.jaymichalekwguscheduler.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {

    private List<Assessment> mAssessment;
    private final Context context;
    private final LayoutInflater mInflater;

    class AssessmentViewHolder extends RecyclerView.ViewHolder{

        private final TextView rowItemAssessment;

        public AssessmentViewHolder(@NonNull View itemView) {
            super(itemView);
            rowItemAssessment = itemView.findViewById(R.id.rowItem_assessment);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Assessment current = mAssessment.get(position);
                    Intent intent = new Intent(context, AssessmentDetail.class);
                    intent.putExtra("id", current.getAssessmentID());
                    intent.putExtra("name", current.getAssessmentName());
                    intent.putExtra("start", current.getAssessmentStart());
                    intent.putExtra("end", current.getAssessmentEnd());
                    intent.putExtra("course", current.getCourseID());
                    context.startActivity(intent);
                }
            });
        }
    }

    public AssessmentAdapter(Context context) {

        mInflater = LayoutInflater.from(context);
        this.context = context;

    }

    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = mInflater.inflate(R.layout.assessment_item_row, parent, false);
        return new AssessmentViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position) {

        if (mAssessment != null) {
            Assessment current = mAssessment.get(position);
            holder.rowItemAssessment.setText(current.getAssessmentName());
        }
        else {
            holder.rowItemAssessment.setText("No Assessments Available");
        }

    }

    public void setAssessment(List<Assessment> assessments) {

        mAssessment = assessments;
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return 0;
    }

}
