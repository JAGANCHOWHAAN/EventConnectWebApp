/*
 * 
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: 2.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package com.eventconnect.svGoogleMapApi.model;

import java.util.Objects;
import java.util.Arrays;
import com.eventconnect.svGoogleMapApi.model.ResponseBounds;
import com.eventconnect.svGoogleMapApi.model.ResponseLocation;
import com.eventconnect.svGoogleMapApi.model.ResponseViewport;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
/**
 * ResponseGeometry
 */

public class ResponseGeometry {
  @JsonProperty("viewport")
  private ResponseViewport viewport = null;

  @JsonProperty("bounds")
  private ResponseBounds bounds = null;

  @JsonProperty("location")
  private ResponseLocation location = null;

  @JsonProperty("location_type")
  private String location_type = null;

  public ResponseGeometry viewport(ResponseViewport viewport) {
    this.viewport = viewport;
    return this;
  }

   /**
   * Get viewport
   * @return viewport
  **/
  public ResponseViewport getViewport() {
    return viewport;
  }

  public void setViewport(ResponseViewport viewport) {
    this.viewport = viewport;
  }

  public ResponseGeometry bounds(ResponseBounds bounds) {
    this.bounds = bounds;
    return this;
  }

   /**
   * Get bounds
   * @return bounds
  **/
  public ResponseBounds getBounds() {
    return bounds;
  }

  public void setBounds(ResponseBounds bounds) {
    this.bounds = bounds;
  }

  public ResponseGeometry location(ResponseLocation location) {
    this.location = location;
    return this;
  }

   /**
   * Get location
   * @return location
  **/
  public ResponseLocation getLocation() {
    return location;
  }

  public void setLocation(ResponseLocation location) {
    this.location = location;
  }

  public ResponseGeometry location_type(String location_type) {
    this.location_type = location_type;
    return this;
  }

   /**
   * Get location_type
   * @return location_type
  **/
  public String getLocationType() {
    return location_type;
  }

  public void setLocationType(String location_type) {
    this.location_type = location_type;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ResponseGeometry Response_geometry = (ResponseGeometry) o;
    return Objects.equals(this.viewport, Response_geometry.viewport) &&
        Objects.equals(this.bounds, Response_geometry.bounds) &&
        Objects.equals(this.location, Response_geometry.location) &&
        Objects.equals(this.location_type, Response_geometry.location_type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(viewport, bounds, location, location_type);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ResponseGeometry {\n");
    
    sb.append("    viewport: ").append(toIndentedString(viewport)).append("\n");
    sb.append("    bounds: ").append(toIndentedString(bounds)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    location_type: ").append(toIndentedString(location_type)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}