package com.netflix.frigga.spi;

import com.netflix.frigga.Names;

/**
 * CustomizedNamesParserProvider.
 */
public class CustomizedNamesParserProvider implements NamesParserProvider {
    @Override
    public Names parseName(String name) {
        return new CustomizedNames(name);
    }
}
