package com.netflix.frigga.cluster;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClusterNames {

    private static final String NAME_CHARS = "a-zA-Z0-9._";
    private static final String NAME_HYPHEN_CHARS = "-a-zA-Z0-9._";
    private static final String PUSH_FORMAT = "v([0-9]{3})";
    private static final String LABELED_VAR_SEPARATOR = "0";
    private static final String LABELED_VARIABLE = "[a-zA-Z][" + LABELED_VAR_SEPARATOR + "][a-zA-Z0-9]+";
    private static final Pattern PUSH_PATTERN = Pattern.compile(
            "^([" + NAME_HYPHEN_CHARS + "]*)-(" + PUSH_FORMAT + ")$");
    private static final Pattern LABELED_VARS_PATTERN = Pattern.compile(
            "^([" + NAME_HYPHEN_CHARS + "]*?)((-" + LABELED_VARIABLE + ")*)$");
    private static final Pattern NAME_PATTERN = Pattern.compile(
            "^([" + NAME_CHARS + "]+)(?:-([" + NAME_CHARS + "]*))?(?:-([" + NAME_HYPHEN_CHARS + "]*?))?$");

    private static final String COUNTRIES_KEY = "c";
    private static final String DEV_PHASE_KEY = "d";
    private static final String HARDWARE_KEY = "h";
    private static final String PARTNERS_KEY = "p";
    private static final String REVISION_KEY = "r";
    private static final String USED_BY_KEY = "u";
    private static final String RED_BLACK_SWAP_KEY = "w";
    private static final String ZONE_KEY = "z";

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

    public static String stackNameFromGroupName(String autoScalingGroupName) {
        ClusterNames names = dissectCompoundName(autoScalingGroupName);
        return names.stack != null ? names.stack : "";
    }

    public static String clusterFromGroupName(String autoScalingGroupName) {
        ClusterNames names = dissectCompoundName(autoScalingGroupName);
        return names.cluster != null ? names.cluster : "";
    }

    /**
     * Breaks down the name of an auto scaling group or load balancer into its component parts.
     *
     * @param asgName the name of an auto scaling group or load balancer
     * @return ClusterNames a data object containing the component parts of the compound name
     */
    public static ClusterNames dissectCompoundName(String asgName) {
        ClusterNames clusterNames = new ClusterNames();
        if (asgName == null || asgName.trim().isEmpty()) {
            return clusterNames;
        }

        clusterNames.group = asgName;
        Matcher pushMatcher = PUSH_PATTERN.matcher(asgName);
        boolean hasPush = pushMatcher.matches();
        clusterNames.cluster = hasPush ? pushMatcher.group(1) : asgName;
        clusterNames.push = hasPush ? pushMatcher.group(2) : null;
        String sequenceString = hasPush ? pushMatcher.group(3) : null;
        if (sequenceString != null) {
            clusterNames.sequence = Integer.parseInt(sequenceString);
        }

        Matcher labeledVarsMatcher = LABELED_VARS_PATTERN.matcher(clusterNames.cluster);
        boolean labeledAndUnlabeledMatches = labeledVarsMatcher.matches();
        if (!labeledAndUnlabeledMatches) {
            return new ClusterNames();
        }
        String unlabeledVars = labeledVarsMatcher.group(1);
        String labeledVariables = labeledVarsMatcher.group(2);

        Matcher nameMatcher = NAME_PATTERN.matcher(unlabeledVars);
        nameMatcher.matches();
        clusterNames.app = nameMatcher.group(1);
        clusterNames.stack = checkEmpty(nameMatcher.group(2));
        clusterNames.detail = checkEmpty(nameMatcher.group(3));

        clusterNames.countries    = extractLabeledVariable(labeledVariables, COUNTRIES_KEY);
        clusterNames.devPhase     = extractLabeledVariable(labeledVariables, DEV_PHASE_KEY);
        clusterNames.hardware     = extractLabeledVariable(labeledVariables, HARDWARE_KEY);
        clusterNames.partners     = extractLabeledVariable(labeledVariables, PARTNERS_KEY);
        clusterNames.revision     = extractLabeledVariable(labeledVariables, REVISION_KEY);
        clusterNames.usedBy       = extractLabeledVariable(labeledVariables, USED_BY_KEY);
        clusterNames.redBlackSwap = extractLabeledVariable(labeledVariables, RED_BLACK_SWAP_KEY);
        clusterNames.zone         = extractLabeledVariable(labeledVariables, ZONE_KEY);
        return clusterNames;
    }

    private static String extractLabeledVariable(String labeledVariablesString, String labelKey) {
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

    private static String checkEmpty(String input) {
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
