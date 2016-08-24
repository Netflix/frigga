package com.netflix.frigga.spi;

import com.netflix.frigga.Names;

/**
 * NamesParserProvider.
 *
 * Allows customization of the parsing of a name into its components by supplying a
 * custom parser provider through Java's ServiceLocator mechanism.
 */
public interface NamesParserProvider {

    Names parseName(String name);
}
