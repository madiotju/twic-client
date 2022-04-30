package com.twicclient.controller;

import com.twicclient.bean.Ville;
import com.twicclient.form.DeleteForm;
import com.twicclient.form.ModifForm;
import com.twicclient.form.VilleDistanceForm;
import com.twicclient.form.VilleForm;
import com.twicclient.service.VilleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;


@Controller
public class VilleController {

    private final VilleService villeService;

    public VilleController(){
        this.villeService = new VilleService();
    }

    @GetMapping("/home")
    public String villes(@RequestParam(required  = false, value="codePostal") String codePostal,
                         Model model, VilleDistanceForm villeDistanceForm) {

        String[] villes = this.villeService.getNomsVillesFromCodePostal(codePostal);

        model.addAttribute("villes", villes);
        model.addAttribute("villeDistanceForm",villeDistanceForm);

        return "home";
    }

    @PostMapping("/home")
    public String submitVilles(@Valid VilleDistanceForm villeDistanceForm, Model model){
        Ville[] villes = this.villeService.getVillesFromForm(villeDistanceForm);
        model.addAttribute("ville1",villes[0]);
        model.addAttribute("ville2",villes[1]);
        model.addAttribute("distance",villeService.getDistance(villes[0],villes[1]));
        return "distance";
    }

    @GetMapping("/villes")
    public String home(Model model, ModifForm modifForm) {
        Ville[] villes = this.villeService.getInfosAllVilles();
        int nbVillesParPage = 50;
        int nbPages = villes.length/nbVillesParPage;
        Ville[][] listeVilles = new Ville[nbPages][nbVillesParPage];
        for (int i=0; i<nbPages; i++) {
            int indexDebut = nbVillesParPage*i;
            int indexFin = nbVillesParPage+nbVillesParPage*i;
            listeVilles[i] = Arrays.copyOfRange(villes,indexDebut,indexFin);
        }
        model.addAttribute("nbVillesParPage", nbVillesParPage);
        model.addAttribute("nbPages",nbPages);
        model.addAttribute("villes",listeVilles);
        model.addAttribute("modifForm",modifForm);
        return "villes";
    }

    @GetMapping("/nouvelleVille")
    public String nouvelleVille(Model model, VilleForm villeForm) {
        model.addAttribute("villeForm",villeForm);
        return "nouvelleVille";
    }

    @PostMapping("/nouvelleVille")
    public String saveVille(Model model, @Valid VilleForm villeForm, VilleDistanceForm villeDistanceForm) {
        villeService.saveVilleFromForm(villeForm);
        String[] villes = this.villeService.getNomsVillesFromCodePostal(null);

        model.addAttribute("villes", villes);
        model.addAttribute("villeDistanceForm",villeDistanceForm);
        return "home";
    }

    @GetMapping("/modifVille")
    public String getModifVille(Model model, VilleForm villeForm, @Valid ModifForm modifForm, DeleteForm deleteForm) {
        model.addAttribute("code",modifForm.getCode());
        model.addAttribute("villeForm",villeForm);
        Ville ville = this.villeService.getVilleFromCodeCommune(modifForm.getCode());
        model.addAttribute("ville",ville);
        model.addAttribute("deleteForm",deleteForm);
        return "modifVille";
    }

    @PostMapping("/modifVille")
    public String postModifVille(Model model, @Valid VilleForm villeForm, VilleDistanceForm villeDistanceForm) {
        this.villeService.updateVilleFromForm(villeForm);

        String[] villes = this.villeService.getNomsVillesFromCodePostal(null);

        model.addAttribute("villes", villes);
        model.addAttribute("villeDistanceForm",villeDistanceForm);
        return "home";
    }

    @PostMapping("/deleteVille")
    public String postDeleteVille(Model model,@Valid DeleteForm deleteForm, VilleDistanceForm villeDistanceForm) {
        this.villeService.deleteVille(deleteForm.getCodeCommune());

        String[] villes = this.villeService.getNomsVillesFromCodePostal(null);

        model.addAttribute("villes", villes);
        model.addAttribute("villeDistanceForm",villeDistanceForm);
        return "home";
    }




}
