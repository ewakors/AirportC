/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserviceserverauth;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

//Service Implementation Bean
@WebService(endpointInterface = "webserviceserverauth.HelloWorld")
public class WebServiceServerAuth implements HelloWorld{

  @Resource
    WebServiceContext wsctx;

    @Override
    public String getHelloWorldAsString(String user, String pswd) {
		
	MessageContext mctx = wsctx.getMessageContext();
		
	//get detail from request headers
        Map http_headers = (Map) mctx.get(MessageContext.HTTP_REQUEST_HEADERS);
        List userList = (List) http_headers.get("Username");
        List passList = (List) http_headers.get("Password");

        String username = user;
        String password = pswd;
        
        if(userList!=null){
        	//get username
        	username = userList.get(0).toString();
        }
        	
        if(passList!=null){
        	//get password
        	password = passList.get(0).toString();
        }

        //Should validate username and password with database
        if (username.equals("username") && password.equals("password")){
        	return "Hello World JAX-WS - Valid User!";
        }else{
            return "Error!!!!";
        	
        } 
    }	

    @Override
    public String getUserInfo(String username) throws InvalidInputException {
         String output = "";

        if (username.equals("username")){
            output = "Username: " + username;
        }else{
            throw new InvalidInputException("Złe dane!!!!! ", username);      	
        } 
        
        return output;
    }
}
