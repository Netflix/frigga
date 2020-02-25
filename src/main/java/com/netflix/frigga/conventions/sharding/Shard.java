package com.netflix.frigga.conventions.sharding;

import java.util.Objects;

/**
 * A Shard is a pair with a numeric (greater than 0) id and value extracted out of the
 * freeFormDetails component of a Name by ShardingNamingConvention.
 */
public class Shard {
  private final Integer shardId;
  private final String shardValue;

  public Shard(Integer shardId, String shardValue) {
    if (Objects.requireNonNull(shardId, "shardId") < 1) {
      throw new IllegalArgumentException("shardId must be greater than 0");
    }

    if (Objects.requireNonNull(shardValue, "shardValue").isEmpty()) {
      throw new IllegalArgumentException("shardValue must be non empty");
    }

    this.shardId = shardId;
    this.shardValue = shardValue;
  }

  public Integer getShardId() {
    return shardId;
  }

  public String getShardValue() {
    return shardValue;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Shard shard = (Shard) o;
    return shardId.equals(shard.shardId) &&
        shardValue.equals(shard.shardValue);
  }

  @Override
  public int hashCode() {
    return Objects.hash(shardId, shardValue);
  }
}
