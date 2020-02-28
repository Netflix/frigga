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
