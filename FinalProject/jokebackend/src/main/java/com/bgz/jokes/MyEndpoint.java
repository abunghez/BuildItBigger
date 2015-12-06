/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.bgz.jokes;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "jokesAPI",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "jokes.bgz.com",
                ownerName = "jokes.bgz.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    BoredomRepellent br;
    /**
     * A simple endpoint method that takes a name and says Hi back
     */

    public MyEndpoint() {
        br = new BoredomRepellent();
    }
    @ApiMethod(name = "getJoke")
    public MyBean getJoke() {
        MyBean response = new MyBean();
        response.setData(br.getNextJoke());
        return response;
    }

}
