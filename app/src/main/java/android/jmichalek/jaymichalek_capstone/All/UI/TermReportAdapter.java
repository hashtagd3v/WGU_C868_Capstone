package android.jmichalek.jaymichalek_capstone.All.UI;

import android.content.Context;
import android.jmichalek.jaymichalek_capstone.All.Entities.Term;
import android.jmichalek.jaymichalek_capstone.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TermReportAdapter extends RecyclerView.Adapter<TermReportAdapter.ViewHolder> {

    Context context;
    List<Term> term_list;

    public TermReportAdapter(Context context, List<Term> term_list) {
        this.context = context;
        this.term_list = term_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_report_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (term_list != null && term_list.size() > 0) {

            Term term = term_list.get(position);
            holder.termName_item.setText(term.getTermName());
            holder.termCreateDate_item.setText(term.getCreated_date());

        } else {

            return;

        }

    }

    @Override
    public int getItemCount() {
        return term_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView termName_item, termCreateDate_item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            termName_item = itemView.findViewById(R.id.termName_item);
            termCreateDate_item = itemView.findViewById(R.id.termCreateDate_item);

        }
    }

}
