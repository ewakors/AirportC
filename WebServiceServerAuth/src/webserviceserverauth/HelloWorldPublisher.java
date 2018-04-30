/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserviceserverauth;

import javax.xml.ws.Endpoint;
import webserviceserverauth.WebServiceServerAuth;

public class HelloWorldPublisher {
    
    public static void main(String[] args) {
	   Endpoint.publish("http://localhost:8888/ws/helloAuth", new WebServiceServerAuth());
           System.out.println("Server ready");
    }
}
