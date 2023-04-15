package apap.propensi.mantra.contoller;

import apap.propensi.mantra.model.SuratModel;
import apap.propensi.mantra.model.UserModel;
import apap.propensi.mantra.service.SuratService;
import apap.propensi.mantra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/surat")
public class SuratController {

    @Autowired
    private SuratService suratService;

    @Autowired
    private UserService userService;

    @GetMapping("/upload-foto/{id}")
    public String uploadFoto(@PathVariable Long id, Model model) {
        SuratModel surat = suratService.getSuratById(id);
        model.addAttribute("surat", surat);
        return "surat/upload-foto";
    }

    @PostMapping("/upload/{id}")
    public String imageUpload(@PathVariable Long id, @RequestParam MultipartFile img, Model model) {
        SuratModel surat = suratService.getSuratById(id);
        surat.setFoto(img.getOriginalFilename());
        surat.setStatus(2);
        SuratModel updateSurat = suratService.updateSurat(surat);

        if (updateSurat != null) {
            try {
                File saveFile = new ClassPathResource("static/img").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + img.getOriginalFilename());
                System.out.println(path);
                Files.copy(img.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(surat.getFoto());

        model.addAttribute("surat", updateSurat);
        return "surat/upload-berhasil";
    }

    @GetMapping("/list")
    public String listSurat(Model model){
        var auth = SecurityContextHolder.getContext().getAuthentication();
        UserModel userSession = userService.getUserByUsername(auth.getName());
        List<SuratModel> listSurat = suratService.getListSuratOrderByStatus();
        List<SuratModel> listSuratDriver = new ArrayList<>();
        for (int i = 0; i < listSurat.size(); i++) {
            if (listSurat.get(i).getDriver().getUsername().equals(userSession.getUsername())) {
                listSuratDriver.add(listSurat.get(i));
            }
        }
        model.addAttribute("listSuratDriver", listSuratDriver);
        return "surat/list";
    }
}
