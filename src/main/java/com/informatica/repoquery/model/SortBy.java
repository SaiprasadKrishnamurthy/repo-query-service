package com.informatica.repoquery.model;

public enum SortBy {
    STARS, FORKS, HELP_WANTED_ISSUES, UPDATED;

    public static String hyphenise(final SortBy sortBy) {
        return sortBy.toString().replace("_", "-");
    }
}
