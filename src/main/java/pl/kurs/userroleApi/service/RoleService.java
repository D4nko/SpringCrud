package pl.kurs.userroleApi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kurs.userroleApi.dto.RoleDto;
import pl.kurs.userroleApi.model.Role;
import pl.kurs.userroleApi.repository.RoleRepository;


@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    @Transactional
    public RoleDto createRole(RoleDto roleDto) {
        Role role = new Role();
        role.setName(roleDto.getName());
        return mapToRoleDTO(roleRepository.save(role));
    }

    private RoleDto mapToRoleDTO(Role role) {
        RoleDto roleDTO = new RoleDto();
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());
        return roleDTO;
    }
}