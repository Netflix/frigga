package com.netflix.frigga.extensions;

/**
 * A NamingConvention can examine a component of a name to extract encoded values.
 *
 * @param <R> the NamingResult type
 */
public interface NamingConvention<R extends NamingResult<?>> {

  /**
   * Parses the nameComponent to extract the naming convention into a typed object
   * @param nameComponent the name component to examine
   * @return The NamingResult, never null
   */
  R extractNamingConvention(String nameComponent);
}
