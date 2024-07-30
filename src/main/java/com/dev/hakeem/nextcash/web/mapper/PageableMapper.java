package com.dev.hakeem.nextcash.web.mapper;

import com.dev.hakeem.nextcash.web.request.PageableDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageableMapper {


    public  static PageableDTO toDTO(Page page){
        return  new ModelMapper().map(page,PageableDTO.class);
    }
}
