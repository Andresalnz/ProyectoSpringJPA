package es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.Services;

import es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.model.Pilot;
import es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.repositories.PilotRepository;
import es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ImageService {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private PilotRepository pilotRepository;

    public boolean imageStore(MultipartFile file,Long id) throws IOException {

        String myFileName = id.toString()+"_"+file.getOriginalFilename();
        Path targetPath = Paths.get("./images/"+ myFileName).normalize();
        Files.copy(file.getInputStream(),targetPath, StandardCopyOption.REPLACE_EXISTING);
        Pilot pilot = pilotRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException(id.toString()));
        pilot.setImageURL("/download/"+myFileName);
        pilotRepository.save(pilot);

        return false;
    }

}
