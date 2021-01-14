package ru.otus.taskmanager.service;

import ru.otus.taskmanager.model.user.Role;

import java.util.List;

public interface RoleService {
    Role createRole(Role role);

    List<Role> findAll();
}
