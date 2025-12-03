package model;

public class ListaEncadeadaInscricoes {
    private No inicio;
    private int tamanho;

    public int getTamanho() {
        return tamanho;
    }

    private static class No {
        long id;
        No proximo;

        public No(long id) {
            this.id = id;
            this.proximo = null;
        }
    }

    // Adiciona um id ao final da lista
    public void adicionar(long id) {
        No novoNo = new No(id);

        if (inicio == null) {
            inicio = novoNo;
        } else {
            No atual = inicio;
            while (atual.proximo != null) {
                atual = atual.proximo;
            }
            atual.proximo = novoNo;
        }

        tamanho++;
    }

    // Remove o primeiro nó que contenha o id informado
    public void remover(long id) {
        if (inicio == null) {
            return;
        }

        if (inicio.id == id) {
            inicio = inicio.proximo;
            tamanho--;
            return;
        }

        No atual = inicio;
        while (atual.proximo != null && atual.proximo.id != id) {
            atual = atual.proximo;
        }

        if (atual.proximo != null) {
            atual.proximo = atual.proximo.proximo;
            tamanho--;
        }


    }

    // Exibe os ides da lista
    public void exibir() {
        No atual = inicio;
        while (atual != null) {
            System.out.print(atual.id + " ");
            atual = atual.proximo;
        }
        System.out.println();
    }
}

