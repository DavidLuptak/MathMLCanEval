package cz.muni.fi.mir.mathmlcaneval.requests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileImportRequest {
  @NotNull
  @Size(min = 1)
  private MultipartFile[] file;
}
