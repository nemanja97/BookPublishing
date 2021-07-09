package rs.ac.uns.ftn.uddproject.model.dto.plagiarism;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;

@NoArgsConstructor
@Getter
@Setter
public class PaperDTO implements Comparator<PaperDTO> {

  private Long id;
  private String title;
  private MultipartFile file;
  private String pathForPDF;
  private double searchHits;
  private double similarProcent;
  private Long plagiatorId;

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;

    if (obj == null) return false;

    if (getClass() != obj.getClass()) return false;

    PaperDTO other = (PaperDTO) obj;
    if (id == null) return other.id == null;
    else return id.equals(other.id);
  }

  @Override
  public int compare(PaperDTO paper1, PaperDTO paper2) {
    return Double.compare(paper2.getSimilarProcent(), paper1.getSimilarProcent());
  }
}
