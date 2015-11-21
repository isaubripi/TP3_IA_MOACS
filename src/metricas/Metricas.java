
package metricas;

import java.io.*;
import java.util.ArrayList;

public class Metricas {
    
    public static ArrayList<Double> xTrue = new ArrayList<Double>();
    public static ArrayList<Double> yTrue = new ArrayList<Double>();
    public static ArrayList<Double> xFrente = new ArrayList<Double>();
    public static ArrayList<Double> yFrente = new ArrayList<Double>();
    public static ArrayList<Double> m1 = new ArrayList<Double>();
    public static ArrayList<Double> m2 = new ArrayList<Double>();
    public static ArrayList<Double> m3 = new ArrayList<Double>();
    public static ArrayList<Double> error = new ArrayList<Double>();
    
    public static double distancia(double x, double y, double xT, double yT){
        double distancia, temp;
        temp = Math.pow(x-xT, 2) + Math.pow(y-yT, 2);
        distancia = Math.pow(temp, 0.5);
        return distancia;
    }
    
    public static double getM1(){
        double min, m1;
        double suma = 0;
        double dist;
        for (int i=0; i<xFrente.size(); i++){
            double x = xFrente.get(i);
            double y = yFrente.get(i);
            min = 10000000;
            for (int j=0; j<xTrue.size(); j++){
                double xT = xTrue.get(j);
                double yT = yTrue.get(j);
                dist = distancia(x,y,xT,yT);
                if (dist < min)
                    min = dist;
            }
            suma = suma + min;
        }
        m1 = suma / xFrente.size();
        return m1;
    }
    
    public static double getM2(){
        double sigma, m2;
        int pos1 = 0;
        int pos2 = 0;
        int contador = 0;
        double mejorF1 = 0;
        double mejorF2 = 0;
        for (int i=0; i < xTrue.size(); i++){
            if (xTrue.get(i) > mejorF1){
                mejorF1 = xTrue.get(i);
                pos1 = i;
            }
            if (yTrue.get(i) > mejorF2){
                mejorF2 = yTrue.get(i);
                pos2 = i;
            }
        }
        double ext1x = xTrue.get(pos1);
        double ext1y = yTrue.get(pos1);
        double ext2x = xTrue.get(pos2);
        double ext2y = yTrue.get(pos2);
        sigma = 0.1 * distancia(ext1x, ext1y, ext2x, ext2y);
        for (int i=0; i < xFrente.size(); i++){
            for (int j=0; j < xFrente.size(); j++){
                if (distancia(xFrente.get(i), yFrente.get(i), xFrente.get(j), yFrente.get(j)) > sigma)
                    contador++;
            }
        }
        m2 = contador / (xFrente.size() - 1);
        return m2;
    }
    
    public static double getM3(){
        double m3;
        double max=0;
        double suma=0;
        double dist, x, y, xPrima, yPrima;
        for (int i=0; i < xFrente.size(); i++){
            x = xFrente.get(i);
            y = yFrente.get(i);
            
            for (int j=0; j<xFrente.size(); j++){
                xPrima = xFrente.get(j);
                yPrima = yFrente.get(j);
                dist = distancia(x, y, xPrima, yPrima);
                if (dist > max)
                    max = dist;
            }
            suma = suma + max;
        }
        m3 = Math.pow(suma, 0.5);
        return m3;
    }
        
    public static double getError(){
        int colaboracion = 0;
                double n1;
                double n2;
                double n3;
                double n4;
                for (int i = 0; i < xFrente.size(); i++) {
                    n1 = xFrente.get(i);
                    n3 = yFrente.get(i);
                    for (int j = 0; j < xTrue.size(); j++) {
                        n2 = xTrue.get(j);
                        n4 = yTrue.get(j);
                        if (n1 == n2 && n3 == n4) {
                            colaboracion++;
                        }
                    }
                }
                double Error = 1 - colaboracion / xFrente.size();
        return Error;
    }

    public static void main() throws FileNotFoundException, IOException {

        

        String archivoTrue = "";
        String archivo = "";
        for (int k = 0; k < 2; k++) {
            String archivoTrue1="";
            
            if (k==0){
                archivoTrue1 = archivoTrue + "YTRUE-kroac100.tsp.txt.txt";
                archivo = "kroac100.tsp.txt-MOACS(";
                System.out.println("\n\n\nIntancia: kroac");
            }else{
                archivoTrue1 = archivoTrue + "YTRUE-KROAB100.TSP.TXT.txt";
                archivo = "KROAB100.TSP.TXT-MOACS(";
                System.out.println("\n\n\nIntancia: KROAB");
            } 
            FileReader f = new FileReader(archivoTrue1);
            BufferedReader b = new BufferedReader(f);
            String linea;
            String[] par;

            while ((linea = b.readLine()) != null) {
                par = linea.split("\t");
                xTrue.add(Double.valueOf(par[0]));
                yTrue.add(Double.valueOf(par[1]));
            }

            b.close();
            for (int eje = 1; eje <= 5; eje++) {
                String archivo1="";
                archivo1 = archivo + Integer.toString(eje) + ").txt";
                FileReader file = new FileReader(archivo1);
                BufferedReader bu = new BufferedReader(file);

                while ((linea = bu.readLine()) != null) {
                    par = linea.split("\t");
                    xFrente.add(Double.valueOf(par[0]));
                    yFrente.add(Double.valueOf(par[1]));
                }

                bu.close();
                m1.add(getM1());
                m2.add(getM2());
                m3.add(getM3());
                error.add(getError());
                System.out.printf("\nEjecucion: %d", eje);
                System.out.printf("\nFrente: %d soluciones\nTrue: %d soluciones", xFrente.size(), xTrue.size());
                System.out.printf("\nM1: %f\nM2: %f\nM3: %f\nError: %f\n", getM1(), getM2(), getM3(), getError());

            }
            double sumM1=0; double promM1=0; double sumM2=0; double promM2=0;
            double sumM3=0; double promM3=0; double sumError=0; double promError = 0;
            int l;
            for(l=0; l<m1.size(); l++){
                sumM1 = sumM1+m1.get(l);
                sumM2 = sumM2+m2.get(l);
                sumM3 = sumM1+m3.get(l);
                sumError = sumError+error.get(l);
            }
            promM1 = sumM1/m1.size();
            promM2 = sumM2/m1.size();
            promM3 = sumM3/m1.size();
            promError = sumError/m1.size();
            System.out.printf("\nM1: %f\nM2: %f\nM3: %f\nError: %f", promM1, promM2, promM3, promError);
        }
    }
}
