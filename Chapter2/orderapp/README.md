###The Order Processing Application

The objective of the Order Processing Application is to process a customer order. The order process functionality will generate the customer order, thereby making 
the order valid and approved. A typical scenario will be a customer making an order request to buy a particular item. The purchase department will receive the 
order request from the customer and prepare a formal purchase order. The purchase order will hold the details of the customer, the name of the item to be purchased, 
the quantity, and the price. Once the order is prepared, it will be sent to the Order Processing department for the necessary approval. If the order is valid and 
approved, then the department will generate the unique order ID and send it back to the Purchase department. The Purchase department will communicate the order ID 
back to the customer.

![](http://snag.gy/Hh3Ci.jpg)

how to create an Order Processing Web Service and then register it as a Spring bean using a JAX-WS frontend

We will use the Code-firstapproach, that is, we will first create a Java class and convert this into a web service component. The first set of tasks will be to 
create server-side components.

To achieve this, we typically perform the following steps: 

*	 Create a Service Endpoint Interface (SEI) and define a business method to be used with the web service. 
*	 Create the implementation class and annotate it as a web service. 
*	 Create beans.xmland define the service class as a Spring bean using a JAX-WS frontend.

###Creating a Service Endpoint Interface (SEI)

```java
package demo.order;
import javax.jws.WebService;
@WebService
public interface OrderProcess {
  @WebMethod
  String processOrder(OrderprocessOrder(Order order);
}
```

The following code shows the Orderclass:
```java
package demo.order;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "Order")
public class Order {
   private String customerID;
   private String itemID;
   private int qty;
   private double price;
   // Contructor
   public Order() {
   }
   public String getCustomerID() {
      return customerID;
   }
   public void setCustomerID(String customerID) {
      this.customerID = customerID;
   }
   public String getItemID() {
      return itemID;
   }
   public void setItemID(String itemID) {
      this.itemID = itemID;
   }
   public int getQty() {
      return qty;
   }
   public void setQty(int qty) {
      this.qty = qty;
   }
   public double getPrice() {
      return price;
   }
   public void setPrice(double price) {
      this.price = price;
   }
}
```  
The @XmlRootElementis part of the **Java Architecture for XML Binding (JAXB)** annotation library 

The following code illustrates the service implementation class OrderProcessImpl:

```java
@WebService
public class OrderProcessImpl implements OrderProcess {
    public String processOrder(Order order) {
      String orderID = validate(order);
        return orderID;
    }
   /**
    * Validates the order and returns the order ID
   **/
    private String validate(Order order) {
      String custID = order.getCustomerID();
      String itemID = order.getItemID();
      int qty = order.getQty();
      double price = order.getPrice();
      if (custID != null && itemID != null && !custID.equals("") 
                         && !itemID.equals("") && qty > 0 
                         && price > 0.0) {
         return "ORD1234";
      }
      return null;
   }
}
```

What makes CXF the obvious choice as a web service framework is its use of Spring-based configuration files to publish web service endpoints.

We will create a server side Spring-based configuration file and name it as beans.xml. The following code illustrates the beans.xmlconfiguration file:
```xml
<beans xmlns="http://www.springframework.org/schema/beans"
   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xmlns:jaxws="http://cxf.apache.org/jaxws"
   xsi:schemaLocation="
http://www.springframework.org/schema/beans 
http://www.springframework.org/schema/beans/spring-beans.xsd
http://cxf.apache.org/jaxws http://cxf.apache.org/schemas/jaxws.xsd">
   <import resource="classpath:META-INF/cxf/cxf.xml" /> 
   <import resource="classpath:META-INF/cxf/cxf-extension-soap.xml" />
   <import resource="classpath:META-INF/cxf/cxf-servlet.xml" /> 
   <jaxws:endpoint 
     id="orderProcess" 
     implementor="demo.order.OrderProcessImpl" 
     address="/OrderProcess" />
</beans>
```
The following code illustrates the client-beans.xml configuration file:

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xmlns:jaxws="http://cxf.apache.org/jaxws"
   xsi:schemaLocation="
http://www.springframework.org/schema/beans 
http://www.springframework.org/schema/beans/spring-beans.xsd
http://cxf.apache.org/jaxws http://cxf.apache.org/schemas/jaxws.xsd">
<jaxws:client id="orderClient" serviceClass=
                 "demo.order.OrderProcess" address=
                 "http://localhost:8080/orderapp/OrderProcess" />
</beans>
```

###Developing web service client code
We will now create a standalone Java class to invoke our OrderProcessweb service. 
The following code illustrates the client invocation of a web service method:

```java
public final class Client {
    public Client() {
    }
    public static void main(String args[]) throws Exception {
        // START SNIPPET: client
        ClassPathXmlApplicationContext context 
         = new ClassPathXmlApplicationContext(new String[] 
                  {"demo/order/client/client-beans.xml"});
        OrderProcess client = (OrderProcess) context.
                               getBean("orderClient");
      // Populate the Order bean
      Order order = new Order();
      order.setCustomerID("C001");
      order.setItemID("I001");
      order.setQty(100);
      order.setPrice(200.00);
      String orderID = client.processOrder(order);
      String message = (orderID == null) ? 
                          "Order not approved" : "Order approved; 
                           order ID is " + orderID;
      System.out.println(message);
      System.exit(0);
        
```


```java
public class Order {
   private String customerID;
   private String itemID;
   private int qty;
   private double price;
   // Contructor
   public Order() {
   }
   // Getter and setter methods for the above declared properties
}
```


The following code illustrates the web.xmlfile:

```xml
<web-app>
   <context-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>WEB-INF/beans.xml</param-value>
   </context-param>
   <listener>
      <listener-class>
         org.springframework.web.context.ContextLoaderListener
      </listener-class>
   </listener>
   <servlet>
      <servlet-name>CXFServlet</servlet-name>
      <display-name>CXF Servlet</display-name>
      <servlet-class>
         org.apache.cxf.transport.servlet.CXFServlet
      </servlet-class>
      <load-on-startup>1</load-on-startup>
   </servlet>
   <servlet-mapping>
      <servlet-name>CXFServlet</servlet-name>
      <url-pattern>/*</url-pattern>
   </servlet-mapping>
</web-app>
```

