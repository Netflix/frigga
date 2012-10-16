package com.netflix.frigga;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that can deconstruct information about AWS Auto Scaling Groups, Load Balancers, Launch Configurations, and
 * Security Groups created by Asgard based on their name.
 */
public class Names implements NameConstants {

    private static final Pattern PUSH_PATTERN = Pattern.compile(
            "^([" + NAME_HYPHEN_CHARS + "]*)-(" + PUSH_FORMAT + ")$");
    private static final Pattern LABELED_VARS_PATTERN = Pattern.compile(
            "^([" + NAME_HYPHEN_CHARS + "]*?)((-" + LABELED_VARIABLE + ")*)$");
    private static final Pattern NAME_PATTERN = Pattern.compile(
            "^([" + NAME_CHARS + "]+)(?:-([" + NAME_CHARS + "]*))?(?:-([" + NAME_HYPHEN_CHARS + "]*?))?$");

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

        group = name;
        cluster = theCluster;
        push = hasPush ? pushMatcher.group(2) : null;
        String sequenceString = hasPush ? pushMatcher.group(3) : null;
        if (sequenceString != null) {
            sequence = Integer.parseInt(sequenceString);
        }

        String unlabeledVars = labeledVarsMatcher.group(1);
        String labeledVariables = labeledVarsMatcher.group(2);

        Matcher nameMatcher = NAME_PATTERN.matcher(unlabeledVars);
        nameMatcher.matches();
        app = nameMatcher.group(1);
        stack = checkEmpty(nameMatcher.group(2));
        detail = checkEmpty(nameMatcher.group(3));

        countries    = extractLabeledVariable(labeledVariables, COUNTRIES_KEY);
        devPhase     = extractLabeledVariable(labeledVariables, DEV_PHASE_KEY);
        hardware     = extractLabeledVariable(labeledVariables, HARDWARE_KEY);
        partners     = extractLabeledVariable(labeledVariables, PARTNERS_KEY);
        revision     = extractLabeledVariable(labeledVariables, REVISION_KEY);
        usedBy       = extractLabeledVariable(labeledVariables, USED_BY_KEY);
        redBlackSwap = extractLabeledVariable(labeledVariables, RED_BLACK_SWAP_KEY);
        zone         = extractLabeledVariable(labeledVariables, ZONE_KEY);
    }

    /**
     * Breaks down the name of an auto scaling group or load balancer into its component parts.
     *
     * @param name the name of an auto scaling group or load balancer
     * @return ClusterNames a data object containing the component parts of the compound name
     */
    public static Names parseName(String name) {
        return new Names(name);
    }

    private String extractLabeledVariable(String labeledVariablesString, String labelKey) {
        if (labeledVariablesString != null) {
            Pattern labelPattern = Pattern.compile(
                    ".*?-" + labelKey + LABELED_VAR_SEPARATOR + "([" + NAME_CHARS + "]*).*?$");
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

}
