/**
 * Este pacote contém a implementação do padrão de projeto Estrutural (Structural) Decorator.
 * <p>
 * O padrão Decorator permite adicionar novos comportamentos a objetos dinamicamente,
 * envolvendo-os em objetos especiais de "decoração" que possuem a mesma interface.
 * </p>
 * <p>
 * A estrutura implementada é a seguinte:
 * </p>
 * <ul>
 *   <li><b>Component ({@link decorator.Cafe})</b>: Define a interface para os objetos que podem ter responsabilidades adicionadas.</li>
 *   <li><b>ConcreteComponent ({@link decorator.CafeSimples})</b>: É o objeto original ao qual funcionalidades extras serão adicionadas.</li>
 *   <li><b>Decorator ({@link decorator.CafeDecorator})</b>: Mantém uma referência para um objeto Component e define uma interface que se conforma com a interface do Component.</li>
 *   <li><b>ConcreteDecorator ({@link decorator.ComAcucar}, {@link decorator.ComLeite})</b>: Adiciona responsabilidades ao componente.</li>
 * </ul>
 * <p>
 * A classe {@link decorator.App} demonstra como usar o padrão para criar diferentes combinações de café de forma dinâmica.
 * </p>
 */
package decorator;