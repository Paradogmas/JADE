# Guia de utilização JADE

* Este tutorial parte do pressuposto de que você já possui o JADE e o eclipse instalado em sua máquina, caso não tenha, acesse: https://jade.tilab.com/ e https://www.eclipse.org/downloads/ para obter estas duas ferramentas.

## Passo a passo - Eclipse

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

## Passo a passo - Intellij

* **1º:** Crie um projeto java no intellij

* **2º:** Em run/debug clique Edit configurations
    - Em Main_class coloque jade.Boot
    - Em programs arquments coloque:
    ```
     -gui nomeagente:NomeAgente
     ```
    - Em Use classpath of module coloque o diretório raíz de seu projeto
    - Em JRE coloque a versão de SDK compatível com o JADE

* **3º:** Crie uma nova classe para o projeto e adicione seu código

* **4º:** Clique em apply e ok.

* **4º:** Clique em run.

Após estes passos a janela do JADE deve aparecer em sua tela.

## Motivação do Projeto

Em 2001 Tim Berner-Lee publicou seu artigo [The Semantic Web](https://www-sop.inria.fr/acacia/cours/essi2006/Scientific%20American_%20Feature%20Article_%20The%20Semantic%20Web_%20May%202001.pdf) no qual ele cita o caso de Pete e Lucy na qual eles precisam marcar uma consulta para a mãe deles:

"His sister, Lucy, was on the line from the doctor's office: "Mom needs to see a specialist and then has to have a series of physical therapy sessions. Biweekly or something. I'm going to have my agent set up the appointments."

Nesse artigo ele apresenta o conceito de agentes semânticos que deveriam auxiliar a realizar tarefas desde que agendar reuniões a escolher o melhor caminho para casa. No caso citado no artigo se refere a como o agente pode interagir com outros agentes e decidir qual as melhores opções.

" ...Lucy instructed her Semantic Web agent through her handheld Web browser. The agent promptly
retrieved information about Mom's prescribed treatment from the doctor's agent, looked up several lists of providers,
and checked for the ones in-plan for Mom's insurance within a 20-mile radius of her home and with a rating of
excellent or very good on trusted rating services." 

Segundo ele o foco dos agentes não é decidir pelas pessoas porém ajudar a tomar as melhores decisões

" ...The agent presented them with a plan. Pete didn't like it—University Hospital was all the way across
town from Mom's place, and he'd be driving back in the middle of rush hour. He set his own agent to redo the search
with stricter preferences about location and time."

Com base neste artigo decidimos implementar agentes capazes de auxiliar na escolha de consultas baseados no preço e na especialidade do médico.

Também foi implementado a Hospital Ontology uma ontologia simples com o objetivo de auxiliar na comunicação dos agentes através da definição semântica de termos e eventos que serão utilizados ao decorrer do desenvolvimento do projeto.

## Funcionamento do Projeto

O paradigma do projeto segue o Sistema de Multiagentes implementado utilizando a linguagem JAVA, possuindo os Agentes Hospital, Paciente e Médico.

O Agente Hospital é responsavel por criar novos Agentes de Paciente e Médico. O Agente Médico possui um comportamento ciclico conhecido tambem chamado de Cyclic Behaviour que permite o Agente permanecer em execução para atender as buscas, o Agente Paciente possui um comportamento Ticker Behaviour que permite o Agente enviar uma requisição e espera a resposta com um tempo determinado definido para essa resposta, limitando as informações, evitando uma quantidade excessiva de respostas.

O sistema, com base nos filtros de busca selecionados, irá realizar a melhor seleção de Médicos com base nas necessidades do Paciente, facilitanto e agilizando o atendimento para uma melhor experiência de usuário.
