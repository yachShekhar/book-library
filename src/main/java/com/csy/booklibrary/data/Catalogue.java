package com.csy.booklibrary.data;

import lombok.Data;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class Catalogue {
    private Set<String> authors = new HashSet<>();
    private Set<String> authorNationality = new HashSet<>();
    private List<Genre> genres = Arrays.asList(Genre.values());
}
