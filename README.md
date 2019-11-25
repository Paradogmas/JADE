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

## Motivação do Projeto

Em 2001 Tim Berner-Lee publicou seu artigo [The Semantic Web](https://www-sop.inria.fr/acacia/cours/essi2006/Scientific%20American_%20Feature%20Article_%20The%20Semantic%20Web_%20May%202001.pdf) no qual ele cita o caso de Pete e Lucy na qual eles precisam marcar uma consulta para a mãe deles:

"His sister, Lucy, was on the line from the doctor's office: "Mom needs to see a specialist and then has to have a series of physical therapy sessions. Biweekly or something. I'm going to have my agent set up the appointments.""

Nesse artigo ele apresenta o conceito de agentes semânticos que deveriam auxiliar a realizar tarefas desde que agendar reuniões a escolher o melhor caminho para casa. No caso citado no artigo se refere a como o agente pode interagir com outros agentes e decidir qual as melhores opções.
