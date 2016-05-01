package nglauber.android.databinding.http;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nglauber.android.databinding.model.Book;

/**
 * Created by nglauber on 4/17/16.
 */
public class BookHttp {

    public static List<Book> getEditoraFromJson(String json) throws JSONException {
        List<Book> books = new ArrayList<>();

        Gson gson = new Gson();
        JSONObject jsonResult = new JSONObject(json);
        JSONArray jsonItems = jsonResult.getJSONArray("items");
        for (int i = 0; i < jsonItems.length(); i++) {
            JSONObject jsonBook = jsonItems.getJSONObject(i);
            JSONObject jsonVolumeInfo = jsonBook.getJSONObject("volumeInfo");

            Book book = gson.fromJson(jsonVolumeInfo.toString(), Book.class);
            books.add(book);
        }
        return books;
    }
}
