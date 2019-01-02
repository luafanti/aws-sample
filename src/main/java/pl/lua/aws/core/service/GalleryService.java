package pl.lua.aws.core.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.lua.aws.core.domain.TournamentGallery;
import pl.lua.aws.core.domain.UploadS3Object;
import pl.lua.aws.core.model.TournamentPhotoEntity;
import pl.lua.aws.core.repository.TournamentPhotoRepository;

import java.util.List;

@Service
@Slf4j
public class GalleryService {

    private  static final String URL_PREFIX = "https://s3-us-west-2.amazonaws.com/poker-tournament-gallery/";

    @Autowired
    private TournamentPhotoRepository tournamentPhotoRepository;

    @Autowired
    private S3Services s3Services;

    public void saveTournamentPhotos(MultipartFile photo,Long tournamentId) {
        TournamentPhotoEntity photoEntity = new TournamentPhotoEntity();
        UploadS3Object uploadedPhoto = s3Services.uploadTournamentPhoto(photo);
        photoEntity.setTournamentId(tournamentId);
        photoEntity.setPhotoUrl(URL_PREFIX + uploadedPhoto.getFileName());
        tournamentPhotoRepository.save(photoEntity);
        log.info("Successful save photo with url {} for tournament {}", photoEntity.getPhotoUrl(), photoEntity.getTournamentId());
    }
}
