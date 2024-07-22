package com.dev.hakeem.nextcash.web.mapper;

import com.dev.hakeem.nextcash.entity.Category;
import com.dev.hakeem.nextcash.entity.User;
import com.dev.hakeem.nextcash.repository.CategoryRepository;
import com.dev.hakeem.nextcash.repository.UserRepository;
import com.dev.hakeem.nextcash.web.exception.BusinessException;
import com.dev.hakeem.nextcash.web.request.CategoryRequest;
import com.dev.hakeem.nextcash.web.response.CategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.Optional;
@Component
public class CategoryMapper {

    private  final CategoryRepository repository;
    private  final UserRepository userRepository;
    @Autowired
    public CategoryMapper(CategoryRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public Category toResquet(CategoryRequest request){


        Category category = new Category();
        category.setId(request.getId());
        category.setName(request.getName());
        category.setDescription(request.getDescription());

        Optional<User> user = userRepository.findById(request.getUserid());
        if (!user.isPresent()){
            throw new BusinessException("Usuário não encontrado");
        }
        category.setUserid(user.get());
        return category;


    }

    public CategoryResponse toResponse(Category category){

        CategoryResponse cat = new CategoryResponse();
        cat.setId(category.getId());
        cat.setName(category.getName());
        cat.setDescription(category.getDescription());

        return cat;
    }
}
