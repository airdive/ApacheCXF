package demo.order;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.2.2
 * Mon Oct 19 14:25:48 IST 2009
 * Generated source version: 2.2.2
 * 
 */
 
@WebService(targetNamespace = "http://order.demo/", name = "OrderProcess")
@XmlSeeAlso({ObjectFactory.class})
public interface OrderProcess {

    @WebResult(name = "return", targetNamespace = "")
    @ResponseWrapper(localName = "processOrderResponse", targetNamespace = "http://order.demo/", className = "demo.order.ProcessOrderResponse")
    @RequestWrapper(localName = "processOrder", targetNamespace = "http://order.demo/", className = "demo.order.ProcessOrder")
    @WebMethod
    public java.lang.String processOrder(
        @WebParam(name = "arg0", targetNamespace = "")
        demo.order.Order arg0
    );
}
