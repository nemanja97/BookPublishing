package rs.ac.uns.ftn.uddproject.model.dto.plagiarism;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class PaperResultPlagiatorDTO {
  private Long id;
  private List<ResultItemDTO> items = new ArrayList<>();
  private List<PaperDTO> similarPapers = new ArrayList<>();
  private PaperDTO uploadedPaper;
}
