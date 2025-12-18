package org.example.whiswriting.dto;

import jdk.jfr.Category;

import java.util.List;

public class PostCreateDto {

    private String title;
    private String content;
    private String author;
    private Category category;
    private boolean anonymous;
    private List<String> images;
    private List<String> links;

}