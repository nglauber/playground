package ngvl.android.exrecyclerview;


import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import ngvl.android.exrecyclerview.db.MensagemContract;


public class MensagemFragment extends DialogFragment
        implements DialogInterface.OnClickListener {

    private static final String EXTRA_ID = "id";

    EditText mEdtTitulo;
    EditText mEdtDescricao;
    long id;

    public static MensagemFragment newInstance(long id){
        Bundle bundle = new Bundle();
        bundle.putLong(EXTRA_ID, id);

        MensagemFragment mensagemFragment = new MensagemFragment();
        mensagemFragment.setArguments(bundle);
        return mensagemFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater()
                .inflate(R.layout.fragment_mensagem, null);

        mEdtTitulo = (EditText)view.findViewById(R.id.edtTitulo);
        mEdtDescricao = (EditText)view.findViewById(R.id.edtDescricao);

        boolean novaMensagem = true;
        if (getArguments() != null && getArguments().getLong(EXTRA_ID) != 0){
            id = getArguments().getLong(EXTRA_ID);
            Uri uri = Uri.withAppendedPath(
                    MensagemContract.URI_MENSAGENS, String.valueOf(id));

            Cursor cursor = getActivity().getContentResolver()
                    .query( uri, null, null, null, null);
            if (cursor.moveToNext()) {
                novaMensagem = false;
                mEdtTitulo.setText(cursor.getString(
                        cursor.getColumnIndex(MensagemContract.TITULO)));
                mEdtDescricao.setText(cursor.getString(
                        cursor.getColumnIndex(MensagemContract.DESCRICAO)));
            }
            cursor.close();
        }

        return new AlertDialog.Builder(getActivity())
                .setTitle(novaMensagem ?
                        R.string.nova_mensagem : R.string.editar_mensagem)
                .setView(view)
                .setPositiveButton(R.string.salvar, this)
                .setNegativeButton(R.string.cancelar, null)
                .create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        ContentValues values = new ContentValues();
        values.put(MensagemContract.TITULO,
                mEdtTitulo.getText().toString());
        values.put(MensagemContract.DESCRICAO,
                mEdtDescricao.getText().toString());
        if (id != 0){
            Uri uri = Uri.withAppendedPath(
                    MensagemContract.URI_MENSAGENS, String.valueOf(id));
            getContext().getContentResolver().update(uri, values, null, null);
        } else {
            getContext().getContentResolver().insert(
                    MensagemContract.URI_MENSAGENS, values);
        }
    }
}
