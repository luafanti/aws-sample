package pl.lua.aws.core.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import pl.lua.aws.core.domain.TournamentGallery;
import pl.lua.aws.core.service.GalleryService;

import java.util.List;

@Controller
@Slf4j
public class GalleryController {

    @Autowired
    private GalleryService galleryService;

    @RequestMapping(value = "tournament-photo", method = RequestMethod.POST)
    public String uploadFile(TournamentGallery gallery) {
        List<MultipartFile> photos = gallery.getUploadedPhotos();
        try {
            if(photos!=null && photos.size()!=0){
                photos.stream().forEach(photo->{
                    if(photo.getSize()!=0 && photo.getContentType().contains("image")){
                        galleryService.saveTournamentPhotos(photo,gallery.getTournamentId());
                    }else{
                        log.warn("Photo {} have wrong extension or is empty",photo.getName());
                    }
                });
            }else{
                log.warn("Tournament photos is empty");
            }
        }
        catch (Exception e) {
            log.error("Error during tournament photos upload. {} ", e.getMessage());
            return "redirect:/tournament/"+gallery.getTournamentId();
        }

        return "redirect:/tournament/"+gallery.getTournamentId();
    }

}
