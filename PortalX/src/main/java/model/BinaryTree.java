package model;

public class BinaryTree {
    No raiz;

    private static class No {
        int valor;
        No esquerda;
        No direita;
        int altura;

        public No(int valor) {
            this.valor = valor;
            this.esquerda = this.direita = null;
            this.altura = 0;
        }
    }

    public void add(int valor) {
        raiz = add(raiz, valor);
    }

    private No add(No raiz, int valor) {
        if(raiz == null) {
            raiz = new No(valor);
        } else if(valor > raiz.valor) {
            raiz.direita = add(raiz.direita, valor);
        } else {
            raiz.esquerda = add(raiz.esquerda, valor);
        }

        atualizaAltura(raiz);

        return balancear(raiz);
        //return raiz;
    }

    public void printInOrder() {
        printInOrder(raiz);
    }

    private void printInOrder(No raiz) {
        if(raiz == null) {
            return;
        }

        printInOrder(raiz.esquerda);
        System.out.print(raiz.valor + " ");
        printInOrder(raiz.direita);
    }

    public void printPreOrder() {
        printPreOrder(raiz);
    }

    private void printPreOrder(No raiz) {
        if(raiz == null) {
            return;
        }

        System.out.print(raiz.valor + " ");
        printPreOrder(raiz.esquerda);
        printPreOrder(raiz.direita);
    }

    public void remove(int valor) {
        raiz = remove(raiz,valor);
    }

    private No remove(No raiz, int valor) {
        if(raiz == null) {
            return null;
        }
        if(valor > raiz.valor) {
            raiz.direita = remove(raiz.direita, valor);
        } else if(valor < raiz.valor) {
            raiz.esquerda = remove(raiz.esquerda,valor);
        } else {
            if(raiz.esquerda == null && raiz.direita == null) {
                return null;
            } else if(raiz.esquerda == null) {
                return raiz.direita;
            } else if(raiz.direita == null) {
                return raiz.esquerda;
            } else {
                No minDireita = min(raiz.direita);
                raiz.valor = minDireita.valor;
                raiz.direita = remove(raiz.direita, minDireita.valor);
            }
        }
        return raiz;
    }

    private No min(No raiz) {
        No aux = raiz;
        while(aux.esquerda != null) {
            aux = aux.esquerda;
        }
        return aux;
    }

    public void printTree() {
        printTree(raiz, 0);
        System.out.println("---------------------------");
        System.out.println();
    }

    private void printTree(No root, int nivel) {
        if (root == null)
            return;
        printTree(root.direita, nivel + 1);
        for (int i = 0; i < nivel; i++) {
            System.out.print("    ");
        }
        System.out.println(root.valor);
        printTree(root.esquerda, nivel + 1);

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

    public static void main(String[] args) {
        BinaryTree arvore = new BinaryTree();

        arvore.add(1);
        arvore.add(3);
        arvore.add(2);
        arvore.add(1);
        arvore.add(3);
        arvore.add(2);
        arvore.add(1);
        arvore.add(3);
        arvore.add(2);
        arvore.add(1);
        arvore.add(3);
        arvore.add(2);


        arvore.printTree();





    }
}

