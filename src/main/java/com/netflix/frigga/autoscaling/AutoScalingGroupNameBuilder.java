package com.netflix.frigga.autoscaling;

import com.netflix.frigga.NameBuilder;
import com.netflix.frigga.NameConstants;
import com.netflix.frigga.NameValidation;

public class AutoScalingGroupNameBuilder extends NameBuilder implements NameConstants {

    private String appName;
    private String stack;
    private String detail;
    private String countries;
    private String devPhase;
    private String hardware;
    private String partners;
    private String revision;
    private String usedBy;
    private String redBlackSwap;
    private String zoneVar;

    public String buildGroupName() {
        return buildGroupName(false);
    }

    public String buildGroupName(Boolean doValidation) {
        NameValidation.notEmpty(appName, "appName");

        if (doValidation) {
            validateNames(appName, stack, countries, devPhase, hardware, partners, revision, usedBy, redBlackSwap, zoneVar);
            if (detail != null && !detail.isEmpty() && !NameValidation.checkDetail(detail)) {
                throw new IllegalArgumentException("(Use alphanumeric characters only)");
            }
        }

        // Build the labeled variables for the end of the group name.
        String labeledVars = "";
        labeledVars += generateIfSpecified(COUNTRIES_KEY, countries);
        labeledVars += generateIfSpecified(DEV_PHASE_KEY, devPhase);
        labeledVars += generateIfSpecified(HARDWARE_KEY, hardware);
        labeledVars += generateIfSpecified(PARTNERS_KEY, partners);
        labeledVars += generateIfSpecified(REVISION_KEY, revision);
        labeledVars += generateIfSpecified(USED_BY_KEY, usedBy);
        labeledVars += generateIfSpecified(RED_BLACK_SWAP_KEY, redBlackSwap);
        labeledVars += generateIfSpecified(ZONE_KEY, zoneVar);

        String result = combineAppStackDetail(appName, stack, detail) + labeledVars;

        return result;
    }

    private static void validateNames(String... names) {
        for (String name : names) {
            if (name != null && !name.isEmpty() && !NameValidation.checkName(name)) {
                throw new IllegalArgumentException("(Use alphanumeric characters only)");
            }
        }
    }

    private static String generateIfSpecified(String key, String value) {
        if (value != null && !value.isEmpty()) {
            return "-" + key + LABELED_VAR_SEPARATOR + value;
        }
        return "";
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getStack() {
        return stack;
    }

    public void setStack(String stack) {
        this.stack = stack;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCountries() {
        return countries;
    }

    public void setCountries(String countries) {
        this.countries = countries;
    }

    public String getDevPhase() {
        return devPhase;
    }

    public void setDevPhase(String devPhase) {
        this.devPhase = devPhase;
    }

    public String getHardware() {
        return hardware;
    }

    public void setHardware(String hardware) {
        this.hardware = hardware;
    }

    public String getPartners() {
        return partners;
    }

    public void setPartners(String partners) {
        this.partners = partners;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public String getUsedBy() {
        return usedBy;
    }

    public void setUsedBy(String usedBy) {
        this.usedBy = usedBy;
    }

    public String getRedBlackSwap() {
        return redBlackSwap;
    }

    public void setRedBlackSwap(String redBlackSwap) {
        this.redBlackSwap = redBlackSwap;
    }

    public String getZoneVar() {
        return zoneVar;
    }

    public void setZoneVar(String zoneVar) {
        this.zoneVar = zoneVar;
    }

}
