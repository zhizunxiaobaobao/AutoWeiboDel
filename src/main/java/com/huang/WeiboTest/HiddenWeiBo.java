package com.huang.WeiboTest;

import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.builder.HCB;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpHeader;
import com.arronlong.httpclientutil.common.SSLs;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: huang.zh
 * @Date: 2018/11/27 17:33
 * @Description:
 */
public class HiddenWeiBo {

    public static void main(String[] args) throws HttpProcessException, FileNotFoundException, InterruptedException {
        HiddenWeiBo demo = new HiddenWeiBo();
        List<String> mid = demo.getMid(demo.getIndex());
        for (String string : mid) {
            demo.postwb(string);
            Thread.sleep(500);
        }
        System.out.println("done");
    }

    public String getIndex() throws HttpProcessException{
        String url = "https://weibo.com/2875848794/profile?rightmod=1&wvr=6&mod=personnumber&is_all=1";
        //插件式配置Header（各种header信息、自定义header）
        Header[] headers    = HttpHeader.custom()
                .host("weibo.com")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36")
                .referer("https://passport.weibo.com/visitor/visitor?entry=miniblog&a=enter&url=https%3A%2F%2Fweibo.com%2Fp%#####自己的ID#####%2Fhome%3Ffrom%3Dpage_100505_profile%26wvr%3D6%26mod%3Ddata%26is_hot%3D1&domain=.weibo.com&ua=php-sso_sdk_client-0.6.28&_rand=1534310854.1213")
                .contentType("application/x-www-form-urlencoded")
                .cookie("YF-Page-G0=b58533766541bcc934acef7f6116c26d1; _s_tentry=passport.weibo.com; Apache=2482346697449.5763.1534310855544; SINAGLOBAL=2482346629749.5763.1534310855544; ULV=1534310855679:1:1:1:248234669749.5763.1534310855544:; YF-Ugrow-G0=b02489d329584fca03ad6347fc915997; YF-V5-G0=cd5d86283b86b0d5506628aedd6f8896e; WBtopGlobal_register_version=2b2691ab11833cbd; wb_view_log_5175311215=1920*10801; login_sid_t=6e3af2ce0d487e764cc21551bd64ee61; cross_origin_proto=SSL; WBStorage=e8781eb7dee3fd7f|undefined; wb_view_log=1920*10801; UOR=,,login.sina.com.cn; SSOLoginState=1534317576; SCF=An380Z_lJI4Er6AG9v5XU6ULsCZ8EswRdbTEUE52u4sKz4mbINDT8lL8v-FgZhuYiwV_2kWCPJORBMVpowD5Sl4.; SUB=_2A252d6RYDeRhGeNP7FcS8S_OyjmIHXVVBJKQrDV8PUNbmtAKLWz6kW9NTnyOtWRIAjuzxoDWBNFsiOTo4QS08kaw; SUBP=0033WrSXqPxfM6725Ws9jqgMF55529P9D9WWWbMZ3lYrAgORyMlPl1Ni15JpX5K2hUgL.Fo-pS0-0eK2EeK-2dJLoI74kPfYLxKqLBK5LB.SAIBtt; SUHB=0YhhvX2IYso1ka; ALF=1565853576; un=; wvr=6")
                .build();

        //插件式配置生成HttpClient时所需参数（超时、连接池、ssl、重试）
        HCB hcb                 = HCB.custom()
                .timeout(10000)         //超时
                .pool(100, 10)      //启用连接池，每个路由最大创建10个链接，总连接数限制为100个
                .sslpv(SSLs.SSLProtocolVersion.TLSv1_2)  //可设置ssl版本号，默认SSLv3，用于ssl，也可以调用sslpv("TLSv1.2")
                .ssl()                      //https，支持自定义ssl证书路径和密码，ssl(String keyStorePath, String keyStorepass)
                .retry(5)                   //重试5次
                ;

        HttpClient client = hcb.build();

        //插件式配置请求参数（网址、请求参数、编码、client）
        HttpConfig config = HttpConfig.custom()
                .headers(headers)   //设置headers，不需要时则无需设置
                .url(url)                   //设置请求的url
                .encoding("utf-8") //设置请求和返回编码，默认就是Charset.defaultCharset()
                .client(client);
        String result2 = HttpClientUtil.get(config);    //post请求
        return result2;
    }

    public List<String> getMid(String html){
        List<String> mid = new ArrayList<String>();
        String patt = "name=[0-9]{16}";
        Pattern r = Pattern.compile(patt);
        Matcher m = r.matcher(html);
        while (m.find()) {
            mid.add(m.group(0).replace("name=", ""));
        }
        return mid;
    }

    public void postwb(String mid) throws HttpProcessException{
        String url = "https://weibo.com/p/aj/v6/mblog/modifyvisible?ajwvr=6&domain=100505&__rnd="+System.currentTimeMillis();
        //插件式配置Header（各种header信息、自定义header）
        Header[] headers    = HttpHeader.custom()
                .host("weibo.com")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36")
                .referer("https://weibo.com/2875848794/profile?is_all=1&stat_date=201212")
                .contentType("application/x-www-form-urlencoded")
                .cookie("SINAGLOBAL=8635537446547.301.1507198819218; UM_distinctid=161cc50123d26d1fa-02fcbe6e2eda04-4a541326-1fa400-161cc50d26e510; un=1231233123; login_sid_t=2c011620a31237dd2c973a420f7bf48e4d8; YF-Ugrow-G0=ea90f703b7694b74b62d38420b5273df; YF-V5-G0=c99031715427fe982b79bf287ae448f6; WBStorage=e8781eb7dee3fd7f|undefined; wb_view_log=1920*10801; _s_tentry=-; Apache=9255634056765.951.1534307466102; ULV=1534307466128:24:3:1:9255634056765.951.1534307466102:1533893011406; SSOLoginState=1534307540; SCF=AhJ5a4l55u6Ym2tu3123rWNWWaszjwG-_9f_3HstU3vWd69GnSGQXqXDKe2GVorlKGqk-3Erg3lfS6XH6V9anNWwUPk.; SUB=_2A252d9yFDeRhGeRJ7lMV9C_OyjiIHXVVBUlNrDV8PUNbmtANLVmikW9NUpanwG_uVHqh5x7c1lnw8hThtgc8AgTY; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9W5D6Yv-_WaN-LU2mIXhSYxE5JpX5KzhUgL.FozNSK2XSh2EeKB2dJLoI0qLxK-L12eL1KMLxKBLB.2LB-eLxKqL1K.LB--LxKnLBoBLBKBLxKnL12-LBozLxKMLBKBLB.-t; SUHB=02M-sImmDLEWQ_; ALF=1565843540; wvr=6; YF-Page-G0=734c07cbfd1a4ed44f254d8b9173a162eb; wb_view_log_2751441214=1920*10801; UOR=cas.baidu.com,widget.weibo.com,login.sina.com.cn; cross_origin_proto=SSL")
                .build();

        //插件式配置生成HttpClient时所需参数（超时、连接池、ssl、重试）
        HCB hcb                 = HCB.custom()
                .timeout(10000)         //超时
                .pool(100, 10)      //启用连接池，每个路由最大创建10个链接，总连接数限制为100个
                .sslpv(SSLs.SSLProtocolVersion.TLSv1_2)  //可设置ssl版本号，默认SSLv3，用于ssl，也可以调用sslpv("TLSv1.2")
                .ssl()                      //https，支持自定义ssl证书路径和密码，ssl(String keyStorePath, String keyStorepass)
                .retry(5)                   //重试5次
                ;

        HttpClient client = hcb.build();
        System.out.println("----");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("visible", "1");
        map.put("mid", mid);
        map.put("_t", "0");

        //插件式配置请求参数（网址、请求参数、编码、client）
        HttpConfig config = HttpConfig.custom()
                .headers(headers)   //设置headers，不需要时则无需设置
                .url(url)                   //设置请求的url
                .map(map)           //设置请求参数，没有则无需设置
                .encoding("utf-8") //设置请求和返回编码，默认就是Charset.defaultCharset()
                .client(client) ;
        String result2 = HttpClientUtil.post(config);   //post请求
        System.out.println(result2);
    }
}
