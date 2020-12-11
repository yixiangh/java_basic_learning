package com.example.utils;


import javax.net.ssl.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * SSL信任管理器
 * 1.https是对链接加了安全证书SSL的，如果服务器中没有相关链接的SSL证书，它就不能够信任那个链接，也就不会访问到了。
 * 所以我们第一步是自定义一个信任管理器。只要实现自带的X509TrustManager接口就可以了
 * 2.可以看到里面的方法都是空的，当方法为空是默认为所有的链接都为安全，也就是所有的链接都能够访问到。
 * 当然这样有一定的安全风险，可以根据实际需要写入内容。
 * @Author: HYX
 * @Date: 2020/12/9 15:29
 */
public class X509TrustManagerUtil implements X509TrustManager {

    /**
     * 该方法检查客户端的证书，若不信任该证书则抛出异常。由于我们不需要对客户端进行认证，
     * 因此我们只需要执行默认的信任管理器的这个方法。JSSE中，默认的信任管理器类为TrustManager。
     */
    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        System.out.println(s);
    }

    /**
     * 该方法检查服务器的证书，若不信任该证书同样抛出异常。通过自己实现该方法，可以使之信任我们指定的任何证书。
     * 在实现该方法时，也可以简单的不做任何处理，即一个空的函数体，由于不会抛出异常，它就会信任任何证书。
     */
    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        System.out.println(s);
    }

    /**
     * 返回受信任的X509证书数组
     */
    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}
