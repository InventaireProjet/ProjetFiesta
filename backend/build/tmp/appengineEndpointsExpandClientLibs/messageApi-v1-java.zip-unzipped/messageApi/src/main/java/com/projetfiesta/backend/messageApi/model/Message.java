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
 * on 2016-09-02 at 14:27:47 UTC 
 * Modify at your own risk.
 */

package com.projetfiesta.backend.messageApi.model;

/**
 * Model definition for Message.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the messageApi. For a detailed explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Message extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String dateHeure;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String texte;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long trajetId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long utilisateurId;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getDateHeure() {
    return dateHeure;
  }

  /**
   * @param dateHeure dateHeure or {@code null} for none
   */
  public Message setDateHeure(java.lang.String dateHeure) {
    this.dateHeure = dateHeure;
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
  public Message setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getTexte() {
    return texte;
  }

  /**
   * @param texte texte or {@code null} for none
   */
  public Message setTexte(java.lang.String texte) {
    this.texte = texte;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getTrajetId() {
    return trajetId;
  }

  /**
   * @param trajetId trajetId or {@code null} for none
   */
  public Message setTrajetId(java.lang.Long trajetId) {
    this.trajetId = trajetId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getUtilisateurId() {
    return utilisateurId;
  }

  /**
   * @param utilisateurId utilisateurId or {@code null} for none
   */
  public Message setUtilisateurId(java.lang.Long utilisateurId) {
    this.utilisateurId = utilisateurId;
    return this;
  }

  @Override
  public Message set(String fieldName, Object value) {
    return (Message) super.set(fieldName, value);
  }

  @Override
  public Message clone() {
    return (Message) super.clone();
  }

}
