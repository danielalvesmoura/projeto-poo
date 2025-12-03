# Portal X

O Portal X é uma plataforma desenvolvida para otimizar o planejamento, a organização e a execução de eventos empresariais de qualquer porte.

- Para a organização de sessões, foi utilizado AVL.

# Instruções para Execução

Para iniciar o app, abra o projeto como Maven em uma IDE, de preferência Intellij, e execute a linha de comando:

```
javafx:run
```

No Intellij, vá para "Edit Configurations...", depois adicione uma configuração Maven, depois cole o comando no campo "Command Line" na aba "Run". Aplique e execute.

## Gerador de Dados

Para preenchimento do banco de dados, foi implementado um gerador de dados utilizando a biblioteca Faker.

Segue o diretório para o gerador abaixo:

```
PortalX  
└── src  
    └── main  
        └── java  
            └── util  
                └── geradorDados  
```
