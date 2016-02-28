package ngvl.android.exrecyclerview.db;

import android.net.Uri;
import android.provider.BaseColumns;

public interface MensagemContract extends BaseColumns {
    String AUTHORITY = "ngvl.android.exrecycler";
    Uri BASE_URI = Uri.parse("content://"+ AUTHORITY);
    Uri URI_MENSAGENS = Uri.withAppendedPath(BASE_URI, "msgs");

    String TABELA_MENSAGEM = "Mensagem";
    String TITULO = "titulo";
    String DESCRICAO = "descricao";
}
