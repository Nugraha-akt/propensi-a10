package apap.propensi.mantra.contoller;

import apap.propensi.mantra.dto.GoogleDriveFileDTO;
import apap.propensi.mantra.model.SuratModel;
import apap.propensi.mantra.model.UserModel;
import apap.propensi.mantra.service.GoogleDriveFileService;
import apap.propensi.mantra.service.SuratService;
import apap.propensi.mantra.service.UserService;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
//import com.itextpdf.io.source.ByteArrayOutputStream;
import java.io.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/surat")
public class SuratController {

    @Autowired
    private SuratService suratService;

    @Autowired
    private UserService userService;

    @Autowired
    private GoogleDriveFileService googleDriveFileService;

    @GetMapping("/upload-foto/{id}")
    public String uploadFoto(@PathVariable Long id, Model model) {
        SuratModel surat = suratService.getSuratById(id);
        model.addAttribute("surat", surat);
        return "surat/upload-foto";
    }

    @GetMapping("/list")
    public String listSurat(@ModelAttribute("successMessage") String successMessage, Model model, RedirectAttributes redirectAttributes){
        if (!successMessage.isEmpty()) {
            System.out.println(successMessage);
            model.addAttribute("toastrSuccessMessage", successMessage);
            redirectAttributes.addFlashAttribute("successMessage", "");
        } else {
            model.addAttribute("toastrSuccessMessage", "");
        }
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

    @GetMapping("/ringkasan")
    public String ringkasanSurat(Model model) {
        Integer sumGenerated = suratService.getSumGenerated();
        Integer sumSubmitted= suratService.getSumSubmitted();
        Integer sumVerified = suratService.getSumVerified();
        Integer sumRejected = suratService.getSumRejected();
        model.addAttribute("sumGenerated", sumGenerated);
        model.addAttribute("sumSubmitted", sumSubmitted);
        model.addAttribute("sumVerified", sumVerified);
        model.addAttribute("sumRejected", sumRejected);
        return "surat/ringkasan";
    }

    @GetMapping("/verifikasi/{id}")
    public String verifikasiSurat(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        SuratModel surat = suratService.getSuratById(id);
        surat.setStatus(3);
        SuratModel updateSurat = suratService.updateSurat(surat);
        model.addAttribute("surat", updateSurat);
        redirectAttributes.addFlashAttribute("successMessage", "Dokumen Berhasil Diverifikasi");
        return "redirect:/surat/viewall";
    }

    @GetMapping("/tolak/{id}")
    public String tolakSurat(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        SuratModel surat = suratService.getSuratById(id);
        surat.setStatus(4);
        SuratModel updateSurat = suratService.updateSurat(surat);
        model.addAttribute("surat", updateSurat);
        redirectAttributes.addFlashAttribute("successMessage", "Dokumen Berhasil Ditolak");
        return "redirect:/surat/viewall";
    }
    @GetMapping("/viewall")
    public String listSuratAdmin(@ModelAttribute("successMessage") String successMessage, Model model, RedirectAttributes redirectAttributes) {
        if (!successMessage.isEmpty()) {
            System.out.println(successMessage);
            model.addAttribute("toastrSuccessMessage", successMessage);
            redirectAttributes.addFlashAttribute("successMessage", "");
        } else {
            model.addAttribute("toastrSuccessMessage", "");
        }
        List<SuratModel> surat = suratService.getListSurat();

        model.addAttribute("listSurat", surat);
        return "surat/viewall-surat";
    }

    @GetMapping(value = "/view/{noSurat}")
    private String viewSuratPage(@PathVariable("noSurat") long noSurat, Model model){
        SuratModel surat = suratService.getSurat(noSurat);
        long days = Duration.between(surat.getRequest().getDepartDate(), surat.getRequest().getReturnDate()).toDays();

        model.addAttribute("unit", surat);
        model.addAttribute("days", days);
        return "surat/view-surat";
    }

    @GetMapping(value = "/pdf/{noSurat}")
    public ResponseEntity<?> getPDF(@PathVariable("noSurat") long noSurat, HttpServletRequest request, HttpServletResponse response, Model model){


        SuratModel surat = suratService.getSurat(noSurat);


        TemplateEngine templateEngine = new SpringTemplateEngine();
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateEngine.setTemplateResolver(templateResolver);
        WebContext context = new WebContext(request, response, request.getServletContext());
        long days = Duration.between(surat.getRequest().getDepartDate(), surat.getRequest().getReturnDate()).toDays();
        context.setVariable("unit", surat);
        context.setVariable("days", days);
        String orderHtml = templateEngine.process("surat/surat-asli", context);

        ByteArrayOutputStream target = new ByteArrayOutputStream();

        ConverterProperties converterProperties = new ConverterProperties();

        HtmlConverter.convertToPdf(orderHtml, target, converterProperties);

        byte[] bytes = target.toByteArray();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(bytes);

    }

    @PostMapping(value = "/upload/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String suratUpload(
            @PathVariable Long id,
            @RequestParam("files")  List<MultipartFile> files,
            @RequestParam("shared") String shared,
            RedirectAttributes redirectAttributes) {
        SuratModel surat = suratService.getSuratById(id);
        for (MultipartFile file : files) {
            googleDriveFileService.upload(file, "Surat", Boolean.parseBoolean(shared));
            surat.setStatus(2);
            surat.setFoto(file.getOriginalFilename());
            suratService.updateSurat(surat);
        }
        redirectAttributes.addFlashAttribute("successMessage", "Dokumen Berhasil Diupload");
        return "redirect:/surat/list";
    }


    @GetMapping("/dokumen/{id}")
    public String dokumenSurat(@PathVariable Long id, Model model) {
        List<GoogleDriveFileDTO> Images = googleDriveFileService.findAllInFolder("14FApMDJNiBoMQeweJsAdrv_h7MYfkOAn");
        var auth = SecurityContextHolder.getContext().getAuthentication();
        SuratModel surat = suratService.getSuratById(id);
        List<GoogleDriveFileDTO> gambar = new ArrayList<>();
        for (int i=0; i < Images.size(); i++) {
            if (surat.getFoto().equals(Images.get(i).getName())) {
                gambar.add(Images.get(i));
            }
        }
        System.out.println(Images.toString());
        model.addAttribute("role", auth.getAuthorities().toArray()[0].toString());
        model.addAttribute("name", auth.getAuthorities().toArray()[0].toString());
        model.addAttribute("Images", Images);
        model.addAttribute("gambar", gambar);
        model.addAttribute("surat", surat);
        return "surat/dokumen";
    }

    //    @PostMapping("/upload/{id}")
//    public String imageUpload(@PathVariable Long id, @RequestParam MultipartFile img, Model model, RedirectAttributes redirectAttributes) {
//        SuratModel surat = suratService.getSuratById(id);
//        surat.setFoto(img.getOriginalFilename());
//        surat.setStatus(2);
//        SuratModel updateSurat = suratService.updateSurat(surat);
//
//        if (updateSurat != null) {
//            try {
//                File saveFile = new ClassPathResource("static/img").getFile();
//                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + img.getOriginalFilename());
//                Files.copy(img.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        System.out.println(surat.getFoto());
//
//        model.addAttribute("surat", updateSurat);
//        redirectAttributes.addFlashAttribute("successMessage", "Dokumen Berhasil Diupload");
//        return "redirect:/surat/list";
//    }

    //    @GetMapping("/dokumen/{id}")
//    public String dokumenSurat(@PathVariable Long id, Model model) {
//        SuratModel surat = suratService.getSuratById(id);
//        model.addAttribute("surat", surat);
//        return "surat/dokumen";
//    }
}

