

### 访问https
```

    import java.security.cert.CertificateException;
    import java.security.cert.X509Certificate;
    import javax.net.ssl.SSLContext;
    import javax.net.ssl.TrustManager;
    import javax.net.ssl.X509TrustManager;

    import org.apache.http.conn.ClientConnectionManager;
    import org.apache.http.conn.scheme.Scheme;
    import org.apache.http.conn.scheme.SchemeRegistry;
    import org.apache.http.conn.ssl.SSLSocketFactory;
    import org.apache.http.impl.client.DefaultHttpClient;

    public class SSLClient extends DefaultHttpClient {
        public SSLClient() throws Exception {
            super();
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain,
                                            String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain,
                                            String authType) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = this.getConnectionManager();
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("https", 443, ssf));
        }
    }



    import org.apache.http.HttpEntity;
    import org.apache.http.HttpResponse;
    import org.apache.http.NameValuePair;
    import org.apache.http.StatusLine;
    import org.apache.http.client.HttpClient;
    import org.apache.http.client.entity.UrlEncodedFormEntity;
    import org.apache.http.client.methods.HttpPost;
    import org.apache.http.entity.StringEntity;
    import org.apache.http.message.BasicHeader;
    import org.apache.http.message.BasicNameValuePair;
    import org.apache.http.util.EntityUtils;
    import org.jsoup.Jsoup;
    import org.jsoup.nodes.Document;

    import java.io.BufferedReader;
    import java.io.InputStream;
    import java.io.InputStreamReader;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.zip.GZIPInputStream;

    /**
    * 说明:
    * <p>
    * <br>
    * 创建时间：2019-08-22 14:47
    *
    * @author 汪强
    */
    public class HttpClientSSL {
        public static void main(String[] args) {
            String url = "https://218.22.181.11:82/frmLogin.aspx";
            String jsonStr = "{xxx}";
            String httpOrgCreateTestRtn = doPost(url, jsonStr, "utf-8");
        }

        @SuppressWarnings("resource")
        public static String doPost(String url, String jsonstr, String charset) {
            HttpClient httpClient = null;
            HttpPost httpPost = null;
            String result = null;
            try {
                httpClient = new SSLClient();
                httpPost = new HttpPost(url);
                httpPost.addHeader("Accept", "*/*");
                httpPost.addHeader("Accept-Encoding", "gzip, deflate, br");
                httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
                httpPost.addHeader("Connection", "keep-alive");
                httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

                List<NameValuePair> nvps = new ArrayList<>();
                nvps.add(new BasicNameValuePair("userName", "dd"));
                nvps.add(new BasicNameValuePair("password", "dd"));
                httpPost.setEntity(new UrlEncodedFormEntity(nvps));

                HttpResponse response = httpClient.execute(httpPost);
                System.out.println(response.getStatusLine().getStatusCode());
                if (response != null) {
                    HttpEntity resEntity = response.getEntity();
                    if (resEntity != null) {
                        result = EntityUtils.toString(resEntity, charset);
                        System.out.println(result);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return result;
        }
    }    
```




### reponse乱码
不是汉字编码问题，是gzip压缩

```
    Header[] headers = response.getHeaders("Content-Encoding");
    for(Header h : headers){
        //忽略大小写
        if(h.getValue().toLowerCase().indexOf("gzip") > -1){
            //For GZip response
            try{
                GZIPInputStream gzin = new GZIPInputStream(is);
                InputStreamReader isr = new InputStreamReader(gzin,"utf-8");

                BufferedReader bufferedReader = new BufferedReader(isr);
                String value = null;
                while ((value = bufferedReader.readLine()) != null) {
                    System.out.println(value.toLowerCase());
                }
                bufferedReader.close();
            }catch (IOException exception){
                exception.printStackTrace();
            }
        }
    }
```