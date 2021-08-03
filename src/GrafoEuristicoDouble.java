import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class GrafoEuristicoDouble {

    
    public static void main(String[] args) {
        
        // BIENVENIDA Y SELECCION DE ARCHIVO
        JOptionPane.showMessageDialog(null, "Porfavor seleccione el archivo tps con ciudades y coordenadas...",
                "Bienvenido al programa - algoritmo agente viajero", JOptionPane.INFORMATION_MESSAGE);
        JFileChooser fc= new JFileChooser();
        
        // DECLARACION DE VARIABLES
        File archivo;
        FileReader fr;
        BufferedReader br;
        
        int dimension = 0, cont = 0;
        Double matriz[][] = null, coords[][] = null; 
        
        
        // LECTURA DE ARCHIVO
        if(((JFileChooser) fc).showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
            File fichero = ((JFileChooser) fc).getSelectedFile();
            System.out.println("Archivo abierto: "+fichero.getName());
            
            try {
                archivo = new File (fichero.getAbsolutePath());
                fr = new FileReader (archivo);
                br = new BufferedReader(fr);

                String linea;
                while((linea=br.readLine())!=null){
                
                   if(linea.contains("DIMENSION")){
                       dimension = Integer.parseInt(linea.split(": ")[1]);  
                       break;
                   } 
                }
                
                matriz = new Double[dimension][dimension];
                coords= new Double[2][dimension];
                
                fr = new FileReader (archivo);
                br = new BufferedReader(fr);
                
                while((linea=br.readLine())!=null){
                    linea = eliminarEspacios(linea);
                    
//                    System.out.println(linea);
                   
                    try {
                        if(Character.isDigit(linea.charAt(0))){
                            
                            coords[0][cont]= Double.parseDouble(linea.split(" ")[1]);
                            coords[1][cont]= Double.parseDouble(linea.split(" ")[2]);
                            cont++;
                        }
                    } catch (Exception e) {
                      
                    }
                }  
            }
            catch(IOException | NumberFormatException e){
                System.out.println(e);
            }
            
            
//            // IMPRIMIR COORDENADAS GUARDADAS
//            for (int i = 0; i < dimension; i++) {
//                System.out.println(i+"| X: "+coords[0][i]+" Y: "+coords[1][i]);
//            }
            System.out.println("------------------------------------------------------------");
            
            // RELLENAR MATRIZ DE ADYACENCIA
            Double distancia;
            for (int i = 0; i < dimension-1; i++){
                for (int j = i+1; j < dimension; j++){
                    
                    distancia = distanciaEntrePuntos(coords[0][i], coords[1][i], coords[0][j], coords[1][j]);
                    matriz[i][j]= distancia;
                    matriz[j][i]= distancia;
                }
            }
            // IMPRIMIENDO MATRIZ DE ADYACENCIA
//            for (int i = 0; i < dimension; i++){
//                for (int j = 0; j < dimension; j++){
//                    System.out.print("["+matriz[i][j]+"]");
//                }
//                System.out.println("");
//            }
        }
        
        // INICIO DE ALGORITMO -------------------------------------------------
        
        // etiqueta : vector para marcar ciudades visitadas
        int etiqueta[]= new int[dimension]; 
        for (int i = 0; i < dimension; i++){    
            etiqueta[i] = 0;    // rellenar vector
        }
        
        // tour : matriz adyacente del tour
//        Double tour[][] = new Double[dimension][dimension];
        
        // caminoTorur : ciudades del tour
        String caminoTour = "Tour:\n";
        
        // cd : Ciudad actual
        int cd = 0, pos = 1;
        Double distMenor,costoTour = 0.0;
        
        etiqueta[cd] = 1; // Marcar ciudad 0 como visitada
        
        // Ciclo para recorrer todas las ciudades
        cont = 0;
        while(cont!=dimension){
            
            distMenor = 100000.00;
            // Ciclo para buscar la arista menor de la ciudad cd con todas las demas
            for (int i = 0; i < dimension; i++){
                if(cd!=i && matriz[cd][i]<distMenor && etiqueta[i]==0){
                    
                    distMenor = matriz[cd][i];
                    pos=i;
                }
            }
            // Finalizando el ciclo tenemos el tamaño de la arista 'distMenor' 
            // y la ciudad con la cual conecta 'pos'
            
            try {
                // AGREGA LAS ARISTAS A LA MATRIZ 'tour'
//                tour[cd][pos] = matriz[cd][pos];
//                tour[pos][cd] = matriz[pos][cd];
                
                // SUMA EL TAMAÑO DE LA ARISTA AL TOTAL DEL TOUR
                costoTour += matriz[cd][pos];
                
                // AGREGAMOS LA CIUDAD AL STRING 'caminoTour'
                caminoTour += (cd+1)+"\n";
            } catch (Exception e) {
            }
            
            cd = pos; // Actualizar a siguiente ciudad
            etiqueta[pos] = 1;  // Marcar ciudad pos como visita
            cont++; // Aumentando contador de ciudades visitadas
        }
        
        // Uniendo ultima ciudad con la ciudad de inicio
//        tour[0][pos] = matriz[0][pos];
//        tour[pos][0] = matriz[pos][0];
        
        costoTour += matriz[0][pos];
        
        System.out.println("------------------------------------------------------------");
        System.out.println(caminoTour);
        /*
        for (int i = 0; i < dimension; i++){
            for (int j = 0; j < dimension; j++){
                System.out.print("["+tour[i][j]+"]");
            }
            System.out.println("");
        }*/
        
        // FIN DEL ALGORITMO
//        JOptionPane.showMessageDialog(null, "Total costo del tour: "+costoTour,
//                "Resultado del algoritmo", JOptionPane.INFORMATION_MESSAGE);
        System.out.println("\nTotal costo del tour: "+costoTour);
    }
    
    
    // METODO DISTANCIA ENTRE PUNTOS
    public static Double distanciaEntrePuntos(Double x1,Double y1,Double x2,Double y2){
        
        Double Caldistancia = (Double) Math.sqrt( Math.pow(x1-x2, 2)+Math.pow(y1-y2, 2));
        return Caldistancia;
    }
    
    // METODO ELIMINAR ESPACIOS
    public static String eliminarEspacios(String cadena){
        String cad = "";
        boolean a = false;
        
        for(int i=0; i<cadena.length(); i++){
            if(cadena.charAt(i)!=' '){
                a = true;
            }
            if(a == true){
                if(i!=0){
                    if(cadena.charAt(i-1)!=' ' || cadena.charAt(i)!=' '){
                        cad += cadena.charAt(i);
                    }
                }
                else{
                    cad += cadena.charAt(i);
                }
            }
        }
        return cad;
    }
}
