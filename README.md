# WindCharge Plugin

## Descrição

O plugin WindCharge para Minecraft permite aos jogadores criar e gerenciar "homes" dentro do jogo. Com funcionalidades adicionais, como cooldowns e partículas, o plugin oferece uma maneira conveniente e personalizada de armazenar e teleportar-se para locais específicos no mundo do Minecraft.

## Funcionalidades

- **Criar e Gerenciar Homes**: Comandos para definir, teletransportar e excluir homes.
- **Cooldown Configurável**: Ajuste o tempo de cooldown entre os teletransportes.
- **Partículas ao Teletransportar**: Exiba partículas durante o teletransporte (opcional).
- **Persistência de Dados**: Armazena as homes dos jogadores em um banco de dados MySQL.

## Requisitos

- **Minecraft**: Versão 1.21
- **Spigot**: Compatível com a versão do Minecraft (Neste caso 1.21)
- **Java**: 17 ou superior
- **MySQL**: Para armazenamento dos dados das homes

## Configuração

1. **Configuração do MySQL**

   Certifique-se de ter o MySQL instalado e configurado. Crie um banco de dados com o nome especificado no arquivo de configuração.

2. **Configuração do Plugin**

   Edite o arquivo `config.yml` no diretório de configuração do plugin para rodar o projeto. Exemplo de configuração:
   Para conseguir rodar de acordo com suas configurações, altere o necessário. Deixei por padrão as minhas configurações, tirando a senha.

   ```yml
   mysql:
     host: "localhost"
     port: 3306
     database: "minecraft"
     username: "root"
     password: sua_senha_mysql

   windcharge:
     explosionStrength: 4.0
     spawnParticles: false
     projectileSpeed: 1.5

   home:
     cooldown: 10
     teleportParticles: true

**Comandos**
    **/sethome [nome]:** Define um local como uma home com o nome especificado.
    **/teleport [nome]:** Teletransporta o jogador para a home com o nome especificado.
    **/getitem [nome]:** Obtém um item com base no nome fornecido (usado no WindCharge).
    **/setwindcharge [valor]:** Define a força da explosão do WindCharge.
    **/teleportcd [tempo]:** Define o cooldown de teletransporte.
    **/teleportparticles [true/false]:** Ativa ou desativa as partículas ao teletransportar.

**Estrutura**
  Para gerar um .jar com as funcionalidades:
     - Na sua IDE altere os dados de conexão do banco de dados no config.yml
     - Após alterar e salvar os dados, na barra de navegação da IDE vá em Build -> Build Artifacts -> Build.
     - Após o build pela IDE, o plugin estará disponível na pastar server -> plugins.
     - Para rodar o servidor com o plugin, vá na pasta server e rode o arquivo run.
