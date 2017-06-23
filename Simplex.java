/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escalonamentolu;

import static java.lang.System.exit;

public class Simplex {
    private int quant = 0;
    private int linha;
    private int coluna;
    private double p;
    private int indS;
    private int indE;
    private int[] indice;
    private double[] b;
    private double[] c;
    private double[] Cb;
    private double[] Xb;
    private double[] lambida;
    private double[] r;
    private double[] Aq;
    private double[] Yq;
    private double[][] a;
    private double[][] B;
    
    public Simplex(double[][] A, double[] b, double[] c, int linha, int coluna){
        this.linha = linha;
        this.coluna = coluna;
        this.p = Double.MAX_VALUE;
        this.indS = 0;
        this.indE = 0;
        this.indice = new int[linha];
        this.b = b;
        this.c = c;
        this.Cb = new double[linha];
        this.Xb = new double[linha];
        this.lambida = new double[linha];
        this.r = new double[coluna];
        this.Aq = new double[linha];
        this.Yq = new double[linha];
        this.a = A;
        this.B = new double[linha][linha];
        
        constroiIndice();
        constroiB();
        constroiCb();
        decomposiçãoLu();
        constroiR();
        calculaCustosReduzidos();
        solucaoOtima();
    }
    
    public void imprimeVet(int[] vet){
        for(int i = 0; i < vet.length; i++){
            System.out.print(vet[i] + "  ");
        }
        System.out.println();
    }
    
    public void imprimeVet(double[] vet){
        for(int i = 0; i < vet.length; i++){
            System.out.print(vet[i] + "  ");
        }
        System.out.println();
    }
    
    public void imprimeMatriz(double[][] mat){
        for(int i = 0; i < mat.length; i++){
            for(int j = 0; j < mat[i].length; j++){
                System.out.print(mat[i][j] + "  ");
            }
            System.out.println();
        }
    }

    private void constroiIndice() {
        int k = 0;
        for(int i = coluna-linha; i < coluna; i++){
            indice[k] = i;
            k++;
        }
        System.out.println("Vetor de indice: ");
        imprimeVet(indice);
    }

    private void constroiB() {
        for(int i = 0; i < B.length; i++){
            for(int j = 0; j < B.length; j++){
                B[i][j] = a[i][indice[j]];
            }
        }
        System.out.println("Matriz base:");
        imprimeMatriz(B);
    }

    private void constroiCb() {
        for(int i = 0; i < Cb.length; i++){
            Cb[i] = c[indice[i]];
        }
        System.out.println("Vetor de custos da base: ");
        imprimeVet(Cb);
    }

    private void decomposiçãoLu() {
        Escalona lu = new Escalona(B, b);
        lu.escalona();
        lu.resolveLyb();
        Xb = lu.resolveUxy();
        
        EscalonaTransposta lut = new EscalonaTransposta(B, Cb);
        lu.escalona();
        lut.matrizTransposta();
        lut.transporP();
        lut.resolveUty();
        lambida = lut.resolveLtx();
    }

    private void calculaCustosReduzidos() {
        calculaAq();
        int k = 0;
        for(int j = 0; j < c.length; j++){
            //if(j != indice[k]){
                r[j] = c[j] - lambida[k]*Aq[k];
                System.out.println("r[j]: " + r[j] + " c[j] " + c[j] + " lambida " + lambida[k]*Aq[k] + " k: " + k);
            //}else{
            //   k++;
            //}
        }
        System.out.println("Vetor de custos reduzido r:");
        imprimeVet(r);
    }

    private void solucaoOtima() {
        //verifica otimalidade da solução
        int cont  = 0;
        for(int j = 0; j < r.length; j++){
            if(r[j] >= 0){
                cont++;
            }
        }
        if(cont == r.length){
            int k = 0;
            System.out.println("Soluca otima:");
            for(int i = 0; i < c.length; i++){
                if(i == indice[k]){
                    System.out.print(Xb[k] + "  ");
                    k++;
                }else{
                    System.out.print(" 0.0 ");
                }
            }
            exit(0);
        }else{
            for(int q = 0; q < r.length; q++){
                if(r[q] < 0){
                    indE = q;
                    //calculaAq();
                    EscalonaTransposta lu = new EscalonaTransposta(B, Aq);
                    lu.escalona();
                    lu.matrizTransposta();
                    lu.transporP();
                    lu.resolveUty();
                    Yq = lu.resolveLtx();
                    System.out.println("Vetor Yq:");
                    imprimeVet(Yq);
                    break;
                }
            }
            //verifica se a soluçao é não limitada
            int cont1 = 0;
            for(int i = 0; i < Yq.length; i++){
                if(Yq[i] > 0){
                    if(Xb[i]/Yq[i] < p){
                        p = Xb[i]/Yq[i];
                        indS = i;
                    }
                }else{
                    cont1++;
                }
            }
            if(cont1 == Yq.length){
                System.out.println("Solucao nao limitada");
            }else{
                indice[indS] = indE;
                chamaRecurcao();//se a solução for limitada repete o algoritmo
            }
        }
    }

    private void calculaAq() {
        for(int i = 0; i < Aq.length; i++){
            Aq[i] = a[i][indE];
        }
        System.out.println("Coluna aq que entrara na base: ");
        imprimeVet(Aq);
    }

    public void chamaRecurcao() {
        imprimeVet(indice);
        constroiB();
        constroiCb();
        decomposiçãoLu();
        calculaCustosReduzidos();
        solucaoOtima();
    }
    
    public void constroiR(){
        for(int i = 0; i < r.length; i++){
            r[i] = 0;
        }
    }
}