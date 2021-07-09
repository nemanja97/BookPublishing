package rs.ac.uns.ftn.uddproject.model.entity.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import rs.ac.uns.ftn.uddproject.model.enums.Genre;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Document(indexName = "books")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookES {

  @Id
  @Field(type = FieldType.Text, store = true)
  private String id;

  @Id
  @Field(type = FieldType.Text, searchAnalyzer = "serbian", analyzer = "serbian", store = true)
  private String title;

  @Id
  @Field(type = FieldType.Object, searchAnalyzer = "serbian", analyzer = "serbian", store = true)
  private UserES writer;

  @Field(type = FieldType.Text, store = true)
  private List<String> genres = new ArrayList<>();

  @Field(type = FieldType.Text, index = false, store = true)
  private String filePath;

  @Field(type = FieldType.Text, index = false, store = true)
  private String base64image;

  @Field(type = FieldType.Text, searchAnalyzer = "serbian", analyzer = "serbian", store = true)
  private String content;

  @Field(type = FieldType.Boolean, store = true)
  private Boolean openAccess;

  @Field(type = FieldType.Scaled_Float, store = true)
  private BigDecimal price;

  @Field(type = FieldType.Text, store = true)
  private String currency = "EUR";

  public void setGenres(List<Genre> genres) {
    this.genres = genres.stream().map(Genre::toString).collect(Collectors.toList());
  }
}
