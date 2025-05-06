import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;

import java.io.FileInputStream;

public class NotificadorFatigaMQ {
    public static void main(String[] args) {
        try {
            FileInputStream serviceAccount = new FileInputStream("C:\\Users\\yepiz\\OneDrive\\Desktop\\Sistemas Distribuidos\\firebaseFatiga.json");

            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://avance-4111c-default-rtdb.firebaseio.com/")
                .build();

            FirebaseApp.initializeApp(options);
            System.out.println("🔥 Conexión con Firebase iniciada...");

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("sensorMyoWare");

            ref.addValueEventListener(new ValueEventListener() {
                public void onDataChange(DataSnapshot snapshot) {
                    Object valueObj = snapshot.getValue();
                    if (valueObj == null) return;

                    String valorStr = valueObj.toString();
                    int valor = Integer.parseInt(valorStr);

                    System.out.println("Valor recibido: " + valor);

                    if (valor > 1100) {
                        EnviarAlertaRabbit.enviar("¡FATIGA DETECTADA! Valor: " + valor);
                    }
                }

                public void onCancelled(DatabaseError error) {
                    System.err.println("Error: " + error.getMessage());
                }
            });

            // Mantener el programa vivo
            Thread.currentThread().join();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

