
package demo.order;

import javax.xml.ws.Endpoint;

/**
 * This class was generated by Apache CXF 2.2.2
 * Mon Oct 19 14:25:48 IST 2009
 * Generated source version: 2.2.2
 * 
 */
 
public class OrderProcess_OrderProcessPort_Server{

    protected OrderProcess_OrderProcessPort_Server() throws Exception {
        System.out.println("Starting Server");
        Object implementor = new OrderProcessImpl();
        String address = "http://localhost:8080/OrderProcess";
        Endpoint.publish(address, implementor);
    }
    
    public static void main(String args[]) throws Exception { 
        new OrderProcess_OrderProcessPort_Server();
        System.out.println("Server ready..."); 
        
        Thread.sleep(5 * 60 * 1000); 
        System.out.println("Server exiting");
        System.exit(0);
    }
}
