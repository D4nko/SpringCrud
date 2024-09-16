package pl.kurs.userroleApi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kurs.userroleApi.dto.UserDto;
import pl.kurs.userroleApi.model.User;
import pl.kurs.userroleApi.service.UserService;


@RestController
@RequestMapping("/userApi/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.createUser(userDto));
    }

    @GetMapping
    public ResponseEntity<Page<User>> getUsers(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(userService.getUsers(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping("/{userId}/roles/{roleName}")
    public ResponseEntity<UserDto> assignRoleToUser(@PathVariable Long userId, @PathVariable String roleName) {
        return ResponseEntity.ok(userService.assignRoleToUser(userId, roleName));
    }
}
