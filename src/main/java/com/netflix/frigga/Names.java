/**
 * Copyright 2012 Netflix, Inc.
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
package com.netflix.frigga;

import com.netflix.frigga.conventions.labeledvariables.LabeledVariables;
import com.netflix.frigga.conventions.labeledvariables.LabeledVariablesNamingConvention;
import com.netflix.frigga.conventions.labeledvariables.LabeledVariablesNamingResult;
import com.netflix.frigga.extensions.NamingConvention;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that can deconstruct information about AWS Auto Scaling Groups, Load Balancers, Launch Configurations, and
 * Security Groups created by Asgard based on their name.
 */
public class Names {

    private static final Pattern PUSH_PATTERN = Pattern.compile(
            "^([" + NameConstants.NAME_HYPHEN_CHARS + "]*)-(" + NameConstants.PUSH_FORMAT + ")$");
    private static final Pattern NAME_PATTERN = Pattern.compile(
            "^([" + NameConstants.NAME_CHARS + "]+)(?:-([" + NameConstants.NAME_CHARS + "]*)(?:-(["
                    + NameConstants.NAME_HYPHEN_CHARS + "]*?))?)?$");

    private static final LabeledVariablesNamingConvention LABELED_VARIABLES_CONVENTION = new LabeledVariablesNamingConvention();

    private final String group;
    private final String cluster;
    private final String app;
    private final String stack;
    private final String detail;
    private final String push;
    private final Integer sequence;
    private final AtomicReference<LabeledVariablesNamingResult> labeledVariables = new AtomicReference<>();

    protected Names(String name) {
        String group = null;
        String cluster = null;
        String app = null;
        String stack = null;
        String detail = null;
        String push = null;
        Integer sequence = null;
        if (name != null && !name.trim().isEmpty()) {
            Matcher pushMatcher = PUSH_PATTERN.matcher(name);
            boolean hasPush = pushMatcher.matches();
            String theCluster = hasPush ? pushMatcher.group(1) : name;

            Matcher nameMatcher = NAME_PATTERN.matcher(theCluster);
            if (nameMatcher.matches()) {
                group = name;
                cluster = theCluster;
                push = hasPush ? pushMatcher.group(2) : null;
                String sequenceString = hasPush ? pushMatcher.group(3) : null;
                if (sequenceString != null) {
                    sequence = Integer.parseInt(sequenceString);
                }
                app = nameMatcher.group(1);
                stack = checkEmpty(nameMatcher.group(2));
                detail = checkEmpty(nameMatcher.group(3));
            }
        }
        this.group = group;
        this.cluster = cluster;
        this.app = app;
        this.stack = stack;
        this.detail = detail;
        this.push = push;
        this.sequence = sequence;
    }

    /**
     * Breaks down the name of an auto scaling group, security group, or load balancer created by Asgard into its
     * component parts.
     *
     * @param name the name of an auto scaling group, security group, or load balancer
     * @return bean containing the component parts of the compound name
     */
    public static Names parseName(String name) {
        return new Names(name);
    }

    /**
     * Same as {@code parseName}, but validates the format of name before constructing
     * the returned Names, throwing if it's invalid.
     *
     * @param name the name of an infrastructure resource
     * @return bean containing the component parts of the compound name
     * @throws IllegalArgumentException if name is not valid
     */
    public static Names parseNameOrThrow(String name) {
        if (!isValidName(name)) {
            throw new IllegalArgumentException(String.format("Invalid name '%s'", name));
        }
        return new Names(name);
    }

    private String checkEmpty(String input) {
        return (input != null && !input.isEmpty()) ? input : null;
    }

    public String getGroup() {
        return group;
    }

    public String getCluster() {
        return cluster;
    }

    public String getApp() {
        return app;
    }

    public String getStack() {
        return stack;
    }

    public String getDetail() {
        return detail;
    }

    public String getPush() {
        return push;
    }

    public Integer getSequence() {
        return sequence;
    }

    public String getCountries() {
        return getLabeledVariable(LabeledVariables::getCountries);
    }

    public String getDevPhase() {
        return getLabeledVariable(LabeledVariables::getDevPhase);
    }

    public String getHardware() {
        return getLabeledVariable(LabeledVariables::getHardware);
    }

    public String getPartners() {
        return getLabeledVariable(LabeledVariables::getPartners);
    }

    public String getRevision() {
        return getLabeledVariable(LabeledVariables::getRevision);
    }

    public String getUsedBy() {
        return getLabeledVariable(LabeledVariables::getUsedBy);
    }

    public String getRedBlackSwap() {
        return getLabeledVariable(LabeledVariables::getRedBlackSwap);
    }

    public String getZone() {
        return getLabeledVariable(LabeledVariables::getZone);
    }

    private <T> T getLabeledVariable(Function<LabeledVariables, T> extractor) {
        LabeledVariablesNamingResult result = labeledVariables.get();
        if (result == null) {
            LabeledVariablesNamingResult applied = LABELED_VARIABLES_CONVENTION.extractNamingConvention(cluster);
            if (labeledVariables.compareAndSet(null, applied)) {
                result = applied;
            } else {
                result = labeledVariables.get();
            }
        }

        return result.getResult().map(extractor).orElse(null);
    }

    private static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }

        return PUSH_PATTERN.matcher(name).matches() || NAME_PATTERN.matcher(name).matches();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Names names = (Names) o;
        return Objects.equals(group, names.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group);
    }

    @Override
    public String toString() {
        return "Names{" +
            "group='" + group + '\'' +
            ", cluster='" + cluster + '\'' +
            ", app='" + app + '\'' +
            ", stack='" + stack + '\'' +
            ", detail='" + detail + '\'' +
            ", push='" + push + '\'' +
            ", sequence=" + sequence +
            '}';
    }
}
