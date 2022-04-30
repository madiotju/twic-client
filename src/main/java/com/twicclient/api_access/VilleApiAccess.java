package com.twicclient.api_access;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.twicclient.bean.Ville;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;


public class VilleApiAccess {
    private final Client client;
    private WebTarget target;
    private static final String URL = "http://localhost:8181/";

    public VilleApiAccess(){
        this.client = ClientBuilder.newClient();
    }

    private String[] getAllVilles(){
        this.target = client.target(URL+"ville");
        String result = target.request(MediaType.APPLICATION_JSON).get(String.class);
        return this.getResultAsArray(result);
    }

    public String[] getVilles(String codePostal){
        if (codePostal == null){
            return this.getAllVilles();
        }
        this.target = client.target(URL+"ville?codePostal="+ codePostal);
        String result = target.request(MediaType.APPLICATION_JSON).get(String.class);
        return this.getResultAsArray(result);
    }

    private String[][] getInfosAllVilles(){
        this.target = client.target(URL+"villes");
        String result = target.request(MediaType.APPLICATION_JSON).get(String.class);
        return this.getResultAsDoubleArray(result);
    }

    public String[][] getInfosVilles(String codeCommune) {
        if (codeCommune == null){
            return this.getInfosAllVilles();
        }
        this.target = client.target(URL+"villes?codeCommune="+ codeCommune);
        String result = target.request(MediaType.APPLICATION_JSON).get(String.class);
        return this.getResultAsDoubleArray(result);
    }

    public String[] getLocation(String nomVille) {
        nomVille = nomVille.replace(" ","_");
        this.target = client.target(URL+"villePosition?nomVille="+ nomVille);
        String result = target.request(MediaType.APPLICATION_JSON).get(String.class);
        return this.getResultAsArray(result);
    }

    public void saveVille(Ville ville) {
        com.sun.jersey.api.client.Client client1 = com.sun.jersey.api.client.Client.create();
        WebResource webResource = client1.resource(URL+"villepost");
        ville.setNom(ville.getNom().replace(" ","_"));
        String input = "?Code_commune_INSEE="+ville.getCode()+
        "&Nom_commune="+ville.getNom()+
                "&Code_postal="+ville.getCodePostal()+
                "&Libelle_acheminement="+ville.getLibelle()+
                "&Ligne_5="+ville.getLigne5()+
                "&Latitude="+ville.getLatitude()+
                "&Longitude="+ville.getLongitude();

        webResource.type("application/json").post(ClientResponse.class,input);
    }

    public void updateVille(Ville ville){
        com.sun.jersey.api.client.Client client1 = com.sun.jersey.api.client.Client.create();
        WebResource webResource = client1.resource(URL+"villeput");
        ville.setNom(ville.getNom().replace(" ","_"));
        String input = "{\"Code_commune_INSEE\":\""+ville.getCode()+
                "\",\"Nom_commune\":\""+ville.getNom()+
                "\",\"Code_postal\":\""+ville.getCodePostal()+
                "\",\"Libelle_acheminement\":\""+ville.getLibelle()+
                "\",\"Ligne_5\":\""+ville.getLigne5()+
                "\",\"Latitude\":\""+ville.getLatitude()+
                "\",\"Longitude\":\""+ville.getLongitude()+"\"}";

        webResource.type("application/json").put(ClientResponse.class,input);
    }

    public void deleteVille(String codeCommune){
        this.target = client.target(URL+"villedelete/"+ codeCommune);
        target.request(MediaType.APPLICATION_JSON).delete();
    }

    private String[] getResultAsArray(String result) {
        result = result.replace("\"","");
        result = result.replace("[","");
        result = result.replace("]","");
        return result.split(",");
    }

    private String[][] getResultAsDoubleArray(String result) {
        String[] villes = result.split("]\\,\\[");
        String[][] resultat = new String[villes.length][7];
        int i=0;
        for (String ville : villes) {
            ville = ville.replace("\"","");
            ville = ville.replace("[","");
            ville = ville.replace("]","");
            String[] infosVille = ville.split(",");
            resultat[i] = infosVille;
            i++;
        }
        return resultat;
    }





}
