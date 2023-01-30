package com.user.service;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.user.entity.Role;
import com.user.repository.RoleRepository;

@SpringBootTest
class RoleServiceTest {

	@MockBean
	private RoleRepository roleRepositoryMock;

	@Autowired
	private RoleService roleService;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testGetByNameNotFound() {

		Role role = new Role();
		role.setId(1L);
		role.setName("Admin");

		Optional<Role> optional = Optional.empty();

		when(roleRepositoryMock.findByName(role.getName())).thenReturn(optional);

		Assertions.assertEquals(null, roleService.getByName(role.getName()));
	}

	@Test
	void testGetByName() {

		Role role = new Role();
		role.setId(1L);
		role.setName("Admin");

		Optional<Role> optional = Optional.of(role);

		when(roleRepositoryMock.findByName(role.getName())).thenReturn(optional);

		Assertions.assertEquals(role, roleService.getByName(role.getName()));
	}

	@Test
	void testGetByNameException() {

		Role role = new Role();
		role.setId(1L);
		role.setName("Admin");

		when(roleRepositoryMock.findByName(role.getName())).thenThrow(RuntimeException.class);

		Assertions.assertEquals(null, roleService.getByName(role.getName()));
	}

	@Test
	void testGetByIdNotFound() {
		Role role = new Role();
		role.setId(1L);
		role.setName("Admin");

		Optional<Role> optional = Optional.empty();

		when(roleRepositoryMock.findById(role.getId())).thenReturn(optional);

		Assertions.assertEquals(null, roleService.getById(role.getId()));
	}

	@Test
	void testGetById() {
		Role role = new Role();
		role.setId(1L);
		role.setName("Admin");

		Optional<Role> optional = Optional.of(role);

		when(roleRepositoryMock.findById(role.getId())).thenReturn(optional);

		Assertions.assertEquals(role, roleService.getById(role.getId()));
	}

	@Test
	void testGetByIdException() {
		Role role = new Role();
		role.setId(1L);
		role.setName("Admin");

		when(roleRepositoryMock.findById(role.getId())).thenThrow(RuntimeException.class);

		Assertions.assertEquals(null, roleService.getById(role.getId()));
	}

}
