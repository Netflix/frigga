/**
 * Copyright 2012 Netflix, Inc.
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
package com.netflix.frigga;


public interface NameConstants {

    static final String NAME_CHARS = "a-zA-Z0-9._";
    static final String NAME_HYPHEN_CHARS = "-a-zA-Z0-9._";
    static final String PUSH_FORMAT = "v([0-9]{3})";
    static final String LABELED_VAR_SEPARATOR = "0";
    static final String LABELED_VARIABLE = "[a-zA-Z][" + LABELED_VAR_SEPARATOR + "][a-zA-Z0-9]+";

    static final String COUNTRIES_KEY = "c";
    static final String DEV_PHASE_KEY = "d";
    static final String HARDWARE_KEY = "h";
    static final String PARTNERS_KEY = "p";
    static final String REVISION_KEY = "r";
    static final String USED_BY_KEY = "u";
    static final String RED_BLACK_SWAP_KEY = "w";
    static final String ZONE_KEY = "z";

}
