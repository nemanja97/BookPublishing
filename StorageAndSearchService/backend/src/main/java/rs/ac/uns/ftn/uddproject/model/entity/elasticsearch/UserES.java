package rs.ac.uns.ftn.uddproject.model.entity.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import rs.ac.uns.ftn.uddproject.model.entity.User;
import rs.ac.uns.ftn.uddproject.model.enums.Genre;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Document(indexName = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserES {

  @Id
  @Field(type = FieldType.Text, index = false, store = true)
  private String id;

  @Field(type = FieldType.Text, searchAnalyzer = "serbian", analyzer = "serbian", store = true)
  private String firstName;

  @Field(type = FieldType.Text, searchAnalyzer = "serbian", analyzer = "serbian", store = true)
  private String lastName;

  @Field(type = FieldType.Text, searchAnalyzer = "serbian", analyzer = "serbian", store = true)
  private String fullName;

  @Field(type = FieldType.Text, store = true)
  private String email;

  @Field(type = FieldType.Text, store = true)
  private String role;

  @GeoPointField private GeoPoint location;

  @Field(type = FieldType.Text, store = true)
  private List<String> genresInterestedInReading = new ArrayList<>();

  @Field(type = FieldType.Text, store = true)
  private List<String> genresInterestedInBetaReading = new ArrayList<>();

  @Field(type = FieldType.Text, searchAnalyzer = "serbian", analyzer = "serbian", store = true)
  private List<BookES> booksWritten = new ArrayList<>();

  public UserES(User user, GeoPoint location, List<BookES> booksWritten) {
    id = user.getId();
    firstName = user.getFirstName();
    lastName = user.getLastName();
    fullName = String.format("%s %s", user.getFirstName(), user.getLastName());
    email = user.getEmail();
    role = user.getRole().toString();
    this.location = location;
    genresInterestedInReading =
        user.getGenresInterestedInReading().stream()
            .map(Genre::toString)
            .collect(Collectors.toList());
    genresInterestedInBetaReading =
        user.getGenresInterestedInBetaReading().stream()
            .map(Genre::toString)
            .collect(Collectors.toList());
    this.booksWritten = booksWritten;
  }

  public void setGenresInterestedInReading(List<Genre> genresInterestedInReading) {
    this.genresInterestedInReading =
        genresInterestedInReading.stream().map(Genre::toString).collect(Collectors.toList());
  }

  public void setGenresInterestedInBetaReading(List<Genre> genresInterestedInBetaReading) {
    this.genresInterestedInBetaReading =
        genresInterestedInBetaReading.stream().map(Genre::toString).collect(Collectors.toList());
  }
}
