package com.example.eBook.controller;

import com.example.eBook.dto.LoginRequest;
import com.example.eBook.dto.UserCreateDto;
import com.example.eBook.dto.UserDto;
import com.example.eBook.entity.Users;
import com.example.eBook.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UsersController
{

@Autowired
private UsersService usersService;

// Tüm kullanıcıları getir (DTO listesi)
// Sadece "user" rolündeki kullanıcıları getir
@GetMapping("/normal-users")
@PreAuthorize("hasRole('ADMIN')")
public List<UserDto> getAllNormalUsers() {
    return usersService.getUsersByRol("user")
            .stream()
            .map(this::toDto)
            .collect(Collectors.toList());
}


// Yeni kullanıcı oluştur
@PostMapping
@PreAuthorize("permitAll()")
public UserDto createUser(@RequestBody UserCreateDto dto) {
    Users user = toEntity(dto);
    return toDto(usersService.createUser(user));
}

// ID'ye göre kullanıcı getir
@GetMapping("/{id}")
@PreAuthorize("hasRole('ADMIN')")
public UserDto getUserById(@PathVariable Integer id) {

    return toDto(usersService.getUserById(id));
}

// ✅ E-posta ile kullanıcı getir
@GetMapping("/email/{email}")
@PreAuthorize("isAuthenticated()")
public UserDto getByEmail(@PathVariable String email) {
    Users user = usersService.getByEmail(email.toLowerCase());
    return toDto(user);
    }

    // Tüm kullanıcıları getir (sadece "user" rolündekiler)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDto> getAllUsers() {
        return usersService.getUsersByRol("user")
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // Güncelleme
@PutMapping("/{id}")
@PreAuthorize("hasRole('ADMIN')")
public UserDto updateUser(@PathVariable Integer id, @RequestBody UserCreateDto dto) {
    Users updated = usersService.updateUser(id, toEntity(dto));
    return toDto(updated);
}

// Silme
@DeleteMapping("/{id}")
@PreAuthorize("hasRole('ADMIN')")
public void deleteUser(@PathVariable Integer id) {
    usersService.deleteUser(id);
}

    /*
    // Giriş işlemi
    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public UserDto login(@RequestBody LoginRequest loginRequest) {
        Users user = usersService.login(loginRequest.getEmail(), loginRequest.getSifre());
        return toDto(user);
    }

    */

// --- Yardımcı dönüşüm metodları ---

private UserDto toDto(Users user) {
    UserDto dto = new UserDto();
    dto.setId(user.getUserId());
    dto.setAdSoyad(user.getAdSoyad());
    dto.setEmail(user.getEmail());
    dto.setAdres(user.getAdres());
    dto.setRol(user.getRol());
    return dto;
}

private Users toEntity(UserCreateDto dto) {
    Users user = new Users();
    user.setAdSoyad(dto.getAdSoyad());
    user.setEmail(dto.getEmail());
    user.setSifre(dto.getSifre());
    user.setAdres(dto.getAdres());
    user.setRol(dto.getRol() != null ? dto.getRol() : "user"); // boşsa user olarak ata
    return user;
}
}