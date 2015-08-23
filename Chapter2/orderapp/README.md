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

##CXF architecture

The architecture of CXF is built upon the following components:
*	 Bus
*	 Frontend 
*	 Messaging and Interceptors
*	 Service Model
*	 Data bindings
*	 Protocol bindings
*	 Transport

![](http://snag.gy/64QQq.jpg)

Bus
is the backbone of the CXF architecture. The CXF bus is comprised of a Spring-based configuration file, namely, cxf.xmlwhich is loaded upon servlet initialization through SpringBusFactory. It defines a common context for all the endpoints. It wires all the runtime infrastructure components and provides a common application context. The SpringBusFactoryscans and loads the relevant configuration files in the META-INF/cxf directory placed in the classpath and accordingly builds the application context. It builds the application context from the following files:

*	 META-INF/cxf/cxf.xml
*	 META-INF/cxf/cxf-extension.xml
*	 META-INF/cxf/cxf-property-editors.xml

The following XML fragment shows the bus definition in the cxf.xml file.

<bean id="cxf" class="org.apache.cxf.bus.CXFBusImpl" />

The core bus component is CXFBusImpl. The class acts more as an interceptor  provider for incoming and outgoing requests to a web service endpoint. 
The cxf.xmlfile also defines other infrastructure components such as BindingFactoryManager, ConduitFactoryManager, and so on. These components are made available as bus extensions. One can access these infrastructure objects using the getExtensionmethod. These infrastructure components are registered so as to get and update various service endpoint level parameters such as service binding, transport protocol, conduits, and so on.

CXF bus architecture can be overridden, but one must apply caution when overriding the default bus behavior. Since the bus is the core component that loads the CXF runtime, many shared objects are also loaded as part of this runtime. You want to make sure that these objects are loaded when overriding the existing bus implementation.

You can extend the default bus to include your own custom components or service objects such as factory managers. You can also add interceptors to the bus bean. 

These interceptors defined at the bus level are available to all the endpoints. The following code shows how to create a custom bus:

SpringBeanFactory.createBus("mycxf.xml")

The following code illustrates the use of interceptors at the bus level:
```xml
<bean id="cxf" class="org.apache.cxf.bus.spring.SpringBusImpl">
    <property name="outInterceptors">
           <list>
              <ref bean="myLoggingInterceptor"/>
           </list>
        </property>
</bean>
<bean id="myLogHandler" class="org.mycompany.com.cxf.logging.
                               LoggingInterceptor">
    ...
</bean>
```
###Frontend

CXF provides the concept of frontend modeling, which lets you create web services using different frontend APIs. The APIs let you create a web service using simple factory beans and JAX-WS implementation. It also lets you create dynamic web 
service clients. The primary frontend supported by CXF is JAX-WS. 

###JAX-WS

is a specification that establishes the semantics to develop, publish, and consume web services. JAX-WS simplifies web service development. It defines Java-based APIs that ease the development and deployment of web services. 

The specification supports WS-Basic Profile 1.1 that addresses web service interoperability. It effectively means a web service can be invoked or consumed by a client written in any language. JAX-WS also defines standards such as JAXBand 
SAAJ. CXF provides support for complete JAX-WS stack.

JAXB provides data binding capabilities by providing a convenient way to map XML schema to a representation in Java code. The JAXB shields the conversion of XML schema messages in SOAP messages to Java code without the developers seeing XML and SOAP parsing. JAXB specification defines the binding between Java and XML Schema. SAAJprovides a standard way of dealing with XML attachments contained in a SOAP message.

JAX-WS also speeds up web service development by providing a library of annotations to turn Plain Old Java classes into web services and specifies a detailed mapping from a service defined in WSDL to the Java classes that will implement that 
service. Any complex types defined in WSDL are mapped into Java classes following the mapping defined by the JAXB specification.

As discussed earlier, two approaches for web service development exist: Code-First and Contract-First. With JAX-WS, you can perform web service development using one of the said approaches, depending on the nature of the application.

With the Code-first approach, you start by developing a Java class and interface and annotating the same as a web service. The approach is particularly useful where Java implementations are already available and you need to expose implementations 
as services.

You typically create a Service Endpoint Interface (SEI) that defines the service methods and the implementation class that implements the SEI methods. The consumer of a web service uses SEI to invoke the service functions. The SEI directly 
corresponds to a wsdl:portTypeelement. The methods defined by SEI correspond to the wsdl:operation element. 
```java
@WebService
public interface OrderProcess {
    String processOrder(Order order);
}
```

JAX-WS makes use of annotations to convert an SEI or a Java class to a web service. In the above example, the @WebServiceannotation defined above the interface declaration signifies an interface as a web service interface or Service 
Endpoint Interface.

In the Contract-first approach, you start with the existing WSDL contract, and generate Java class to implement the service. The advantage is that you are sure about what to expose as a service since you define the appropriate WSDL Contract-first. Again the contract definitions can be made consistent with respect to data types so that it can be easily converted in Java objects without any portability issue.

WSDL contains different elements that can be directly mapped to a Java class that implements the service. For example, the wsdl:portTypeelement is directly mapped to SEI, type elements are mapped to Java class types through the use of Java 
Architecture of XML Binding (JAXB), and the wsdl:serviceelement is mapped to a Java class that is used by a consumer to access the web service.

The WSDL2Javatool can be used to generate a web service from WSDL. It has various options to generate SEI and the implementation web service class. As a developer, you need to provide the method implementation for the generated class. If the WSDL includes custom XML Schema types, then the same is converted into its equivalent Java class.

###Simple frontend

 The 
simple frontend uses factory components to create a service and the client. It does so by using Java reflection API. 

The following code shows a web service created using simple frontend:

```java
// Build and publish the service
OrderProcessImpl orderProcessImpl = new OrderProcessImpl();
ServerFactoryBean svrFactory = new ServerFactoryBean();
svrFactory.setServiceClass(OrderProcess.class);
svrFactory.setAddress("http://localhost:8080/OrderProcess");
svrFactory.setServiceBean(orderProcessImpl);
svrFactory.create();
```

###Messaging and Interceptors

One of the important elements of CXF architecture is the Interceptor components. Interceptors are components that intercept the messages exchanged or passed between web service clients and server components. In CXF, this is implemented 
through the concept of Interceptor chains. The concept of Interceptor chaining is the core functionality of CXF runtime. 

Each interceptor in a chain is configurable, and the user has the ability to control its execution.

![](http://snag.gy/KfJCZ.jpg)

The core of the framework is the Interceptor interface. It defines two abstract methods—handleMessageand handleFault. Each of the methods takes the object of type Messageas a parameter.

The handleFaultmethod is implemented to handle the error condition. Interceptors are usually processed in chains with every 
interceptor in the chain performing some processing on the message in sequence, and the chain moves forward. Whenever an error condition arises, a handleFault method is invoked on each interceptor, and the chain unwinds or moves backwards.
Interceptors are often organized or grouped into phases. Interceptors providing common functionality can be grouped into one phase. Each phase performs specific message processing. Each phase is then added to the interceptor chain. The chain, 
therefore, is a list of ordered interceptor phases. The chain can be created for both inbound and outbound messages. A typical web service endpoint will have three interceptor chains:

*	 Inbound messages chain
*	 Outbound messages chain
*	 Error messages chain

There are built-in interceptors such as logging, security, and so on, and the developers can also choose to create custom interceptors.

###Service model

The Service model, in a true sense, models your service. It is a framework of components that represents a service in a WSDL-like model. It provides functionality to create various WSDL elements such as operations, bindings, endpoints, 
schema, and so on. The following figure shows the various components that form the Service model:

![](http://snag.gy/lTRTM.jpg)

The service model's primary component is ServiceInfowhich aggregates other related components that make up the complete service model. ServiceInfois comprised of the following components that more or less represent WSDL elements:
 
*	 InterfaceInfo
*	 OperationInfo
*	 MessageInfo
*	 BindingInfo
*	 EndpointInfo

A web service is usually created using one of the frontends offered by CXF. It can be either constructed from a Java class or from a WSDL. CXF frontends internally use the service model to create web services. For example, by using a simple frontend, we can create, publish, and consume web services through factory components such as ServerFactoryBeanand  ClientProxyFactoryBean. These factory classes internally use the service model of CXF.

###Data binding

Data binding components perform mapping between Java objects and XML elements, this mapping for you. CXF supports two types of data binding components—JAXBand Aegis. CXF uses JAXB as the default data binding component. As a developer, you have the choice of specifying the binding discipline through a configuration file or API. If no binding is specified, then JAXB is taken as a default binding discipline. The latest version of CXF uses JAXB 2.1. JAXB uses annotations to define the mapping between Java objects and XML.

CXF also supports the Aegis data binding component to map between Java objects and XML. Aegis allows developers to gain control of data binding through its flexible mapping system. You do not have to rely on annotations to devise the mapping. Your Java code is clean and simple POJO. 

Aegis also supports some annotations that can be used to devise binding. Some of the annotations that can be used with Aegis are:

•	 XmlAttribute
•	 XmlElement
•	 XmlParamType
•	 XmlReturnType
•	 XmlType

In Aegis, you define the data mapping in a file called <MyJavaObject>.aegis.xml, where MyJavaObjectis the object that you are trying to map with XML. Aegis reads this XML to perform the necessary binding. Aegis also uses reflection to derive the 
mapping between Java object and XML. The following code fragment shows the sample Aegis mapping file: 

```xml
<?xml version="1.0" encoding="UTF-8"?>
<mappings>
    <mapping name="HelloWorld">
        <method name="sayHi">
            <parameter index="0" mappedName="greeting" nillable='false' />
        </method>
    </mapping>
</mappings>
```

The above XML fragment states that a string parameter of a method named sayHi of the bean HelloWorldshould be mapped to a name as greeting. You can configure your web service to use Aegis data binding as follows:

```xml
<jaxws:endpoint id="orderProcess" implementor="demo.order.OrderProcessImpl" address="/OrderProcess" >
   <jaxws:dataBinding>
    <bean class="org.apache.cxf.aegis.databinding.AegisDatabinding" />
   </jaxws:dataBinding>
</jaxws:endpoint>
```
