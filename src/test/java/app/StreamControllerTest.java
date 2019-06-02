package app;

import app.dto.PersonDto;
import app.dto.UserDto;
import app.model.Account;
import app.model.Person;
import app.dal.UserDao;
import app.dto.TransferCashDto;
import app.model.User;
import app.util.TestUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class StreamControllerTest {
	private static final MediaType CONTENT_TYPE = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

	private MockMvc mockMvc;

	@Autowired
	private UserDao userDao;

	@PersistenceContext
	private EntityManager entityManager;

    @Autowired
    public void setContext(WebApplicationContext context) {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

	@Test
	public void getCash_Success() throws Exception {
		User user = createUser("A","A", "A");
		mockMvc.perform(MockMvcRequestBuilders.get("/api/user/{userId}/cash", user.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.cash", is(TestUtils.decimal(user.getAccount().getCash()))));
	}

	@Test
	public void createUser_Success() throws Exception {
    	PersonDto personDto = new PersonDto("name", "surname");
		UserDto userDto = new UserDto(personDto, "email@email.ru");
		mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                .content(TestUtils.toJsonBytes(userDto))
                .contentType(CONTENT_TYPE))
				.andExpect(status().isOk());

		List<User> users = userDao.findAll();
		Assert.assertEquals(1, users.size());
		User user = users.get(0);
		Assert.assertEquals(userDto.getEmail(), user.getEmail());
		Person person = user.getPerson();
		Assert.assertEquals(personDto.getName(), person.getName());
		Assert.assertEquals(personDto.getSurname(), person.getSurname());
	}

	@Test
	public void transferCash_Success() throws Exception {
    	final BigDecimal initialCash = BigDecimal.TEN;
    	User initiator = createUser("A", "A", "A");
    	Account account = initiator.getAccount();
    	Field field = ReflectionUtils.findField(account.getClass(), "cash");
    	Assert.assertNotNull(field);
    	try {
    		field.setAccessible(true);
    		ReflectionUtils.setField(field, account, initialCash);
		} finally {
			field.setAccessible(false);
		}
		userDao.save(initiator);

    	User addressee = createUser("B", "B", "B");

    	final BigDecimal cashTransfer = initialCash.divide(BigDecimal.valueOf(2L), RoundingMode.HALF_UP);

		TransferCashDto dto = new TransferCashDto(cashTransfer, addressee.getId());
		mockMvc.perform(MockMvcRequestBuilders.post("/api/user/{userId}/cash", initiator.getId())
                .content(TestUtils.toJsonBytes(dto))
                .contentType(CONTENT_TYPE))
				.andExpect(status().isOk());
		entityManager.flush();

		Assert.assertEquals(cashTransfer, addressee.getAccount().getCash());
		Assert.assertEquals(initialCash.subtract(cashTransfer), initiator.getAccount().getCash());
	}

	@Test(expected = NestedServletException.class)
	public void transferCash_NotEnoughMoney_Fail() throws Exception {
		User initiator = createUser("A", "A", "A");
		User addressee = createUser("B", "B", "B");
    	final BigDecimal cashTransfer = BigDecimal.TEN;
    	Assert.assertTrue(addressee.getAccount().getCash().compareTo(cashTransfer) < 0);

		TransferCashDto dto = new TransferCashDto(cashTransfer, addressee.getId());
		mockMvc.perform(MockMvcRequestBuilders.post("/api/user/{userId}/cash", initiator.getId())
                .content(TestUtils.toJsonBytes(dto))
                .contentType(CONTENT_TYPE));
	}

	private User createUser (String name, String surname, String email) {
		User user = new User();
		Person person = new Person();
		person.setName(name);
		person.setSurname(surname);
		user.setPerson(person);
		user.setEmail(email);
		user.setAccount(new Account());
		userDao.save(user);
		return user;
	}
}
