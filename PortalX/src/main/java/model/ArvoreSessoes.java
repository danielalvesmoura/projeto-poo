package model;

import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ArvoreSessoes {
    No sessaoRaiz;

    public No getSessaoRaiz() {
        return sessaoRaiz;
    }

    private static class No {
        No esquerda;
        No direita;
        int altura;
        Sessao sessao;
        LocalDateTime inicio;
        LocalDateTime fim;

        public No(Sessao sessao) {
            this.sessao = sessao;

            this.inicio = LocalDateTime.of(sessao.getDataInicio(),sessao.getHoraInicio());
            this.fim = LocalDateTime.of(sessao.getDataFim(),sessao.getHoraFim());

            this.esquerda = this.direita = null;
            this.altura = 0;
        }
    }


    public void printTree() {
        printTree(sessaoRaiz, 0);
        System.out.println("---------------------------");
        System.out.println();
    }

    private void printTree(No root, int nivel) {
        if (root == null)
            return;
        printTree(root.direita, nivel + 1);
        for (int i = 0; i < nivel; i++) {
            System.out.print("                    ");
        }
        System.out.println(root.sessao.getDataInicio() + " - " + root.fim + "\n");
        printTree(root.esquerda, nivel + 1);

    }


    public void printInOrder() {
        printInOrder(sessaoRaiz);
    }

    private void printInOrder(No raiz) {
        if(raiz == null) {
            return;
        }

        printInOrder(raiz.esquerda);
        System.out.print(raiz.inicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + " - " + raiz.fim.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + " | ");
        printInOrder(raiz.direita);
    }

    public void add(Sessao sessao) {
        if(sessaoRaiz != null && sessaoRaiz.sessao.getId() == sessao.getId()) return;
        sessaoRaiz = add(sessaoRaiz, sessao);
    }

    private No add(No raiz, Sessao sessao) {
        LocalDateTime inicio = LocalDateTime.of(sessao.getDataInicio(), sessao.getHoraInicio());
        LocalDateTime fim = LocalDateTime.of(sessao.getDataFim(), sessao.getHoraFim());

        if (raiz == null) {
            return new No(sessao);
        }

        System.out.println("Inicio da sessão nova: " + inicio);
        System.out.println("Fim da sessão nova: " + fim);

        System.out.println("\nInicio da raiz: " + raiz.inicio);
        System.out.println("Fim da raiz: " + raiz.fim);

        // checa se a nova sessao termina antes da sessão atual
        if (fim.isBefore(raiz.inicio) || fim.isEqual(raiz.inicio)) {
            raiz.esquerda = add(raiz.esquerda, sessao);
        }
        // Confere se a nova sessão começa depois da sessão atual
        else if (inicio.isAfter(raiz.fim) || inicio.isEqual(raiz.fim)) {
            raiz.direita = add(raiz.direita, sessao);
        }
        // se nao tem sobreposição
        else {
            throw new IllegalArgumentException("Sobreposicao de horarios");
        }

        atualizaAltura(raiz);
        return balancear(raiz);
    }


    public void remove(Sessao sessao) {
        sessaoRaiz = remove(sessaoRaiz,sessao);
    }

    private No remove(No raiz, Sessao sessao) {
        if(raiz == null) {
            return null;
        }

        LocalDateTime inicio = LocalDateTime.of(sessao.getDataInicio(),sessao.getHoraInicio());
        LocalDateTime fim = LocalDateTime.of(sessao.getDataFim(),sessao.getHoraFim());

        if(inicio.isAfter(raiz.fim) || inicio.isEqual(raiz.fim)) {
            raiz.direita = remove(raiz.direita, sessao);
        } else if(fim.isBefore(raiz.inicio) || fim.isEqual(raiz.inicio)) {
            raiz.esquerda = remove(raiz.esquerda,sessao);
        } else {
            if(raiz.esquerda == null && raiz.direita == null) {
                return null;
            } else if(raiz.esquerda == null) {
                return raiz.direita;
            } else if(raiz.direita == null) {
                return raiz.esquerda;
            } else {
                No minDireita = min(raiz.direita);
                raiz.inicio = minDireita.inicio;
                raiz.fim = minDireita.fim;
                raiz.direita = remove(raiz.direita, minDireita.sessao);
            }
        }
        return raiz;
    }

    public void adicionaItensNaLista(ObservableList<Sessao> observableList, No raiz) {
        if (raiz == null) {
            return;
        }

        // Percorre a subárvore esquerda
        adicionaItensNaLista(observableList, raiz.esquerda);

        // Adiciona o nó atual
        observableList.add(raiz.sessao);

        // Percorre a subárvore direita
        adicionaItensNaLista(observableList, raiz.direita);
    }


    public boolean contains(Sessao s) {
        return buscar(sessaoRaiz, s) != null;
    }

    public No buscar(No raiz, Sessao s) {
        if (raiz == null) {
            return null;
        }

        if (raiz.sessao.getDataInicio().equals(s.getDataInicio()) &&
                raiz.sessao.getHoraInicio().equals(s.getHoraInicio()) &&
                raiz.sessao.getDataFim().equals(s.getDataFim()) &&
                raiz.sessao.getHoraFim().equals(s.getHoraFim())) {
            return raiz;
        }

        // Comparação: iniciar comparando data/hora de início
        LocalDateTime inicio = s.getDataInicio().atTime(s.getHoraInicio());

        LocalDateTime inicioRaiz = raiz.sessao.getDataInicio()
                .atTime(raiz.sessao.getHoraInicio());

        if (inicio.isAfter(inicioRaiz)) {
            return buscar(raiz.direita, s);
        }

        return buscar(raiz.esquerda, s);
    }

    private No min(No raiz) {
        No aux = raiz;
        while(aux.esquerda != null) {
            aux = aux.esquerda;
        }
        return aux;
    }

    private No rotateRight(No raiz) {
        No novaRaiz = raiz.esquerda;
        No direitaEsquerda = novaRaiz.direita;

        raiz.esquerda = direitaEsquerda;
        novaRaiz.direita = raiz;

        return novaRaiz;
    }

    private No rotateLeft(No raiz) {
        No novaRaiz = raiz.direita;
        No esquerdaDireita = novaRaiz.esquerda;

        raiz.direita = esquerdaDireita;
        novaRaiz.esquerda = raiz;

        return novaRaiz;
    }

    private int altura(No raiz) {
        if(raiz == null) return -1;
        return raiz.altura;
    }

    private void atualizaAltura(No raiz) {
        int esquerda = 1 + altura(raiz.esquerda);
        int direita = 1 + altura(raiz.direita);
        raiz.altura = Math.max(esquerda,direita);
    }

    private int balanceFactor(No raiz) {
        return altura(raiz.esquerda) - altura(raiz.direita);
    }

    private No balancear(No raiz) {
        int fb = balanceFactor(raiz);

        if(fb < -1 && balanceFactor(raiz.direita) <= 0) {
            return rotateLeft(raiz);
        }
        if(fb > 1 && balanceFactor(raiz.esquerda) >= 0) {
            return rotateRight(raiz);
        }
        if(fb < -1 && balanceFactor(raiz.direita) > 0) {
            raiz.direita = rotateRight(raiz.direita);
            return rotateLeft(raiz);
        }
        if(fb > 1 && balanceFactor(raiz.esquerda) < 0) {
            raiz.esquerda = rotateLeft(raiz.esquerda);
            return  rotateRight(raiz);
        }

        return raiz;
    }

}