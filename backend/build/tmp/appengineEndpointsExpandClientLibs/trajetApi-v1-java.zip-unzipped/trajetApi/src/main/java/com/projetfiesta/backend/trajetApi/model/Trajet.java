/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://github.com/google/apis-client-generator/
 * (build: 2016-07-08 17:28:43 UTC)
 * on 2016-09-03 at 09:59:11 UTC 
 * Modify at your own risk.
 */

package com.projetfiesta.backend.trajetApi.model;

/**
 * Model definition for Trajet.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the trajetApi. For a detailed explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Trajet extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long conducteurId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String destination;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long evenementId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String heureDepart;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer nombrePlaces;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getConducteurId() {
    return conducteurId;
  }

  /**
   * @param conducteurId conducteurId or {@code null} for none
   */
  public Trajet setConducteurId(java.lang.Long conducteurId) {
    this.conducteurId = conducteurId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getDestination() {
    return destination;
  }

  /**
   * @param destination destination or {@code null} for none
   */
  public Trajet setDestination(java.lang.String destination) {
    this.destination = destination;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getEvenementId() {
    return evenementId;
  }

  /**
   * @param evenementId evenementId or {@code null} for none
   */
  public Trajet setEvenementId(java.lang.Long evenementId) {
    this.evenementId = evenementId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getHeureDepart() {
    return heureDepart;
  }

  /**
   * @param heureDepart heureDepart or {@code null} for none
   */
  public Trajet setHeureDepart(java.lang.String heureDepart) {
    this.heureDepart = heureDepart;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getId() {
    return id;
  }

  /**
   * @param id id or {@code null} for none
   */
  public Trajet setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getNombrePlaces() {
    return nombrePlaces;
  }

  /**
   * @param nombrePlaces nombrePlaces or {@code null} for none
   */
  public Trajet setNombrePlaces(java.lang.Integer nombrePlaces) {
    this.nombrePlaces = nombrePlaces;
    return this;
  }

  @Override
  public Trajet set(String fieldName, Object value) {
    return (Trajet) super.set(fieldName, value);
  }

  @Override
  public Trajet clone() {
    return (Trajet) super.clone();
  }

}
