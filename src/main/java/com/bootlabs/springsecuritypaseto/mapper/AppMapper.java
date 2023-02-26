package com.bootlabs.springsecuritypaseto.mapper;

import com.bootlabs.springsecuritypaseto.dto.GroupRoleDTO;
import com.bootlabs.springsecuritypaseto.dto.UserDTO;
import com.bootlabs.springsecuritypaseto.entities.GroupRole;
import com.bootlabs.springsecuritypaseto.entities.User;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;

@UtilityClass
public class AppMapper {

    public UserDTO copyUserEntityToDto(User userEntity) {
        var userDTO = new UserDTO();
        BeanUtils.copyProperties(userEntity, userDTO);
        userDTO.setRoles(userEntity.getRoles().stream().map(AppMapper::copyGroupRoleToGroupRoleDTO).toList());
        return userDTO;
    }

    public User copyUserDtoToEntity(UserDTO dto) {
        var user = new User();
        BeanUtils.copyProperties(dto, user);
        user.setRoles(dto.getRoles().stream().map(AppMapper::copyGroupRoleDTOToGroupRole).toList());
        BeanUtils.copyProperties(dto.getRoles(), user.getRoles());
        return user;
    }
    public GroupRole copyGroupRoleDTOToGroupRole(GroupRoleDTO dto) {
        var groupRole = new GroupRole();
        BeanUtils.copyProperties(dto, groupRole);
        return groupRole;
    }
    public GroupRoleDTO copyGroupRoleToGroupRoleDTO(GroupRole entity) {
        var groupRoleDTO = new GroupRoleDTO();
        BeanUtils.copyProperties(entity, groupRoleDTO);
        return groupRoleDTO;
    }
}
