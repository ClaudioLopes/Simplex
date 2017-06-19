/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escalonamentolu;

    public class EscalonaTransposta {
    private double at[][];
    private double mat[][];
    private double y[];
    private double b[];
    private double[] x;
    private int[] p;
    private int ind = 0;
    private double maior;
    
    /*
       Construtor da classe
    */
    public EscalonaTransposta(double[][] a, double[] B){
        mat = new double[a.length][a.length + 1];
        b = new double[B.length];
        this.b = B;
        p = new int[a.length];
       for(int i = 0; i < mat.length; i++){
           for(int j = 0; j < mat.length; j++){
                  mat[i][j] = a[i][j];
           }
           mat[i][mat.length] = b[i];
           p[i] = i;
       }
    }
    
    /*
       Encontra o pivor 
    */
    public void pivo(int i){
        ind  = i;
        maior = mat[i][i];
        for(int j = i; j < mat.length; j++){
            if(Math.abs(mat[j][i]) > Math.abs(maior)){
                maior = mat[j][i];
                ind = j;
            }
        }
    }
    
    /*
       Faz a permuta da matriz A
    */
    public void permuta(int i){
        double[] x = new double[mat.length];
        int aux = 0 ;
        for(int j = 0; j < mat.length; j++){
            x[j] = mat[ind][j];
            mat[ind][j] = mat[i][j];
            mat[i][j] = x[j];
        }
        aux = p[i];
        p[i] = p[ind];
        p[ind] = aux;
    }
    
    // faz a transposta do vetor p
    public void transporP(){
        double[][] per = new double[mat.length][mat.length];
        for(int i = 0; i < mat.length; i++){
            for(int j = 0; j < mat.length; j++){
                if(p[i] == j){
                    per[i][j] = 1;
                }else{
                    per[i][j] = 0;
                }
            }
        }
        for(int i = 0; i < mat.length; i++){
            for(int j = 0; j < mat.length; j++){
                  if(per[i][j] == 1){
                      p[j] = i;
                  } 
            }
        }
    }
    
    /*
       Escalona a matriz A gerando a matriz LU 
    */
    public void escalona(){
        double m = 0;
        for(int k = 0; k < mat.length-1; k++){
            pivo(k);
            permuta(k);
            for(int i = k+1; i < mat.length; i++){
                m = mat[i][k]/maior;
                for(int j = k; j < mat[0].length-1; j++){
                    mat[i][j] = mat[i][j] - m * mat[k][j];
                }
                if(i != k){
                    mat[i][k] = m;
                }
            }
        }
    }
    
    /*
       Faz a transposta da matriz LU
    */
    public void matrizTransposta(){
       at = new double[mat.length][mat.length];
       System.out.println();
       System.out.println("matriz transposta LU");
	for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat.length; j++) {
                at[j][i] = mat[i][j];
            }
        }
        for(int i = 0; i < at.length; i++){
            for(int j = 0; j < at.length; j++){
                System.out.print(at[i][j] + "     ");
            }
            System.out.println();
        }
    }
    
    /*
       Resolva Uty = b pelo metodo de substituiçao sucessiva
    */
    public void resolveUty(){
       double h[][] = new double[at.length][at.length];
       for(int i = 0; i < at.length; i++){
            for(int j = 0; j < at.length; j++){
                if(j > i){
                    h[i][j] = 0;
                }else {
                    h[i][j] = at[i][j];
                }
            }
        }
        y = new double[at.length];
        for(int k = 0; k < at.length; k++){
            y[k] = b[k];
            for(int j = 0; j < k; j++){
                y[k] += -h[k][j]*y[j];
            }
            y[k] = y[k]/h[k][k]; 
        }
    }
   
    /*
       Resolva Ltx = y pelo metodo de substituiçao progreciva
    */
    public void resolveLtx(){
       for(int i = 0; i < at.length; i++){
            for(int j = 0; j < at.length; j++){
                if(i == j){
                    at[i][j] = 1;
                }else if(j < i){
                    at[i][j] = 0;
                }
                
            }
        }
       x = new double[at.length];
        for(int k = at.length - 1; k >= 0; k--){
            x[k] = y[k];
            for(int j = k + 1; j < at.length; j++){
                x[k] = x[k] - at[k][j] * x[j];
            }
            x[k] = x[k]/1.0;
        }
        System.out.println();
        System.out.println("vetor lambiada");// Imprime o vetro resposta lambida
        for(int i = 0; i < x.length; i++){
            System.out.println(x[p[i]]);
        }
   }
}
