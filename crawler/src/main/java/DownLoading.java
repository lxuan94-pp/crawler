import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Lx on 2017/1/7.
 */
public class DownLoading {
    public static void downloadURL(String[] addresses,String []av_num,String cato) {
        URL urlfile = null;
        HttpURLConnection httpUrl = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        for (int i = 0; i < 3; i++) {
            try {
                if (addresses != null) {
                    urlfile = new URL(addresses[i]);
                    httpUrl = (HttpURLConnection) urlfile.openConnection();
                    httpUrl.connect();
                    bis = new BufferedInputStream(httpUrl.getInputStream());
                    bos = new BufferedOutputStream(new FileOutputStream("H:\\JAVA视频/"+cato+" av"+av_num[i]+".mp4"));
                    System.out.println(cato+" av"+av_num[i]+".mp4"+"下载成功");
                    int len = 2048;
                    byte[] b = new byte[len];
                    while ((len = bis.read(b)) != -1) {
                        bos.write(b, 0, len);
                    }
                    bos.flush();
                    bis.close();
                    httpUrl.disconnect();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    bis.close();
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static String downLoad(String url)throws IOException{
        String downloadUrl = "http://www.ibilibili.com/video/av"+url;
        Document doc = Jsoup.connect(downloadUrl).get();
        Elements links = doc.select("a[download]");
        String n = null;
        for (Element link : links){
            if(links.text().contains("视频下载")){
                Element str = links.get(1);
                System.out.println(str);
                n = str.attr("href");
                System.out.println(n);
            }

        }
        return n;
    }
}
