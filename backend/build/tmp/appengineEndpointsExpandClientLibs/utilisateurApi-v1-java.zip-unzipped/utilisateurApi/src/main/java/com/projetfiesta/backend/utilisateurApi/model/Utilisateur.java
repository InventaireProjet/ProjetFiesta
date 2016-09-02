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
 * on 2016-09-02 at 14:27:45 UTC 
 * Modify at your own risk.
 */

package com.projetfiesta.backend.utilisateurApi.model;

/**
 * Model definition for Utilisateur.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the utilisateurApi. For a detailed explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Utilisateur extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String dateNaissance;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String email;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String motDePasse;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String nom;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String prenom;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getDateNaissance() {
    return dateNaissance;
  }

  /**
   * @param dateNaissance dateNaissance or {@code null} for none
   */
  public Utilisateur setDateNaissance(java.lang.String dateNaissance) {
    this.dateNaissance = dateNaissance;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getEmail() {
    return email;
  }

  /**
   * @param email email or {@code null} for none
   */
  public Utilisateur setEmail(java.lang.String email) {
    this.email = email;
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
  public Utilisateur setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getMotDePasse() {
    return motDePasse;
  }

  /**
   * @param motDePasse motDePasse or {@code null} for none
   */
  public Utilisateur setMotDePasse(java.lang.String motDePasse) {
    this.motDePasse = motDePasse;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getNom() {
    return nom;
  }

  /**
   * @param nom nom or {@code null} for none
   */
  public Utilisateur setNom(java.lang.String nom) {
    this.nom = nom;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPrenom() {
    return prenom;
  }

  /**
   * @param prenom prenom or {@code null} for none
   */
  public Utilisateur setPrenom(java.lang.String prenom) {
    this.prenom = prenom;
    return this;
  }

  @Override
  public Utilisateur set(String fieldName, Object value) {
    return (Utilisateur) super.set(fieldName, value);
  }

  @Override
  public Utilisateur clone() {
    return (Utilisateur) super.clone();
  }

}
