# Loja-de-carros
Sistema cliente/servidor para gerenciamento de carros em uma loja. O sistema consiste de um mecanismo de armazenamento de carros e seus dados associados como renavan, nome, categoria, ano de fabricação, quantidade disponível e preço.

Sobre os carros:
Nessa loja, os carros são agrupados em três categorias: econômico, intermediário e
executivo.
Exemplos de carros econômicos: fiat novo uno, chevrolet onix, ford ka, hyundai hb20, nissan march.
Exemplos de carros intermediários: ford ka sedan, chevrolet onix plus, hyundai hb20s, renault logan, toyota etios.
Exemplos de carros executivos: toyota corolla, honda civic, chevrolet cruze, audi a3.

Funcionalidades:
1. Autenticação: Um usuário deve se autenticar no sistema usando login e senha.
2. Adicionar carro: Um usuário pode adicionar carros ao sistema da loja. Para adicionar, os seguintes atributos são fornecidos: renavan, nome, categoria, ano de fabricação e preço. Atualizar quantidade disponível.
3. Apagar carro: Um usuário pode apagar registros de carros da loja. Todos os atributos são removidos a partir do nome do carro ou quando a quantidade disponível chegar em zero.
4. Listar carros: Um usuário pode listar os carros da loja (com todos os atributos). A listagem pode ocorrer por categoria ou de forma geral e deve ser apresentada em ordem alfabética dos nomes.
5. Pesquisar (consultar) carro: Um usuário pode realizar uma busca por carro a partir de seu nome ou do renavan.
6. Alterar atributos de carros: Um usuário pode alterar atributos de carros armazenados. Exemplo: um cadastro pode ter sido feito de forma errada (nome ou data de fabricação incorretos, etc).
7. Atualizar listagem de carros enviada aos clientes conectados: Adicionar, apagar e alterar atributos de carros são operações que fazem com que o servidor tenha que atualizar os clientes.
8. Exibir quantidade de carros: Um usuário pode consultar o sistema para saber quantos carros estão armazenados em um dado momento.
9. Comprar carro: Um usuário pode efetuar a compra de um carro após consulta e análise de preço.

Detalhamento dos Requisitos:
- Usuários podem ser clientes ou funcionários da loja (autenticação é necessária).
- Clientes podem listar, pesquisar, exibir a quantidade e comprar carros.
- Funcionários podem fazer tudo que os clientes fazem. Além disso, podem adicionar, apagar e alterar atributos dos carros.
- Clientes e funcionários devem realizar as operações listadas via conexão com serviços separados.
- O servidor da aplicação pode armazenar os dados ou utilizar um servidor auxiliar específico para guardar os dados.
- O servidor de autenticação realiza a verificação de login e senha em uma base de dados própria para usuário legítimos.

Representação da arquitetura proposta:
![image](https://github.com/MikaelAlvez/Loja-de-carros/assets/47370510/6d70c8c6-3fdf-4515-ac21-db4d39d5395e)
