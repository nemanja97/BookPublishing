package rs.ac.uns.ftn.uddproject.startup;

import com.google.common.collect.Sets;
import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uddproject.model.dto.CoordinatesDTO;
import rs.ac.uns.ftn.uddproject.model.entity.User;
import rs.ac.uns.ftn.uddproject.model.entity.elasticsearch.UserES;
import rs.ac.uns.ftn.uddproject.model.enums.Genre;
import rs.ac.uns.ftn.uddproject.model.enums.Role;
import rs.ac.uns.ftn.uddproject.service.BookService;
import rs.ac.uns.ftn.uddproject.service.GeolocationService;
import rs.ac.uns.ftn.uddproject.service.UserESService;
import rs.ac.uns.ftn.uddproject.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Component
public class StartupDataAdder implements ApplicationRunner {

  @Autowired UserService userService;

  @Autowired UserESService userESService;

  @Autowired BookService bookService;

  @Autowired GeolocationService geolocationService;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    addUsersIfNotExist();
    // addBooksIfNotExist();
  }

  private void addUsersIfNotExist() throws Exception {
    List<User> users = userService.findAll();
    System.out.println(users.size());
    if (users.isEmpty()) {
      registerHeadEditor();

      registerEditor1();
      registerEditor2();

      registerWriter1();
      registerWriter2();
      registerWriter3();

      registerReader1();
      registerReader2();
      registerReader3();
    }
  }

  private void addBooksIfNotExist() {
    if (bookService.findAll().isEmpty()) {
      // TODO Add books
    }
  }

  private void registerHeadEditor() throws Exception {
    User user =
        new User(
            "he1@m.com",
            "he1",
            "John",
            "Smith",
            "Belgrade",
            "Serbia",
            Role.ROLE_HEAD_EDITOR,
            true,
            false);
    user = userService.registerUser(user);

    CoordinatesDTO coordinatesDTO = geolocationService.locate(user.getCountry(), user.getCity());
    UserES userES =
        new UserES(
            user,
            new GeoPoint(coordinatesDTO.getLat(), coordinatesDTO.getLon()),
            new ArrayList<>());
    userES = userESService.save(userES);
  }

  private void registerEditor1() throws Exception {
    User user =
        new User(
            "e1@m.com", "e1", "Jane", "Smith", "Belgrade", "Serbia", Role.ROLE_EDITOR, true, false);
    user = userService.registerUser(user);

    CoordinatesDTO coordinatesDTO = geolocationService.locate(user.getCountry(), user.getCity());
    UserES userES =
        new UserES(
            user,
            new GeoPoint(coordinatesDTO.getLat(), coordinatesDTO.getLon()),
            new ArrayList<>());
    userES = userESService.save(userES);
  }

  private void registerEditor2() throws Exception {
    User user =
        new User(
            "e2@m.com",
            "e2",
            "Jinny",
            "Smith",
            "Novi Sad",
            "Serbia",
            Role.ROLE_EDITOR,
            true,
            false);
    user = userService.registerUser(user);

    CoordinatesDTO coordinatesDTO = geolocationService.locate(user.getCountry(), user.getCity());
    UserES userES =
        new UserES(
            user,
            new GeoPoint(coordinatesDTO.getLat(), coordinatesDTO.getLon()),
            new ArrayList<>());
    userES = userESService.save(userES);
  }

  private void registerWriter1() throws Exception {
    User user =
        new User(
            "w1@m.com", "w1", "James", "Smith", "Paris", "France", Role.ROLE_WRITER, true, false);
    user = userService.registerUser(user);

    CoordinatesDTO coordinatesDTO = geolocationService.locate(user.getCountry(), user.getCity());
    UserES userES =
        new UserES(
            user,
            new GeoPoint(coordinatesDTO.getLat(), coordinatesDTO.getLon()),
            new ArrayList<>());
    userES = userESService.save(userES);
  }

  private void registerWriter2() throws Exception {
    User user =
        new User(
            "w2@m.com",
            "w2",
            "Hannah",
            "Smith",
            "Berlin",
            "Germany",
            Role.ROLE_WRITER,
            true,
            false);
    user = userService.registerUser(user);

    CoordinatesDTO coordinatesDTO = geolocationService.locate(user.getCountry(), user.getCity());
    UserES userES =
        new UserES(
            user,
            new GeoPoint(coordinatesDTO.getLat(), coordinatesDTO.getLon()),
            new ArrayList<>());
    userES = userESService.save(userES);
  }

  private void registerWriter3() throws Exception {
    User user =
        new User(
            "w3@m.com", "w3", "Tim", "Smith", "Madrid", "Spain", Role.ROLE_WRITER, true, false);
    user = userService.registerUser(user);

    CoordinatesDTO coordinatesDTO = geolocationService.locate(user.getCountry(), user.getCity());
    UserES userES =
        new UserES(
            user,
            new GeoPoint(coordinatesDTO.getLat(), coordinatesDTO.getLon()),
            new ArrayList<>());
    userES = userESService.save(userES);
  }

  private void registerReader1() throws Exception {
    User user =
        new User(
            "r1@m.com", "r1", "Terry", "Smith", "Paris", "France", Role.ROLE_READER, true, true);
    user.setGenresInterestedInBetaReading(Sets.newHashSet(Genre.ART, Genre.CRIME));
    user = userService.registerUser(user);

    CoordinatesDTO coordinatesDTO = geolocationService.locate(user.getCountry(), user.getCity());
    UserES userES =
        new UserES(
            user,
            new GeoPoint(coordinatesDTO.getLat(), coordinatesDTO.getLon()),
            new ArrayList<>());
    userES = userESService.save(userES);
  }

  private void registerReader2() throws Exception {
    User user =
        new User(
            "r2@m.com", "r2", "Ivan", "Smith", "Moscow", "Russia", Role.ROLE_READER, true, true);
    user.setGenresInterestedInBetaReading(Sets.newHashSet(Genre.ART, Genre.CRIME));
    user = userService.registerUser(user);

    CoordinatesDTO coordinatesDTO = geolocationService.locate(user.getCountry(), user.getCity());
    UserES userES =
        new UserES(
            user,
            new GeoPoint(coordinatesDTO.getLat(), coordinatesDTO.getLon()),
            new ArrayList<>());
    userES = userESService.save(userES);
  }

  private void registerReader3() throws Exception {
    User user =
        new User(
            "r3@m.com", "r3", "Lee", "Smith", "Beijing", "China", Role.ROLE_READER, true, true);
    user.setGenresInterestedInBetaReading(Sets.newHashSet(Genre.ART, Genre.CRIME));
    user = userService.registerUser(user);

    CoordinatesDTO coordinatesDTO = geolocationService.locate(user.getCountry(), user.getCity());
    UserES userES =
        new UserES(
            user,
            new GeoPoint(coordinatesDTO.getLat(), coordinatesDTO.getLon()),
            new ArrayList<>());
    userES = userESService.save(userES);
  }
}
