package apap.propensi.mantra.contoller;

import apap.propensi.mantra.model.SuratModel;
import apap.propensi.mantra.model.UserModel;
import apap.propensi.mantra.service.SuratService;
import apap.propensi.mantra.service.UserService;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.source.ByteArrayOutputStream;
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

    @GetMapping("/upload-foto/{id}")
    public String uploadFoto(@PathVariable Long id, Model model) {
        SuratModel surat = suratService.getSuratById(id);
        model.addAttribute("surat", surat);
        return "surat/upload-foto";
    }

    @PostMapping("/upload/{id}")
    public String imageUpload(@PathVariable Long id, @RequestParam MultipartFile img, Model model, RedirectAttributes redirectAttributes) {
        SuratModel surat = suratService.getSuratById(id);
        surat.setFoto(img.getOriginalFilename());
        surat.setStatus(2);
        SuratModel updateSurat = suratService.updateSurat(surat);

        if (updateSurat != null) {
            try {
                File saveFile = new ClassPathResource("static/img").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + img.getOriginalFilename());
                Files.copy(img.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(surat.getFoto());

        model.addAttribute("surat", updateSurat);
        redirectAttributes.addFlashAttribute("successMessage", "Dokumen Berhasil Diupload");
        return "redirect:/surat/list";
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

    @GetMapping("/dokumen/{id}")
    public String dokumenSurat(@PathVariable Long id, Model model) {
        SuratModel surat = suratService.getSuratById(id);
        model.addAttribute("surat", surat);
        return "surat/dokumen";
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
//        return "surat/tolak";
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

        /* Do Business Logic*/

        SuratModel surat = suratService.getSurat(noSurat);

        /* Create HTML using Thymeleaf template Engine */

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

        /* Setup Source and target I/O streams */

        ByteArrayOutputStream target = new ByteArrayOutputStream();

        /*Setup converter properties. */
        ConverterProperties converterProperties = new ConverterProperties();

        /* Call convert method/ masih bermasalah, bikin ngelag*/
        HtmlConverter.convertToPdf(orderHtml, target, converterProperties);

        /* extract output as bytes */
        byte[] bytes = target.toByteArray();


        /* Send the response as downloadable PDF */

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(bytes);

    }
}
