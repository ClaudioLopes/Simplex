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
        int indiceLinha = 0,indiceColuna = 0;
        int numLinhas = 0;
        int numColunas = 0;
        double [][]MatrizA = null;
        double []vetorB = null;
        double []vetorC = null;
        try{
        FileReader arq = new FileReader(nome);
        BufferedReader lerArq = new BufferedReader(arq);
        
        String linha = lerArq.readLine();
        while(linha != null){
            if(cont == 0){ // Primeira linha do arquivo
                String c[] = linha.split(",");
                numLinhas = Integer.parseInt(c[0]);
                numColunas = Integer.parseInt(c[1]);
                MatrizA = new double[numLinhas][numColunas];
                vetorB = new double[numLinhas];
                vetorC = new double[numColunas];
            }else if(cont <= numLinhas && cont >0){ // Demais linhas até ultima;
                String x[] = linha.split(" ");
                indiceColuna = 0;
                for(int i =0;i<x.length;i++){
                    if(!x[i].equals("")){
                        MatrizA[indiceLinha][indiceColuna] = Double.parseDouble(x[i]);
                        indiceColuna++;
                    }
                }
                indiceLinha++;
            }else if(cont == numLinhas+1){
                String x[] = linha.split(" ");
                indiceColuna = 0;
                indiceLinha = numLinhas -1;
                for(int i =0;i<x.length;i++){
                    if(!x[i].equals("")){
                        vetorB[indiceColuna] = Double.parseDouble(x[i]);
                        indiceColuna++;
                    }
                }
            }else{ //Ultima linha;
                String x[] = linha.split(" ");
                indiceColuna = 0;
                indiceLinha = numLinhas -1;
                for(int i =0;i<x.length;i++){
                    if(!x[i].equals("")){
                        vetorC[indiceColuna] = Double.parseDouble(x[i]);
                        indiceColuna++;
                    }
                }
            }
            linha = lerArq.readLine();
            cont++;
            
        }
         System.out.println("Tamanho da Matriz: "+numLinhas);
        }catch(IOException e){
            System.err.printf("Erro na abertura do arquivo: %s. \n",e.getMessage());
        }
        
        Simplex s = new Simplex(MatrizA, vetorB, vetorC, numLinhas, numColunas);
        //s.resolveSistemaLinear();
    }
    
}
