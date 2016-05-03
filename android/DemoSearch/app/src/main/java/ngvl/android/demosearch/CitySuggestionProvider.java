package ngvl.android.demosearch;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CitySuggestionProvider extends ContentProvider {

    private static final String AUTHORITY = "ngvl.android.demosearch.citysuggestion";

    private static final int TYPE_ALL_SUGGESTIONS = 1;
    private static final int TYPE_SINGLE_SUGGESTION = 2;

    private UriMatcher mUriMatcher;
    private List<String> cities;

    @Override
    public boolean onCreate() {
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(AUTHORITY, "/#", TYPE_SINGLE_SUGGESTION);
        mUriMatcher.addURI(AUTHORITY, "suggestions/search_suggest_query/*", TYPE_ALL_SUGGESTIONS);
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        if (cities == null || cities.isEmpty()){
            Log.d("NGVL", "WEB");
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://dl.dropboxusercontent.com/u/6802536/cidades.json")
                    .build();

            try {
                Response response = client.newCall(request).execute();
                String jsonString = response.body().string();
                JSONArray jsonArray = new JSONArray(jsonString);

                cities = new ArrayList<>();

                int lenght = jsonArray.length();
                for (int i = 0; i < lenght; i++) {
                    String city = jsonArray.getString(i);
                    cities.add(city);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.d("NGVL", "Cache!");
        }

        MatrixCursor cursor = new MatrixCursor(
                new String[] {
                        BaseColumns._ID,
                        SearchManager.SUGGEST_COLUMN_TEXT_1,
                        SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID
                }
        );

        if (mUriMatcher.match(uri) == TYPE_ALL_SUGGESTIONS) {
            if (cities != null) {
                String query = uri.getLastPathSegment().toUpperCase();
                int limit = Integer.parseInt(uri.getQueryParameter(SearchManager.SUGGEST_PARAMETER_LIMIT));

                int lenght = cities.size();
                for (int i = 0; i < lenght && cursor.getCount() < limit; i++) {
                    String city = cities.get(i);
                    if (city.toUpperCase().contains(query)) {
                        cursor.addRow(new Object[]{i, city, i});
                    }
                }
            }
        } else if (mUriMatcher.match(uri) == TYPE_SINGLE_SUGGESTION) {
            int position = Integer.parseInt(uri.getLastPathSegment());
            String city = cities.get(position);
            cursor.addRow(new Object[]{position, city, position});
        }
        return cursor;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
