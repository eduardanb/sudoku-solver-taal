# Sudoku Solver – Análise de Estratégias de Busca

## Descrição do Projeto

Este projeto tem como objetivo implementar e comparar diferentes estratégias algorítmicas para a resolução do problema do Sudoku. O foco principal é analisar o comportamento de técnicas clássicas de projeto de algoritmos aplicadas a um problema de busca em espaço de estados.

As estratégias implementadas são:

* Backtracking
* Branch and Bound
* Programação Dinâmica
* Estratégia Gulosa

O sistema executa os algoritmos sobre instâncias de Sudoku fornecidas por arquivos de entrada e coleta métricas que permitem comparar formalmente o desempenho de cada abordagem.

---

## Objetivos

O projeto busca:

* Implementar diferentes técnicas de resolução para o Sudoku
* Comparar as estratégias em termos de eficiência e qualidade da solução
* Analisar o comportamento teórico e experimental dos algoritmos
* Avaliar o impacto de heurísticas na redução do espaço de busca

---

## Estrutura do Projeto

O projeto segue a estrutura padrão de projetos Java com Maven.

```
src
 ├── main
 │   ├── java
 │   │   └── br.edu.sudoku
 │   │       ├── experiment
 │   │       │   └── ExperimentRunner.java
 │   │       │
 │   │       ├── heuristics
 │   │       │   └── VariableOrderingHeuristic.java
 │   │       │
 │   │       ├── io
 │   │       │   ├── SudokuReader.java
 │   │       │   └── SudokuWriter.java
 │   │       │
 │   │       ├── metrics
 │   │       │   └── Metrics.java
 │   │       │
 │   │       ├── model
 │   │       │   └── SudokuBoard.java
 │   │       │
 │   │       ├── solver
 │   │       │   ├── SudokuSolver.java
 │   │       │   ├── BacktrackingSolver.java
 │   │       │   ├── BranchAndBoundSolver.java
 │   │       │   ├── DynamicProgrammingSolver.java
 │   │       │   └── GreedySolver.java
 │   │       │
 │   │       └── utils
 │   │           └── SudokuValidator.java
 │   │
 │   └── resources
 │       └── sudokus
 │           ├── sudoku1.txt
 │           └── sudoku2.txt
 │
 └── test
     ├── java
     │   └── br.edu.sudoku
     │       ├── BacktrackingSolverTest.java
     │       ├── BranchAndBoundSolverTest.java
     │       ├── SolverComparisonTest.java
     │       └── SudokuValidatorTest.java
     │
     └── resources
         └── sudokus
             └── testSudoku.txt
```

---

## Descrição dos Componentes

### Model

Representação do tabuleiro do Sudoku e suas operações básicas.

* `SudokuBoard`: estrutura que representa o estado do tabuleiro.

---

### Solver

Implementações dos algoritmos responsáveis por resolver o Sudoku.

* `SudokuSolver`: interface comum para todos os algoritmos.
* `BacktrackingSolver`: resolução baseada em busca recursiva com retrocesso.
* `BranchAndBoundSolver`: busca com poda de ramos inviáveis.
* `DynamicProgrammingSolver`: tentativa de reutilização de soluções parciais.
* `GreedySolver`: abordagem baseada em escolhas locais.

---

### Heuristics

Heurísticas utilizadas para melhorar a eficiência da busca.

* `VariableOrderingHeuristic`: define a ordem de escolha das variáveis (células).

---

### IO

Responsável pela leitura e escrita de arquivos.

* `SudokuReader`: leitura de instâncias de Sudoku.
* `SudokuWriter`: escrita das soluções e resultados.

---

### Metrics

Coleta e armazenamento de métricas de desempenho dos algoritmos.

Exemplos de métricas:

* tempo de execução
* número de nós visitados
* número de podas
* uso de memória

---

### Experiment

Coordena a execução dos experimentos.

* `ExperimentRunner`: executa os algoritmos sobre os mesmos problemas e registra os resultados.

---

### Utils

Funções auxiliares utilizadas pelos algoritmos.

* `SudokuValidator`: valida se um número pode ser inserido em determinada posição do tabuleiro.

---

### Test

Conjunto de testes unitários utilizados para verificar a corretude das implementações.

* testes de algoritmos
* testes de validação do Sudoku
* testes de comparação entre solvers

---

## Formato do Arquivo de Entrada

O arquivo de entrada contém uma matriz 9x9 representando o Sudoku.

* números de **1 a 9** representam valores já preenchidos
* o valor **0** representa uma célula vazia

Exemplo:

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

---

## Métricas de Avaliação

Os algoritmos são comparados utilizando as seguintes métricas:

* Complexidade assintótica teórica
* Tempo de execução (análise empírica)
* Uso de memória
* Número de nós visitados
* Número de podas realizadas
* Qualidade da solução (ótima ou aproximada)
* Escalabilidade

---

## Execução do Projeto

O projeto utiliza Maven.

Para compilar:

```
mvn compile
```

Para executar os testes:

```
mvn test
```

A execução dos experimentos é feita pela classe:

```
ExperimentRunner
```

---

## Conclusão

Este projeto permite estudar e comparar diferentes paradigmas de projeto de algoritmos aplicados a um problema clássico de busca combinatória. A análise experimental permite observar, na prática, como diferentes estratégias influenciam a eficiência da exploração do espaço de soluções.
