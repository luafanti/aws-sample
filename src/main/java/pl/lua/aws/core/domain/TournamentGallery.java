package pl.lua.aws.core.domain;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
public class TournamentGallery {
    private Long tournamentId;
    private List<String> photoUrls = new ArrayList<>();
    private List<MultipartFile> uploadedPhotos = new ArrayList<>();
}
