import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Created by Lx on 2017/1/6.
 */
public class Bilibili {
    public static void Crawler()throws Exception{

    for (int tid = 1; tid < 200; tid++) {//爬1到200个板块
        for (int pg = 1; pg < 1000; pg++) {//爬1到1000页
            HttpClient httpClient = HttpClients.createDefault();
            HttpGet req = new HttpGet(
                    "Http://api.bilibili.com/archive_rank/getarchiverankbypartion?callback=?&type=jsonp&tid="+tid+"&pn=" + pg);
            req.addHeader("Accept", "application/json,text/javascript,*/*; q=0.01");
            req.addHeader("Accept-Encoding", "gzip.deflate.sdch");
            req.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
            req.addHeader("Content-Type", "textml; charset=UTF-8");
            req.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.2050.400 QQBrowser/9.5.10161.400 ");
            HttpResponse rep = httpClient.execute(req);
            HttpEntity repEntity = rep.getEntity();
            String content = EntityUtils.toString(repEntity,"UTF-8").trim();

            DataBase db = new DataBase();
            db.getconnection();
            //   System.out.println(content);
            int len =content.length();
            if(len <15000){break;}//数据过小时直接退出该TID 保证不会出错
            else{
                JsonParser parser = new JsonParser();  //创建JSON解析器
                JsonObject json = (JsonObject) parser.parse(content);  //创建JsonObject对象
                JsonObject data = json.get("data").getAsJsonObject();
                JsonObject archives = data.get("archives").getAsJsonObject();
                JsonObject page = data.get("page").getAsJsonObject();
                int size = page.get("size").getAsInt();
                for (int i = 0; i < size; i++) {
                    String numq = i + "";
                    String regexp = "\'";

                    JsonObject number = archives.get(numq).getAsJsonObject();
                    JsonObject stat = number.get("stat").getAsJsonObject();
                    String title = number.get("title").getAsString();//视频名
                    title = title.replaceAll(regexp,"");

                    String author = number.get("author").getAsString();//上传者
                    author = author.replaceAll(regexp,"");
                    String tname = number.get("tname").getAsString();//分区名字
                    tname = tname.replaceAll(regexp,"");
                    int coin = stat.get("coin").getAsInt();//硬币数目
                    int favorite = stat.get("favorite").getAsInt();//收藏数目
                    int url = number.get("aid").getAsInt();//http://www.bilibili.com/video/av7854100/ av的后面一串数字
                    System.out.println(" av号 " + url + "     分区 " + tname + "     视频名"+title+"     硬币数 " + coin + "    up主 " + author + "    收藏数 " + favorite);
                    db.insert(tid,title,tname,author,coin,favorite,url);
                }
                System.out.println(pg);
            }
            System.out.println(tid);
        }
    }
}
}
