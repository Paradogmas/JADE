# Guia de utilização JADE

* Este tutorial parte do pressuposto de que você já possui o JADE e o eclipse instalado em sua máquina, caso não tenha, acesse: https://jade.tilab.com/ e https://www.eclipse.org/downloads/ para obter estas duas ferramentas.

## Passo a passo

* **1º:** Crie um projeto java no eclipse

* **2º:** Adicione as bibliotecas externas do JADE
    - Clique com direito em JRE System Libraries
    - Acesse Build Path > Configure build path
    - Clique em add External JARs
    - Selecione jade.jar e commons-codec-1.3.jar

* **3º:** Crie uma nova classe para o projeto e adicione seu código

* **4º:** Rode seu código
    - Acesse Run As > Run configurations
    - Em Main coloque o nome do seu projeto
    - Em Main class coloque jade.Boot
    - Na aba arguments coloque:
     ```
     -gui nomeagente:NomeAgente
     ```

* **5º:** Clique em apply e Run.

Após estes passos a janela do JADE deve aparecer em sua tela.