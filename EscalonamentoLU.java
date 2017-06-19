/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escalonamentolu;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class EscalonamentoLU {
    
    public static void main(String[] args){
        Scanner Nomearq = new Scanner(System.in);
        System.out.print("informe o local e o nome do arquivo:\n");
        String nome;
        nome = Nomearq.nextLine();
        Scanner ler = new Scanner(System.in);
        int cont = 0;
        int indiceLinha = 0,indiceColuna =0;
        int tamMatriz = 0;
        double [][]MatrizA = null;
        double []vetorB =null;
        try{
        FileReader arq = new FileReader(nome);
        BufferedReader lerArq = new BufferedReader(arq);
        
        String linha = lerArq.readLine();
        while(linha != null){
            if(cont == 0){ // Primeira linha do arquivo
                tamMatriz = Integer.parseInt(linha);
                MatrizA = new double[tamMatriz][tamMatriz];
                vetorB = new double[tamMatriz];
            }else if(cont<= tamMatriz && cont >0){ // Demais linhas até ultima;
                String x[] = linha.split(" ");
                indiceColuna = 0;
                for(int i =0;i<x.length;i++){
                    if(!x[i].equals("")){
                        MatrizA[indiceLinha][indiceColuna] = Double.parseDouble(x[i]);
                        indiceColuna++;
                    }
                }
                indiceLinha++;
            }else{ //Ultima linha;
                String x[] = linha.split(" ");
                indiceColuna =0;
                indiceLinha = tamMatriz -1;
                for(int i =0;i<x.length;i++){
                    if(!x[i].equals("")){
                        vetorB[indiceColuna] = Double.parseDouble(x[i]);
                        indiceColuna++;
                    }
                }
            }
            linha = lerArq.readLine();
            cont++;
            
        }
         System.out.println("Tamanho da Matriz: "+tamMatriz);
        }catch(IOException e){
            System.err.printf("Erro na abertura do arquivo: %s. \n",e.getMessage());
        }
        System.out.println("A matriz A");
       for(int i =0;i<MatrizA.length;i++){
           for(int j =0;j<MatrizA.length;j++){
               System.out.print(MatrizA[i][j]+ " ");
           }
           System.out.println(" ");
       }
       System.out.println(" ");
       System.out.println("O vetor B");
       for(int i =0;i<vetorB.length;i++){
               System.out.print(vetorB[i]+ " \n");
       }
        
        Escalona lu = new Escalona(MatrizA,vetorB);
        lu.escalona();
        lu.resolveLyb();
        lu.resolveUxy();
        
        
        EscalonaTransposta lut = new EscalonaTransposta(MatrizA,vetorB);
        lut.escalona();
        lut.matrizTransposta();
        lut.transporP();
        lut.resolveUty();
        lut.resolveLtx();
    }
    
}
