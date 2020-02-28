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
package com.netflix.frigga.conventions.sharding;

import com.netflix.frigga.NameConstants;
import com.netflix.frigga.extensions.NamingConvention;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A NamingConvention that extracts Shards out of the freeFormDetails component of a name.
 *
 * Shards are only considered in the leading portion of the freeFormDetails, and are extracted while
 * present, leaving any remaining portion of the details as the unprocessed portion of the NamingResult.
 *
 * A shard has a numeric id greater than 0.
 * The first component of a shard value must be a non digit (leading digits get consumed into the shardId)
 * A shard value can contain ASCII letters, digits, as well as '.', '_', '^', and '~'
 *
 * A specification for the ShardingNamingConvention follows:
 * <pre>
 * {@code
 * ; the naming convention operates on free-form-details:
 * free-form-details              = *free-form-detail-char
 *
 * ; shard extraction specification:
 * shards                         = [first-shard *additional-shard] [unprocessed]
 * first-shard                    = shard-content
 * additional-shard               = "-" shard-content
 * shard-content                  = "x" shard
 * shard                          = shard-id shard-value
 * shard-id                       = non-zero-digit *DIGIT
 * shard-value                    = shard-value-first-char *shard-value-remaining-char
 * unprocessed                    = *free-form-detail-char
 *
 * ; character sets:
 * non-zero-digit                 = %x31-39
 * shard-value-first-char         = ALPHA / "." / "_" / "^" / "~"
 * shard-value-remaining-char     = shard-value-first-char / DIGIT
 * free-form-detail-char          = shard-value-remaining-char / "-"
 * }
 * </pre>
 *
 * If multiple shard tokens are present with the same shardId, the last value supplied takes precedence, and
 * the NamingResult will contain a warning indicating that there was a collision.
 *
 * If a shard appears to be present in the unprocessed portion of the freeFormDetails, this is noted as a warning.
 */
public class ShardingNamingConvention implements NamingConvention<ShardingNamingResult> {
  private static final String SHARD_CONTENTS_REGEX = "x([1-9][0-9]*)([" + NameConstants.EXTENDED_NAME_CHARS + "]+)(.*?)$";
  private static final String VALID_SHARDS_REGEX = "(?:^|-)" + SHARD_CONTENTS_REGEX;
  private static final String SHARD_WARNING_REGEX = ".*?-" + SHARD_CONTENTS_REGEX;
  private static final Pattern SHARD_PATTERN = Pattern.compile(VALID_SHARDS_REGEX);
  private static final Pattern SHARD_WARNING_PATTERN = Pattern.compile(SHARD_WARNING_REGEX);

  @Override
  public ShardingNamingResult extractNamingConvention(String freeFormDetails) {
    Map<Integer, Shard> result = new HashMap<>();
    String remaining = freeFormDetails;
    Matcher matcher = SHARD_PATTERN.matcher(freeFormDetails);
    Collection<String> warnings = new ArrayList<>();
    while (matcher.matches() && !remaining.isEmpty()) {
      Integer shardId = Integer.parseInt(matcher.group(1));
      String shardValue = matcher.group(2);
      Shard shard = new Shard(shardId, shardValue);
      Shard previous = result.put(shardId, shard);
      if (previous != null) {
        warnings.add(duplicateShardWarning(previous, shard));
      }
      remaining = matcher.group(3);
      matcher.reset(remaining);
    }
    if (!remaining.isEmpty()) {
      Matcher warningsMatcher = SHARD_WARNING_PATTERN.matcher(remaining);
      if (warningsMatcher.matches() && warningsMatcher.groupCount() > 2) {
        warnings.add(shardInRemainingWarning(remaining, warningsMatcher.group(1), warningsMatcher.group(2)));
      }
    }
    return new ShardingNamingResult(result, remaining, warnings, Collections.emptyList());
  }

  //visible for testing
  static String duplicateShardWarning(Shard previous, Shard current) {
    return String.format("duplicate shardId %s, shard value %s will be ignored in favor of %s",
        current.getShardId(),
        previous.getShardValue(),
        current.getShardValue());
  }

  //visible for testing
  static String shardInRemainingWarning(String remaining, String shardId, String shardValue) {
    return String.format("detected additional shard configuration in remaining detail string %s. shard %s, value %s",
        remaining, shardId, shardValue);
  }
}
