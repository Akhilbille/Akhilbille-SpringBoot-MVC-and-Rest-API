package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.RegisterUserRequest;
import com.codewithmosh.store.dtos.UpdatePasswordDto;
import com.codewithmosh.store.dtos.UpdateUserRequest;
import com.codewithmosh.store.dtos.UserDto;
import com.codewithmosh.store.entities.User;
import com.codewithmosh.store.mappers.UserMapper;
import com.codewithmosh.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping
    public Iterable<UserDto> getAllUsers(
            @RequestParam(name = "sort",defaultValue = "",required = false) String sort
    ){
        if(!Set.of("name","email").contains(sort))
            sort = "name";
        return userRepository.findAll(Sort.by(sort))
                .stream()
//                .map(user->new UserDto(user.getId(),user.getName(),user.getEmail()))
                .map(userMapper::toDto)
                .toList();
    }

    //This one not able to send response properly
//    @GetMapping("/{id}")
//    public User getUser(@PathVariable("id") Long id){
//        return userRepository.findById(id).orElse(null);
//    }

    //finding user by id and sending a response
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") Long id){
        var user = userRepository.findById(id).orElse(null);
        if(user == null)
            return ResponseEntity.notFound().build();
//        var userDto = new UserDto(user.getId(),user.getName(),user.getEmail());
        return  ResponseEntity.ok(userMapper.toDto(user));
    }

    //Extracting user
//    @PostMapping
//    public UserDto createUser(@RequestBody UserDto data){
//        return data;
//    }

    //Creating resource
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody RegisterUserRequest request){
        var user = userMapper.toEntity(request);
        if(userRepository.findByEmail(request.getEmail())!=null)
            throw new IllegalArgumentException("Email Already Exists..");
        userRepository.save(user);
        return new ResponseEntity<UserDto>(userMapper.toDto(user), HttpStatus.CREATED);
    }

    //Updating user based on their id
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable(name = "id") Long id,
            @RequestBody UpdateUserRequest request
            ){
        var user = userRepository.findById(id).orElse(null);
        if(user==null){
            return ResponseEntity.notFound().build();
        }
        userMapper.update(request,user);
        userRepository.save(user);
        return ResponseEntity.ok(userMapper.toDto(user));

    }

    //Deleting resources
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "id") Long id){
        var user = userRepository.findById(id).orElse(null);
        if(user == null)
            return ResponseEntity.notFound().build();
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    //Handling action based updates - To this type of requests use Post method
    @PostMapping("/{id}/change-password")
    public ResponseEntity<Void> updatePassword(
            @PathVariable(name = "id") Long id,
            @RequestBody UpdatePasswordDto request){
        var user = userRepository.findById(id).orElse(null);
        if(user == null)
            return ResponseEntity.notFound().build();
        if(!user.getPassword().equals(request.getOldPassword()))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        user.setPassword(request.getNewPassword());
        userRepository.save(user);
        return ResponseEntity.noContent().build();
    }
    
}
