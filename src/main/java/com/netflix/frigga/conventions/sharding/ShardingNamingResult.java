package com.netflix.frigga.conventions.sharding;

import com.netflix.frigga.extensions.NamingResult;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * A NamingResult for ShardingNamingConvention.
 *
 * Result is a Map of {@code shardId -> Shard}, or {@code Optional.empty} if no shards were present.
 *
 * unprocessed is the remaining portion of the freeFormDetails after no more shards were present.
 *
 * Duplicate values for a shard Id are treated as a warning.
 * The presence of the sharding pattern in the unprocessed text is treated as a warning.
 */
public class ShardingNamingResult implements NamingResult<Map<Integer, Shard>> {

  private final Map<Integer, Shard> result;
  private final String unprocessed;
  private final Collection<String> warnings;
  private final Collection<String> errors;

  public ShardingNamingResult(Map<Integer, Shard> result, String unprocessed, Collection<String> warnings, Collection<String> errors) {
    this.result = result != null && result.isEmpty() ? null : result;
    this.unprocessed = unprocessed == null ? "" : unprocessed;
    this.warnings = warnings == null || warnings.isEmpty() ? Collections.emptyList() : Collections.unmodifiableCollection(warnings);
    this.errors = errors == null || errors.isEmpty() ? Collections.emptyList() : Collections.unmodifiableCollection(errors);
  }

  @Override
  public Optional<Map<Integer, Shard>> getResult() {
    return Optional.ofNullable(result);
  }

  @Override
  public String getUnprocessed() {
    return unprocessed;
  }

  @Override
  public Collection<String> getWarnings() {
    return warnings;
  }

  @Override
  public Collection<String> getErrors() {
    return errors;
  }
}
