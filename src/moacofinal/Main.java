package moacofinal;

public class Main {

    public static int ejecucion = 0;

    public static void main(String[] args) {
        // TODO code application logic here
        String parametros = new String(new char[100]); // indica el archivo de parametros a utilizar
        MOACO alg; // instancia del algoritmo
        Problem prob; // instancia del problema a resolver
        String cad = new String(new char[300]);
        String pr = new String(new char[60]);

        String[] arrayArchivoProblema = {"KROAB100.TSP.TXT", "kroac100.tsp.txt"};
        String ruta = "";
        String subRuta = "";
        String archivoParametros = "parametros_tsp.txt";
        String problemaEjecucion = "TSP";
        String algoritmoEjecucion = "MOACS";
        String archivoProblema;

        for (int m = 0; m < 2; m++) { //recorrer array de ArchivoProblema

            parametros = ruta + archivoParametros;
            pr = arrayArchivoProblema[m]; // 0=KROAB, 1=kroac
            archivoProblema = pr;
            System.out.println();
            System.out.println("Ejecutandose con los parametros:");
            System.out.println();
            System.out.println("archivoProblema = " + archivoProblema);
            System.out.println("archivoParametros = " + archivoParametros);
            System.out.println("algoritmoEjecucion = " + algoritmoEjecucion);
            System.out.println();

            String cadenaYtrue = ruta + subRuta + "YTRUE-" + pr + ".txt";
            System.out.println("Ejecutando... ");
            for (ejecucion = 1; ejecucion < 6; ejecucion++) {
                System.out.print(" " + ejecucion);
                cad = ruta + subRuta + pr + "-" + algoritmoEjecucion + "(" + ejecucion + ").txt";
                prob = new TSP(ruta + archivoProblema);
                alg = new MOACS(prob, parametros);
                alg.ejecutarTSP();
                alg.pareto.agregarSoluciones(prob, cad);
                alg.pareto.agregarSoluciones(prob, cadenaYtrue);
            }
            System.out.println("\nPareto Generado: " + cad);
            System.out.println("Pareto Ytrue Generado: " + cadenaYtrue);
            System.out.println("Ejecución Finalizada...");

        }
        try{
            metricas.Metricas.main();
        }catch(Exception e){
            System.out.printf("\nError al leer archivos para las metricas");
        }
        
    }
}
