
import java.io.IOException;

/**
 * Created by Lx on 2017/1/7.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Bilibili.Crawler();//爬虫
        String tempdata[] = new String[3];
        String str = "宅舞";//板块名称

        DataBase db = new DataBase();
        try {
            //连接数据库
            db.getconnection();
            for (int i = 0; i < 3; i++) {
                String target_av = db.select(str)[i];
                System.out.println(target_av);
                tempdata[i] = target_av;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String tempaddress[] = new String[3];
            for (int n = 0; n < 3; n++) {
                tempaddress[n] = DownLoading.downLoad(tempdata[n]);
                System.out.println("tempaddress[n]:" + tempaddress[n]);
            }
            DownLoading.downloadURL(tempaddress, tempdata, str);
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        System.out.println("全部下载成功");

    }
}
