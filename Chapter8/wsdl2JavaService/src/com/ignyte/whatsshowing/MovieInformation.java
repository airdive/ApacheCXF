
/*
 * 
 */

package com.ignyte.whatsshowing;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.2.2
 * Wed Oct 28 16:41:36 IST 2009
 * Generated source version: 2.2.2
 * 
 */


@WebServiceClient(name = "MovieInformation", 
                  wsdlLocation = "http://www.ignyte.com/webservices/ignyte.whatsshowing.webservice/moviefunctions.asmx?wsdl",
                  targetNamespace = "http://www.ignyte.com/whatsshowing") 
public class MovieInformation extends Service {

    public final static URL WSDL_LOCATION;
    public final static QName SERVICE = new QName("http://www.ignyte.com/whatsshowing", "MovieInformation");
    public final static QName MovieInformationSoap = new QName("http://www.ignyte.com/whatsshowing", "MovieInformationSoap");
    public final static QName MovieInformationSoap12 = new QName("http://www.ignyte.com/whatsshowing", "MovieInformationSoap12");
    static {
        URL url = null;
        try {
            url = new URL("http://www.ignyte.com/webservices/ignyte.whatsshowing.webservice/moviefunctions.asmx?wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from http://www.ignyte.com/webservices/ignyte.whatsshowing.webservice/moviefunctions.asmx?wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public MovieInformation(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public MovieInformation(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public MovieInformation() {
        super(WSDL_LOCATION, SERVICE);
    }

    /**
     * 
     * @return
     *     returns MovieInformationSoap
     */
    @WebEndpoint(name = "MovieInformationSoap")
    public MovieInformationSoap getMovieInformationSoap() {
        return super.getPort(MovieInformationSoap, MovieInformationSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns MovieInformationSoap
     */
    @WebEndpoint(name = "MovieInformationSoap")
    public MovieInformationSoap getMovieInformationSoap(WebServiceFeature... features) {
        return super.getPort(MovieInformationSoap, MovieInformationSoap.class, features);
    }
    /**
     * 
     * @return
     *     returns MovieInformationSoap
     */
    @WebEndpoint(name = "MovieInformationSoap12")
    public MovieInformationSoap getMovieInformationSoap12() {
        return super.getPort(MovieInformationSoap12, MovieInformationSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns MovieInformationSoap
     */
    @WebEndpoint(name = "MovieInformationSoap12")
    public MovieInformationSoap getMovieInformationSoap12(WebServiceFeature... features) {
        return super.getPort(MovieInformationSoap12, MovieInformationSoap.class, features);
    }

}
