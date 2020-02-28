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
package com.netflix.frigga.extensions;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * The result of applying a NamingConvention.
 *
 * @param <T> the type of the result extracted from the name
 */
public interface NamingResult<T> {

  /**
   * @return the result (if any) extracted by a NamingConvention
   */
  Optional<T> getResult();

  /**
   * @return the remaining part of a name component after applying a NamingConvention
   */
  String getUnprocessed();

  /**
   * Warnings encountered applying a NamingConvention.
   *
   * Warnings are non fatal.
   *
   * @return any warnings encountered applying a NamingConvention.
   */
  default Collection<String> getWarnings() {
    return Collections.emptyList();
  }

  /**
   * Errors encountered applying a NamingConvention.
   *
   * Errors are fatal - if errors were encountered this NamingResult should be considered unusable.
   *
   * @return any errors encountered applying a NamingConvention
   */
  default Collection<String> getErrors() {
    return Collections.emptyList();
  }

  /**
   * @return whether this NamingResult represents a valid application of a NamingConvention
   */
  default boolean isValid() {
    return getErrors().isEmpty();
  }
}
