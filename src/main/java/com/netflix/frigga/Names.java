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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that can deconstruct information about AWS Auto Scaling Groups, Load Balancers, Launch Configurations, and
 * Security Groups created by Asgard based on their name.
 */
public class Names {

    private static final Pattern PUSH_PATTERN = Pattern.compile(
            "^([" + NameConstants.NAME_HYPHEN_CHARS + "]*)-(" + NameConstants.PUSH_FORMAT + ")$");
    private static final Pattern LABELED_VARS_PATTERN = Pattern.compile(
            "^([" + NameConstants.NAME_HYPHEN_CHARS + "]*?)((-" + NameConstants.LABELED_VARIABLE + ")*)$");
    private static final Pattern NAME_PATTERN = Pattern.compile(
            "^([" + NameConstants.NAME_CHARS + "]+)(?:-([" + NameConstants.NAME_CHARS + "]*)(?:-(["
                    + NameConstants.NAME_HYPHEN_CHARS + "]*?))?)?$");
    private static final Pattern LABELED_COUNTRIES_PATTERN = createLabeledVariablePattern(NameConstants.COUNTRIES_KEY);
    private static final Pattern LABELED_DEV_PHASE_KEY_PATTERN = createLabeledVariablePattern(NameConstants.DEV_PHASE_KEY);
    private static final Pattern LABELED_HARDWARE_KEY_PATTERN = createLabeledVariablePattern(NameConstants.HARDWARE_KEY);
    private static final Pattern LABELED_PARTNERS_KEY_PATTERN = createLabeledVariablePattern(NameConstants.PARTNERS_KEY);
    private static final Pattern LABELED_REVISION_KEY_PATTERN = createLabeledVariablePattern(NameConstants.REVISION_KEY);
    private static final Pattern LABELED_USED_BY_KEY_PATTERN = createLabeledVariablePattern(NameConstants.USED_BY_KEY);
    private static final Pattern LABELED_RED_BLACK_SWAP_KEY_PATTERN = createLabeledVariablePattern(NameConstants.RED_BLACK_SWAP_KEY);
    private static final Pattern LABELED_ZONE_KEY_PATTERN = createLabeledVariablePattern(NameConstants.ZONE_KEY);

    private String group;
    private String cluster;
    private String app;
    private String stack;
    private String detail;
    private String push;
    private Integer sequence;
    private String countries;
    private String devPhase;
    private String hardware;
    private String partners;
    private String revision;
    private String usedBy;
    private String redBlackSwap;
    private String zone;

    protected Names(String name) {
        if (name == null || name.trim().isEmpty()) {
            return;
        }

        Matcher pushMatcher = PUSH_PATTERN.matcher(name);
        boolean hasPush = pushMatcher.matches();
        String theCluster = hasPush ? pushMatcher.group(1) : name;

        Matcher labeledVarsMatcher = LABELED_VARS_PATTERN.matcher(theCluster);
        boolean labeledAndUnlabeledMatches = labeledVarsMatcher.matches();
        if (!labeledAndUnlabeledMatches) {
            return;
        }

        String unlabeledVars = labeledVarsMatcher.group(1);
        String labeledVariables = labeledVarsMatcher.group(2);

        Matcher nameMatcher = NAME_PATTERN.matcher(unlabeledVars);
        if (!nameMatcher.matches()) {
            return;
        }

        group = name;
        cluster = theCluster;
        push = hasPush ? pushMatcher.group(2) : null;
        String sequenceString = hasPush ? pushMatcher.group(3) : null;
        if (sequenceString != null) {
            try {
                sequence = Integer.parseInt(sequenceString);
            } catch (NumberFormatException e) {
                // This is fine.
            }
        }
        app = nameMatcher.group(1);
        stack = checkEmpty(nameMatcher.group(2));
        detail = checkEmpty(nameMatcher.group(3));

        countries    = extractLabeledVariable(labeledVariables, LABELED_COUNTRIES_PATTERN);
        devPhase     = extractLabeledVariable(labeledVariables, LABELED_DEV_PHASE_KEY_PATTERN);
        hardware     = extractLabeledVariable(labeledVariables, LABELED_HARDWARE_KEY_PATTERN);
        partners     = extractLabeledVariable(labeledVariables, LABELED_PARTNERS_KEY_PATTERN);
        revision     = extractLabeledVariable(labeledVariables, LABELED_REVISION_KEY_PATTERN);
        usedBy       = extractLabeledVariable(labeledVariables, LABELED_USED_BY_KEY_PATTERN);
        redBlackSwap = extractLabeledVariable(labeledVariables, LABELED_RED_BLACK_SWAP_KEY_PATTERN);
        zone         = extractLabeledVariable(labeledVariables, LABELED_ZONE_KEY_PATTERN);
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

    private String extractLabeledVariable(String labeledVariablesString, Pattern labelPattern) {
        if (labeledVariablesString != null && !labeledVariablesString.isEmpty()) {
            Matcher labelMatcher = labelPattern.matcher(labeledVariablesString);
            boolean hasLabel = labelMatcher.matches();
            if (hasLabel) {
                return labelMatcher.group(1);
            }
        }
        return null;
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((app == null) ? 0 : app.hashCode());
        result = prime * result + ((cluster == null) ? 0 : cluster.hashCode());
        result = prime * result + ((countries == null) ? 0 : countries.hashCode());
        result = prime * result + ((detail == null) ? 0 : detail.hashCode());
        result = prime * result + ((devPhase == null) ? 0 : devPhase.hashCode());
        result = prime * result + ((group == null) ? 0 : group.hashCode());
        result = prime * result + ((hardware == null) ? 0 : hardware.hashCode());
        result = prime * result + ((partners == null) ? 0 : partners.hashCode());
        result = prime * result + ((push == null) ? 0 : push.hashCode());
        result = prime * result + ((redBlackSwap == null) ? 0 : redBlackSwap.hashCode());
        result = prime * result + ((revision == null) ? 0 : revision.hashCode());
        result = prime * result + ((sequence == null) ? 0 : sequence.hashCode());
        result = prime * result + ((stack == null) ? 0 : stack.hashCode());
        result = prime * result + ((usedBy == null) ? 0 : usedBy.hashCode());
        result = prime * result + ((zone == null) ? 0 : zone.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Names other = (Names) obj;
        if (app == null) {
            if (other.app != null)
                return false;
        } else if (!app.equals(other.app))
            return false;
        if (cluster == null) {
            if (other.cluster != null)
                return false;
        } else if (!cluster.equals(other.cluster))
            return false;
        if (countries == null) {
            if (other.countries != null)
                return false;
        } else if (!countries.equals(other.countries))
            return false;
        if (detail == null) {
            if (other.detail != null)
                return false;
        } else if (!detail.equals(other.detail))
            return false;
        if (devPhase == null) {
            if (other.devPhase != null)
                return false;
        } else if (!devPhase.equals(other.devPhase))
            return false;
        if (group == null) {
            if (other.group != null)
                return false;
        } else if (!group.equals(other.group))
            return false;
        if (hardware == null) {
            if (other.hardware != null)
                return false;
        } else if (!hardware.equals(other.hardware))
            return false;
        if (partners == null) {
            if (other.partners != null)
                return false;
        } else if (!partners.equals(other.partners))
            return false;
        if (push == null) {
            if (other.push != null)
                return false;
        } else if (!push.equals(other.push))
            return false;
        if (redBlackSwap == null) {
            if (other.redBlackSwap != null)
                return false;
        } else if (!redBlackSwap.equals(other.redBlackSwap))
            return false;
        if (revision == null) {
            if (other.revision != null)
                return false;
        } else if (!revision.equals(other.revision))
            return false;
        if (sequence == null) {
            if (other.sequence != null)
                return false;
        } else if (!sequence.equals(other.sequence))
            return false;
        if (stack == null) {
            if (other.stack != null)
                return false;
        } else if (!stack.equals(other.stack))
            return false;
        if (usedBy == null) {
            if (other.usedBy != null)
                return false;
        } else if (!usedBy.equals(other.usedBy))
            return false;
        if (zone == null) {
            if (other.zone != null)
                return false;
        } else if (!zone.equals(other.zone))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Names [group=" + group + ", cluster=" + cluster + ", app=" + app + ", stack=" + stack + ", detail="
                + detail + ", push=" + push + ", sequence=" + sequence + ", countries=" + countries + ", devPhase="
                + devPhase + ", hardware=" + hardware + ", partners=" + partners + ", revision=" + revision
                + ", usedBy=" + usedBy + ", redBlackSwap=" + redBlackSwap + ", zone=" + zone + "]";
    }

    private static Pattern createLabeledVariablePattern(String label) {
        return Pattern.compile(
            ".*?-" + label + NameConstants.LABELED_VAR_SEPARATOR + "(["
                + NameConstants.NAME_CHARS + "]*).*?$");
    }
}
