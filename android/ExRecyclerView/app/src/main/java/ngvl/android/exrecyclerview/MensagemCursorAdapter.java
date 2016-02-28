package ngvl.android.exrecyclerview;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ngvl.android.exrecyclerview.db.MensagemContract;

public class MensagemCursorAdapter extends
        RecyclerView.Adapter<MensagemCursorAdapter.VH> {

    private Cursor mCursor;
    private AoClicarNoItem mListener;

    public MensagemCursorAdapter(AoClicarNoItem listener) {
        mListener = listener;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mensagem, parent, false);

        final VH vh = new VH(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = vh.getAdapterPosition();
                mCursor.moveToPosition(position);
                if (mListener != null) mListener.itemFoiClicado(mCursor);
            }
        });

        return vh;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        mCursor.moveToPosition(position);
        int idx_titulo = mCursor.getColumnIndex(MensagemContract.TITULO);
        int idx_descricao = mCursor.getColumnIndex(MensagemContract.DESCRICAO);

        String titulo = mCursor.getString(idx_titulo);
        String descricao = mCursor.getString(idx_descricao);

        holder.mText1.setText(titulo);
        holder.mText2.setText(descricao);
    }

    @Override
    public int getItemCount() {
        return (mCursor != null) ? mCursor.getCount() : 0;
    }

    @Override
    public long getItemId(int position) {
        if (mCursor != null) {
            if (mCursor.moveToPosition(position)) {
                int idx_id = mCursor.getColumnIndex(MensagemContract._ID);
                return mCursor.getLong(idx_id);
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public Cursor getCursor(){
        return mCursor;
    }

    public void setCursor(Cursor newCursor){
        mCursor = newCursor;
        notifyDataSetChanged();
    }

    public interface AoClicarNoItem {
        void itemFoiClicado(Cursor cursor);
    }

    public static class VH extends RecyclerView.ViewHolder {
        public TextView mText1;
        public TextView mText2;

        public VH(View v) {
            super(v);
            mText1 = (TextView) v.findViewById(R.id.text1);
            mText2 = (TextView) v.findViewById(R.id.text2);
        }
    }
}