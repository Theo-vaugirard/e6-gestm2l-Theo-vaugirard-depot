package m2l.desktop.gestion.model;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/*
🌐 4. ACCÈS AUX DONNÉES API (ModelQueries)
-----------------------------------------------------------
- Appelle l’API REST
- Récupère les salles au format JSON
- Convertit en objets Java (Salle)
 */

public class ModelQueries {

    // URL de l'API à modifier pour correspondre à votre API
    private static String API_URL = "http://localhost:8080/e6-site-m2l-Theo-vaugirard/public/api/";


    /************************************  INTERVENTIONS  ****************************************/
    public static List<Intervention> getInterventionsFromApi() throws IOException {
        //connexion à l'API et récupération des données
        String apiUrl = API_URL + "interventions";

        URL url = new URL(apiUrl);
        List<Intervention> liste_des_interventions = null;

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");


        if (conn.getResponseCode() == 200) {

            System.out.println("ModelQueries :  Response from API: " + conn.getResponseMessage() + "Chaine :" + conn.getInputStream().toString());

            String jsonResponse = Tools.convertInputStreamToString(conn.getInputStream());

            liste_des_interventions = Tools.InterventionsJSonToList(jsonResponse);
        } else {
            System.out.println("ModelQueries : Failed : HTTP error code : "
                    + conn.getResponseCode());
        }
        conn.disconnect();
        return liste_des_interventions;
    }


    /************************************  SALLES  ****************************************/
    public static List<Salle> getSallesFromApi() {
        //connexion à l'API et récupération des données
        String apiUrl = API_URL + "salles";

        URL API = null;
        List<Salle> listeSalles = null;

        try {
            API = new URL(apiUrl);

            HttpURLConnection conn = null;

            conn = (HttpURLConnection) API.openConnection();

            conn.setRequestMethod("GET");

            conn.setRequestProperty("Accept", "application/json");


            if (conn.getResponseCode() == 200) {

                System.out.println("ModelQueries :  Response from API: " + conn.getResponseMessage() + "Chaine :" + conn.getInputStream().toString());

                String jsonResponse = Tools.convertInputStreamToString(conn.getInputStream());

                System.out.println("ModelQueries : JSON Response : " + jsonResponse);

                listeSalles = Tools.SallesJSonToList(jsonResponse);
            } else {
                System.out.println("ModelQueries : Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return listeSalles;
    }

    public static Salle getSalleById(int id) {
        String apiUrl = API_URL + "salles/" + id;

        URL url = null;
        Salle salle = null;
        try {
            url = new URL(apiUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() == 200) {
                String jsonResponse = Tools.convertInputStreamToString(conn.getInputStream());
                salle = new Gson().fromJson(jsonResponse, Salle.class);

            }

        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return salle;
    }

    //************************************  CLIMATISEURS  ****************************************/
    public static List<Climatiseur> getClimatiseursFromApi() throws IOException {
        String apiUrl = API_URL + "climatiseurs";

        URL url = new URL(apiUrl);
        List<Climatiseur> liste_des_climatiseurs = new ArrayList<>();

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");


        if (conn.getResponseCode() == 200) {

            System.out.println("ModelQueries :  Response from API: " + conn.getResponseMessage() + "Chaine :" + conn.getInputStream().toString());

            String jsonResponse = Tools.convertInputStreamToString(conn.getInputStream());

            liste_des_climatiseurs = Tools.ClimatiseursJSonToList(jsonResponse);
        } else {
            System.out.println("ModelQueries : Failed : HTTP error code : "
                    + conn.getResponseCode());
        }
        conn.disconnect();
        return liste_des_climatiseurs;
    }


    public static void insertClimatiseurViaApi(Climatiseur c) {
        try {
            String apiUrl = API_URL + "climatiseurs";

            URL url = new URL(apiUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);
            String jsonInputString = new Gson().toJson(c);

            System.out.println("Insertion du climatiseur via l'API : " + jsonInputString);

            try (java.io.OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            } catch (IOException e) {
                e.printStackTrace();
            }

            int responseCode = conn.getResponseCode();

            System.out.println("Response Code : " + responseCode);

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateClimatiseur(Climatiseur c) throws IOException {
        try {
            String apiUrl = API_URL + "climatiseurs/" + c.getId();

            URL url = new URL(apiUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);
            String jsonInputString = new Gson().toJson(c);
            try (java.io.OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            } catch (IOException e) {
                e.printStackTrace();
            }

            int responseCode = conn.getResponseCode();

            System.out.println("Response Code : " + responseCode);

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void deleteClimatiseur(Climatiseur selectedItem) {
        try {
            String apiUrl = API_URL + "climatiseurs/" + selectedItem.getId();

            URL url = new URL(apiUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();

            System.out.println("Response Code : " + responseCode);

            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Salle ajouterSalleApi(Salle salle) {
        try {
            // Utiliser la constante API_URL comme pour les climatiseurs
            String apiUrl = API_URL + "salles";
            URL url = new URL(apiUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            // Utiliser Gson comme pour les climatiseurs (plus propre)
            String jsonInputString = new Gson().toJson(salle);
            System.out.println("Insertion de la salle via l'API : " + jsonInputString);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            } catch (IOException e) {
                e.printStackTrace();
            }

            int responseCode = conn.getResponseCode();
            System.out.println("Response Code : " + responseCode);

            if (responseCode == 200 || responseCode == 201) {
                System.out.println("Salle ajoutée avec succès !");
                conn.disconnect();
                return salle;
            } else {
                System.out.println("Erreur POST : " + responseCode);
                // Lire le message d'erreur si disponible
                if (conn.getErrorStream() != null) {
                    String errorResponse = Tools.convertInputStreamToString(conn.getErrorStream());
                    System.out.println("Détail erreur: " + errorResponse);
                }
                conn.disconnect();
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Dans ModelQueries.java, ajoutez cette méthode après ajouterSalleApi

    public static boolean deleteSalleApi(int id) {
        try {
            String apiUrl = API_URL + "salles/" + id;
            URL url = new URL(apiUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");

            System.out.println("Suppression de la salle ID: " + id);
            System.out.println("URL appelée: " + apiUrl);

            int responseCode = conn.getResponseCode();
            System.out.println("Response Code : " + responseCode);

            if (responseCode == 200 || responseCode == 204) {
                System.out.println("Salle supprimée avec succès !");
                conn.disconnect();
                return true;
            } else {
                System.out.println("Erreur DELETE : " + responseCode);
                if (conn.getErrorStream() != null) {
                    String errorResponse = Tools.convertInputStreamToString(conn.getErrorStream());
                    System.out.println("Détail erreur: " + errorResponse);
                }
                conn.disconnect();
                return false;
            }

        } catch (Exception e) {
            System.err.println("Exception lors de la suppression: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}
