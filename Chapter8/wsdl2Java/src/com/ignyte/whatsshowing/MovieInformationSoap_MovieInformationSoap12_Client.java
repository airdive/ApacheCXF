
package com.ignyte.whatsshowing;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.2.2
 * Mon Oct 19 00:35:46 IST 2009
 * Generated source version: 2.2.2
 * 
 */

public final class MovieInformationSoap_MovieInformationSoap12_Client {

    private static final QName SERVICE_NAME = new QName("http://www.ignyte.com/whatsshowing", "MovieInformation");

    private MovieInformationSoap_MovieInformationSoap12_Client() {
    }

    public static void main(String args[]) throws Exception {
        URL wsdlURL = MovieInformation.WSDL_LOCATION;
        if (args.length > 0) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        MovieInformation ss = new MovieInformation(wsdlURL, SERVICE_NAME);
        MovieInformationSoap port = ss.getMovieInformationSoap12();  
        
        {
        System.out.println("Invoking getUpcomingMovies...");
        int _getUpcomingMovies_month = 0;
        int _getUpcomingMovies_year = 0;
        com.ignyte.whatsshowing.ArrayOfUpcomingMovie _getUpcomingMovies__return = port.getUpcomingMovies(_getUpcomingMovies_month, _getUpcomingMovies_year);
        System.out.println("getUpcomingMovies.result=" + _getUpcomingMovies__return);


        }
        {
        System.out.println("Invoking getTheatersAndMovies...");
        java.lang.String _getTheatersAndMovies_zipCode = "";
        int _getTheatersAndMovies_radius = 0;
        com.ignyte.whatsshowing.ArrayOfTheater _getTheatersAndMovies__return = port.getTheatersAndMovies(_getTheatersAndMovies_zipCode, _getTheatersAndMovies_radius);
        System.out.println("getTheatersAndMovies.result=" + _getTheatersAndMovies__return);


        }

        System.exit(0);
    }

}
