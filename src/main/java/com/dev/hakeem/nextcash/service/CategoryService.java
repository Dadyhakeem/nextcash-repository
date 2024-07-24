package com.dev.hakeem.nextcash.service;


import com.dev.hakeem.nextcash.entity.Category;
import com.dev.hakeem.nextcash.entity.User;
import com.dev.hakeem.nextcash.exception.EntityNotFoundException;
import com.dev.hakeem.nextcash.repository.AccountRepository;
import com.dev.hakeem.nextcash.repository.CategoryRepository;
import com.dev.hakeem.nextcash.repository.UserRepository;
import com.dev.hakeem.nextcash.web.request.CategoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CategoryService {

    private final CategoryRepository repository;
    private final UserRepository userRepository;
    @Autowired
    public CategoryService(CategoryRepository repository, AccountRepository accountRepository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;

    }

    public Category createCategory(CategoryRequest request){
        Optional<User> user = userRepository.findById(request.getUserid());

        if (!user.isPresent()){
                 throw  new EntityNotFoundException("Usuario não encontrado");
        }
        Category category =new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setUserid(user.get());
        return repository.save(category);

    }

    public Category atualizar(CategoryRequest request){
        Category category = repository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException("Categoria  não encontrado"));
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        return repository.save(category);

    }

    public Category buscarPorId(Long id){
       return repository.findById(id)
               .orElseThrow(() -> new EntityNotFoundException(String.format("Categoria id = %s não encontrado",id)));
    }

    public List<Category> listarTodos(){
        return repository.findAll();
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(String.format("Categoria id = %s não encontrado",id));
        }
         repository.deleteById(id);
    }
}
