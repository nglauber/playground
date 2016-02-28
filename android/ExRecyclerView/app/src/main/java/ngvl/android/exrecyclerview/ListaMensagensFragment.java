package ngvl.android.exrecyclerview;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ngvl.android.exrecyclerview.db.MensagemContract;


public class ListaMensagensFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private RecyclerView mRecyclerView;
    private MensagemCursorAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_mensagens, container, false);

        view.findViewById(R.id.fabAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MensagemFragment().show(getFragmentManager(), "dialog");
            }
        });

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        configuraSwipe();

        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MensagemCursorAdapter(new MensagemCursorAdapter.AoClicarNoItem() {
            @Override
            public void itemFoiClicado(Cursor cursor) {
                long id  = cursor.getLong(cursor.getColumnIndex(MensagemContract._ID));
                MensagemFragment f = MensagemFragment.newInstance(id);
                f.show(getFragmentManager(), "dialog");
            }
        });
        mAdapter.setHasStableIds(true);
        mRecyclerView.setAdapter(mAdapter);

        getLoaderManager().initLoader(0, null, this);

        return view;
    }

    private void configuraSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                final int x = viewHolder.getLayoutPosition();
                Cursor cursor = mAdapter.getCursor();
                cursor.moveToPosition(x);
                final long id = cursor.getLong(cursor.getColumnIndex(MensagemContract._ID));
                getActivity().getContentResolver().delete(
                        Uri.withAppendedPath(MensagemContract.URI_MENSAGENS, String.valueOf(id)),
                        null, null);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), MensagemContract.URI_MENSAGENS, null, null, null, MensagemContract.TITULO);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.setCursor(data);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        mAdapter.setCursor(null);
    }
}
