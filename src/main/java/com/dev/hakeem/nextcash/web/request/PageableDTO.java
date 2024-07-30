package com.dev.hakeem.nextcash.web.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter @Setter
public class PageableDTO {

    private List content = new ArrayList();

    private boolean first;
    private  boolean last;
    @JsonProperty("page")
    private int number;
    private  int size;
    @JsonProperty("pageElements")
    private int numberOfElemnts;
     private  int totalpage;
     private int totalElements;
}
