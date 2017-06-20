/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escalonamentolu;

class Escalona {
    private double mat[][];
    private double l[][];
    private double y2[];
    private double b[];
    private int[] p;
    private double[] x;
    private double[] x2;
    private int ind = 0;
    private double maior;
    private double[] y;
    private double at[][];
    
    /*
       construtor da classe.Recebe uma matriz A e um vetor B 
    */
    public Escalona(double[][] a, double[] B){
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
    
    /*
       Escalona a matriz A gerando a matriz LU 
    */
    public void escalona(){
        double m = 0;
        for(int k = 0; k < mat.length-1; k++){
            pivo(k);//busca o pivor
            permuta(k);// permuta a matriz
            for(int i = k+1; i < mat.length; i++){
                m = mat[i][k]/maior;
                for(int j = k; j < mat.length; j++){
                    mat[i][j] = mat[i][j] - m * mat[k][j];
                }
                if(i != k){
                    mat[i][k] = m;
                }
            }
        }
        //System.out.println();
        //System.out.println("matriz escalonada LU: ");
        //imprime();
    }
    
    /*
       Funçao para imprimir matriz
    */
    public void imprime(){
        for(int i = 0; i < mat.length; i++){
            for(int j = 0; j < mat.length; j++){
                System.out.print(mat[i][j] + "     ");
            }
            System.out.println();
        }
        for(int i = 0; i < mat.length; i++){
            System.out.println("P " + p[i]);
        }
    }
    
    /*
       Resolva Ly = b pelo metodo de substituiçao sucessiva
    */
    public void resolveLyb(){
        l = new double[mat.length][mat.length];
        for(int i = 0; i < mat.length; i++){
            for(int j = 0; j < mat.length; j++){
                if(i == j){// coloca 1 na diagonal principal da matriz
                    l[i][j] = 1;
                }else if(j > i){
                    l[i][j] = 0;
                }else{
                    l[i][j] = mat[i][j];
                }
            }
        }
        y = new double[l.length];
        for(int k = 0; k < l.length; k++){
            y[k] = b[p[k]];
            for(int j = 0; j < k; j++){
                y[k] += -l[k][j]*y[j];
            }
            y[k] = y[k]/1.0; 
        }
    }
    
    /*
       Resolva Ux = y pelo metodo de substituiçao progreciva
    */
    public double[] resolveUxy(){
        for(int i = 0; i < mat.length; i++){
            for(int j = 0; j < mat.length; j++){
                if(i > j){
                    mat[i][j] = 0;
                }
            }
        }
        x = new double[mat.length];
        for(int k = mat.length - 1; k >= 0; k--){
            x[k] = y[k];
            for(int j = k + 1; j < mat.length; j++){
                x[k] = x[k] - mat[k][j] * x[j];
            }
            x[k] = x[k]/mat[k][k];
        }
        //imprimeVet();
        return x;
    }
    
    /*
       Imprime o vetor solução X
    */
   public void imprimeVet(){
       System.out.println();
       System.out.println("vetor x");
       for(int i = 0; i < x.length; i++){
           System.out.println(x[i]);
       }
   }
}
