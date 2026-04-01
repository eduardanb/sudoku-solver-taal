# Sudoku Solver – Análise de Estratégias de Busca

## Descrição do Projeto

Este projeto tem como objetivo implementar e comparar diferentes estratégias algorítmicas para a resolução do problema do Sudoku. O foco principal é analisar o comportamento de técnicas clássicas de projeto de algoritmos aplicadas a um problema de busca em espaço de estados.

As estratégias implementadas são:

- Backtracking
- Branch and Bound
- Programação Dinâmica
- Estratégia Gulosa

O sistema executa os algoritmos sobre instâncias de Sudoku fornecidas por arquivos de entrada e coleta métricas que permitem comparar formalmente o desempenho de cada abordagem.

`LINK DO RELATÓRIO PT. 1:` https://docs.google.com/document/d/1i_WHgjf_wGwaYyXxn1F9tmtGn_zP-Su9bXPqPEhaLxo/edit?usp=sharing.
`LINK DO RELATÓRIO PT. 2:` https://docs.google.com/document/d/18C4tPikuUdCBnJLe_4Jjyz_ubkZ7k7tqk6tiC9qzEdk/edit?usp=sharing.
`LINK DO RELATÓRIO COMPARATIVO:` EM PROCESSO.

(Acessar com email institucional.)

## Objetivos

O projeto busca:

- Implementar diferentes técnicas de resolução para o Sudoku
- Comparar as estratégias em termos de eficiência e qualidade da solução
- Analisar o comportamento teórico e experimental dos algoritmos
- Avaliar o impacto de heurísticas na redução do espaço de busca

## Pré-requisitos

Para executar este projeto, você precisa ter instalado:

- Java Development Kit (JDK) 23 ou superior
- Apache Maven 3.6 ou superior
- Um editor de texto ou IDE como Visual Studio Code, IntelliJ IDEA ou Eclipse

## Instalação

1. Clone o repositório para sua máquina local:

   ```
   git clone https://github.com/seu-usuario/sudoku-solver-taal.git
   ```

2. Navegue até o diretório do projeto:

   ```
   cd sudoku-solver-taal
   ```

3. Compile o projeto usando Maven:

   ```
   mvn clean compile
   ```

## Uso

### Executando os Solvers

Para executar um dos solvers, você pode usar as classes principais ou o ExperimentRunner para comparações.

#### Exemplo: Executando o Backtracking Solver

```java
import br.edu.sudoku.io.SudokuReader;
import br.edu.sudoku.solver.BacktrackingSolver;
import br.edu.sudoku.model.SudokuBoard;

public class Main {
    public static void main(String[] args) {
        SudokuBoard board = SudokuReader.readFromFile("src/main/resources/sudokus/sudoku_facil.txt");
        BacktrackingSolver solver = new BacktrackingSolver();
        SudokuBoard solution = solver.solve(board);
        if (solution != null) {
            System.out.println("Sudoku resolvido!");
            // Imprimir solução
        } else {
            System.out.println("Não foi possível resolver o Sudoku.");
        }
    }
}
```

#### Usando o ExperimentRunner

O ExperimentRunner permite executar todos os algoritmos sobre as mesmas instâncias e comparar os resultados:

```java
import br.edu.sudoku.experiment.ExperimentRunner;

public class Main {
    public static void main(String[] args) {
        ExperimentRunner runner = new ExperimentRunner();
        runner.runExperiments();
    }
}
```

### Arquivos de Entrada

Os arquivos de Sudoku estão localizados em `src/main/resources/sudokus/`. Exemplos incluem:

- `sudoku_facil.txt`: Instância fácil
- `sudoku_medio.txt`: Instância média
- `sudoku_dificil.txt`: Instância difícil

## Estrutura do Projeto

O projeto segue a estrutura padrão de projetos Java com Maven.

```
├───src
│   ├───main
│   │   ├───java
│   │   │   └───br
│   │   │       └───edu
│   │   │           └───sudoku
│   │   │               ├───experiment
│   │   │               │       ExperimentRunner.java
│   │   │               │
│   │   │               ├───heuristics
│   │   │               │       MRVHeuristic.java
│   │   │               │       VariableOrderingHeuristic.java
│   │   │               │
│   │   │               ├───io
│   │   │               │       SudokuReader.java
│   │   │               │       SudokuWriter.java
│   │   │               │
│   │   │               ├───metrics
│   │   │               │       Metrics.java
│   │   │               │
│   │   │               ├───model
│   │   │               │       SudokuBoard.java
│   │   │               │
│   │   │               ├───solver
│   │   │               │   │   SudokuSolver.java
│   │   │               │   │
│   │   │               │   ├───backtracking
│   │   │               │   │       BacktrackingAlgorithm.java
│   │   │               │   │       BacktrackingSolver.java
│   │   │               │   │
│   │   │               │   ├───branchandbound
│   │   │               │   │       BranchAndBoundAlgorithm.java
│   │   │               │   │       BranchAndBoundSolver.java
│   │   │               │   │
│   │   │               │   ├───dynamicprogramming
│   │   │               │   │       DynamicProgrammingAlgorithm.java
│   │   │               │   │       DynamicProgrammingSolver.java
│   │   │               │   │
│   │   │               │   └───greedy
│   │   │               │           GreedyAlgorithm.java
│   │   │               │           GreedySolver.java
│   │   │               │
│   │   │               └───utils
│   │   │                       SudokuValidator.java
│   │   │
│   │   └───resources
│   │       └───sudokus
│   │               sudoku_dificil.txt
│   │               sudoku_facil.txt
│   │               sudoku_medio.txt
│   │
│   └───test
│       ├───java
│       │   └───br
│       │       └───edu
│       │           └───sudoku
│       │               │   BacktrackingSolverTest.java
│       │               │   BranchAndBoundSolverTest.java
│       │               │   QuickPerformanceTest.java
│       │               │   SolverComparisonTest.java
│       │               │   SudokuValidatorTest.java
│       │               │
│       │               └───experiment
│       │                       PerformanceComparisonTest.java
│       │
│       └───resources
│           └───sudokus
│                   testSudoku.txt
│
└───target
    │   sudoku-solver-taal-1.0.jar
    ...
```

## Descrição dos Componentes

### Model

Representação do tabuleiro do Sudoku e suas operações básicas.

- `SudokuBoard`: Estrutura que representa o estado do tabuleiro, incluindo métodos para manipulação de células, verificação de validade e clonagem.

### Solver

Implementações dos algoritmos responsáveis por resolver o Sudoku.

- `SudokuSolver`: Interface comum para todos os algoritmos, definindo o contrato para resolução.
- `BacktrackingSolver`: Resolução baseada em busca recursiva com retrocesso.
- `BranchAndBoundSolver`: Busca com poda de ramos inviáveis baseada em limites.
- `DynamicProgrammingSolver`: Tentativa de reutilização de soluções parciais através de memoização.
- `GreedySolver`: Abordagem baseada em escolhas locais ótimas em cada passo.

### Heuristics

Heurísticas utilizadas para melhorar a eficiência da busca.

- `VariableOrderingHeuristic`: Define a ordem de escolha das variáveis (células) durante a busca.
- `MRVHeuristic`: Heurística de Valor Mais Restrito (Minimum Remaining Values) para seleção de variáveis.

### IO

Responsável pela leitura e escrita de arquivos.

- `SudokuReader`: Leitura de instâncias de Sudoku a partir de arquivos de texto.
- `SudokuWriter`: Escrita das soluções e resultados em arquivos de saída.

### Metrics

Coleta e armazenamento de métricas de desempenho dos algoritmos.

Exemplos de métricas coletadas:

- Tempo de execução
- Número de nós visitados
- Número de podas realizadas
- Uso de memória
- Profundidade máxima da busca

### Experiment

Coordena a execução dos experimentos.

- `ExperimentRunner`: Executa os algoritmos sobre os mesmos problemas e registra os resultados para comparação.

### Utils

Funções auxiliares utilizadas pelos algoritmos.

- `SudokuValidator`: Valida se um número pode ser inserido em determinada posição do tabuleiro, verificando regras do Sudoku.

### Test

Conjunto de testes unitários utilizados para verificar a corretude das implementações.

- `BacktrackingSolverTest.java`: Testes específicos para o algoritmo de backtracking
- `BranchAndBoundSolverTest.java`: Testes para branch and bound
- `SolverComparisonTest.java`: Testes de comparação entre diferentes solvers
- `SudokuValidatorTest.java`: Testes para o validador
- `QuickPerformanceTest.java`: Testes de performance rápida
- `PerformanceComparisonTest.java`: Comparações de performance detalhadas

## Formato do Arquivo de Entrada

O arquivo de entrada contém uma matriz 9x9 representando o Sudoku.

- Números de 1 a 9 representam valores já preenchidos
- O valor 0 representa uma célula vazia

Exemplo de arquivo de entrada:

```
5 3 0 0 7 0 0 0 0
6 0 0 1 9 5 0 0 0
0 9 8 0 0 0 0 6 0
8 0 0 0 6 0 0 0 3
4 0 0 8 0 3 0 0 1
7 0 0 0 2 0 0 0 6
0 6 0 0 0 0 2 8 0
0 0 0 4 1 9 0 0 5
0 0 0 0 8 0 0 7 9
```

## Métricas de Avaliação

Os algoritmos são comparados utilizando as seguintes métricas:

- **Complexidade assintótica teórica**: Análise de tempo e espaço no pior caso
- **Tempo de execução**: Análise empírica em diferentes instâncias
- **Uso de memória**: Quantidade de memória utilizada durante a execução
- **Número de nós visitados**: Total de estados explorados
- **Número de podas realizadas**: Eficiência na redução do espaço de busca
- **Qualidade da solução**: Se a solução é ótima ou aproximada
- **Escalabilidade**: Como o desempenho se comporta com instâncias maiores

## Executando os Testes

### Via linha de comando (Maven)

Para executar todos os testes unitários:

```
mvn test
```

Para executar um teste específico:

```
mvn test -Dtest=NomeDoTeste
```

Exemplo:

```
mvn test -Dtest=BacktrackingSolverTest
```

### Via IntelliJ IDEA

#### Configuração inicial (faça isso uma vez)

1. Abra o projeto no IntelliJ IDEA
2. Acesse **View → Tool Windows → Maven** para abrir o painel do Maven
3. Na árvore do Maven, expanda o projeto e vá até **Lifecycle**
4. Execute **clean** e em seguida **install** para compilar e instalar todas as dependências

Se não houver erros, o projeto estará pronto para ser executado.

#### Executando o projeto e os testes

Com o projeto compilado, qualquer classe pode ser executada diretamente pelo IntelliJ IDEA — seja uma classe principal do projeto ou uma classe de teste:

- Clique no ícone de **Run** (▶) ao lado de qualquer classe ou método para executá-lo
- Isso funciona tanto para rodar o projeto em si (ex: `ExperimentRunner`) quanto para executar testes individuais ou suítes completas

## Licença

Este projeto está licenciado sob a Licença MIT - veja o arquivo [LICENSE](LICENSE) para detalhes.

Copyright (c) 2026 Maria Eduarda da Nóbrega e Adrielly Carla Ferreira de Melo.

## Equipe de desenvolvimento

Maria Eduarda da Nóbrega e Adrielly Carla Ferreira de Melo.
