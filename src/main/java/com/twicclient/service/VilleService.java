package com.twicclient.service;

import com.twicclient.api_access.VilleApiAccess;
import com.twicclient.bean.Ville;
import com.twicclient.form.VilleDistanceForm;
import com.twicclient.form.VilleForm;
import org.springframework.stereotype.Service;


@Service
public class VilleService {

    private final VilleApiAccess villeApiAccess;

    public VilleService(){
        this.villeApiAccess = new VilleApiAccess();
    }

    public String[] getAllVilles(){
        return this.villeApiAccess.getVilles(null);
    }

    public String[] getNomsVillesFromCodePostal(String codePostal){
        return this.villeApiAccess.getVilles(codePostal);
    }

    public Ville[] getInfosAllVilles(){
        return this.getVillesFromStringArray(this.villeApiAccess.getInfosVilles(null));
    }

    public String[] getInfosVillesFromCodeCommune(String codeCommune){
        return this.villeApiAccess.getInfosVilles(codeCommune)[0];
    }

    public Ville[] getVillesFromForm(VilleDistanceForm villeForm) {
        Ville ville1 = new Ville();
        Ville ville2 = new Ville();

        ville1.setNom(villeForm.getVille1name());
        ville2.setNom(villeForm.getVille2name());

        ville1.setLatitude(Double.parseDouble(villeApiAccess.getLocation(ville1.getNom())[0]));
        ville1.setLongitude(Double.parseDouble(villeApiAccess.getLocation(ville1.getNom())[1]));
        ville2.setLatitude(Double.parseDouble(villeApiAccess.getLocation(ville2.getNom())[0]));
        ville2.setLongitude(Double.parseDouble(villeApiAccess.getLocation(ville2.getNom())[1]));
        Ville[] villes = new Ville[2];
        villes[0] = ville1;
        villes[1] = ville2;
        return villes;
    }

    public Ville getVilleFromCodeCommune(String codeCommune){
        return this.getVillesFromStringArray(this.villeApiAccess.getInfosVilles(codeCommune))[0];
    }

    public void saveVilleFromForm(VilleForm villeForm) {
        Ville ville = getVilleFromForm(villeForm);

        this.villeApiAccess.saveVille(ville);
    }

    public void updateVilleFromForm(VilleForm villeForm) {
        Ville ville = getVilleFromForm(villeForm);

        this.villeApiAccess.updateVille(ville);
    }

    private Ville getVilleFromForm(VilleForm villeForm) {
        Ville ville = new Ville();

        ville.setCode(villeForm.getCode());
        ville.setNom(villeForm.getNom());
        ville.setLibelle(villeForm.getLibelle());
        ville.setCodePostal(villeForm.getCodePostal());
        ville.setLigne5(villeForm.getLigne5());
        ville.setLatitude(Double.parseDouble(villeForm.getLatitude()));
        ville.setLongitude(Double.parseDouble(villeForm.getLongitude()));
        return ville;
    }

    public double getDistance(Ville ville1, Ville ville2) {
        double x = (ville2.getLongitude()- ville1.getLongitude())*
                Math.cos((ville1.getLatitude()+ville2.getLatitude())/2);
        double y = ville2.getLatitude() - ville1.getLatitude();
        double z = Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
        return 1.852 * 60 * z;
    }

    public void deleteVille(String codeCommune) {
        this.villeApiAccess.deleteVille(codeCommune);
    }

    private Ville[] getVillesFromStringArray(String[][] data) {
        Ville[] villes = new Ville[data.length];
        for (int i=0; i<data.length; i++) {
            villes[i] = new Ville();
            villes[i].setCode(data[i][0]);
            villes[i].setNom(data[i][1]);
            villes[i].setCodePostal(data[i][2]);
            villes[i].setLibelle(data[i][3]);
            villes[i].setLigne5(data[i][4]);
            villes[i].setLatitude(Double.parseDouble(data[i][5]));
            villes[i].setLongitude(Double.parseDouble(data[i][6]));
        }
        return villes;
    }

}
