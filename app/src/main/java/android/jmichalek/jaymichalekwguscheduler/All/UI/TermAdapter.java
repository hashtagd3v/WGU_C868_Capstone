package android.jmichalek.jaymichalekwguscheduler.All.UI;

import android.content.Context;
import android.content.Intent;
import android.jmichalek.jaymichalekwguscheduler.All.Entities.Term;
import android.jmichalek.jaymichalekwguscheduler.R;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.collection.CircularArray;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {

    class TermViewHolder extends RecyclerView.ViewHolder {


        private final TextView rowItemTerm;
        private TermViewHolder(View itemView) {
            super(itemView);
            rowItemTerm = itemView.findViewById(R.id.rowItem_term);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Term current = mTerms.get(position);
                    Intent intent = new Intent(context, TermDetail.class);
                    intent.putExtra("id", current.getTermID());
                    intent.putExtra("name", current.getTermName());
                    intent.putExtra("start", current.getTermStart());
                    intent.putExtra("end", current.getTermEnd());
                    context.startActivity(intent);
                }
            });
        }


    }

    private List<Term> mTerms;
    private final Context context;
    private final LayoutInflater mInflater;

    public TermAdapter(Context context){

        mInflater = LayoutInflater.from(context);
        this.context = context;

    }

    @NonNull
    @Override
    public TermAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = mInflater.inflate(R.layout.term_item_row, parent, false);

        return new TermViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermViewHolder holder, int position) {

        if (mTerms != null) {
            Term current = mTerms.get(position);
            int id = current.getTermID();
            holder.rowItemTerm.setText(current.getTermName());
        }
        else {
            holder.rowItemTerm.setText(R.string.empty_noTermsAvailable);
        }

    }

    public void setTerms(List<Term> terms) {
        mTerms = terms;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mTerms.size();
    }

}
