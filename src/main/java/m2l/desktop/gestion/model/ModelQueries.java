package m2l.desktop.gestion.model;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ModelQueries {

    // URL de l'API à modifier pour correspondre à votre API
    private static String API_URL = "http://localhost:8080/SIO2/laravel/e6/site_m2l_12.4/public/api/";


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

    public static void updateClimatiseur(Climatiseur c) throws IOException
    {
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
        }catch (Exception e) {
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
}
