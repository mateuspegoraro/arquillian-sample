package br.com.ladobsoftware.arquillian.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.ladobsoftware.arquillian.dao.UserDao;
import br.com.ladobsoftware.arquillian.model.User;

@RunWith(Arquillian.class)
public class UserDaoTest {
 

	@Deployment
	public static Archive<?> createTestApplicationWar() {
		Archive<?> testApplicationWar = ShrinkWrap.create(WebArchive.class, "testApplication.war")
				.addPackage(UserDao.class.getPackage())
				.addClass(User.class)
				.addAsResource("META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		return testApplicationWar;
	}

	@Inject
	UserDao userDao;

	@Test
	@InSequence(1)
	public void saveUserTest() {
		User user1 = new User();
		user1.setLogin("login1");
		user1.setPassword("password1");
		userDao.save(user1);

		User user2 = new User();
		user2.setLogin("login2");
		user2.setPassword("password2");
		userDao.save(user2);
	}

	@Test
	@InSequence(2)
	public void updateUser1Test() {
		User user1 = userDao.find(1);
		user1.setLogin("login1updated");
		user1.setPassword("password1updated");
		userDao.update(user1);

		assertEquals("login1updated", user1.getLogin());
		assertEquals("password1updated", user1.getPassword());

	}

	@Test
	@InSequence(3)
	public void findUser2Test() {
		User user = userDao.find(2);

		assertEquals("login2", user.getLogin());
		assertEquals("password2", user.getPassword());
	}

	@Test
	@InSequence(4)
	public void findAllUsers() {
		List<User> users = userDao.findAll();
		assertEquals(2, users.size());
	}

}
