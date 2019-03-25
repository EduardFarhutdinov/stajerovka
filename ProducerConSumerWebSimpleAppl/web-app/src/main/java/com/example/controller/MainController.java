package com.example.controller;

import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.ResourceBundle;

public class MainController implements Initializable  {

    @FXML
    private WebView webView;


    @FXML
    private Button loadPageButton;

    @FXML
    void loadPageButtonAction(ActionEvent event) {
        final String url = "https://localhost:8443/";
        WebEngine webEngine = webView.getEngine();
//        webEngine.load(url);
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };

// Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (GeneralSecurityException e) {
        }
// Now you can access an https URL without having the certificate in the truststore
        try {
            URL url1 = new URL("https://hostname/index.html");
        } catch (MalformedURLException e) {
        }
//now you can load the content:

        webEngine.setUserStyleSheetLocation(getClass().getResource("/css/style.css").toString());
        webEngine.load(url);

        System.out.println("fdsfs");

    }




    public void initialize(URL location, ResourceBundle resources) {

    }
}
