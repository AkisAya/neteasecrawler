package me.aki.crawler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by Akis on 5/10/2017.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
            System.out.println("parsing page " + i + "...");
            String url = "https://music.163.com/discover/playlist/?order=hot&cat=%E5%85%A8%E9%83%A8&limit=35&offset=" + i*35;

            List<String> playLists = HtmlParser.parseListPage(url);
            if (playLists != null)
                playLists.forEach(HtmlParser::parsePlayList);
        }

        Container.songSet.forEach(HtmlParser::parseSong);


        File file = new File("comments");
        BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));

        Container.songSet.forEach(song -> {
            song.getCommentList().forEach(comment -> {
                try {
                    bw.write(comment.getContent() + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });

        long end = System.currentTimeMillis();

        long time = (end - start) / 1000;

        System.out.println("歌曲总数：" + Container.songSet.size());
        System.out.println("耗时：" + time + " s");
    }
}
