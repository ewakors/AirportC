/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserviceclientauth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.handler.MessageContext;

import webserviceserverauth.HelloWorld;

public class HelloWorldClient {

    private static final String WS_URL = "http://localhost:8888/ws/helloAuth?wsdl";

    public static void main(String[] args) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Please enter user name and password: ");
        String username = null;
        String password = null;
        try {
            username = reader.readLine();
            password = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("You entered : " + username + " password: " + password);
        
        URL url = new URL(WS_URL);
        QName qname = new QName("http://webserviceserverauth/", "WebServiceServerAuthService");

        Service service = Service.create(url, qname);
        HelloWorld hello = service.getPort(HelloWorld.class);

        /**
         * *****************UserName & Password *****************************
         */
        Map<String, Object> req_ctx = ((BindingProvider) hello).getRequestContext();
        req_ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, WS_URL);

        Map<String, List<String>> headers = new HashMap<String, List<String>>();

        headers.put("Username", Collections.singletonList(username));
        headers.put("Password", Collections.singletonList(password));
        req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
        /**
         * *******************************************************************
         */

        System.out.println(hello.getHelloWorldAsString(username,password));
        System.out.println(hello.getUserInfo(username));
    }
}
