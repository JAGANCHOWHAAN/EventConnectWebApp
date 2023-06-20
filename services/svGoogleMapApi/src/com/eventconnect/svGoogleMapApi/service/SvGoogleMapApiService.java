package com.eventconnect.svGoogleMapApi.service;


import com.eventconnect.svGoogleMapApi.model.*;
import com.eventconnect.svGoogleMapApi.model.RootResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.Object;
import org.springframework.util.MultiValueMap;
import feign.*;

public interface SvGoogleMapApiService {

  /**
   * 
   * 
    * @param address address (optional)
    * @param key key (optional)
   * @return RootResponse
   */
  @RequestLine("GET /api/geocode/json?address={address}&key={key}")
  @Headers({
    "Accept: application/json",  })
  RootResponse invoke(@Param("address") String address, @Param("key") String key);


    /**
     * 
     * 
     * Note, this is equivalent to the other <code>invoke</code> method,
     * but with the query parameters collected into a single Map parameter. This
     * is convenient for services with optional query parameters, especially when
     * used with the {@link InvokeQueryParams} class that allows for
     * building up this map in a fluent style.
     * @param queryParams Map of query parameters as name-value pairs
     *   <p>The following elements may be specified in the query map:</p>
     *   <ul>
     *   <li>address - address (optional)</li>
     *   <li>key - key (optional)</li>
     *   </ul>
     * @return RootResponse
     */
    @RequestLine("GET /api/geocode/json?address={address}&key={key}")
    @Headers({
    "Accept: application/json",    })
    RootResponse invoke
    (@QueryMap(encoded=true)
    MultiValueMap<String, String> queryParams);

}
