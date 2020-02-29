/**
 * Copyright 2020 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.netflix.frigga.conventions.labeledvariables;

import java.util.Objects;

/**
 * The legacy labeled variables that can be encoded in the details component of a name.
 */
public class LabeledVariables {
  private final String countries;
  private final String devPhase;
  private final String hardware;
  private final String partners;
  private final String revision;
  private final String usedBy;
  private final String redBlackSwap;
  private final String zone;

  public LabeledVariables(String countries, String devPhase, String hardware, String partners, String revision, String usedBy, String redBlackSwap, String zone) {
    this.countries = countries;
    this.devPhase = devPhase;
    this.hardware = hardware;
    this.partners = partners;
    this.revision = revision;
    this.usedBy = usedBy;
    this.redBlackSwap = redBlackSwap;
    this.zone = zone;
  }

  public String getCountries() {
    return countries;
  }

  public String getDevPhase() {
    return devPhase;
  }

  public String getHardware() {
    return hardware;
  }

  public String getPartners() {
    return partners;
  }

  public String getRevision() {
    return revision;
  }

  public String getUsedBy() {
    return usedBy;
  }

  public String getRedBlackSwap() {
    return redBlackSwap;
  }

  public String getZone() {
    return zone;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    LabeledVariables that = (LabeledVariables) o;
    return Objects.equals(countries, that.countries) &&
        Objects.equals(devPhase, that.devPhase) &&
        Objects.equals(hardware, that.hardware) &&
        Objects.equals(partners, that.partners) &&
        Objects.equals(revision, that.revision) &&
        Objects.equals(usedBy, that.usedBy) &&
        Objects.equals(redBlackSwap, that.redBlackSwap) &&
        Objects.equals(zone, that.zone);
  }

  @Override
  public int hashCode() {
    return Objects.hash(countries, devPhase, hardware, partners, revision, usedBy, redBlackSwap, zone);
  }

  @Override
  public String toString() {
    return "LabeledVariables{" +
        "countries='" + countries + '\'' +
        ", devPhase='" + devPhase + '\'' +
        ", hardware='" + hardware + '\'' +
        ", partners='" + partners + '\'' +
        ", revision='" + revision + '\'' +
        ", usedBy='" + usedBy + '\'' +
        ", redBlackSwap='" + redBlackSwap + '\'' +
        ", zone='" + zone + '\'' +
        '}';
  }
}
