package com.netflix.frigga.conventions.labeledvariables;

import com.netflix.frigga.NameConstants;
import com.netflix.frigga.extensions.NamingConvention;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A NamingConvention that applies the legacy labeled variables convention against a name.
 *
 * This logic was extracted out of Names (and referenced from there for backwards-ish compatibility).
 */
public class LabeledVariablesNamingConvention implements NamingConvention<LabeledVariablesNamingResult> {
  private static final Pattern LABELED_VARS_PATTERN = Pattern.compile(
      "(.*?)((?:(?:^|-)" +NameConstants.LABELED_VARIABLE + ")+)$");

  //--------------------------
  //VisibleForTesting:
  static final Pattern LABELED_COUNTRIES_PATTERN = createLabeledVariablePattern(NameConstants.COUNTRIES_KEY);
  static final Pattern LABELED_DEV_PHASE_KEY_PATTERN = createLabeledVariablePattern(NameConstants.DEV_PHASE_KEY);
  static final Pattern LABELED_HARDWARE_KEY_PATTERN = createLabeledVariablePattern(NameConstants.HARDWARE_KEY);
  static final Pattern LABELED_PARTNERS_KEY_PATTERN = createLabeledVariablePattern(NameConstants.PARTNERS_KEY);
  static final Pattern LABELED_REVISION_KEY_PATTERN = createLabeledVariablePattern(NameConstants.REVISION_KEY);
  static final Pattern LABELED_USED_BY_KEY_PATTERN = createLabeledVariablePattern(NameConstants.USED_BY_KEY);
  static final Pattern LABELED_RED_BLACK_SWAP_KEY_PATTERN = createLabeledVariablePattern(NameConstants.RED_BLACK_SWAP_KEY);
  static final Pattern LABELED_ZONE_KEY_PATTERN = createLabeledVariablePattern(NameConstants.ZONE_KEY);
  //--------------------------

  @Override
  public LabeledVariablesNamingResult extractNamingConvention(String nameComponent) {
    if (nameComponent == null || nameComponent.isEmpty()) {
      return LabeledVariablesNamingResult.EMPTY;
    }

    Matcher labeledVarsMatcher = LABELED_VARS_PATTERN.matcher(nameComponent);
    boolean labeledAndUnlabeledMatches = labeledVarsMatcher.matches();
    if (!labeledAndUnlabeledMatches) {
      return LabeledVariablesNamingResult.EMPTY;
    }

    String unprocessed = labeledVarsMatcher.group(1);
    String labeledVariables = labeledVarsMatcher.group(2);

    String countries    = extractLabeledVariable(labeledVariables, LABELED_COUNTRIES_PATTERN);
    String devPhase     = extractLabeledVariable(labeledVariables, LABELED_DEV_PHASE_KEY_PATTERN);
    String hardware     = extractLabeledVariable(labeledVariables, LABELED_HARDWARE_KEY_PATTERN);
    String partners     = extractLabeledVariable(labeledVariables, LABELED_PARTNERS_KEY_PATTERN);
    String revision     = extractLabeledVariable(labeledVariables, LABELED_REVISION_KEY_PATTERN);
    String usedBy       = extractLabeledVariable(labeledVariables, LABELED_USED_BY_KEY_PATTERN);
    String redBlackSwap = extractLabeledVariable(labeledVariables, LABELED_RED_BLACK_SWAP_KEY_PATTERN);
    String zone         = extractLabeledVariable(labeledVariables, LABELED_ZONE_KEY_PATTERN);

    return new LabeledVariablesNamingResult(new LabeledVariables(countries, devPhase, hardware, partners, revision, usedBy, redBlackSwap, zone), unprocessed);
  }

  //VisibleForTesting
  static String extractLabeledVariable(String labeledVariablesString, Pattern labelPattern) {
    if (labeledVariablesString != null && !labeledVariablesString.isEmpty()) {
      Matcher labelMatcher = labelPattern.matcher(labeledVariablesString);
      boolean hasLabel = labelMatcher.find();
      if (hasLabel) {
        return labelMatcher.group(1);
      }
    }
    return null;
  }

  private static Pattern createLabeledVariablePattern(String label) {
    if (label == null || label.length() != 1 || !NameConstants.EXISTING_LABELS.contains(label)) {
      throw new IllegalArgumentException(String.format("Invalid label %s must be one of %s", label, NameConstants.EXISTING_LABELS));
    }
    String labeledVariablePattern
        = "-?"
        + label
        + NameConstants.LABELED_VAR_SEPARATOR
        + "(" + NameConstants.LABELED_VAR_VALUES + "+)";
    return Pattern.compile(labeledVariablePattern);
  }

}
