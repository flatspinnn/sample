/**
 * 3mail API接続サンプルプログラム
 * 要 Jakarta Commons HttpClient4 (http://hc.apache.org/)
 *
 * @author 3hands dev Team
 * @copyright 3hands Inc.
 */

import java.io.File;
import java.io.StringReader;
import java.nio.charset.Charset;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


/**
 * メインクラス
 *
 * @author 3hands dev Team
 */
public class SendMail {

    /**
     * コンストラクタ
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        new SendData().send();
    }
}

/**
 * APIへ接続及びデータ送信
 *
 * @author 3hands dev Team
 */
class SendData {

    /**
     * データ送信
     *
     * @throws Exception
     */
    public void send() throws Exception {

        //3mail APIへ接続
        final HttpClient client = new DefaultHttpClient();
        final HttpPost post = new HttpPost("http://demo.3mail.jp/API/set");
        final MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

        //送信文字コードを設定
        Charset charSet = Charset.forName("shift_jis");

        //送信データを設定
        entity.addPart("login_id", new StringBody("noda.takumi@raccoon.ne.jp"));
        entity.addPart("password", new StringBody("noda0515*"));
        entity.addPart("subject", new StringBody("メールタイトル", charSet));
        entity.addPart("body", new StringBody("$val1", charSet));
        entity.addPart("from_address", new StringBody("noda.takumi@raccoon.ne.jp"));
        entity.addPart("start_sending", new StringBody("now"));

        //メールアドレスリストファイル登録
//        FileBody file = new FileBody(new File("C:\\pleiades\\workspace\\3mail_prototype\\src\\test.zip"), "application/zip");
        FileBody file = new FileBody(new File("C:\\pleiades\\workspace\\3mail_prototype\\src\\test.zip"), ContentType.create("application/zip"), "test.zip");
        entity.addPart("file", file);

        FileBody html = new FileBody(new File("C:\\pleiades\\workspace\\3mail_prototype\\src\\test.html"), ContentType.create("text/html"), "test.html");
        entity.addPart("file_to_attach", html);

        //データセット
        post.setEntity(entity);

        //データ送信し結果(XML形式)を取得
        HttpResponse response = client.execute(post);

        //XMLをパース
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(EntityUtils.toString(response.getEntity()))));

        //結果を出力
        Element root = doc.getDocumentElement();

        String[] resultList = {"status", "code", "message", "valid", "error", "task_id"};

        for (String res : resultList){
            NodeList list = root.getElementsByTagName(res);
            Element element = (Element)list.item(0);
            System.out.println(res + "->" + new String(element.getFirstChild().getNodeValue().getBytes("SJIS"), "UTF-8"));
        }

        //コネクションを閉じる
        client.getConnectionManager().shutdown();
    }
}