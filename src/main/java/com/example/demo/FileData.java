package com.example.demo;

import com.fasterxml.jackson.annotation.JsonInclude;


/**
 * Wrapper for a file description, can include meta information about the file in the future.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)

public class FileData {

    private String content;

    public FileData() {}

    public FileData(String content) {
        this.setContent(content);
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
