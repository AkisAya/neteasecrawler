package me.aki.crawler;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import me.aki.crawler.bean.Comment;
import me.aki.crawler.bean.Song;
import me.aki.crawler.util.TokenUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Akis on 5/9/2017.
 */
public class HtmlParser {

    private static String BASE_URL = "https://music.163.com";

    /**
     * 从歌单列表页拿到所有的歌单页面 url
     * @param url
     * @return
     */
    public static List<String> parseListPage(String url) {
        Document document = Jsoup.parse(OkHttpUtil.fetch(url));
        Elements playlists = document.select("div.u-cover > a");
        if (playlists.size() > 0)
            return playlists.stream()
                    .map(e -> BASE_URL + e.attr("href"))
                    .collect(Collectors.toList());
        else
            return null;
    }

    /**
     * 从歌单页面拿到所有歌曲的 id
     * @param url
     * @return
     */
    public static List<Song> parsePlayList(String url) {
        System.out.println("Parsing playlist: " + url + "...");
        Document document = Jsoup.parse(OkHttpUtil.fetch(url));
        List<Song> list =  document.select("ul[class=f-hide] a")
                    .stream()
                    .map(a -> new Song(Integer.parseInt(a.attr("href").split("=")[1]),
                            a.text(), Song.Status.uncrawled))
                    .collect(Collectors.toList());
        Container.songSet.addAll(list);
        return list;
    }


    public static void parseSong(Song song) {
        System.out.println("parsing song " + song.getId() + "...");
        String url = "https://music.163.com/weapi/v1/resource/comments/R_SO_4_" + song.getId() + "?csrf_token=";
        String secKey = TokenUtil.genSecretKey();
        String encText = TokenUtil.genEncText(secKey);
        String encSecKey = TokenUtil.genEncSecKey(secKey);

        String jsonStr = OkHttpUtil.post(url, encText, encSecKey);

        JsonObject jsonObject = new JsonParser().parse(jsonStr).getAsJsonObject();
        int total = jsonObject.getAsJsonPrimitive("total").getAsInt();
        song.setCommentCount(total);

        JsonArray array = jsonObject.getAsJsonArray("hotComments");
        Gson gson = new Gson();
        List<Comment> comments = gson.fromJson(array, new TypeToken<List<Comment>>(){}.getType());
        song.setCommentList(comments);
//        System.out.println(total);
//        comments.forEach(e -> System.out.println(e.getContent()));
    }


    public static void main(String[] args) {
        Song song = new Song();
        song.setId(316938);

        parseSong(song);
    }

}
