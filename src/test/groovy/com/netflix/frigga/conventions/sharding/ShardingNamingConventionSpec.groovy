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
package com.netflix.frigga.conventions.sharding

import spock.lang.Specification

import static com.netflix.frigga.conventions.sharding.ShardingNamingConvention.duplicateShardWarning
import static com.netflix.frigga.conventions.sharding.ShardingNamingConvention.shardInRemainingWarning

class ShardingNamingConventionSpec extends Specification {

  ShardingNamingConvention subject = new ShardingNamingConvention()

  def "extracts expected shards"() {
    given:
    def resultOptional = Optional.ofNullable(expectedResult)

    when:
    def result = subject.extractNamingConvention(details)

    then:
    result.result == resultOptional
    result.unprocessed == expectedRemaining
    new ArrayList<>(result.warnings) == expectedWarnings

    where:
    details                     || expectedRemaining || expectedResult                   || expectedWarnings
    "foo"                       || "foo"             || null                             || []
    "x1foo-bar-baz"             || "-bar-baz"        || [1: s(1, "foo")]                 || []
    "x1foo-x2bar-x1baz-bazinga" || "-bazinga"        || [1: s(1, "baz"), 2: s(2, "bar")] || [dupe(1, "foo", "baz")]
    "x2bar-x1foo-blah-x3baz"    || "-blah-x3baz"     || [1: s(1, "foo"), 2: s(2, "bar")] || [remains("-blah-x3baz", 3, "baz")]
    "x10"                       || "x10"             || null                             || []
    "x1"                        || "x1"              || null                             || []
  }

  private static String dupe(Integer id, String prev, String current) {
    return duplicateShardWarning(new Shard(id, prev), new Shard(id, current))
  }

  private static String remains(String remaining, Integer id, String value) {
    return shardInRemainingWarning(remaining, id as String, value)
  }

  private static Shard s(Integer id, String value) {
    return new Shard(id, value)
  }
}
