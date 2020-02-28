package com.netflix.frigga.conventions.labeledvariables;

import com.netflix.frigga.extensions.NamingResult;

import java.util.Optional;

/**
 * The result of applying the LabeledVariablesNamingConvention.
 *
 * Contains LabeledVariables for the extracted labeled variables, as well as
 * the unprocessed leading component of the name up to the start of the first labeled
 * variable.
 */
public class LabeledVariablesNamingResult implements NamingResult<LabeledVariables> {

  public static final LabeledVariablesNamingResult EMPTY = new LabeledVariablesNamingResult(null, null);

  private final LabeledVariables labeledVariables;
  private final String unprocessed;

  public LabeledVariablesNamingResult(LabeledVariables labeledVariables, String unprocessed) {
    this.labeledVariables = labeledVariables;
    this.unprocessed = unprocessed;
  }

  @Override
  public Optional<LabeledVariables> getResult() {
    return Optional.ofNullable(labeledVariables);
  }

  @Override
  public String getUnprocessed() {
    return unprocessed;
  }
}
