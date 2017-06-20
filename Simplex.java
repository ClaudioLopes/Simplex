/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

Escalona lu = new Escalona(B,b);
        lu.escalona();
        lu.resolveLyb();
        xb = lu.resolveUxy();
        
        EscalonaTransposta lut = new EscalonaTransposta(B,Cb);
        lut.escalona();
        lut.matrizTransposta();
        lut.transporP();
        lut.resolveUty();
        lambida = lut.resolveLtx();

 */
package escalonamentolu;

import static java.lang.System.exit;

public class Simplex {
    
    private int[] indice;
    private double[][] B;
    private double[][] A;
    private double[] b;
    private double[] c;
    private double[] Cb;
    private double[] r;
    private double[] lambida;
    private double[] Xb;
    private double[] Yq;
    private int linha;
    private int coluna;
    private int p;
    
    public Simplex(double[][] mat, double[] b, double[] c,int linha, int coluna){
        this.linha = linha;
        this.coluna = coluna;
        indice = new int[linha];
        A = new double[linha][coluna];
        A = mat;
        this.b = new double[linha];
        this.b = b;
        this.c = new double[coluna];
        this.c = c;
        B = new double[linha][linha];
        Cb = new double[linha];
        r = new double[coluna];
        lambida = new double[linha];
        Xb = new double[linha];
        Yq = new double[linha];
        
        constroiIndice();
        constroiB();
        constroiCb();
        constroiR();
        DecomposicaoLU();
        calculaCustoReduzido();
        VerificaSolucaoOtima();
    }
    
    public void imprimeVetor(int[] vet){
        System.out.println("Imprimindo Vetor");
        for(int i = 0; i < vet.length; i++){
            System.out.print(vet[i] + " ");
        }
        System.out.println();
    }
    
    public void imprimeVetor(double[] vet){
        System.out.println("Imprimindo Vetor");
        for(int i = 0; i < vet.length; i++){
            System.out.print(vet[i] + " ");
        }
        System.out.println();
    }
    
    public void imprimeMatriz(double[][] mat){
        System.out.println("Imprimindo Matriz");
        for(int i = 0; i < mat.length; i++){
            for(int j = 0; j < mat[0].length; j++){
                System.out.print(mat[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    public void constroiIndice(){
        int k = 0;
        for(int i = coluna-linha; i < coluna; i++){
            indice[k] = i;
            k++;// esse K serve para iniciar o indice com 0
        }
    }
    
    public void constroiB(){
        for(int i = 0; i < B.length; i++){
            for(int j = 0; j < B.length; j++){
                B[i][j] = A[i][indice[j]];
            }
        }
        imprimeMatriz(B);
    }
    
    public void constroiCb(){
        for(int i = 0; i < Cb.length; i++){
            Cb[i] = c[indice[i]];
        }
        imprimeVetor(Cb);
    }
    
    public void DecomposicaoLU(){
        Escalona lu = new Escalona(B,b);
        lu.escalona();
        lu.resolveLyb();
        Xb = lu.resolveUxy();
        
        EscalonaTransposta lut = new EscalonaTransposta(B,Cb);
        lut.escalona();
        lut.matrizTransposta();
        lut.transporP();
        lut.resolveUty();
        lambida = lut.resolveLtx();
    }
    
    public void calculaCustoReduzido(){
        System.out.println("Impimindo a lenha");
        for(int i = 0; i < r.length; i++){
            for(int k = 0; k < lambida.length; k++){
               r[i] = r[i] - lambida[k];
            }
        }
        imprimeVetor(r);  
    }
    
    public void VerificaSolucaoOtima(){
        double[] aq = new double[linha];
        double menor = Double.MAX_VALUE;
        int ind = 0;
        int cont = 0;
        for(int i = 0; i < r.length; i++){
            if(r[i] >= 0){
                cont++;
            }
        }
        if(cont == r.length){
            System.out.println("Solucao Otima");
            int t = 0;
                for(int i = 0; i < c.length; i++){
                    if(i == indice[t]){
                        System.out.print(Xb[t] + "  ");
                        t++;
                    }else{
                        System.out.print(" 0.0 ");
                    }
                }
                System.out.println("Vetor custo Final");
                imprimeVetor(r);
            exit(1);
        }else{
            for(int i = 0; i < r.length; i++){
                if(r[i] < 0){
                    ind = i;
                    aq = constroiAq(ind);
                    Escalona lu = new Escalona(B,aq);
                    lu.escalona();
                    lu.resolveLyb();
                    Yq = lu.resolveUxy();
                    break;
                }
            }
            int cont1 = 0;
            for(int i = 0; i < Yq.length; i++){
                if(Yq[i] <= 0){
                    cont1++;
                }else{
                    if(Xb[i]/Yq[i] < menor){
                        menor = Xb[i]/Yq[i];
                        p = i;
                    }
                }
            }
            if(cont1 == Yq.length){
                System.out.println("Resposta nao limitda");
                System.out.println("Vetor Yq final");
                imprimeVetor(Yq);
                exit(0);
            }
        }
        indice[p] = ind;
        constroiB();
        constroiCb();
        DecomposicaoLU();
        calculaCustoReduzido();
        VerificaSolucaoOtima();
    }
    
    public double[] constroiAq(int ind){
        double[] aq = new double[linha];
        for(int i = 0; i < A.length; i ++){
            aq[i] = A[i][ind];
        }
        return aq;
    }
    
    public void constroiR(){
        for(int i = 0; i < r.length; i++){
                r[i] = c[i];
            }
    }

}
