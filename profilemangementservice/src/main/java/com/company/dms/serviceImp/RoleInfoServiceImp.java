package com.company.dms.serviceImp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.dms.dto.RoleDto;
import com.company.dms.entity.Role;
import com.company.dms.exception.NoSuchRolePresentException;
import com.company.dms.repository.RoleInfoRepo;
import com.company.dms.service.RoleInfoService;
import com.company.dms.utils.logger.LoggableClass;
import com.company.dms.utils.logger.LoggableMethod;
import com.company.dms.utils.logger.NotLoggableMethod;

import java.util.Optional;

/**
 * Implementation of the RoleInfoService interface.
 */
@LoggableClass
@Service
public class RoleInfoServiceImp implements RoleInfoService {
    private final RoleInfoRepo roleInfoRepo;
    private final ModelMapper mapper;
    @Autowired
    public RoleInfoServiceImp(RoleInfoRepo roleInfoRepo,ModelMapper mapper){
        this.roleInfoRepo = roleInfoRepo;
        this.mapper = mapper;
    }

    /**
     * Retrieves a role by its name.
     *
     * @param roleName The name of the role.
     * @return Role The role entity.
     * @throws NoSuchRolePresentException If no role with the given name is found.
     */
    @LoggableMethod
    @Override
    public Role findByRoleName(String roleName) {
        Optional<Role> role = roleInfoRepo.findByName(roleName);
        if(role.isEmpty()) throw new NoSuchRolePresentException("No such role is present");
        return role.get();
    }

    /**
     * Populates the role information into the database.
     * This method creates roles with IDs ranging from 1 to 3.
     * The role names and descriptions are predefined based on the role ID.
     */
    @Override
    public void roleEntry() {
        for(long i = 1L; i <= 3L; i++) {
            Role role = new Role();
            role.setRoleId(i);
            if(i == 1) {
                role.setName("admin");
                role.setDescription("Administration");
            } else if(i == 2) {
                role.setName("distributor");
                role.setDescription("Distributor");
            } else {
                role.setName("retailer");
                role.setDescription("Retailer");
            }
            roleInfoRepo.save(role);
        }
    }



    /**
     * Converts a Role entity to a RoleDto.
     *
     * @param role The Role entity.
     * @return RoleDto The Role DTO.
     */
    @NotLoggableMethod
    public RoleDto roleToDto(Role role) {
        return mapper.map(role, RoleDto.class);
    }
}
