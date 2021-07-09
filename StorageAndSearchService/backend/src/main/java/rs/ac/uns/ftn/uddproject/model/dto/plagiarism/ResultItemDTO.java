package rs.ac.uns.ftn.uddproject.model.dto.plagiarism;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Comparator;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ResultItemDTO implements Comparator<ResultItemDTO> {
  private List<PaperDTO> papers;
  private int partOfPage;
  private String textToShow;

  @Override
  public int compare(ResultItemDTO resultItem1, ResultItemDTO resultItem2) {
    return Double.compare(resultItem1.getPartOfPage(), resultItem2.getPartOfPage());
  }
}
